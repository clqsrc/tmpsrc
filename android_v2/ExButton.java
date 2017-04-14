package aui;

//import Button;

import android.R.integer;
import android.R.string;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.view.MotionEvent;
import apublic.*;
import android.content.*; //for Intent
import android.graphics.*; //for color

import android.graphics.drawable.*; //for Drawable
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.DisplayMetrics;

public class ExButton extends Button 
{
	//新加背景色//默认透明色
	public int backgroundColor = Color.TRANSPARENT;
	public int backgroundColor_active = Color.LTGRAY; //激活时的背景色,一般不改
	
    //一般来说 1sp = 1dp, sp 是 google 专有的概念,据说会根据 用户设置的系统字体大小而变化,所以对于固定的界面大小和文字要用 dp ,而显示内容的的文字大小则可以换算成 sp (其实我觉得统一 dp 比较好)
	//目前按屏幕宽度为 320 缩放
	public int fontSize_dp = 14;
	
	//方便计算的变量
	private int screenWidth = 0;
	
	//按下还是抬起等的状态
	private int state = MotionEvent.ACTION_UP; //MotionEvent.ACTION_DOWN
	
	//本身的  setTextColor 是不起作用的,要用  setTextColor(ColorStateList colors),所 以这里直接简化了
	public int fontColor = Color.BLACK;
	
	//这个类一定要有这个
    public ExButton(Context context) 
    {
        super(context);
       
        //--------------------------------------------------
        
        //设置Touch事件
        setOnTouchListener(new OnTouchListener() 
        {
            @Override
            public boolean onTouch(View arg0, MotionEvent event) 
            {
                //按下改变样式
                //setColor(event.getAction());
            	//setStyle(event.getAction());
            	
            	state = event.getAction();
            	
            	postInvalidate(); //postInvalidate 和 Invalidate 都会触发 ondraw ,如果不发送这个命令的话在真机上按钮可能不会刷新
                
                //此处设置为false，防止Click事件被屏蔽
                return false;
                
            }//匿名类的按下事件处理虚函数
            
        });//匿名类
        
        //--------------------------------------------------
        
    }//
    
    
    //设置颜色背景等,根据是否按下等
    public void setStyle(int state)
    {
    	//按下
    	if (state == MotionEvent.ACTION_DOWN)
    	{
    		//this.setBackgroundColor(android.graphics.Color.parseColor("#F5F5DC"));
    		//this.setBackgroundColor(android.graphics.Color.GRAY);
    		//this.setBackgroundColor(android.graphics.Color.TRANSPARENT);
    		
    		//this.setBackgroundColor(android.graphics.Color.parseColor("#dEdEFF"));
    		this.setBackgroundColor(android.graphics.Color.parseColor("#666666"));
    	}
    	else 
    	{
    		//this.setBackgroundColor(android.graphics.Color.RED);
    		this.setBackgroundColor(this.backgroundColor);
    		//this.setBackground(createRoundCornerShapeDrawable(8, 2, 0));
    		
            float[] outerRadii = new float[8];
            float[] innerRadii = new float[8];
            for (int i = 0; i < 8; i++) {
                outerRadii[i] = 8 + 2;
                innerRadii[i] = 8;
            }
     
            ShapeDrawable sd = new ShapeDrawable(new RoundRectShape(outerRadii, new RectF(2, 2,
                    2, 2), innerRadii));
            sd.getPaint().setColor(0);
            
            //this.setBackground(sd);
            //this.setBackgroundDrawable(sd); //没用,底版本 android 不支持
		}
    	this.setBackgroundColor(android.graphics.Color.TRANSPARENT);
    	this.setBackgroundColor(android.graphics.Color.RED);
    }//
    
    /**
     * @param borderLength: 一般取较小的值，比如10以内
     * 给你要添加的view(比如TextView)设置background:
     * 没用,低版本 android 不支持
     * urView.setBackground(createRoundCornerShapeDrawable(...))
     */
    /*
    public static ShapeDrawable createRoundCornerShapeDrawable(float radius, float borderLength, int borderColor) {
        float[] outerRadii = new float[8];
        float[] innerRadii = new float[8];
        for (int i = 0; i < 8; i++) {
            outerRadii[i] = radius + borderLength;
            innerRadii[i] = radius;
        }
 
        ShapeDrawable sd = new ShapeDrawable(new RoundRectShape(outerRadii, new RectF(borderLength, borderLength,
                borderLength, borderLength), innerRadii));
        sd.getPaint().setColor(borderColor);
 
        return sd;
    }
    */
    
    protected void onDraw(Canvas canvas) 
    {
    	// TODO Auto-generated method stub
    	super.onDraw(canvas);
    	
    	//新建一只画笔，并设置为绿色属性
    	/*
    	Paint _paint = new Paint();
    	_paint.setColor(Color.GREEN);
    	//新建矩形r1
    	RectF r1 = new RectF();
    	r1.left = 50;
    	r1.right = 250;
    	r1.top = 50 ;
    	r1.bottom = 150;

    	//新建矩形r2
    	RectF r2 = new RectF();
    	r2.left = 50;
    	r2.right = 250;
    	r2.top = 200 ;
    	r2.bottom = 300;

    	//画出矩形r1
    	canvas.drawRect(r1, _paint);
    	//画出圆角矩形r2
    	_paint.setColor(Color.rgb(204, 204, 204));
    	canvas.drawRoundRect(r2, 10, 10, _paint);
    	*/
    	int bkcolor = this.backgroundColor;
    	//this.getTextColor(context, attrs, def)
    	int txtcolor = this.getCurrentTextColor();
    	this.fontColor = txtcolor;
    	
    	//模拟器上用这个逻辑也可以
    	if (state == MotionEvent.ACTION_DOWN) 
    		bkcolor = this.backgroundColor_active;
    	
    	//真机上最好是用这个逻辑
    	bkcolor = this.backgroundColor_active;
    	if (state == MotionEvent.ACTION_UP) 
    		bkcolor = this.backgroundColor;
    	
    	Paint paint = new Paint();
    	paint.setColor(Color.GREEN);
        // 去锯齿
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
        //paint.setStyle(Paint.Style.STROKE);
        //paint.setStrokeWidth(3);
        //paint.setStrokeWidth(1); //应当是边框的宽度//效果不好,还是两个 rect 重合好了

    	//新建矩形
    	RectF rect = new RectF();
    	rect.left = 0;
    	rect.right = this.getWidth();
    	rect.top = 0 ;
    	rect.bottom = this.getHeight();
    	
    	//缩放适应屏幕//应当在生成前缩放
    	//rect.right = (float)this.dp2px((int)rect.right);
    	//rect.bottom = (float)this.dp2px((int)rect.bottom);

    	//画出圆角矩形//中间两个是圆的范围,据说小于 10 比较好
    	canvas.drawRoundRect(rect, 4, 4, paint); //要用这个,前提是没有输出过背景即还是全透明的
    	
        paint.setColor(Color.GREEN);
        paint.setColor(bkcolor);
        paint.setStyle(Paint.Style.FILL); //据说 FILL_AND_STROKE 的行为奇特
    	rect.left += 1;
    	rect.right -= 1;
    	rect.top += 1;
    	rect.bottom -= 1;
        canvas.drawRoundRect(rect, 4, 4, paint); //前面的 rect 当边框,退缩 1 个像素做边框好了
    	
    	//_paint.setColor(Color.TRANSPARENT);
    	//canvas.drawRect(rect, _paint); 
        
        //--------------------------------------------------
        String text = getText().toString();
        
        paint.setColor(Color.RED);//test
        paint.setColor(this.fontColor);
//为了兼容性,还是用外部的计算结果吧//        paint.setTextSize((int)this.dp2px(this.fontSize_dp));  
        paint.setTextSize(this.getTextSize()); //似乎和上面的内部计算结果不太一样
        //paint.setTextSize(20);
        
        //canvas.drawText("test", 0, 0, paint); //这样是不出字的,据说是因为 y 坐标是指的文字底部
        //canvas.drawText("test", 0, 20, paint); 
        
        int w = vgTextWidth(paint, text);
        int h = vgTextHeight(paint, text);
        
        int left = (int)(rect.width() - w)/2;
        
        int bottom = (int)(rect.height() - h)/2; //int top;//这里是按 bottom 来画的
        bottom += h;
        
        //canvas.drawText(text, 0, 20, paint); 
        canvas.drawText(text, left, bottom, paint);
        
        //paint.measureText("test");


    }//
    
    //一般来说 1sp = 1dp, sp 是 google 专有的概念,据说会根据 用户设置的系统字体大小而变化,所以对于固定的界面大小和文字要用 dp ,而显示内容的的文字大小则可以换算成 sp (其实我觉得统一 dp 比较好)
    public void SetTextSize(int dp)
    {
    	//界面适配的时候发现Paint.setTextSize与TextView.setTextSize传入的单位不一致。
    	//Paint.setTextSize传入的单位是px,
    	//TextView.setTextSize默认传入的单位是sp。
    	
    	fontSize_dp = dp;
    	
    	
    }//
    
    //绘出的文字高度//出于性能的要求尽量只计算一次,因为都是一样的(大部分情况下)
    public int vgTextHeight(Paint paint, String s)
    {
    	Rect bounds = new Rect();
    	paint.getTextBounds(s, 0, s.length(), bounds);
    	
    	return bounds.height();
    }//
    
    //绘出的文字宽度,例如实现右对齐
    public int vgTextWidth(Paint paint, String s)
    {
    	Rect bounds = new Rect();
    	paint.getTextBounds(s, 0, s.length(), bounds);
    	
    	return bounds.width();
    }//    
    
    
    //根据屏幕宽度换算 dp 为  px//只是按手机宽度标准为 320 计算,不是官方算法//效果等同于 ios 的直接设置
    public double dp2px(int dp) 
    {
    	int w = 320;
    	
    	//屏幕宽度可能计算比较麻烦,所以算一次就好
    	if (this.screenWidth == 0)
    	{
    		w = getScreenWidth();
    		this.screenWidth = w;
    	}
    	else w = screenWidth;
    	
    	//--------------------------------------------------

    	
        double px = (dp * w + 0.000001)/320; //生成小数,方便一次计算 
		
    	return px;
	}//    
    
    //方便计算的函数
    public int getScreenWidth()
    {
    	int w = 320;
    	
    	//w = ((Activity)this.getContext()).getWindowManager().getDefaultDisplay().getWidth();
    	
    	Activity activity = (Activity)this.getContext();

        DisplayMetrics metric = new DisplayMetrics();  //import android.util.DisplayMetrics; 
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);  
        int width = metric.widthPixels;     // 屏幕宽度（像素）  
        int height = metric.heightPixels;   // 屏幕高度（像素）  
        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）  
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）  
        
        w = width;
        
        return w;
    	
    }//
          


}//class

