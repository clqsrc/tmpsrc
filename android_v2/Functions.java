package apublic;

//类似 ios 的公用函数

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
import java.lang.reflect.*; //反射函数需要的
//import aui.*;


public class Functions 
{
	//还可以在配置文件中设置整个程序的风格,如果是用函数,则每个 Activity 都要这样调用
	//--------------------------------------------------
	//<!-- Application theme. -->
	//<style name="AppTheme" parent="AppBaseTheme">
	//    <!-- All customizations that are NOT specific to a particular API-level can go here. -->
	//    <!-- 隐藏状态栏 -->
	//    <item name="android:windowFullscreen">true</item>
	//    <!-- 隐藏标题栏 -->
	//    <item name="android:windowNoTitle">true</item>
	//</style>	
	//--------------------------------------------------
	static public void SetFullScreen(Activity _this)
	{
		//在Activity的onCreate()方法中的super()和setContentView()两个方法之间加入
		//--------------------------------------------------

		//隐藏标题栏
		_this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//隐藏状态栏
		_this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
	}//
	
	//取错误的堆栈等详细信息
	static public String GetErrorFullMessage(Exception e)
	{
		 StringWriter sw = new StringWriter(); 
         PrintWriter pw = new PrintWriter(sw); 
         e.printStackTrace(pw); 
         
         return sw.toString();
		
	}//
	
	//是否主线程中
	static public boolean isMainThread() 
	{
	    return Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId();
	}//
	
	//显示一个消息框
	static public Activity _last;
	static public void ShowMessage(String s, Activity  ct)
	{
		if (false == Functions.isMainThread()) return;
		
		if (ct == null) ct = _last; //暂时如此,好象一定要有个 Activity
		
		new AlertDialog.Builder(ct)//self)    
		.setTitle("信息")  
		.setMessage(s)  
		.setPositiveButton("确定", null)  
		.show(); 
		
	}//
	
	//设置一个按钮[其实是 view]的  onClick 事件,来自据说是 xml 设计器的源码,因为java不能直接将函数作为参数传递[只能用类],所以其原理应该是
	//类的名称反射查找
	//太复杂,其实自己用的话可以写得简单点
	static public void SetOnClick(View _view, String funcName, Activity _activity)
	{
		//据说来自 View 类的源码
		final String handlerName = funcName;
		final View view = _view; //必须用一个 final 参数送到后面的匿名函数中去才行,普通变量是不行的
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
                        
                        //这个是根据方法名称,以及方法参数的类型来获取指定的方法//用错了是不行的,要根据函数原来的参数列表来取
                    } 
                    catch (NoSuchMethodException e) //没找到这个名称的函数的话
                    {
                    	Functions.ShowMessage(handlerName + "函数不存在(注意参数类型).", activity);
                    	return;
                    	
                    	//还是不要抛出异常的好,在某些模拟器上会直接崩溃
//                        throw new IllegalStateException("Could not find a method " +
//                                handlerName + "(View) in the activity "
//                                + activity.getClass() + " for onClick handler"
//                                + " on view " + activity.getClass().getName(), e);
                    }//



                    try 
                    {
                        //mHandler.invoke(getContext(), View.this);
                    	////mHandler.invoke(view.getContext(), view);
                    	mHandler.invoke(activity); //invoke 的参数也必须对得上,并且要多传递一个 this
                    } 
                    catch (IllegalAccessException e) 
                    {
                    	Functions.ShowMessage(handlerName + "函数执行异常(注意参数类型).", activity);
                    	return;
                    	
                    	//还是不要抛出异常的好,在某些模拟器上会直接崩溃                    	
//                        throw new IllegalStateException("Could not execute non "
//                                + "public method of the activity", e);

                    } 
                    catch (InvocationTargetException e) 
                    {
                    	Functions.ShowMessage(handlerName + "函数执行异常(注意参数类型).", activity);
                    	return;                    	
//                        throw new IllegalStateException("Could not execute "
//                                + "method of the activity", e);

                    }
                    catch (Exception e) 
                    {
                    	Functions.ShowMessage(handlerName + "函数执行异常(注意参数类型)." + e.getMessage(), activity);
                    	return;
                    }                    
                    //try

                }//public 

            });//setOnClickListener

		
	}//
	
	//设置位置,类似 ios
	static public void SetControlFrame(View view, int x, int y, int width, int height)
	{
		AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(
			        width,  //如果是 ViewGroup.LayoutParams.WRAP_CONTENT 的话就是内容多少就是多少
			        height,
			        x,y);
		
		view.setLayoutParams(lp);
		
		
	}//
	
	//移动控件//向左
	static public void MoveControl_left(View view, int left_off) 
	{
		
    	
    	//1、声明控件参数获取对象
    	//ViewGroup.LayoutParams lp; //确实可以通过这个类型动态修改  AbsoluteLayout 情况下的控件大小位置
    	//2、获取控件参数： 
    	//lp = (AbsoluteLayout.LayoutParams)tmp.getLayoutParams();
    	//lp = tmp.getLayoutParams();
    	//3、设置控件参数：如高度。
    	//lp.height -= 10;
    	//4:、使设置生效：控件
    	//tmp.setLayoutParams(lp);
    	
    	//--------------------------------------------------
    	
    	//1、声明控件参数获取对象
    	AbsoluteLayout.LayoutParams lp2; //要左右移的话就要用  AbsoluteLayout ,不能是通用参数
    	//2、获取控件参数： 
    	lp2 = (AbsoluteLayout.LayoutParams)view.getLayoutParams();
    	//3、设置控件参数：如高度。
    	//lp2.x -= 10;
    	lp2.x -= left_off;
    	//4:、使设置生效：控件
    	view.setLayoutParams(lp2);
	    	
	    	
	    	
	}//
	
	//activity setContentView() 后,里面 view 的大小似乎一直取不到,似乎是只能取  activity 自己的
	public static Rect GetRootViewRect(Activity _this)
	{
//		 android activity窗口的大小及移动activity
//
//		 Activity的窗口大小，其本质还是通过windowManager来操作，WindowManger将其作为一个window来控制大小
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
	
	
	//类似 ios//oncreate 下取不到的,还是算了
	public static Rect GetControlFrame(View view) 
	{
		//view.getLeft()
		
		Rect r = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
		
		//view.getMeasuredWidth()
		
		//直接取是不对的,因为控件可能还没创建布局完成,例如 oncreate 事件
		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
		int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		//r = new Rect(view.getLeft(), view.getTop(), view.getLeft()+view.getMeasuredWidth(), view.getRight()+view.getMeasuredHeight());
		r = new Rect(view.getLeft(), view.getTop(), view.getLeft()+w, view.getRight()+h);
		
		return r;
	}//
	
	
	//加一个子控件//在 android 里实际上是加到 Layout 控件中的,然后 Layout 再加入到父控件中
	static public void AddChildControl(ViewGroup parent, View child)
	{
		//ViewGroup.LayoutParams lp = child.getLayoutParams();
		
		parent.addView(child);
		
	}//
	
	static public void AddChildControl(View parent, View child)
	{
		//View.class.is
	    ViewGroup vg = (ViewGroup)parent; //目前来说不能直接加,除非上一级是  layout
	    Functions.AddChildControl(vg, child);

		
	}//
	
	//简写形式//CGColor
	static public int Color(String hex)
	{
		int r = android.graphics.Color.parseColor(hex); //例如  panel.setBackgroundColor(android.graphics.Color.parseColor("#F5F5DC"));
		
		return r;
	}//
	
	static public int screenWidth = 0;
	
    //根据屏幕宽度换算 dp 为  px//只是按手机宽度标准为 320 计算,不是官方算法//效果等同于 ios 的直接设置
    static public double dp2px(int dp, Activity activity) 
    {
    	int w = 320;
    	
    	//屏幕宽度可能计算比较麻烦,所以算一次就好
    	if (Functions.screenWidth == 0)
    	{
    		w = Functions.getScreenWidth(activity);
    		Functions.screenWidth = w;
    	}
    	else w = screenWidth;
    	
    	//--------------------------------------------------

    	
        double px = (dp * w + 0.000001)/320; //生成小数,方便一次计算 
		
    	return px;
	}//    
    
    //方便计算的函数
    static public int getScreenWidth(Activity activity)
    {
    	int w = 320;
    	
    	//w = ((Activity)this.getContext()).getWindowManager().getDefaultDisplay().getWidth();
    	
    	//Activity activity = (Activity)this.getContext();

        DisplayMetrics metric = new DisplayMetrics();  //import android.util.DisplayMetrics; 
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);  
        int width = metric.widthPixels;     // 屏幕宽度（像素）  
        int height = metric.heightPixels;   // 屏幕高度（像素）  
        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）  
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）  
        
        w = width;
        
        return w;
    	
    }//
    
    //方便计算的函数
    static public int getScreenHeight(Activity activity)
    {
    	//int w = 320;
    	
    	//w = ((Activity)this.getContext()).getWindowManager().getDefaultDisplay().getWidth();
    	
    	//Activity activity = (Activity)this.getContext();

        DisplayMetrics metric = new DisplayMetrics();  //import android.util.DisplayMetrics; 
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);  
        int width = metric.widthPixels;     // 屏幕宽度（像素）  
        int height = metric.heightPixels;   // 屏幕高度（像素）  
        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）  
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）  
        
        //w = width;
        
        return height;
    	
    }//    
    
    
	

	

}//class

