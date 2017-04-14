package apublic;

//���� ios �Ĺ��ú���

import android.os.Bundle;

import android.os.Looper;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.AbsoluteLayout;
import android.widget.Button;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.*; //���亯����Ҫ��
//import aui.*;


public class Functions 
{
	//�������������ļ���������������ķ��,������ú���,��ÿ�� Activity ��Ҫ��������
	//--------------------------------------------------
	//<!-- Application theme. -->
	//<style name="AppTheme" parent="AppBaseTheme">
	//    <!-- All customizations that are NOT specific to a particular API-level can go here. -->
	//    <!-- ����״̬�� -->
	//    <item name="android:windowFullscreen">true</item>
	//    <!-- ���ر����� -->
	//    <item name="android:windowNoTitle">true</item>
	//</style>	
	//--------------------------------------------------
	static public void SetFullScreen(Activity _this)
	{
		//��Activity��onCreate()�����е�super()��setContentView()��������֮�����
		//--------------------------------------------------

		//���ر�����
		_this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//����״̬��
		_this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
	}//
	
	//ȡ����Ķ�ջ����ϸ��Ϣ
	static public String GetErrorFullMessage(Exception e)
	{
		 StringWriter sw = new StringWriter(); 
         PrintWriter pw = new PrintWriter(sw); 
         e.printStackTrace(pw); 
         
         return sw.toString();
		
	}//
	
	//�Ƿ����߳���
	static public boolean isMainThread() 
	{
	    return Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId();
	}//
	
	//��ʾһ����Ϣ��
	static public Activity _last;
	static public void ShowMessage(String s, Activity  ct)
	{
		if (false == Functions.isMainThread()) return;
		
		if (ct == null) ct = _last; //��ʱ���,����һ��Ҫ�и� Activity
		
		new AlertDialog.Builder(ct)//self)    
		.setTitle("��Ϣ")  
		.setMessage(s)  
		.setPositiveButton("ȷ��", null)  
		.show(); 
		
	}//
	
	//����һ����ť[��ʵ�� view]��  onClick �¼�,���Ծ�˵�� xml �������Դ��,��Ϊjava����ֱ�ӽ�������Ϊ��������[ֻ������],������ԭ��Ӧ����
	//������Ʒ������
	//̫����,��ʵ�Լ��õĻ�����д�ü򵥵�
	static public void SetOnClick(View _view, String funcName, Activity _activity)
	{
		//��˵���� View ���Դ��
		final String handlerName = funcName;
		final View view = _view; //������һ�� final �����͵����������������ȥ����,��ͨ�����ǲ��е�
		final Activity activity = _activity;
		
		//view.getContext().getClass().getMethod("");

    	//view.setOnClickListener(new OnClickListener() {
		//view.setOnClickListener(new Button.OnClickListener() {
        view.setOnClickListener(new View.OnClickListener() {
        	
            private Method mHandler;
  
            public void onClick(View v) 
            {
                    try 
                    {
                        //mHandler = view.getContext().getClass().getMethod(handlerName, View.class);
                        ////mHandler = activity.getClass().getMethod(handlerName, View.class);
                        mHandler = activity.getClass().getMethod(handlerName);
                        
                        //����Ǹ��ݷ�������,�Լ�������������������ȡָ���ķ���//�ô����ǲ��е�,Ҫ���ݺ���ԭ���Ĳ����б���ȡ
                    } 
                    catch (NoSuchMethodException e) //û�ҵ�������Ƶĺ����Ļ�
                    {
                    	Functions.ShowMessage(handlerName + "����������(ע���������).", activity);
                    	return;
                    	
                    	//���ǲ�Ҫ�׳��쳣�ĺ�,��ĳЩģ�����ϻ�ֱ�ӱ���
//                        throw new IllegalStateException("Could not find a method " +
//                                handlerName + "(View) in the activity "
//                                + activity.getClass() + " for onClick handler"
//                                + " on view " + activity.getClass().getName(), e);
                    }//



                    try 
                    {
                        //mHandler.invoke(getContext(), View.this);
                    	////mHandler.invoke(view.getContext(), view);
                    	mHandler.invoke(activity); //invoke �Ĳ���Ҳ����Ե���,����Ҫ�ഫ��һ�� this
                    } 
                    catch (IllegalAccessException e) 
                    {
                    	Functions.ShowMessage(handlerName + "����ִ���쳣(ע���������).", activity);
                    	return;
                    	
                    	//���ǲ�Ҫ�׳��쳣�ĺ�,��ĳЩģ�����ϻ�ֱ�ӱ���                    	
//                        throw new IllegalStateException("Could not execute non "
//                                + "public method of the activity", e);

                    } 
                    catch (InvocationTargetException e) 
                    {
                    	Functions.ShowMessage(handlerName + "����ִ���쳣(ע���������).", activity);
                    	return;                    	
//                        throw new IllegalStateException("Could not execute "
//                                + "method of the activity", e);

                    }
                    catch (Exception e) 
                    {
                    	Functions.ShowMessage(handlerName + "����ִ���쳣(ע���������)." + e.getMessage(), activity);
                    	return;
                    }                    
                    //try

                }//public 

            });//setOnClickListener

		
	}//
	
	//����λ��,���� ios
	static public void SetControlFrame(View view, int x, int y, int width, int height)
	{
		AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(
			        width,  //����� ViewGroup.LayoutParams.WRAP_CONTENT �Ļ��������ݶ��پ��Ƕ���
			        height,
			        x,y);
		
		view.setLayoutParams(lp);
		
		
	}//
	
	//�ƶ��ؼ�//����
	static public void MoveControl_left(View view, int left_off) 
	{
		
    	
    	//1�������ؼ�������ȡ����
    	//ViewGroup.LayoutParams lp; //ȷʵ����ͨ��������Ͷ�̬�޸�  AbsoluteLayout ����µĿؼ���Сλ��
    	//2����ȡ�ؼ������� 
    	//lp = (AbsoluteLayout.LayoutParams)tmp.getLayoutParams();
    	//lp = tmp.getLayoutParams();
    	//3�����ÿؼ���������߶ȡ�
    	//lp.height -= 10;
    	//4:��ʹ������Ч���ؼ�
    	//tmp.setLayoutParams(lp);
    	
    	//--------------------------------------------------
    	
    	//1�������ؼ�������ȡ����
    	AbsoluteLayout.LayoutParams lp2; //Ҫ�����ƵĻ���Ҫ��  AbsoluteLayout ,������ͨ�ò���
    	//2����ȡ�ؼ������� 
    	lp2 = (AbsoluteLayout.LayoutParams)view.getLayoutParams();
    	//3�����ÿؼ���������߶ȡ�
    	//lp2.x -= 10;
    	lp2.x -= left_off;
    	//4:��ʹ������Ч���ؼ�
    	view.setLayoutParams(lp2);
	    	
	    	
	    	
	}//
	
	//activity setContentView() ��,���� view �Ĵ�С�ƺ�һֱȡ����,�ƺ���ֻ��ȡ  activity �Լ���
	public static Rect GetRootViewRect(Activity _this)
	{
//		 android activity���ڵĴ�С���ƶ�activity
//
//		 Activity�Ĵ��ڴ�С���䱾�ʻ���ͨ��windowManager��������WindowManger������Ϊһ��window�����ƴ�С
//
//		           WindowManager m = getWindowManager();
//		         Display d = m.getDefaultDisplay();
//		         android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
//		         p.height = (int)(d.getHeight() *0.8);
//		         p.width = (int)(d.getWidth()*0.7);
//		         p.dimAmount = 0.0f;
//		         getWindow().setAttributes(p);
		
		

		WindowManager m = _this.getWindowManager();
		Display d = m.getDefaultDisplay();
		android.view.WindowManager.LayoutParams p = _this.getWindow().getAttributes();
		//p.height = (int)(d.getHeight() *0.8);
		//p.width = (int)(d.getWidth()*0.7);
		//p.dimAmount = 0.0f;
		//_this.getWindow().setAttributes(p);
		
		Rect rect = new Rect();
		
		//rect.right = d.getWidth();
		
		DisplayMetrics packageMetrics = _this.getResources().getDisplayMetrics();

		int w = packageMetrics.widthPixels;

		int h = packageMetrics.heightPixels;
		
		rect = new Rect(0, 0, w, h);
		
		return rect;
	}//
	
	
	//���� ios//oncreate ��ȡ������,��������
	public static Rect GetControlFrame(View view) 
	{
		//view.getLeft()
		
		Rect r = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
		
		//view.getMeasuredWidth()
		
		//ֱ��ȡ�ǲ��Ե�,��Ϊ�ؼ����ܻ�û�����������,���� oncreate �¼�
		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
		int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		//r = new Rect(view.getLeft(), view.getTop(), view.getLeft()+view.getMeasuredWidth(), view.getRight()+view.getMeasuredHeight());
		r = new Rect(view.getLeft(), view.getTop(), view.getLeft()+w, view.getRight()+h);
		
		return r;
	}//
	
	
	//��һ���ӿؼ�//�� android ��ʵ�����Ǽӵ� Layout �ؼ��е�,Ȼ�� Layout �ټ��뵽���ؼ���
	static public void AddChildControl(ViewGroup parent, View child)
	{
		//ViewGroup.LayoutParams lp = child.getLayoutParams();
		
		parent.addView(child);
		
	}//
	
	static public void AddChildControl(View parent, View child)
	{
		//View.class.is
	    ViewGroup vg = (ViewGroup)parent; //Ŀǰ��˵����ֱ�Ӽ�,������һ����  layout
	    Functions.AddChildControl(vg, child);

		
	}//
	
	//��д��ʽ//CGColor
	static public int Color(String hex)
	{
		int r = android.graphics.Color.parseColor(hex); //����  panel.setBackgroundColor(android.graphics.Color.parseColor("#F5F5DC"));
		
		return r;
	}//
	
	static public int screenWidth = 0;
	
    //������Ļ��Ȼ��� dp Ϊ  px//ֻ�ǰ��ֻ���ȱ�׼Ϊ 320 ����,���ǹٷ��㷨//Ч����ͬ�� ios ��ֱ������
    static public double dp2px(int dp, Activity activity) 
    {
    	int w = 320;
    	
    	//��Ļ��ȿ��ܼ���Ƚ��鷳,������һ�ξͺ�
    	if (Functions.screenWidth == 0)
    	{
    		w = Functions.getScreenWidth(activity);
    		Functions.screenWidth = w;
    	}
    	else w = screenWidth;
    	
    	//--------------------------------------------------

    	
        double px = (dp * w + 0.000001)/320; //����С��,����һ�μ��� 
		
    	return px;
	}//    
    
    //�������ĺ���
    static public int getScreenWidth(Activity activity)
    {
    	int w = 320;
    	
    	//w = ((Activity)this.getContext()).getWindowManager().getDefaultDisplay().getWidth();
    	
    	//Activity activity = (Activity)this.getContext();

        DisplayMetrics metric = new DisplayMetrics();  //import android.util.DisplayMetrics; 
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);  
        int width = metric.widthPixels;     // ��Ļ��ȣ����أ�  
        int height = metric.heightPixels;   // ��Ļ�߶ȣ����أ�  
        float density = metric.density;      // ��Ļ�ܶȣ�0.75 / 1.0 / 1.5��  
        int densityDpi = metric.densityDpi;  // ��Ļ�ܶ�DPI��120 / 160 / 240��  
        
        w = width;
        
        return w;
    	
    }//
    
    //�������ĺ���
    static public int getScreenHeight(Activity activity)
    {
    	//int w = 320;
    	
    	//w = ((Activity)this.getContext()).getWindowManager().getDefaultDisplay().getWidth();
    	
    	//Activity activity = (Activity)this.getContext();

        DisplayMetrics metric = new DisplayMetrics();  //import android.util.DisplayMetrics; 
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);  
        int width = metric.widthPixels;     // ��Ļ��ȣ����أ�  
        int height = metric.heightPixels;   // ��Ļ�߶ȣ����أ�  
        float density = metric.density;      // ��Ļ�ܶȣ�0.75 / 1.0 / 1.5��  
        int densityDpi = metric.densityDpi;  // ��Ļ�ܶ�DPI��120 / 160 / 240��  
        
        //w = width;
        
        return height;
    	
    }//    
    
    
	

	

}//class

