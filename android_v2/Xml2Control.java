package apublic;

import java.util.Hashtable; //据说 Hashtable 线程同步, hashmap 不同步

import android.R.integer;
import android.view.*;
import android.widget.*;
import android.widget.ImageView.ScaleType;
import android.app.Activity;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.graphics.*;

import android.text.*;
import android.text.method.*;

import aui.*;

//功能同 ios Xml2Control.h

//不好,要另开文件//好象可以用静态类,可以为子类,例如 AbsoluteLayout.LayoutParams = public static class LayoutParams extends ViewGroup.LayoutParams
//public class Hashtable_controls extends Hashtable<String, View>
//{}//




public class Xml2Control
{
	// 静态内部类 //静态内部类 才可以独立创建
	public static class Hashtable_controls extends Hashtable<String, View>
	{}//
	
	//从 xml 中装载所有控件//资源文件路径
	//Rect rectParent 父控件的大小,因为在 oncreate 中时控件还取不到大小
	public Hashtable<String, View> LoadFromXml(String fileName, View view, Hashtable<String, View> controls, Rect rectParent)
	{
		
		
		return controls;
	}//
	
	
	//从 xml 中装载所有控件//完整全路径
	//Rect rectParent 父控件的大小,因为在 oncreate 中时控件还取不到大小
	//只是延用了 ios 的名称,目前并不是全路径,目前实现是 Assets 目录下的文件
	static public Hashtable<String, View> LoadFromXml_FullFileName(String fileName, View view, Hashtable<String, View> controls, Rect rectParent)
	{

	    if (controls == null) controls = new Hashtable<String, View>();
	    
    	//--------------------------------------------------
    	//先创建 xml 环境
    	DocumentBuilderFactory factory=null;
        DocumentBuilder builder=null;
        Document document=null;
        InputStream inputStream=null;
        //List<River> rivers=new ArrayList<River>();
        
        String errorXml = "密码错误";	    
        
        //--------------------------------------------------

	    //NSString *path = fileName; //支持全路径比较好
	    
        factory = DocumentBuilderFactory.newInstance();
        String s = "";
        try {
        	//--------------------------------------------------
        	//这是解码项目中自带的示例 xml
            //找到xml，并加载文档
            //inputStream = this.getResources().getAssets().open("ud_op_test2.xml");//这里是使用assets目录中的river.xml文件 其实可以从  url 或者是物理文件中取
            ////inputStream = new FileInputStream(fileName);//从物理文件读取,例如 sd 卡中
        	////inputStream = view.getContext().getResources().getAssets().open("login.xml");
        	inputStream = view.getContext().getResources().getAssets().open(fileName);
            
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
            BufferedReader in = new BufferedReader(inputStreamReader);
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append(in.readLine() + "\n");
            //s = sBuffer.toString();
            
            String strLine = null;
            while((strLine =  in.readLine()) != null) {
                sBuffer.append(strLine);
            }
            
            s = sBuffer.toString();
            
            //--------------------------------------------------
            //这是解码网络中取得的 xml
            //将一个字符串当做 xml 解码,如果不成功就直接显示其原始内容作为错误信息就行了
            //inputStream = new ByteArrayInputStream(errorXml.getBytes("ISO-8859-1"));
            //inputStream = new ByteArrayInputStream(s.getBytes());//这时的字符串已经转过码了,所以不需要再带  gbk 的转换标志
            
            inputStream = new ByteArrayInputStream(s.getBytes());//这时的字符串已经转过码了,所以不需要再带  gbk 的转换标志
            inputStream = new ByteArrayInputStream(s.getBytes("gbk"));//ok
            //inputStream = view.getContext().getResources().getAssets().open("login.xml");//no//原始的字节流文件反而不行

            //--------------------------------------------------
            builder = factory.newDocumentBuilder();
            document = builder.parse(inputStream);//上面为做示例生成了两个 inputStream ,实际项目中只能使用其中一个
            
            //找到根Element
            Element root = document.getDocumentElement();
            Functions.ShowMessage(root.getTagName(), (Activity)view.getContext());//这个得到  <Root> 节点
            //这里和 delphi 是不同的
            //Element itemList = (Element)root.getElementsByTagName("Data").item(0);
            //Functions.ShowMessage(itemList.getTagName(), this);//这个得到  <ItemList> 节点
            //NodeList nodes = itemList.getElementsByTagName("Row");
            
            NodeList nodes = root.getChildNodes();
	    
    	    //NSData *xmlData = [[NSMutableData alloc] initWithContentsOfFile: path];

    	    //NSError *error;
    	    
    	    //第一个节点实际上是空白,所以直接这样是不行的//[Xml2Control CreateControls:doc.rootElement parent:view controls:dictionary];
            //遍历所有子节点
            for(int i=0;i<nodes.getLength();i++)
            {
                //获取元素节点
            	System.out.println("nodes.item: " + nodes.item(i).getClass()); 
            	Node n = nodes.item(i);
            	
            	if (n == null) continue;
            	
            	if (!n.hasAttributes()) continue;
            	
            	//元素(Element)和结点(Node)的区别//元素是结点的一种,另外还有 text ,属性等等,参见 Element.ELEMENT_NODE 的源码定义
            	if (n.getNodeType() != Element.ELEMENT_NODE) continue; 
            	
                Element node=(Element)(nodes.item(i)); //有异常,有些节点是不能转换的
                
                Xml2Control.CreateControls(node, view, controls, rectParent);
                
                //break;
            }

    	    
        }//try
        catch (Exception e)
        {
            e.printStackTrace();
            
            try {
            	//Functions.ShowMessage("<这里在出错时直接显示要解析的  xml 源码就行>" + new String(errorXml.getBytes("ISO-8859-1"), "unicode"), this);
            	Functions.ShowMessage("<这里在出错时直接显示要解析的  xml 源码就行>" + s, (Activity)view.getContext());
            } catch (Exception e_codepage) {   
            	e_codepage.printStackTrace();
            }
        } 
//        catch (SAXException e) {
//            e.printStackTrace();
//        }
//         catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        }
        finally
        {
            try 
            {
                inputStream.close();
            } catch (IOException e) {   
                e.printStackTrace();
            }
        }//try
	    
	    
	    return controls;		

	}//
	
	//android 中不能直接把控件加到 view 要在 Activity 中
	static public Panel CreateRootPanel(Activity ac)
	{
		Panel panel = new Panel(ac);
		
		ac.setContentView(panel); //好象必须有这个
		
		
		return panel;
				
	}//
	
	//按 xml 展开控件位置
	//android.graphics.Rect
	//Rect rectParent 父控件的大小,因为在 oncreate 中时控件还取不到大小
	static public Rect PosFromXml(View control, Element node,  View parent, Rect rectParent )//parentNode:(GDataXMLElement *)parentNode
	{
	    //------------------------
	    //基本尺寸
	    ////Rect rect = control.frame;
		Rect rect = new Rect(control.getLeft(), control.getTop(), control.getRight(), control.getBottom());
		//rect.left = control.getLeft(); rect.right = control.getRight();
	    
//	    rect.origin.x = [node attributeForName:@"Left"].stringValue.integerValue;
//	    rect.origin.y = [node attributeForName:@"Top"].stringValue.integerValue;
//
//	    rect.size.width = [node attributeForName:@"Width"].stringValue.integerValue;
//	    rect.size.height = [node attributeForName:@"Height"].stringValue.integerValue;
	    
	    int left = Integer.parseInt(node.getAttribute("Left"));
	    int top = Integer.parseInt(node.getAttribute("Top"));

	    int width = Integer.parseInt(node.getAttribute("Width"));
	    int height = Integer.parseInt(node.getAttribute("Height"));

	    //--------------------------------------------------
	    //缩放适应屏幕//应当放在对齐之前
	    Activity activity = (Activity)control.getContext();
	    left = (int)Functions.dp2px(left, activity);
	    top = (int)Functions.dp2px(top, activity);
	    width = (int)Functions.dp2px(width, activity);
	    height = (int)Functions.dp2px(height, activity);

	    
	    
	    //------------------------
	    //如果有以右边为准的值则要再计算//类似于 delphi 的 Anchors,不过是每个方向都存储值
	    //UIView * parent = control.superview;
	    ////CGRect rect_p = parent.frame; //父控件的大小
	    ////Rect rect_p = new Rect(parent.getLeft(), parent.getTop(), parent.getRight(), parent.getBottom()); //父控件的大小//还是传进来比较好,因为控件可能还未布局完成
	    Rect rect_p = rectParent;

	    String anchor_right = node.getAttribute("anchor_right");
	    String anchor_left = node.getAttribute("anchor_left");
	    
	    if ((anchor_right != null)&&(anchor_right.length()>0)) //这个可判断是否存在这个属性
	    {
	        int anchor_right_i = Integer.parseInt(anchor_right);
	        
	        //如果有左对齐则改宽度
	        if ((anchor_left != null)&&(anchor_left.length()>0))
	        {
	            int anchor_left_i = Integer.parseInt(anchor_left);
	            width = rect_p.width() - anchor_right_i - anchor_left_i; //右对齐并且与右边间隔多少
	            
	            left = anchor_left_i;
	        }
	        else
	        //如果没有左对齐则改位置
	            left = rect_p.width() - anchor_right_i - rect.width();
	    }//if 1
	    
	    //------------------------
	    //如果有以底部为准的值则要再计算//类似于 delphi 的 Anchors,不过是每个方向都存储值
	    String anchor_bottom = node.getAttribute("anchor_bottom");
	    String anchor_top = node.getAttribute("anchor_top");
	    
	    if ((anchor_bottom != null)&&(anchor_bottom.length()>0)) //这个可判断是否存在这个属性
	    {
	        int anchor_bottom_i = Integer.parseInt(anchor_bottom);
	        
	        //如果有顶对齐则改高度
	        if ((anchor_top != null)&&(anchor_top.length()>0))
	        {
	            int anchor_top_i = Integer.parseInt(anchor_top);
	            height = rect_p.height() - anchor_bottom_i - anchor_top_i;
	            
	            top = anchor_top_i;
	        }
	        else
	            //如果没有顶对齐则改位置
	            top = rect_p.height() - anchor_bottom_i - rect.height();
	    }//if 1

	    //------------------------
	    //control.frame = rect;
	    
	    //缩放适应屏幕//应当放在对齐之前
//	    Activity activity = (Activity)control.getContext();
//	    left = (int)Functions.dp2px(left, activity);
//	    top = (int)Functions.dp2px(top, activity);
//	    width = (int)Functions.dp2px(width, activity);
//	    height = (int)Functions.dp2px(height, activity);
	    
	    Functions.SetControlFrame(control, left, top, width, height);
	    
	    //--------------------------------------------------
	    //android 下要写回去一下
	   
	    rect.left = left;
	    rect.right = left + width;
	    rect.top = top;
	    rect.bottom = top + height;
	    
	    return rect;
	}//	
	
	//AlignCenterH
	//PosFromXml 应该是生成控件时设置,而对齐属性则是每次都要重置一次
	//按 xml 展开控件位置
	//Rect rectParent 父控件的大小,因为在 oncreate 中时控件还取不到大小
	static public void AlignFromXml(View control, Element node, View parent, Rect _rect, Rect rectParent)
	{
	    ////CGRect rect_p = parent.frame; //父控件的大小
		Rect rect_p = Functions.GetControlFrame(parent); //这个其实也不太对,最好还是上一级传过来
		rect_p = rectParent;
	    /*//不太好,用原来计算的大小好了
	    //------------------------
	    //基本尺寸
	    CGRect rect = control.frame;
	    
	    rect.origin.x = [node attributeForName:@"Left"].stringValue.integerValue;
	    rect.origin.y = [node attributeForName:@"Top"].stringValue.integerValue;
	    
	    rect.size.width = [node attributeForName:@"Width"].stringValue.integerValue;
	    rect.size.height = [node attributeForName:@"Height"].stringValue.integerValue;
	    
	    //------------------------
	    //如果有以右边为准的值则要再计算//类似于 delphi 的 Anchors,不过是每个方向都存储值
	    //UIView * parent = control.superview;
	    CGRect rect_p = parent.frame; //父控件的大小
	    
	    GDataXMLNode * anchor_right = [node attributeForName:@"anchor_right"];
	    GDataXMLNode * anchor_left = [node attributeForName:@"anchor_left"];
	    */

	    /*
	    
	    if (anchor_right != nil) //这个可判断是否存在这个属性
	    {
	        NSInteger anchor_right_i = anchor_right.stringValue.integerValue;
	        
	        //如果有左对齐则改宽度
	        if (anchor_left != nil)
	        {
	            NSInteger anchor_left_i = anchor_left.stringValue.integerValue;
	            rect.size.width = rect_p.size.width - anchor_right_i - anchor_left_i; //右对齐并且与右边间隔多少
	            
	            rect.origin.x = anchor_left_i;
	        }
	        else
	            //如果没有左对齐则改位置
	            rect.origin.x = rect_p.size.width - anchor_right_i - rect.size.width;
	    }//if 1
	    
	    //------------------------
	    //如果有以底部为准的值则要再计算//类似于 delphi 的 Anchors,不过是每个方向都存储值
	    GDataXMLNode * anchor_bottom = [node attributeForName:@"anchor_bottom"];
	    GDataXMLNode * anchor_top = [node attributeForName:@"anchor_top"];
	    
	    if (anchor_bottom != nil) //这个可判断是否存在这个属性
	    {
	        NSInteger anchor_bottom_i = anchor_bottom.stringValue.integerValue;
	        
	        //如果有顶对齐则改高度
	        if (anchor_top != nil)
	        {
	            NSInteger anchor_top_i = anchor_top.stringValue.integerValue;
	            rect.size.height = rect_p.size.height - anchor_bottom_i - anchor_top_i;
	        }
	        else
	            //如果没有顶对齐则改位置
	            rect.origin.y = rect_p.size.height - anchor_bottom_i - rect.size.height;
	    }//if 1
	    */
	    
	    //===========
	    //左右水平对齐的情况
	    //------------------------
		//rect = new Rect(rect.left, rect.top, rect.right, rect.bottom);//这样都不行,还是会传出去,因为 java 的参数是引用,不是指针
		Rect rect = new Rect(_rect.left, _rect.top, _rect.right, _rect.bottom);
		
		int left = rect.left;
	    String AlignCenterH = node.getAttribute("AlignCenterH");
	    if ((AlignCenterH != null)&&(AlignCenterH.length()>0)) //这个可判断是否存在这个属性
	    {
	        int AlignCenterH_i = Integer.parseInt(AlignCenterH);
	        
	        //如果水平对齐则改位置//左右居中
	        if (AlignCenterH_i == 1)
	        {
	            left = (rect_p.width() - rect.width()) / 2;
	            rect.left = left;//不同的模拟器对这个的解析还不一样,google 官方的会将 rect 修改并带出,BlueStacks 则不会,所以安全的做法还是再生成一个内部 rect 吧//ios 是结构体,所以不会传出,所以没有这个问题
	            rect.right = rect.left + _rect.width(); //android 的  rect 比较特别,只改 left 会影响宽度,因为原始数据是 left 和 right , width 是算出来的
	            //所以要用原来的宽度再重新计算一下
	            //delphi / windows 的和 android 的类似,ios 只算左边就行了
	        }

	    }//if 1

	    
	    ////control.frame = rect;
	    Functions.SetControlFrame(control, rect.left, rect.top, rect.width(), rect.height());
	}//

	
	
	//创建所有控件
	static public void CreateControls(Element node, View parent, Hashtable<String, View> controls, Rect rectParent)
	{

        try 
        {
        	View create = null;
        	View android_create = null;
        	int is_android_create = 0;
            
            if (node.getNodeName().equals("text")) return; //忽略空白节点//xmlKeepBlanksDefault(0);//据说能去掉空白,但是没用

            String  ClassName = node.getAttribute("ClassName");
            String  Name = node.getAttribute("Name");
            String  Color = node.getAttribute("Color");
            String  FontColor = node.getAttribute("FontColor");
            String  FontSize = node.getAttribute("FontSize");
            String  Src = node.getAttribute("Src"); //类似 html 图片的 src
            String  Caption = node.getAttribute("Caption");
            
            //---------------------
            //如果控件已经存在就不要再创建,修改其位置就可以了//所以控件一定要给名字否则会重复创建
            if ((Name != null)&&(Name.length()>0))
            {
                View tmp = controls.get("name");//[controls valueForKey:Name];
                if (tmp != null) create = tmp;
                
                //if (create.superview == nil) [parent addSubview:create];
            }
            
            //---------------------
            //安卓才有的
            //缩放适应屏幕
            int iFontSize = 12; //默认应该是多少? 似乎很多地方都说是 12
            
            Activity activity = (Activity)parent.getContext();
            
            //if (FontSize != null) //和  ios 不同,不存在的节点不一定是 null ,可能是空字符串//注意!!!
            if ((FontSize != null)&&(FontSize.length()>0))
            	iFontSize = (int)Functions.dp2px(Integer.parseInt(FontSize), activity);

            
            
            //--------------------------------------------------

            if ((create == null)&&(ClassName.equals("Panel")))
            {
                //View panel = new View(parent.getContext()); //这样行吗?
            	Panel panel = new Panel(parent.getContext()); //这样行吗?
                
                Functions.SetControlFrame(panel, 0, 0, 100, 100); //panel.frame = CGRectMake(0, 0, 10, 10);
                
                //panel.backgroundColor = [UIColor redColor];
                panel.setBackgroundColor(Functions.Color(Color));
                //panel.setBackgroundColor(android.graphics.Color.RED);//
                ViewGroup vg = (ViewGroup)parent; //目前来说不能直接加,除非上一级是  layout
                Functions.AddChildControl(vg, panel);
                
                
                //[Xml2Control PosFromXml:panel node:node parent:parent];
                //Xml2Control.PosFromXml(panel, node, parent);
                
                create = panel;
                
                //--------------------------------------------------
                //panel.addView(child)                
                
            }//if
            
            if ((create == null)&&(ClassName.equals("ScrollView")))
            {
            	ScrollView panel = new ScrollView(parent.getContext());
            	panel.setFillViewport(false); //什么作用?
                
                Functions.SetControlFrame(panel, 0, 0, 100, 100); //panel.frame = CGRectMake(0, 0, 10, 10);
                
                //panel.backgroundColor = [UIColor redColor];
                panel.setBackgroundColor(Functions.Color(Color));
                //panel.setBackgroundColor(android.graphics.Color.RED);//
                
                //要修改为 android:minSdkVersion="9" 后才可以用(2.3) 原来是 8 (2.2) //不过仍然消除不了 2.3 下的边框
                //panel.setOverScrollMode(View.OVER_SCROLL_NEVER);
                
                
                ViewGroup vg = (ViewGroup)parent; //目前来说不能直接加,除非上一级是  layout
                Functions.AddChildControl(vg, panel);
                
                
                //[Xml2Control PosFromXml:panel node:node parent:parent];
                //Xml2Control.PosFromXml(panel, node, parent);
                
                create = panel;
                
                //--------------------------------------------------
                //好象必须用 LinearLayout 来配套
                
                LinearLayout child = new LinearLayout(panel.getContext());
                child.setOrientation(LinearLayout.VERTICAL); //这个比较重要,应该是方向
            	//Functions.SetControlFrame(child, 0, 0, 100, 1000); //panel.frame = CGRectMake(0, 0, 10, 10);
                panel.addView(child);
                
                //--------------------------------------------------
                
                AbsoluteLayout c1 = new AbsoluteLayout(parent.getContext());
            	//c1.setHeight(2000); //这个是有效的
            	c1.layout(0, 0, 1000, 1000); //这个无效
            	c1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)); //无效,其实刀可以,只要里面有东西
            	////c1.setLayoutParams(new LinearLayout.LayoutParams(100, 100)); //可以
            	////c1.setLayoutParams(new LinearLayout.LayoutParams(1000, 1000)); //可以
            	
            	//c1.setBackgroundColor(android.graphics.Color.BLUE);//
            	if ((Color != null)&&(Color.length()>0)) c1.setBackgroundColor(Functions.Color(Color)); //注意这里是给子控件颜色,这里的子控件其实是容纳其他控件的容器
            	
            	child.addView(c1, 0); 
            	//c.setHeight(200);
            	
            	android_create = c1; is_android_create = 1;
            	
            	//--------------------------------------------------
                
//                for (int i = 0; i < 10; i++) {  
//                	ExButton c = new ExButton(parent.getContext());
//                	c.setHeight(200); //这个是有效的
//                	//c.layout(0, 0, 1000, 1000); //这个无效
//                	c.setText(String.valueOf(i));
//                	child.addView(c, i+1); 
//                	//c.setHeight(200);
//                }            
            	
//              for (int i = 0; i < 10; i++) {  
//            	EditText c = new EditText(parent.getContext());
//            	c.setHeight(20); //这个是有效的//对 edit 来说这样会绘画偏移,至少 35
//            	c.setHeight(100); //这个是有效的//对 edit 来说这样会绘画偏移,至少 35
//            	//c.layout(0, 0, 1000, 1000); //这个无效
//            	c.setText(String.valueOf(i));
//            	child.addView(c, i+1); 
//            	//c.setHeight(200);
//            }             	
             
                
            }//if            
            
            if ((create == null)&&(ClassName.equals("Button")))
            {
            	//Button panel = new Button(parent.getContext());
            	ExButton panel = new ExButton(parent.getContext());
            	Functions.SetControlFrame(panel, 0, 0, 100, 100); //panel.frame = CGRectMake(0, 0, 10, 10);

            	//panel.backgroundColor = [UIColor grayColor];
            	//panel.setBackgroundColor(0);//白色
            	//panel.setBackgroundColor(android.graphics.Color.parseColor("#F5F5DC"));
            	//这样有个问题,按下后就没有变化了
            	
            	//android.graphics.drawable.ColorDrawable cd1 =
            	//new android.graphics.drawable.ColorDrawable(0xff000000);
            	//new android.graphics.drawable.ColorDrawable(android.graphics.Color.RED);
            	
            	//panel.setBackgroundDrawable(cd1);
            	
            	//panel.setBackgroundColor(Functions.Color("#0000DD"));
            	panel.backgroundColor = Functions.Color(Color);
            	////panel.setBackgroundColor(Functions.Color(Color));
            	
            	//panel.getBackground().clearColorFilter(); //清空滤镜
            	
            	//panel.getBackground().setColorFilter(android.graphics.Color.BLUE, android.graphics.PorterDuff.Mode.MULTIPLY);
            	
            	//panel.getBackground().setColorFilter(new android.graphics.ColorMatrixColorFilter(0));  
            	//panel.setBackgroundDrawable(panel.getBackground());
            	
            	Functions.AddChildControl(parent, panel);
                
                //[Xml2Control PosFromXml:panel node:node parent:parent];
            	//Xml2Control.PosFromXml(panel, node, parent);
             
                if (Src != null)
                {
                    //UIImage * bkimage = [UIImage imageWithNamed:Src];
                    
                    //[panel setBackgroundImage:bkimage forState:UIControlStateNormal];
                	//panel.setBackgroundDrawable(background)
                }
                
                //btn.titleLabel.font = [UIFont systemFontOfSize: 14.0];
                //[btn seBackgroundColor: [UIColor blueColor]];
                // btn.contentEdgeInsets = UIEdgeInsetsMake(0,10, 0, 0);  //使文字距离做边框保持10个像素的距离。
                
                if ((Caption != null)&&(Caption.length()>0)) panel.setText(Caption);
                if ((FontColor != null)&&(FontColor.length()>0)) panel.setTextColor(Functions.Color(FontColor));
                if ((FontSize != null)&&(FontSize.length()>0)) panel.setTextSize(iFontSize); //缩放适应屏幕
                /*
                if (FontSize != null) panel.titleLabel.font = [UIFont systemFontOfSize: FontSize.integerValue];//14.0
                
                if (FontColor != null) [panel setTitleColor:[Functions Color:FontColor]forState:UIControlStateNormal];
                
                if ([@"Center" isEqualToString:[node attributeForName:@"Alignment"].stringValue]) panel.contentHorizontalAlignment = UIControlContentHorizontalAlignmentCenter;//文字靠左//UITextAlignmentCenter; //按钮是 btnShowSite.contentHorizontalAlignment = UIControlContentHorizontalAlignmentLeft;//文字靠左
                if ([@"Right" isEqualToString:[node attributeForName:@"Alignment"].stringValue]) panel.contentHorizontalAlignment = UIControlContentHorizontalAlignmentRight;//文字靠左
                if ([@"Left" isEqualToString:[node attributeForName:@"Alignment"].stringValue]) panel.contentHorizontalAlignment = UIControlContentHorizontalAlignmentLeft;//文字靠左
                */
            
                create = panel;
                
            }//if
            
            if ((create == null)&&(ClassName.equals("Label")))
            {
                
            	TextView panel = new TextView(parent.getContext());
            	Functions.SetControlFrame(panel, 0, 0, 10, 10); 


            	Functions.AddChildControl(parent, panel);
                
            	if ((Caption != null)&&(Caption.length()>0)) panel.setText(Caption);
                
                //[Xml2Control PosFromXml:panel node:node parent:parent];
                
                if ((FontColor != null)&&(FontColor.length()>0)) panel.setTextColor(Functions.Color(FontColor));
                
                ////if (FontSize != null) panel.setTextSize(Integer.parseInt(FontSize));
                
                if ((FontSize != null)&&(FontSize.length()>0)) panel.setTextSize(iFontSize); //缩放适应屏幕
                

                
                String  Alignment = node.getAttribute("Alignment");
                
                if ("Center".equals(Alignment)) panel.setGravity(Gravity.CENTER);//文字靠左
                if ("Right".equals(Alignment)) panel.setGravity(Gravity.RIGHT);//文字靠左
                
                create = panel;
                
            }//if
            
            if ((create == null)&&(ClassName.equals("Edit_test1")))
            {
            	EditText panel = new EditText(parent.getContext());
            	//ExEdit panel = new ExEdit(parent.getContext());
            	Functions.SetControlFrame(panel, 0, 0, 100, 30); //2.3 的真机默认字体情况下高度 30 是不够的,最好是 35
            	
            	panel.getExtendedPaddingTop();
            	panel.setGravity(Gravity.CENTER);


            	Functions.AddChildControl(parent, panel);
                
            	panel = new EditText(parent.getContext());
            	//ExEdit panel = new ExEdit(parent.getContext());
            	Functions.SetControlFrame(panel, 0, 0, 100, 100); 
            	
            	panel.getExtendedPaddingTop();
            	panel.setGravity(Gravity.CENTER);


            	Functions.AddChildControl(parent, panel);
                

//            	String Text = node.getAttribute("Text");
//
//                panel.setText(Text);
//                //panel.text = @"aaa";
//                
//                //[Xml2Control PosFromXml:panel node:node parent:parent];
//                
//                if ((FontColor != null)&&(FontColor.length()>0)) panel.setTextColor(Functions.Color(FontColor));
//                
//                //panel.setTextColor(android.graphics.Color.BLACK);
//                
//                //要求不高的项目,最好直接定义白色背景,这样兼容性高一点
//                if ((Color != null)&&(Color.length()>0)) panel.setBackgroundColor(Functions.Color(Color));
//                //panel.setBackgroundColor(android.graphics.Color.RED);
//                
//                ////if (FontSize != null) panel.setTextSize(Integer.parseInt(FontSize));
//                
//                if ((FontSize != null)&&(FontSize.length()>0)) panel.setTextSize(iFontSize); //缩放适应屏幕
//                
//
//                
//                String  Alignment = node.getAttribute("Alignment");
//                
//                if ("Center".equals(Alignment)) panel.setGravity(Gravity.CENTER);//文字靠左
//                if ("Right".equals(Alignment)) panel.setGravity(Gravity.RIGHT);//文字靠左
//               
//                String  Pass = node.getAttribute("Pass");
//                
//                //if ("True".equals(Pass)) panel.setTransformationMethod(PasswordTransformationMethod.getInstance());//设置密码为不可见。
//                if ("True".equals(Pass)) panel.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); //效果应该同上,但是有什么区别?
                
                
                
                create = panel;
                
            }//if            
            
            if ((create == null)&&(ClassName.equals("Edit")))
            {
            	//EditText panel = new EditText(parent.getContext());
            	ExEdit panel = new ExEdit(parent.getContext());
            	Functions.SetControlFrame(panel, 0, 0, 10, 10); //2.3 的真机默认字体情况下高度 30 是不够的,最好是 35
            	
            	//panel.getExtendedPaddingTop();
            	//panel.setGravity(Gravity.CENTER);


            	Functions.AddChildControl(parent, panel);
                

            	String Text = node.getAttribute("Text");

                panel.setText(Text);
                //panel.text = @"aaa";
                
                //[Xml2Control PosFromXml:panel node:node parent:parent];
                
                if ((FontColor != null)&&(FontColor.length()>0)) panel.setTextColor(Functions.Color(FontColor));
                
                //panel.setTextColor(android.graphics.Color.BLACK);
                
                //要求不高的项目,最好直接定义白色背景,这样兼容性高一点
                if ((Color != null)&&(Color.length()>0)) panel.setBackgroundColor(Functions.Color(Color));
                //panel.setBackgroundColor(android.graphics.Color.RED);
                
                ////if (FontSize != null) panel.setTextSize(Integer.parseInt(FontSize));
                
                if ((FontSize != null)&&(FontSize.length()>0)) panel.setTextSize(iFontSize); //缩放适应屏幕
                

                
                String  Alignment = node.getAttribute("Alignment");
                
                if ("Center".equals(Alignment)) panel.setGravity(Gravity.CENTER);//文字靠左
                if ("Right".equals(Alignment)) panel.setGravity(Gravity.RIGHT);//文字靠左
               
                String  Pass = node.getAttribute("Pass");
                
                //if ("True".equals(Pass)) panel.setTransformationMethod(PasswordTransformationMethod.getInstance());//设置密码为不可见。
                if ("True".equals(Pass)) panel.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); //效果应该同上,但是有什么区别?
                
                
                
                create = panel;
                
            }//if
	
            if ((create == null)&&(ClassName.equals("Image")))
            {
            	ImageView panel = new ImageView(parent.getContext());
            	Functions.SetControlFrame(panel, 0, 0, 10, 10); 


            	Functions.AddChildControl(parent, panel);
                	        
                
                //[Xml2Control PosFromXml:panel node:node parent:parent];
                
            	if ((Src != null)&&(Src.length()>0))
                {
                    //panel.setImageResource(); //这个只能指向 id
            		//String path = Environment.getExternalStorageDirectory()+File.separator+"test1.jpg";
            		//activity.getResources().getAssets().getLocales()
            		InputStream inputStream = activity.getResources().getAssets().open(Src);
            		Bitmap bm = BitmapFactory.decodeStream(inputStream);
            		//Bitmap bm = BitmapFactory.decodeFile(path); 
            		panel.setImageBitmap(bm);
                }
                
                //图片是默认全部变形填充,可以选择为等比例缩放
            	String AutoSize = node.getAttribute("AutoSize");
                
            	if ((AutoSize != null)&&(AutoSize.length()>0))
                {
                    if ("1".equals(AutoSize))
                        panel.setScaleType(ScaleType.FIT_CENTER); //感觉 android 默认效果就是这个 
                    //panel.contentMode = UIViewContentModeScaleAspectFit;
                    //UIViewContentModeScaleToFill属性会导致图片变形。UIViewContentModeScaleAspectFit会保证图片比例不变，而且全部显示在ImageView中，这意味着ImageView会有部分空白。UIViewContentModeScaleAspectFill也会证图片比例不变，但是是填充整个ImageView的，可能只有部分图片显示出来。
                }
                
                create = panel;
                
            }//if
            
            if ((create == null)&&(ClassName.equals("CheckBox")))
            {
            	//EditText panel = new EditText(parent.getContext());
            	CheckBox panel = new CheckBox(parent.getContext());
            	Functions.SetControlFrame(panel, 0, 0, 10, 10); //2.3 的真机默认字体情况下高度 30 是不够的,最好是 35

            	Functions.AddChildControl(parent, panel);
                

            	//String Text = node.getAttribute("Text");
            	String Text = node.getAttribute("Caption");
            	

                panel.setText(Text);
                //panel.setText("aaa");
                //panel.text = @"aaa";
                
                //[Xml2Control PosFromXml:panel node:node parent:parent];
                
                if ((FontColor != null)&&(FontColor.length()>0)) panel.setTextColor(Functions.Color(FontColor));
                
                //panel.setTextColor(android.graphics.Color.BLACK);
                
                //要求不高的项目,最好直接定义白色背景,这样兼容性高一点
                if ((Color != null)&&(Color.length()>0)) panel.setBackgroundColor(Functions.Color(Color));
                //panel.setBackgroundColor(android.graphics.Color.RED);
                
                
                if ((FontSize != null)&&(FontSize.length()>0)) panel.setTextSize(iFontSize); //缩放适应屏幕
                

                
                String  Alignment = node.getAttribute("Alignment");
                
                if ("Center".equals(Alignment)) panel.setGravity(Gravity.CENTER);//文字靠左
                if ("Right".equals(Alignment)) panel.setGravity(Gravity.RIGHT);//文字靠左
                
                
                create = panel;
                
            }//if            
            
            
            if (create == null) return; //没有创建控件
            
            if ((Name != null)&&(Name.length()>0))
                controls.put(Name, create); //[controls setValue:create forKey:Name]; //NSDictionary 是不能用这个的,会去取 NSDictionary 本身的控件属性值
            
            ////if (Color != nil) create.backgroundColor = [Functions Color:Color]; //16进制字符串的颜色
            
            Rect newrect = Xml2Control.PosFromXml(create, node, parent, rectParent);
            //CGRect newrect= [Xml2Control PosFromXml:create node:node parent:parent];
            Xml2Control.AlignFromXml(create, node, parent, newrect, rectParent); //对齐要每次都计算
            
            
            //UIButton * tmp2 = [dictionary valueForKey:@"tmp"];
            
            //tmp2.backgroundColor = [UIColor blueColor];

            //----------------
            //for (int i=0; i<node.childCount; i++)
            NodeList nodes = node.getChildNodes();
            for(int i=0;i<nodes.getLength();i++)
            {
                ////GDataXMLElement * subnode = node.children[i];
            
                ////[Xml2Control CreateControls:subnode parent:create controls:controls];
                
                //获取元素节点
            	System.out.println("nodes.item: " + nodes.item(i).getClass()); 
            	Node n = nodes.item(i);
            	
            	if (n == null) continue;
            	
            	if (!n.hasAttributes()) continue;
            	
            	//元素(Element)和结点(Node)的区别//元素是结点的一种,另外还有 text ,属性等等,参见 Element.ELEMENT_NODE 的源码定义
            	if (n.getNodeType() != Element.ELEMENT_NODE) continue; 
            	
                Element subnode=(Element)(nodes.item(i)); //有异常,有些节点是不能转换的
                
                //Xml2Control.CreateControls(subnode, create, controls, rectParent);
                ////Xml2Control.CreateControls(subnode, create, controls, newrect); //这里要用自己的 rect 作为下级的父 rect 要小心
                
                //复合控件是有可能加在另外的变量上的
                if (is_android_create == 1)
                {
                	Xml2Control.CreateControls(subnode, android_create, controls, newrect); //这里要用自己的 rect 作为下级的父 rect 要小心
                }
                else 
                {
                	Xml2Control.CreateControls(subnode, create, controls, newrect); //这里要用自己的 rect 作为下级的父 rect 要小心
				}
                
            }//for            
        	
        } 
        catch (Exception e) 
        {   
            e.printStackTrace();
            Functions.ShowMessage("error CreateControls():" + e.getMessage(), (Activity)parent.getContext());
        }

	}//
	

	
}//class







