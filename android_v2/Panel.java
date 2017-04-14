package aui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import apublic.*;
import android.content.*; //for Intent

//Layout 其实也是一个 view
public class Panel extends AbsoluteLayout 
{
	
	//如果 ide 带有  android 的源码就可以看到这个类其实就是在  onLayout() 事件中调用了子控件的 layout() 方法来绝对布局而已
	
	//这个类一定要有这个
    public Panel(Context context) 
    {
        super(context);
    }//
    
    

}//class
