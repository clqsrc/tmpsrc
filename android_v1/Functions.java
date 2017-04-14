package com.sister;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Base64;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class Functions 
{
	//-------------------------------------------------- 

    //http post方法,服务端返回的数据string类型,数据进行xml解析
    //data 为 [&] 符号分隔的键值对,如  "name=abc&pass=123"//并且其等号右边的数值需加  URLEncoder.encode(data, "GBK") 转换
    //public static String HttpsPost(String httpsurl, String data, Activity  ct) 
    public static String HttpsPost(String httpsurl, String data) 
    {
        String result = "";//null; 为 null 会有很多问题
        HttpURLConnection http = null;//据说 HttpURLConnection 比 httpclient 好一点
        URL url;
        try {
            url = new URL(httpsurl);
            // 判断是http请求还是https请求
            if (url.getProtocol().toLowerCase().equals("https")) 
            {
//                trustAllHosts();
//                http = (HttpsURLConnection) url.openConnection();
//                //javax.net.ssl.HostnameVerifier
//                ((HttpsURLConnection) http).setHostnameVerifier(DO_NOT_VERIFY);// 不进行主机名确认
            } 
            else 
            {
                http = (HttpURLConnection) url.openConnection();
            }

            int CONNENT_TIMEOUT = 20000;//15000;

            http.setConnectTimeout(CONNENT_TIMEOUT);// 设置超时时间            http.setReadTimeout(READ_TIMEOUT);
            if (data == null) 
            {
                http.setRequestMethod("GET");// 设置请求类型
                http.setDoInput(true);
                // http.setRequestProperty("Content-Type", "text/xml");
//                if (AppSession.mCookieStore != null)
//                    http.setRequestProperty("Cookie", AppSession.mCookieStore);
            } 
            else 
            {
                http.setRequestMethod("POST");// 设置请求类型为post
                http.setDoInput(true);
                http.setDoOutput(true);
                // http.setRequestProperty("Content-Type", "text/xml");
//                if (AppSession.mCookieStore != null
//                        && AppSession.mCookieStore.trim().length() > 0)
//                    http.setRequestProperty("Cookie", AppSession.mCookieStore);

                //DataOutputStream out = new DataOutputStream(http.getOutputStream());
                
                //out.writeBytes(data);
                //--------------------------------------------------
                //byte[] requestStringBytes = data.getBytes("UTF-8");
                ////byte[] requestStringBytes = data.getBytes("GBK");
                
                //out.writeBytes(requestStringBytes);
                ////out.write(requestStringBytes);
                //--------------------------------------------------
                //如果已经编码了,实际上不用再取字节流,因为编码后的都是英文字符
                //String data_encode = URLEncoder.encode(data, "GBK");
                
                //byte[] requestStringBytes = data_encode.getBytes("GBK");//("UTF-8");
                byte[] requestStringBytes = data.getBytes("ISO-8859-1");//("GBK");
                http.setRequestProperty("Content-length", "" + requestStringBytes.length);
                //http.setRequestProperty("Content-Type", "application/octet-stream");
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=gbk");//这个决不能写错
                //http.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
                http.setRequestProperty("Charset", "GBK");//"UTF-8");
                
                //这一句会实际连接,setRequestProperty 要在这之前设置
                DataOutputStream out = new DataOutputStream(http.getOutputStream());

                
                //out.writeBytes(data_encode);
                out.write(requestStringBytes);
                //out.writeBytes(data);
                //--------------------------------------------------
                
                out.flush();
                out.close();
            }

            // 设置http返回状态200（ok）还是403
            int httpsResponseCode = http.getResponseCode();
            BufferedReader in = null;
            if (httpsResponseCode == 200) 
            {
                //getCookie(http);
                //in = new BufferedReader(new InputStreamReader(http.getInputStream()));//这个不行的,会使用当前字符集
            	//使用 "ISO-8859-1"字符集才能 取得原始字节
                in = new BufferedReader(new InputStreamReader(http.getInputStream(), "ISO-8859-1"));
            } 
            else
            {
            	//使用 "ISO-8859-1"字符集才能 取得原始字节
                //in = new BufferedReader(new InputStreamReader(http.getErrorStream()));
            	in = new BufferedReader(new InputStreamReader(http.getErrorStream(), "ISO-8859-1"));
            }
            
            String temp = in.readLine();
            
            while (temp != null) 
            {
                if (result != null)
                    result += temp;
                else
                    result = temp;
                temp = in.readLine();
            }
            in.close();
            http.disconnect();
        } 
        catch (Exception e) 
        {
        	String err = e.getMessage();
        	//ShowMessage(err, ct);
            e.printStackTrace();
        }
        return result;
    }//
    
	//显示一个消息框
	static public void ShowMessage(String s, Activity  ct)
	{
		new AlertDialog.Builder(ct)//self)    
		.setTitle("信息")  
		.setMessage(s)  
		.setPositiveButton("确定", null)  
		.show(); 
		
	}//
    
    //--------------------------------------------------
    //目前统一使用 gbk 编码
    public static String HttpEncode(String s)
    {
    	try
    	{
	    	//URLEncoder.encode("中文1中文2", "GBK")
	    	return URLEncoder.encode(s, "GBK");
    	}
    	catch (IOException e) 
    	{   
            //e.printStackTrace();
    		return "";
        }
    	
    }//
    
    //base64解码
    public static String Base64ToStr(String s, String charsetName)
    {
    	try
    	{
    		return new String(Base64.decode(s, Base64.DEFAULT), charsetName);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return "";
    }//


	//打开一个浏览器
	//public static void OpenUrl_Out(android.view.View v)android.app.Activity
	public static void OpenUrl_Out(android.app.Activity v, String url)
	{
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        //Uri content_url = Uri.parse("http://www.163.com");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        //startActivity(intent);
        v.startActivity(intent);

		
	}//


}//
