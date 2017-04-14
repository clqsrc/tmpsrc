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
	//�¼ӱ���ɫ//Ĭ��͸��ɫ
	public int backgroundColor = Color.TRANSPARENT;
	public int backgroundColor_active = Color.LTGRAY; //����ʱ�ı���ɫ,һ�㲻��
	
    //һ����˵ 1sp = 1dp, sp �� google ר�еĸ���,��˵����� �û����õ�ϵͳ�����С���仯,���Զ��ڹ̶��Ľ����С������Ҫ�� dp ,����ʾ���ݵĵ����ִ�С����Ի���� sp (��ʵ�Ҿ���ͳһ dp �ȽϺ�)
	//Ŀǰ����Ļ���Ϊ 320 ����
	public int fontSize_dp = 14;
	
	//�������ı���
	private int screenWidth = 0;
	
	//���»���̧��ȵ�״̬
	private int state = MotionEvent.ACTION_UP; //MotionEvent.ACTION_DOWN
	
	//�����  setTextColor �ǲ������õ�,Ҫ��  setTextColor(ColorStateList colors),�� ������ֱ�Ӽ���
	public int fontColor = Color.BLACK;
	
	//�����һ��Ҫ�����
    public ExButton(Context context) 
    {
        super(context);
       
        //--------------------------------------------------
        
        //����Touch�¼�
        setOnTouchListener(new OnTouchListener() 
        {
            @Override
            public boolean onTouch(View arg0, MotionEvent event) 
            {
                //���¸ı���ʽ
                //setColor(event.getAction());
            	//setStyle(event.getAction());
            	
            	state = event.getAction();
            	
            	postInvalidate(); //postInvalidate �� Invalidate ���ᴥ�� ondraw ,����������������Ļ�������ϰ�ť���ܲ���ˢ��
                
                //�˴�����Ϊfalse����ֹClick�¼�������
                return false;
                
            }//������İ����¼������麯��
            
        });//������
        
        //--------------------------------------------------
        
    }//
    
    
    //������ɫ������,�����Ƿ��µ�
    public void setStyle(int state)
    {
    	//����
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
            //this.setBackgroundDrawable(sd); //û��,�װ汾 android ��֧��
		}
    	this.setBackgroundColor(android.graphics.Color.TRANSPARENT);
    	this.setBackgroundColor(android.graphics.Color.RED);
    }//
    
    /**
     * @param borderLength: һ��ȡ��С��ֵ������10����
     * ����Ҫ��ӵ�view(����TextView)����background:
     * û��,�Ͱ汾 android ��֧��
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
    	
    	//�½�һֻ���ʣ�������Ϊ��ɫ����
    	/*
    	Paint _paint = new Paint();
    	_paint.setColor(Color.GREEN);
    	//�½�����r1
    	RectF r1 = new RectF();
    	r1.left = 50;
    	r1.right = 250;
    	r1.top = 50 ;
    	r1.bottom = 150;

    	//�½�����r2
    	RectF r2 = new RectF();
    	r2.left = 50;
    	r2.right = 250;
    	r2.top = 200 ;
    	r2.bottom = 300;

    	//��������r1
    	canvas.drawRect(r1, _paint);
    	//����Բ�Ǿ���r2
    	_paint.setColor(Color.rgb(204, 204, 204));
    	canvas.drawRoundRect(r2, 10, 10, _paint);
    	*/
    	int bkcolor = this.backgroundColor;
    	//this.getTextColor(context, attrs, def)
    	int txtcolor = this.getCurrentTextColor();
    	this.fontColor = txtcolor;
    	
    	//ģ������������߼�Ҳ����
    	if (state == MotionEvent.ACTION_DOWN) 
    		bkcolor = this.backgroundColor_active;
    	
    	//����������������߼�
    	bkcolor = this.backgroundColor_active;
    	if (state == MotionEvent.ACTION_UP) 
    		bkcolor = this.backgroundColor;
    	
    	Paint paint = new Paint();
    	paint.setColor(Color.GREEN);
        // ȥ���
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
        //paint.setStyle(Paint.Style.STROKE);
        //paint.setStrokeWidth(3);
        //paint.setStrokeWidth(1); //Ӧ���Ǳ߿�Ŀ��//Ч������,�������� rect �غϺ���

    	//�½�����
    	RectF rect = new RectF();
    	rect.left = 0;
    	rect.right = this.getWidth();
    	rect.top = 0 ;
    	rect.bottom = this.getHeight();
    	
    	//������Ӧ��Ļ//Ӧ��������ǰ����
    	//rect.right = (float)this.dp2px((int)rect.right);
    	//rect.bottom = (float)this.dp2px((int)rect.bottom);

    	//����Բ�Ǿ���//�м�������Բ�ķ�Χ,��˵С�� 10 �ȽϺ�
    	canvas.drawRoundRect(rect, 4, 4, paint); //Ҫ�����,ǰ����û�����������������ȫ͸����
    	
        paint.setColor(Color.GREEN);
        paint.setColor(bkcolor);
        paint.setStyle(Paint.Style.FILL); //��˵ FILL_AND_STROKE ����Ϊ����
    	rect.left += 1;
    	rect.right -= 1;
    	rect.top += 1;
    	rect.bottom -= 1;
        canvas.drawRoundRect(rect, 4, 4, paint); //ǰ��� rect ���߿�,���� 1 ���������߿����
    	
    	//_paint.setColor(Color.TRANSPARENT);
    	//canvas.drawRect(rect, _paint); 
        
        //--------------------------------------------------
        String text = getText().toString();
        
        paint.setColor(Color.RED);//test
        paint.setColor(this.fontColor);
//Ϊ�˼�����,�������ⲿ�ļ�������//        paint.setTextSize((int)this.dp2px(this.fontSize_dp));  
        paint.setTextSize(this.getTextSize()); //�ƺ���������ڲ���������̫һ��
        //paint.setTextSize(20);
        
        //canvas.drawText("test", 0, 0, paint); //�����ǲ����ֵ�,��˵����Ϊ y ������ָ�����ֵײ�
        //canvas.drawText("test", 0, 20, paint); 
        
        int w = vgTextWidth(paint, text);
        int h = vgTextHeight(paint, text);
        
        int left = (int)(rect.width() - w)/2;
        
        int bottom = (int)(rect.height() - h)/2; //int top;//�����ǰ� bottom ������
        bottom += h;
        
        //canvas.drawText(text, 0, 20, paint); 
        canvas.drawText(text, left, bottom, paint);
        
        //paint.measureText("test");


    }//
    
    //һ����˵ 1sp = 1dp, sp �� google ר�еĸ���,��˵����� �û����õ�ϵͳ�����С���仯,���Զ��ڹ̶��Ľ����С������Ҫ�� dp ,����ʾ���ݵĵ����ִ�С����Ի���� sp (��ʵ�Ҿ���ͳһ dp �ȽϺ�)
    public void SetTextSize(int dp)
    {
    	//���������ʱ����Paint.setTextSize��TextView.setTextSize����ĵ�λ��һ�¡�
    	//Paint.setTextSize����ĵ�λ��px,
    	//TextView.setTextSizeĬ�ϴ���ĵ�λ��sp��
    	
    	fontSize_dp = dp;
    	
    	
    }//
    
    //��������ָ߶�//�������ܵ�Ҫ����ֻ����һ��,��Ϊ����һ����(�󲿷������)
    public int vgTextHeight(Paint paint, String s)
    {
    	Rect bounds = new Rect();
    	paint.getTextBounds(s, 0, s.length(), bounds);
    	
    	return bounds.height();
    }//
    
    //��������ֿ��,����ʵ���Ҷ���
    public int vgTextWidth(Paint paint, String s)
    {
    	Rect bounds = new Rect();
    	paint.getTextBounds(s, 0, s.length(), bounds);
    	
    	return bounds.width();
    }//    
    
    
    //������Ļ��Ȼ��� dp Ϊ  px//ֻ�ǰ��ֻ���ȱ�׼Ϊ 320 ����,���ǹٷ��㷨//Ч����ͬ�� ios ��ֱ������
    public double dp2px(int dp) 
    {
    	int w = 320;
    	
    	//��Ļ��ȿ��ܼ���Ƚ��鷳,������һ�ξͺ�
    	if (this.screenWidth == 0)
    	{
    		w = getScreenWidth();
    		this.screenWidth = w;
    	}
    	else w = screenWidth;
    	
    	//--------------------------------------------------

    	
        double px = (dp * w + 0.000001)/320; //����С��,����һ�μ��� 
		
    	return px;
	}//    
    
    //�������ĺ���
    public int getScreenWidth()
    {
    	int w = 320;
    	
    	//w = ((Activity)this.getContext()).getWindowManager().getDefaultDisplay().getWidth();
    	
    	Activity activity = (Activity)this.getContext();

        DisplayMetrics metric = new DisplayMetrics();  //import android.util.DisplayMetrics; 
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);  
        int width = metric.widthPixels;     // ��Ļ��ȣ����أ�  
        int height = metric.heightPixels;   // ��Ļ�߶ȣ����أ�  
        float density = metric.density;      // ��Ļ�ܶȣ�0.75 / 1.0 / 1.5��  
        int densityDpi = metric.densityDpi;  // ��Ļ�ܶ�DPI��120 / 160 / 240��  
        
        w = width;
        
        return w;
    	
    }//
          


}//class

