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

    //http post����,����˷��ص�����string����,���ݽ���xml����
    //data Ϊ [&] ���ŷָ��ļ�ֵ��,��  "name=abc&pass=123"//������Ⱥ��ұߵ���ֵ���  URLEncoder.encode(data, "GBK") ת��
    //public static String HttpsPost(String httpsurl, String data, Activity  ct) 
    public static String HttpsPost(String httpsurl, String data) 
    {
        String result = "";//null; Ϊ null ���кܶ�����
        HttpURLConnection http = null;//��˵ HttpURLConnection �� httpclient ��һ��
        URL url;
        try {
            url = new URL(httpsurl);
            // �ж���http������https����
            if (url.getProtocol().toLowerCase().equals("https")) 
            {
//                trustAllHosts();
//                http = (HttpsURLConnection) url.openConnection();
//                //javax.net.ssl.HostnameVerifier
//                ((HttpsURLConnection) http).setHostnameVerifier(DO_NOT_VERIFY);// ������������ȷ��
            } 
            else 
            {
                http = (HttpURLConnection) url.openConnection();
            }

            int CONNENT_TIMEOUT = 20000;//15000;

            http.setConnectTimeout(CONNENT_TIMEOUT);// ���ó�ʱʱ��            http.setReadTimeout(READ_TIMEOUT);
            if (data == null) 
            {
                http.setRequestMethod("GET");// ������������
                http.setDoInput(true);
                // http.setRequestProperty("Content-Type", "text/xml");
//                if (AppSession.mCookieStore != null)
//                    http.setRequestProperty("Cookie", AppSession.mCookieStore);
            } 
            else 
            {
                http.setRequestMethod("POST");// ������������Ϊpost
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
                //����Ѿ�������,ʵ���ϲ�����ȡ�ֽ���,��Ϊ�����Ķ���Ӣ���ַ�
                //String data_encode = URLEncoder.encode(data, "GBK");
                
                //byte[] requestStringBytes = data_encode.getBytes("GBK");//("UTF-8");
                byte[] requestStringBytes = data.getBytes("ISO-8859-1");//("GBK");
                http.setRequestProperty("Content-length", "" + requestStringBytes.length);
                //http.setRequestProperty("Content-Type", "application/octet-stream");
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=gbk");//���������д��
                //http.setRequestProperty("Connection", "Keep-Alive");// ά�ֳ�����
                http.setRequestProperty("Charset", "GBK");//"UTF-8");
                
                //��һ���ʵ������,setRequestProperty Ҫ����֮ǰ����
                DataOutputStream out = new DataOutputStream(http.getOutputStream());

                
                //out.writeBytes(data_encode);
                out.write(requestStringBytes);
                //out.writeBytes(data);
                //--------------------------------------------------
                
                out.flush();
                out.close();
            }

            // ����http����״̬200��ok������403
            int httpsResponseCode = http.getResponseCode();
            BufferedReader in = null;
            if (httpsResponseCode == 200) 
            {
                //getCookie(http);
                //in = new BufferedReader(new InputStreamReader(http.getInputStream()));//������е�,��ʹ�õ�ǰ�ַ���
            	//ʹ�� "ISO-8859-1"�ַ������� ȡ��ԭʼ�ֽ�
                in = new BufferedReader(new InputStreamReader(http.getInputStream(), "ISO-8859-1"));
            } 
            else
            {
            	//ʹ�� "ISO-8859-1"�ַ������� ȡ��ԭʼ�ֽ�
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
    
	//��ʾһ����Ϣ��
	static public void ShowMessage(String s, Activity  ct)
	{
		new AlertDialog.Builder(ct)//self)    
		.setTitle("��Ϣ")  
		.setMessage(s)  
		.setPositiveButton("ȷ��", null)  
		.show(); 
		
	}//
    
    //--------------------------------------------------
    //Ŀǰͳһʹ�� gbk ����
    public static String HttpEncode(String s)
    {
    	try
    	{
	    	//URLEncoder.encode("����1����2", "GBK")
	    	return URLEncoder.encode(s, "GBK");
    	}
    	catch (IOException e) 
    	{   
            //e.printStackTrace();
    		return "";
        }
    	
    }//
    
    //base64����
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


	//��һ�������
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
