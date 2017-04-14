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
import android.widget.*;
import android.view.MotionEvent;
import apublic.*;
import android.content.*; //for Intent
import android.graphics.*; //for color

import android.graphics.drawable.*; //for Drawable
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.DisplayMetrics;

public class ExEdit extends EditText 
{
	//新加背景色//默认透明色
	public int backgroundColor = Color.TRANSPARENT;

	
	//本身的  setTextColor 是不起作用的,要用  setTextColor(ColorStateList colors),所 以这里直接简化了
	public int fontColor = Color.BLACK;
	
	//这个类一定要有这个
    public ExEdit(Context context) 
    {
        super(context);
       
        //--------------------------------------------------

        
    }//

    
    //@Override  
    protected void onDraw2(Canvas canvas) 
    {  
        //super.onDraw(canvas);  
        Paint paint = new Paint();  
        // 将边框设为黑色  
        paint.setColor(android.graphics.Color.BLACK);  
        paint.setStrokeWidth(1);  
        // 画TextView的4个边  
        canvas.drawLine(0, 0, this.getWidth() - 1, 0, paint);  
        canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);  
        canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1,  
                this.getHeight() - 1, paint);  
        canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1,  
                this.getHeight() - 1, paint);  
        setBackgroundColor(Color.TRANSPARENT);                
    }   
    
    //@Override
    protected void onDraw3(Canvas canvas) 
    {
    	//canvas.restore(); //什么都不做也会画背景,那说明在另外一个地方还有绘画动作
    	
    	Rect r = canvas.getClipBounds(); 
    	
    	//Functions.ShowMessage(String.valueOf(r.top), null);
    
    }//
    

    ///*
    @Override
    protected void onDraw(Canvas canvas) 
    {
    	//Rect r = canvas.getClipBounds(); //放在这里也没用,这是  ondraw 之前就有的偏差值,基本上无解
    	
        final int compoundPaddingLeft = getCompoundPaddingLeft();
        int compoundPaddingTop = getCompoundPaddingTop();
        final int compoundPaddingRight = getCompoundPaddingRight();
        final int compoundPaddingBottom = getCompoundPaddingBottom();
        
        //compoundPaddingTop = this.getTopPaddingOffset(); //不对
        //compoundPaddingTop = (int)this.getShadowDy(); //2.3 用不了
        
        //--------------------------------------------------
        final int scrollX = getScrollX();
        final int scrollY = getScrollY();
        final int right = getRight();
        final int left = getLeft();
        final int bottom = getBottom();
        final int top = getTop();        
        
        int vspace = bottom - top - compoundPaddingBottom - compoundPaddingTop;
        int hspace = right - left - compoundPaddingRight - compoundPaddingLeft;

        // IMPORTANT: The coordinates computed are also used in invalidateDrawable()
        // Make sure to update invalidateDrawable() when changing this code.
        //if (dr.mDrawableLeft != null) 
        //{
        
        super.onDraw(canvas); 
        
        canvas.save();
//            canvas.translate(//scrollX + mPaddingLeft + leftOffset,
//            		         //scrollX + getPaddingLeft() + 0,
//            		         scrollX + 0 + 0,
//                             scrollY + compoundPaddingTop +
//                             //(vspace - dr.mDrawableHeightLeft) / 2);
//                             (vspace - 0) / 2); //mDrawableHeightLeft 是什么东西?
            
        canvas.translate(
	            scrollX,
                scrollY);
        //最后正确的是这个(参考 textview 源码),大概是因为被 scrollTo(),scrollBy() 函数移动过    
        
            
        //    dr.mDrawableLeft.draw(canvas);
        //    canvas.restore();
        //}
        
        //--------------------------------------------------
        
    	//canvas.save(); //奇怪,没用
    	// TODO Auto-generated method stub
//    	super.onDraw(canvas); 
    	//canvas.restore(); //奇怪,没用
    	
    	//canvas.restore();//不行,会导致画到整个画面左上
//    	canvas.save();//android 的画布的特殊方法,这里应当理解为保存当前画布的偏移坐标//一般和 canvas.restore(); 配合使用
    	//canvas.translate(0, 10);
    	
    	//得到裁剪的边界Rect getClipBounds() 、void getClipBounds(Rect bounds) 
    	Rect r = canvas.getClipBounds(); 
    	
    	//在 2.3 sdk(或者是 4.4 [adt-bundle-windows-x86-20131030]) 下的  EditText 大概是因为默认样式绘图的需要,它的画布是向下移动了的,如果画 top 线可以发现出不来
    	//根据这个版本的绘图实现,这个应当是 画布  裁剪实现的,所以这个向下的偏移量可以用 getClipBounds 来算出(不过其他版本估计会到某个时候不兼容)
    	
//    	canvas.translate(0, r.top); //画布向下移动以抵消默认的 EditText 效果
//    	canvas.translate(0, compoundPaddingTop);
//    	canvas.translate(r.left, 0); //画布向左移动以抵消默认的 EditText 效果//这个在光标闪动时还是有问题,所以直接设置背景是最好的
    	
    	//super.onDraw(canvas); //test 4.4 放这里很好,2.3 效果很差    	
    	
    	
    	int bkcolor = this.backgroundColor;

    	Paint paint = new Paint();
    	paint.setColor(Color.GRAY);
        // 去锯齿
        paint.setAntiAlias(true);


    	//新建矩形
    	RectF rect = new RectF();
    	rect.left = 0;
    	rect.right = this.getWidth();
    	rect.top = 0 ;
    	rect.bottom = this.getHeight();


//        paint.setStyle(Paint.Style.FILL); //据说 FILL_AND_STROKE 的行为奇特
        paint.setStyle(Paint.Style.STROKE); //据说 FILL_AND_STROKE 的行为奇特
        paint.setStrokeWidth(1); //应当是边框线的大小
    	rect.left += 1;
    	rect.right -= 1;
    	rect.top += 1;//奇怪,edit 的这个位置算不对
    	rect.bottom -= 1;
    	
    	//画出圆角矩形//中间两个是圆的范围,据说小于 10 比较好
        canvas.drawRoundRect(rect, 4, 4, paint); //前面的 rect 当边框,退缩 1 个像素做边框好了
    	
    	//_paint.setColor(Color.TRANSPARENT);
    	//canvas.drawRect(rect, _paint); 
        
        //--------------------------------------------------
        canvas.restore(); //和 canvas.save 配对使用的
        
        //setBackgroundColor(Color.TRANSPARENT); //感觉这样不对
    	
    }
    //*/
    


          


}//class

