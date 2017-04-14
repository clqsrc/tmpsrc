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
	//�¼ӱ���ɫ//Ĭ��͸��ɫ
	public int backgroundColor = Color.TRANSPARENT;

	
	//�����  setTextColor �ǲ������õ�,Ҫ��  setTextColor(ColorStateList colors),�� ������ֱ�Ӽ���
	public int fontColor = Color.BLACK;
	
	//�����һ��Ҫ�����
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
        // ���߿���Ϊ��ɫ  
        paint.setColor(android.graphics.Color.BLACK);  
        paint.setStrokeWidth(1);  
        // ��TextView��4����  
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
    	//canvas.restore(); //ʲô������Ҳ�ử����,��˵��������һ���ط����л滭����
    	
    	Rect r = canvas.getClipBounds(); 
    	
    	//Functions.ShowMessage(String.valueOf(r.top), null);
    
    }//
    

    ///*
    @Override
    protected void onDraw(Canvas canvas) 
    {
    	//Rect r = canvas.getClipBounds(); //��������Ҳû��,����  ondraw ֮ǰ���е�ƫ��ֵ,�������޽�
    	
        final int compoundPaddingLeft = getCompoundPaddingLeft();
        int compoundPaddingTop = getCompoundPaddingTop();
        final int compoundPaddingRight = getCompoundPaddingRight();
        final int compoundPaddingBottom = getCompoundPaddingBottom();
        
        //compoundPaddingTop = this.getTopPaddingOffset(); //����
        //compoundPaddingTop = (int)this.getShadowDy(); //2.3 �ò���
        
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
//                             (vspace - 0) / 2); //mDrawableHeightLeft ��ʲô����?
            
        canvas.translate(
	            scrollX,
                scrollY);
        //�����ȷ�������(�ο� textview Դ��),�������Ϊ�� scrollTo(),scrollBy() �����ƶ���    
        
            
        //    dr.mDrawableLeft.draw(canvas);
        //    canvas.restore();
        //}
        
        //--------------------------------------------------
        
    	//canvas.save(); //���,û��
    	// TODO Auto-generated method stub
//    	super.onDraw(canvas); 
    	//canvas.restore(); //���,û��
    	
    	//canvas.restore();//����,�ᵼ�»���������������
//    	canvas.save();//android �Ļ��������ⷽ��,����Ӧ�����Ϊ���浱ǰ������ƫ������//һ��� canvas.restore(); ���ʹ��
    	//canvas.translate(0, 10);
    	
    	//�õ��ü��ı߽�Rect getClipBounds() ��void getClipBounds(Rect bounds) 
    	Rect r = canvas.getClipBounds(); 
    	
    	//�� 2.3 sdk(������ 4.4 [adt-bundle-windows-x86-20131030]) �µ�  EditText �������ΪĬ����ʽ��ͼ����Ҫ,���Ļ����������ƶ��˵�,����� top �߿��Է��ֳ�����
    	//��������汾�Ļ�ͼʵ��,���Ӧ���� ����  �ü�ʵ�ֵ�,����������µ�ƫ���������� getClipBounds �����(���������汾���ƻᵽĳ��ʱ�򲻼���)
    	
//    	canvas.translate(0, r.top); //���������ƶ��Ե���Ĭ�ϵ� EditText Ч��
//    	canvas.translate(0, compoundPaddingTop);
//    	canvas.translate(r.left, 0); //���������ƶ��Ե���Ĭ�ϵ� EditText Ч��//����ڹ������ʱ����������,����ֱ�����ñ�������õ�
    	
    	//super.onDraw(canvas); //test 4.4 ������ܺ�,2.3 Ч���ܲ�    	
    	
    	
    	int bkcolor = this.backgroundColor;

    	Paint paint = new Paint();
    	paint.setColor(Color.GRAY);
        // ȥ���
        paint.setAntiAlias(true);


    	//�½�����
    	RectF rect = new RectF();
    	rect.left = 0;
    	rect.right = this.getWidth();
    	rect.top = 0 ;
    	rect.bottom = this.getHeight();


//        paint.setStyle(Paint.Style.FILL); //��˵ FILL_AND_STROKE ����Ϊ����
        paint.setStyle(Paint.Style.STROKE); //��˵ FILL_AND_STROKE ����Ϊ����
        paint.setStrokeWidth(1); //Ӧ���Ǳ߿��ߵĴ�С
    	rect.left += 1;
    	rect.right -= 1;
    	rect.top += 1;//���,edit �����λ���㲻��
    	rect.bottom -= 1;
    	
    	//����Բ�Ǿ���//�м�������Բ�ķ�Χ,��˵С�� 10 �ȽϺ�
        canvas.drawRoundRect(rect, 4, 4, paint); //ǰ��� rect ���߿�,���� 1 ���������߿����
    	
    	//_paint.setColor(Color.TRANSPARENT);
    	//canvas.drawRect(rect, _paint); 
        
        //--------------------------------------------------
        canvas.restore(); //�� canvas.save ���ʹ�õ�
        
        //setBackgroundColor(Color.TRANSPARENT); //�о���������
    	
    }
    //*/
    


          


}//class

