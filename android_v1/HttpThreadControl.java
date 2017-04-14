package com.sister;

import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

//--------------------------------------------------
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//--------------------------------------------------
//定义接口//必须在自己的文件中//其实就是类似于 delphi 的函数指针类型定义
//public interface OnClickListener {
//　　public void OnClick(Button b);
//}
//--------------------------------------------------

//http 访问线程类
public class HttpThreadControl {
	
	public HttpThreadControlEvent httpEvent;
	
	//public void setOnClickListener(HttpThreadControlEvent listener)
	public void setEvent(HttpThreadControlEvent listener)
	{
		this.httpEvent = listener;
	}//
	
	//执行 post  
	public void Post(String AUrl, String kvStr)
	{
		this.url = AUrl;
		this.data = kvStr;
		//this.httpEvent.OnHttpData(this);
		
        ProgressBarAsyncTask asyncTask = new ProgressBarAsyncTask(this);  
        asyncTask.execute(1000); 
		
	}//
	
	public String url;
	public String data;
	public String response;
	
	public void Execute()
	{
		//String url = "";
		//String data = "";
		
		response = Functions.HttpsPost(url, data); 

	}//
	

}//

//--------------------------------------------------
class ProgressBarAsyncTask extends AsyncTask<Integer, Integer, String> 
{  
	private HttpThreadControl httpThread;
	     
    public ProgressBarAsyncTask(HttpThreadControl httpThread) {  
        super();  
        //this.textView = textView;  
        this.httpThread = httpThread;

    }  
  
  
    /**  
     * 这里的Integer参数对应AsyncTask中的第一个参数   
     * 这里的String返回值对应AsyncTask的第三个参数  
     * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改  
     * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作  
     */  
    @Override  
    protected String doInBackground(Integer... params) 
    {  
        //NetOperator netOperator = new NetOperator();  
        int i = 0;  
        for (i = 10; i <= 100; i+=10) {  
            //netOperator.operator();  
            publishProgress(i);  
        }  
        
        //--------------------------------------------------

        try 
        {  
            //休眠1秒  
            Thread.sleep(1000); 
            //httpThread.post
            
            //Functions.HttpsPost(url, data);
            httpThread.Execute();
        } 
        catch (InterruptedException e) 
        {  
            e.printStackTrace();  
        }  
        //--------------------------------------------------
        
        
        return i + params[0].intValue() + "";  
    }  
  
  
    /**  
     * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）  
     * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置  
     */  
    @Override  
    protected void onPostExecute(String result) 
    {  
        //textView.setText("异步操作执行结束" + result);  
    	httpThread.httpEvent.OnHttpData(httpThread);
    }  
  
  
    //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置  
    @Override  
    protected void onPreExecute() 
    {  
        //textView.setText("开始执行异步线程");  
    }  
  
  
    /**  
     * 这里的Intege参数对应AsyncTask中的第二个参数  
     * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行  
     * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作  
     */  
    @Override  
    protected void onProgressUpdate(Integer... values) 
    {  
        //int vlaue = values[0];  
        //progressBar.setProgress(vlaue);  
    }  
  
    
  
}//

//--------------------------------------------------

//异步任务,异步线程类
class PageTask extends AsyncTask<String, Integer, String> {
    // 可变长的输入参数，与AsyncTask.exucute()对应
    ProgressDialog pdialog;
    public PageTask(Context context){
        pdialog = new ProgressDialog(context, 0);   
        pdialog.setButton("cancel", new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int i) {
          dialog.cancel();
         }
        });
        pdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
         public void onCancel(DialogInterface dialog) 
         {
          //finish();
         }
        });
        pdialog.setCancelable(true);
        pdialog.setMax(100);
        pdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pdialog.show();


    }
    @Override
    protected String doInBackground(String... params) {

        try{

           HttpClient client = new DefaultHttpClient();
           // params[0]代表连接的url
           HttpGet get = new HttpGet(params[0]);
           HttpResponse response = client.execute(get);
           HttpEntity entity = response.getEntity();
           long length = entity.getContentLength();
           InputStream is = entity.getContent();
           String s = null;
           if(is != null) {
               ByteArrayOutputStream baos = new ByteArrayOutputStream();

               byte[] buf = new byte[128];

               int ch = -1;

               int count = 0;

               while((ch = is.read(buf)) != -1) {

                  baos.write(buf, 0, ch);

                  count += ch;

                  if(length > 0) {
                      // 如果知道响应的长度，调用publishProgress（）更新进度
                      publishProgress((int) ((count / (float) length) * 100));
                  }

                  // 让线程休眠100ms
                  Thread.sleep(100);
               }
               s = new String(baos.toByteArray());              }
           // 返回结果
           return s;
        } catch(Exception e) {
           e.printStackTrace();

        }

        return null;

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onPostExecute(String result) {
        // 返回HTML页面的内容
        //message.setText(result);
        pdialog.dismiss(); 
    }

    @Override
    protected void onPreExecute() {
        // 任务启动，可以在这里显示一个对话框，这里简单处理
        //message.setText(R.string.task_started);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // 更新进度
          System.out.println(""+values[0]);
          //message.setText(""+values[0]);
          pdialog.setProgress(values[0]);
    }

 }

//


