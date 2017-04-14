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
//����ӿ�//�������Լ����ļ���//��ʵ���������� delphi �ĺ���ָ�����Ͷ���
//public interface OnClickListener {
//����public void OnClick(Button b);
//}
//--------------------------------------------------

//http �����߳���
public class HttpThreadControl {
	
	public HttpThreadControlEvent httpEvent;
	
	//public void setOnClickListener(HttpThreadControlEvent listener)
	public void setEvent(HttpThreadControlEvent listener)
	{
		this.httpEvent = listener;
	}//
	
	//ִ�� post  
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
     * �����Integer������ӦAsyncTask�еĵ�һ������   
     * �����String����ֵ��ӦAsyncTask�ĵ���������  
     * �÷�������������UI�̵߳��У���Ҫ�����첽�����������ڸ÷����в��ܶ�UI���еĿռ�������ú��޸�  
     * ���ǿ��Ե���publishProgress��������onProgressUpdate��UI���в���  
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
            //����1��  
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
     * �����String������ӦAsyncTask�еĵ�����������Ҳ���ǽ���doInBackground�ķ���ֵ��  
     * ��doInBackground����ִ�н���֮�������У�����������UI�̵߳��� ���Զ�UI�ռ��������  
     */  
    @Override  
    protected void onPostExecute(String result) 
    {  
        //textView.setText("�첽����ִ�н���" + result);  
    	httpThread.httpEvent.OnHttpData(httpThread);
    }  
  
  
    //�÷���������UI�̵߳���,����������UI�̵߳��� ���Զ�UI�ռ��������  
    @Override  
    protected void onPreExecute() 
    {  
        //textView.setText("��ʼִ���첽�߳�");  
    }  
  
  
    /**  
     * �����Intege������ӦAsyncTask�еĵڶ�������  
     * ��doInBackground�������У���ÿ�ε���publishProgress�������ᴥ��onProgressUpdateִ��  
     * onProgressUpdate����UI�߳���ִ�У����п��Զ�UI�ռ���в���  
     */  
    @Override  
    protected void onProgressUpdate(Integer... values) 
    {  
        //int vlaue = values[0];  
        //progressBar.setProgress(vlaue);  
    }  
  
    
  
}//

//--------------------------------------------------

//�첽����,�첽�߳���
class PageTask extends AsyncTask<String, Integer, String> {
    // �ɱ䳤�������������AsyncTask.exucute()��Ӧ
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
           // params[0]�������ӵ�url
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
                      // ���֪����Ӧ�ĳ��ȣ�����publishProgress�������½���
                      publishProgress((int) ((count / (float) length) * 100));
                  }

                  // ���߳�����100ms
                  Thread.sleep(100);
               }
               s = new String(baos.toByteArray());              }
           // ���ؽ��
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
        // ����HTMLҳ�������
        //message.setText(result);
        pdialog.dismiss(); 
    }

    @Override
    protected void onPreExecute() {
        // ����������������������ʾһ���Ի�������򵥴���
        //message.setText(R.string.task_started);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // ���½���
          System.out.println(""+values[0]);
          //message.setText(""+values[0]);
          pdialog.setProgress(values[0]);
    }

 }

//


