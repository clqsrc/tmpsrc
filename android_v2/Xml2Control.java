package apublic;

import java.util.Hashtable; //��˵ Hashtable �߳�ͬ��, hashmap ��ͬ��

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

//����ͬ ios Xml2Control.h

//����,Ҫ���ļ�//��������þ�̬��,����Ϊ����,���� AbsoluteLayout.LayoutParams = public static class LayoutParams extends ViewGroup.LayoutParams
//public class Hashtable_controls extends Hashtable<String, View>
//{}//




public class Xml2Control
{
	// ��̬�ڲ��� //��̬�ڲ��� �ſ��Զ�������
	public static class Hashtable_controls extends Hashtable<String, View>
	{}//
	
	//�� xml ��װ�����пؼ�//��Դ�ļ�·��
	//Rect rectParent ���ؼ��Ĵ�С,��Ϊ�� oncreate ��ʱ�ؼ���ȡ������С
	public Hashtable<String, View> LoadFromXml(String fileName, View view, Hashtable<String, View> controls, Rect rectParent)
	{
		
		
		return controls;
	}//
	
	
	//�� xml ��װ�����пؼ�//����ȫ·��
	//Rect rectParent ���ؼ��Ĵ�С,��Ϊ�� oncreate ��ʱ�ؼ���ȡ������С
	//ֻ�������� ios ������,Ŀǰ������ȫ·��,Ŀǰʵ���� Assets Ŀ¼�µ��ļ�
	static public Hashtable<String, View> LoadFromXml_FullFileName(String fileName, View view, Hashtable<String, View> controls, Rect rectParent)
	{

	    if (controls == null) controls = new Hashtable<String, View>();
	    
    	//--------------------------------------------------
    	//�ȴ��� xml ����
    	DocumentBuilderFactory factory=null;
        DocumentBuilder builder=null;
        Document document=null;
        InputStream inputStream=null;
        //List<River> rivers=new ArrayList<River>();
        
        String errorXml = "�������";	    
        
        //--------------------------------------------------

	    //NSString *path = fileName; //֧��ȫ·���ȽϺ�
	    
        factory = DocumentBuilderFactory.newInstance();
        String s = "";
        try {
        	//--------------------------------------------------
        	//���ǽ�����Ŀ���Դ���ʾ�� xml
            //�ҵ�xml���������ĵ�
            //inputStream = this.getResources().getAssets().open("ud_op_test2.xml");//������ʹ��assetsĿ¼�е�river.xml�ļ� ��ʵ���Դ�  url �����������ļ���ȡ
            ////inputStream = new FileInputStream(fileName);//�������ļ���ȡ,���� sd ����
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
            //���ǽ���������ȡ�õ� xml
            //��һ���ַ������� xml ����,������ɹ���ֱ����ʾ��ԭʼ������Ϊ������Ϣ������
            //inputStream = new ByteArrayInputStream(errorXml.getBytes("ISO-8859-1"));
            //inputStream = new ByteArrayInputStream(s.getBytes());//��ʱ���ַ����Ѿ�ת������,���Բ���Ҫ�ٴ�  gbk ��ת����־
            
            inputStream = new ByteArrayInputStream(s.getBytes());//��ʱ���ַ����Ѿ�ת������,���Բ���Ҫ�ٴ�  gbk ��ת����־
            inputStream = new ByteArrayInputStream(s.getBytes("gbk"));//ok
            //inputStream = view.getContext().getResources().getAssets().open("login.xml");//no//ԭʼ���ֽ����ļ���������

            //--------------------------------------------------
            builder = factory.newDocumentBuilder();
            document = builder.parse(inputStream);//����Ϊ��ʾ������������ inputStream ,ʵ����Ŀ��ֻ��ʹ������һ��
            
            //�ҵ���Element
            Element root = document.getDocumentElement();
            Functions.ShowMessage(root.getTagName(), (Activity)view.getContext());//����õ�  <Root> �ڵ�
            //����� delphi �ǲ�ͬ��
            //Element itemList = (Element)root.getElementsByTagName("Data").item(0);
            //Functions.ShowMessage(itemList.getTagName(), this);//����õ�  <ItemList> �ڵ�
            //NodeList nodes = itemList.getElementsByTagName("Row");
            
            NodeList nodes = root.getChildNodes();
	    
    	    //NSData *xmlData = [[NSMutableData alloc] initWithContentsOfFile: path];

    	    //NSError *error;
    	    
    	    //��һ���ڵ�ʵ�����ǿհ�,����ֱ�������ǲ��е�//[Xml2Control CreateControls:doc.rootElement parent:view controls:dictionary];
            //���������ӽڵ�
            for(int i=0;i<nodes.getLength();i++)
            {
                //��ȡԪ�ؽڵ�
            	System.out.println("nodes.item: " + nodes.item(i).getClass()); 
            	Node n = nodes.item(i);
            	
            	if (n == null) continue;
            	
            	if (!n.hasAttributes()) continue;
            	
            	//Ԫ��(Element)�ͽ��(Node)������//Ԫ���ǽ���һ��,���⻹�� text ,���Եȵ�,�μ� Element.ELEMENT_NODE ��Դ�붨��
            	if (n.getNodeType() != Element.ELEMENT_NODE) continue; 
            	
                Element node=(Element)(nodes.item(i)); //���쳣,��Щ�ڵ��ǲ���ת����
                
                Xml2Control.CreateControls(node, view, controls, rectParent);
                
                //break;
            }

    	    
        }//try
        catch (Exception e)
        {
            e.printStackTrace();
            
            try {
            	//Functions.ShowMessage("<�����ڳ���ʱֱ����ʾҪ������  xml Դ�����>" + new String(errorXml.getBytes("ISO-8859-1"), "unicode"), this);
            	Functions.ShowMessage("<�����ڳ���ʱֱ����ʾҪ������  xml Դ�����>" + s, (Activity)view.getContext());
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
	
	//android �в���ֱ�Ӱѿؼ��ӵ� view Ҫ�� Activity ��
	static public Panel CreateRootPanel(Activity ac)
	{
		Panel panel = new Panel(ac);
		
		ac.setContentView(panel); //������������
		
		
		return panel;
				
	}//
	
	//�� xml չ���ؼ�λ��
	//android.graphics.Rect
	//Rect rectParent ���ؼ��Ĵ�С,��Ϊ�� oncreate ��ʱ�ؼ���ȡ������С
	static public Rect PosFromXml(View control, Element node,  View parent, Rect rectParent )//parentNode:(GDataXMLElement *)parentNode
	{
	    //------------------------
	    //�����ߴ�
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
	    //������Ӧ��Ļ//Ӧ�����ڶ���֮ǰ
	    Activity activity = (Activity)control.getContext();
	    left = (int)Functions.dp2px(left, activity);
	    top = (int)Functions.dp2px(top, activity);
	    width = (int)Functions.dp2px(width, activity);
	    height = (int)Functions.dp2px(height, activity);

	    
	    
	    //------------------------
	    //��������ұ�Ϊ׼��ֵ��Ҫ�ټ���//������ delphi �� Anchors,������ÿ�����򶼴洢ֵ
	    //UIView * parent = control.superview;
	    ////CGRect rect_p = parent.frame; //���ؼ��Ĵ�С
	    ////Rect rect_p = new Rect(parent.getLeft(), parent.getTop(), parent.getRight(), parent.getBottom()); //���ؼ��Ĵ�С//���Ǵ������ȽϺ�,��Ϊ�ؼ����ܻ�δ�������
	    Rect rect_p = rectParent;

	    String anchor_right = node.getAttribute("anchor_right");
	    String anchor_left = node.getAttribute("anchor_left");
	    
	    if ((anchor_right != null)&&(anchor_right.length()>0)) //������ж��Ƿ�����������
	    {
	        int anchor_right_i = Integer.parseInt(anchor_right);
	        
	        //������������Ŀ��
	        if ((anchor_left != null)&&(anchor_left.length()>0))
	        {
	            int anchor_left_i = Integer.parseInt(anchor_left);
	            width = rect_p.width() - anchor_right_i - anchor_left_i; //�Ҷ��벢�����ұ߼������
	            
	            left = anchor_left_i;
	        }
	        else
	        //���û����������λ��
	            left = rect_p.width() - anchor_right_i - rect.width();
	    }//if 1
	    
	    //------------------------
	    //������Եײ�Ϊ׼��ֵ��Ҫ�ټ���//������ delphi �� Anchors,������ÿ�����򶼴洢ֵ
	    String anchor_bottom = node.getAttribute("anchor_bottom");
	    String anchor_top = node.getAttribute("anchor_top");
	    
	    if ((anchor_bottom != null)&&(anchor_bottom.length()>0)) //������ж��Ƿ�����������
	    {
	        int anchor_bottom_i = Integer.parseInt(anchor_bottom);
	        
	        //����ж�������ĸ߶�
	        if ((anchor_top != null)&&(anchor_top.length()>0))
	        {
	            int anchor_top_i = Integer.parseInt(anchor_top);
	            height = rect_p.height() - anchor_bottom_i - anchor_top_i;
	            
	            top = anchor_top_i;
	        }
	        else
	            //���û�ж��������λ��
	            top = rect_p.height() - anchor_bottom_i - rect.height();
	    }//if 1

	    //------------------------
	    //control.frame = rect;
	    
	    //������Ӧ��Ļ//Ӧ�����ڶ���֮ǰ
//	    Activity activity = (Activity)control.getContext();
//	    left = (int)Functions.dp2px(left, activity);
//	    top = (int)Functions.dp2px(top, activity);
//	    width = (int)Functions.dp2px(width, activity);
//	    height = (int)Functions.dp2px(height, activity);
	    
	    Functions.SetControlFrame(control, left, top, width, height);
	    
	    //--------------------------------------------------
	    //android ��Ҫд��ȥһ��
	   
	    rect.left = left;
	    rect.right = left + width;
	    rect.top = top;
	    rect.bottom = top + height;
	    
	    return rect;
	}//	
	
	//AlignCenterH
	//PosFromXml Ӧ�������ɿؼ�ʱ����,��������������ÿ�ζ�Ҫ����һ��
	//�� xml չ���ؼ�λ��
	//Rect rectParent ���ؼ��Ĵ�С,��Ϊ�� oncreate ��ʱ�ؼ���ȡ������С
	static public void AlignFromXml(View control, Element node, View parent, Rect _rect, Rect rectParent)
	{
	    ////CGRect rect_p = parent.frame; //���ؼ��Ĵ�С
		Rect rect_p = Functions.GetControlFrame(parent); //�����ʵҲ��̫��,��û�����һ��������
		rect_p = rectParent;
	    /*//��̫��,��ԭ������Ĵ�С����
	    //------------------------
	    //�����ߴ�
	    CGRect rect = control.frame;
	    
	    rect.origin.x = [node attributeForName:@"Left"].stringValue.integerValue;
	    rect.origin.y = [node attributeForName:@"Top"].stringValue.integerValue;
	    
	    rect.size.width = [node attributeForName:@"Width"].stringValue.integerValue;
	    rect.size.height = [node attributeForName:@"Height"].stringValue.integerValue;
	    
	    //------------------------
	    //��������ұ�Ϊ׼��ֵ��Ҫ�ټ���//������ delphi �� Anchors,������ÿ�����򶼴洢ֵ
	    //UIView * parent = control.superview;
	    CGRect rect_p = parent.frame; //���ؼ��Ĵ�С
	    
	    GDataXMLNode * anchor_right = [node attributeForName:@"anchor_right"];
	    GDataXMLNode * anchor_left = [node attributeForName:@"anchor_left"];
	    */

	    /*
	    
	    if (anchor_right != nil) //������ж��Ƿ�����������
	    {
	        NSInteger anchor_right_i = anchor_right.stringValue.integerValue;
	        
	        //������������Ŀ��
	        if (anchor_left != nil)
	        {
	            NSInteger anchor_left_i = anchor_left.stringValue.integerValue;
	            rect.size.width = rect_p.size.width - anchor_right_i - anchor_left_i; //�Ҷ��벢�����ұ߼������
	            
	            rect.origin.x = anchor_left_i;
	        }
	        else
	            //���û����������λ��
	            rect.origin.x = rect_p.size.width - anchor_right_i - rect.size.width;
	    }//if 1
	    
	    //------------------------
	    //������Եײ�Ϊ׼��ֵ��Ҫ�ټ���//������ delphi �� Anchors,������ÿ�����򶼴洢ֵ
	    GDataXMLNode * anchor_bottom = [node attributeForName:@"anchor_bottom"];
	    GDataXMLNode * anchor_top = [node attributeForName:@"anchor_top"];
	    
	    if (anchor_bottom != nil) //������ж��Ƿ�����������
	    {
	        NSInteger anchor_bottom_i = anchor_bottom.stringValue.integerValue;
	        
	        //����ж�������ĸ߶�
	        if (anchor_top != nil)
	        {
	            NSInteger anchor_top_i = anchor_top.stringValue.integerValue;
	            rect.size.height = rect_p.size.height - anchor_bottom_i - anchor_top_i;
	        }
	        else
	            //���û�ж��������λ��
	            rect.origin.y = rect_p.size.height - anchor_bottom_i - rect.size.height;
	    }//if 1
	    */
	    
	    //===========
	    //����ˮƽ��������
	    //------------------------
		//rect = new Rect(rect.left, rect.top, rect.right, rect.bottom);//����������,���ǻᴫ��ȥ,��Ϊ java �Ĳ���������,����ָ��
		Rect rect = new Rect(_rect.left, _rect.top, _rect.right, _rect.bottom);
		
		int left = rect.left;
	    String AlignCenterH = node.getAttribute("AlignCenterH");
	    if ((AlignCenterH != null)&&(AlignCenterH.length()>0)) //������ж��Ƿ�����������
	    {
	        int AlignCenterH_i = Integer.parseInt(AlignCenterH);
	        
	        //���ˮƽ�������λ��//���Ҿ���
	        if (AlignCenterH_i == 1)
	        {
	            left = (rect_p.width() - rect.width()) / 2;
	            rect.left = left;//��ͬ��ģ����������Ľ�������һ��,google �ٷ��ĻὫ rect �޸Ĳ�����,BlueStacks �򲻻�,���԰�ȫ����������������һ���ڲ� rect ��//ios �ǽṹ��,���Բ��ᴫ��,����û���������
	            rect.right = rect.left + _rect.width(); //android ��  rect �Ƚ��ر�,ֻ�� left ��Ӱ����,��Ϊԭʼ������ left �� right , width ���������
	            //����Ҫ��ԭ���Ŀ�������¼���һ��
	            //delphi / windows �ĺ� android ������,ios ֻ����߾�����
	        }

	    }//if 1

	    
	    ////control.frame = rect;
	    Functions.SetControlFrame(control, rect.left, rect.top, rect.width(), rect.height());
	}//

	
	
	//�������пؼ�
	static public void CreateControls(Element node, View parent, Hashtable<String, View> controls, Rect rectParent)
	{

        try 
        {
        	View create = null;
        	View android_create = null;
        	int is_android_create = 0;
            
            if (node.getNodeName().equals("text")) return; //���Կհ׽ڵ�//xmlKeepBlanksDefault(0);//��˵��ȥ���հ�,����û��

            String  ClassName = node.getAttribute("ClassName");
            String  Name = node.getAttribute("Name");
            String  Color = node.getAttribute("Color");
            String  FontColor = node.getAttribute("FontColor");
            String  FontSize = node.getAttribute("FontSize");
            String  Src = node.getAttribute("Src"); //���� html ͼƬ�� src
            String  Caption = node.getAttribute("Caption");
            
            //---------------------
            //����ؼ��Ѿ����ھͲ�Ҫ�ٴ���,�޸���λ�þͿ�����//���Կؼ�һ��Ҫ�����ַ�����ظ�����
            if ((Name != null)&&(Name.length()>0))
            {
                View tmp = controls.get("name");//[controls valueForKey:Name];
                if (tmp != null) create = tmp;
                
                //if (create.superview == nil) [parent addSubview:create];
            }
            
            //---------------------
            //��׿���е�
            //������Ӧ��Ļ
            int iFontSize = 12; //Ĭ��Ӧ���Ƕ���? �ƺ��ܶ�ط���˵�� 12
            
            Activity activity = (Activity)parent.getContext();
            
            //if (FontSize != null) //��  ios ��ͬ,�����ڵĽڵ㲻һ���� null ,�����ǿ��ַ���//ע��!!!
            if ((FontSize != null)&&(FontSize.length()>0))
            	iFontSize = (int)Functions.dp2px(Integer.parseInt(FontSize), activity);

            
            
            //--------------------------------------------------

            if ((create == null)&&(ClassName.equals("Panel")))
            {
                //View panel = new View(parent.getContext()); //��������?
            	Panel panel = new Panel(parent.getContext()); //��������?
                
                Functions.SetControlFrame(panel, 0, 0, 100, 100); //panel.frame = CGRectMake(0, 0, 10, 10);
                
                //panel.backgroundColor = [UIColor redColor];
                panel.setBackgroundColor(Functions.Color(Color));
                //panel.setBackgroundColor(android.graphics.Color.RED);//
                ViewGroup vg = (ViewGroup)parent; //Ŀǰ��˵����ֱ�Ӽ�,������һ����  layout
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
            	panel.setFillViewport(false); //ʲô����?
                
                Functions.SetControlFrame(panel, 0, 0, 100, 100); //panel.frame = CGRectMake(0, 0, 10, 10);
                
                //panel.backgroundColor = [UIColor redColor];
                panel.setBackgroundColor(Functions.Color(Color));
                //panel.setBackgroundColor(android.graphics.Color.RED);//
                
                //Ҫ�޸�Ϊ android:minSdkVersion="9" ��ſ�����(2.3) ԭ���� 8 (2.2) //������Ȼ�������� 2.3 �µı߿�
                //panel.setOverScrollMode(View.OVER_SCROLL_NEVER);
                
                
                ViewGroup vg = (ViewGroup)parent; //Ŀǰ��˵����ֱ�Ӽ�,������һ����  layout
                Functions.AddChildControl(vg, panel);
                
                
                //[Xml2Control PosFromXml:panel node:node parent:parent];
                //Xml2Control.PosFromXml(panel, node, parent);
                
                create = panel;
                
                //--------------------------------------------------
                //��������� LinearLayout ������
                
                LinearLayout child = new LinearLayout(panel.getContext());
                child.setOrientation(LinearLayout.VERTICAL); //����Ƚ���Ҫ,Ӧ���Ƿ���
            	//Functions.SetControlFrame(child, 0, 0, 100, 1000); //panel.frame = CGRectMake(0, 0, 10, 10);
                panel.addView(child);
                
                //--------------------------------------------------
                
                AbsoluteLayout c1 = new AbsoluteLayout(parent.getContext());
            	//c1.setHeight(2000); //�������Ч��
            	c1.layout(0, 0, 1000, 1000); //�����Ч
            	c1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)); //��Ч,��ʵ������,ֻҪ�����ж���
            	////c1.setLayoutParams(new LinearLayout.LayoutParams(100, 100)); //����
            	////c1.setLayoutParams(new LinearLayout.LayoutParams(1000, 1000)); //����
            	
            	//c1.setBackgroundColor(android.graphics.Color.BLUE);//
            	if ((Color != null)&&(Color.length()>0)) c1.setBackgroundColor(Functions.Color(Color)); //ע�������Ǹ��ӿؼ���ɫ,������ӿؼ���ʵ�����������ؼ�������
            	
            	child.addView(c1, 0); 
            	//c.setHeight(200);
            	
            	android_create = c1; is_android_create = 1;
            	
            	//--------------------------------------------------
                
//                for (int i = 0; i < 10; i++) {  
//                	ExButton c = new ExButton(parent.getContext());
//                	c.setHeight(200); //�������Ч��
//                	//c.layout(0, 0, 1000, 1000); //�����Ч
//                	c.setText(String.valueOf(i));
//                	child.addView(c, i+1); 
//                	//c.setHeight(200);
//                }            
            	
//              for (int i = 0; i < 10; i++) {  
//            	EditText c = new EditText(parent.getContext());
//            	c.setHeight(20); //�������Ч��//�� edit ��˵������滭ƫ��,���� 35
//            	c.setHeight(100); //�������Ч��//�� edit ��˵������滭ƫ��,���� 35
//            	//c.layout(0, 0, 1000, 1000); //�����Ч
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
            	//panel.setBackgroundColor(0);//��ɫ
            	//panel.setBackgroundColor(android.graphics.Color.parseColor("#F5F5DC"));
            	//�����и�����,���º��û�б仯��
            	
            	//android.graphics.drawable.ColorDrawable cd1 =
            	//new android.graphics.drawable.ColorDrawable(0xff000000);
            	//new android.graphics.drawable.ColorDrawable(android.graphics.Color.RED);
            	
            	//panel.setBackgroundDrawable(cd1);
            	
            	//panel.setBackgroundColor(Functions.Color("#0000DD"));
            	panel.backgroundColor = Functions.Color(Color);
            	////panel.setBackgroundColor(Functions.Color(Color));
            	
            	//panel.getBackground().clearColorFilter(); //����˾�
            	
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
                // btn.contentEdgeInsets = UIEdgeInsetsMake(0,10, 0, 0);  //ʹ���־������߿򱣳�10�����صľ��롣
                
                if ((Caption != null)&&(Caption.length()>0)) panel.setText(Caption);
                if ((FontColor != null)&&(FontColor.length()>0)) panel.setTextColor(Functions.Color(FontColor));
                if ((FontSize != null)&&(FontSize.length()>0)) panel.setTextSize(iFontSize); //������Ӧ��Ļ
                /*
                if (FontSize != null) panel.titleLabel.font = [UIFont systemFontOfSize: FontSize.integerValue];//14.0
                
                if (FontColor != null) [panel setTitleColor:[Functions Color:FontColor]forState:UIControlStateNormal];
                
                if ([@"Center" isEqualToString:[node attributeForName:@"Alignment"].stringValue]) panel.contentHorizontalAlignment = UIControlContentHorizontalAlignmentCenter;//���ֿ���//UITextAlignmentCenter; //��ť�� btnShowSite.contentHorizontalAlignment = UIControlContentHorizontalAlignmentLeft;//���ֿ���
                if ([@"Right" isEqualToString:[node attributeForName:@"Alignment"].stringValue]) panel.contentHorizontalAlignment = UIControlContentHorizontalAlignmentRight;//���ֿ���
                if ([@"Left" isEqualToString:[node attributeForName:@"Alignment"].stringValue]) panel.contentHorizontalAlignment = UIControlContentHorizontalAlignmentLeft;//���ֿ���
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
                
                if ((FontSize != null)&&(FontSize.length()>0)) panel.setTextSize(iFontSize); //������Ӧ��Ļ
                

                
                String  Alignment = node.getAttribute("Alignment");
                
                if ("Center".equals(Alignment)) panel.setGravity(Gravity.CENTER);//���ֿ���
                if ("Right".equals(Alignment)) panel.setGravity(Gravity.RIGHT);//���ֿ���
                
                create = panel;
                
            }//if
            
            if ((create == null)&&(ClassName.equals("Edit_test1")))
            {
            	EditText panel = new EditText(parent.getContext());
            	//ExEdit panel = new ExEdit(parent.getContext());
            	Functions.SetControlFrame(panel, 0, 0, 100, 30); //2.3 �����Ĭ����������¸߶� 30 �ǲ�����,����� 35
            	
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
//                //Ҫ�󲻸ߵ���Ŀ,���ֱ�Ӷ����ɫ����,���������Ը�һ��
//                if ((Color != null)&&(Color.length()>0)) panel.setBackgroundColor(Functions.Color(Color));
//                //panel.setBackgroundColor(android.graphics.Color.RED);
//                
//                ////if (FontSize != null) panel.setTextSize(Integer.parseInt(FontSize));
//                
//                if ((FontSize != null)&&(FontSize.length()>0)) panel.setTextSize(iFontSize); //������Ӧ��Ļ
//                
//
//                
//                String  Alignment = node.getAttribute("Alignment");
//                
//                if ("Center".equals(Alignment)) panel.setGravity(Gravity.CENTER);//���ֿ���
//                if ("Right".equals(Alignment)) panel.setGravity(Gravity.RIGHT);//���ֿ���
//               
//                String  Pass = node.getAttribute("Pass");
//                
//                //if ("True".equals(Pass)) panel.setTransformationMethod(PasswordTransformationMethod.getInstance());//��������Ϊ���ɼ���
//                if ("True".equals(Pass)) panel.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); //Ч��Ӧ��ͬ��,������ʲô����?
                
                
                
                create = panel;
                
            }//if            
            
            if ((create == null)&&(ClassName.equals("Edit")))
            {
            	//EditText panel = new EditText(parent.getContext());
            	ExEdit panel = new ExEdit(parent.getContext());
            	Functions.SetControlFrame(panel, 0, 0, 10, 10); //2.3 �����Ĭ����������¸߶� 30 �ǲ�����,����� 35
            	
            	//panel.getExtendedPaddingTop();
            	//panel.setGravity(Gravity.CENTER);


            	Functions.AddChildControl(parent, panel);
                

            	String Text = node.getAttribute("Text");

                panel.setText(Text);
                //panel.text = @"aaa";
                
                //[Xml2Control PosFromXml:panel node:node parent:parent];
                
                if ((FontColor != null)&&(FontColor.length()>0)) panel.setTextColor(Functions.Color(FontColor));
                
                //panel.setTextColor(android.graphics.Color.BLACK);
                
                //Ҫ�󲻸ߵ���Ŀ,���ֱ�Ӷ����ɫ����,���������Ը�һ��
                if ((Color != null)&&(Color.length()>0)) panel.setBackgroundColor(Functions.Color(Color));
                //panel.setBackgroundColor(android.graphics.Color.RED);
                
                ////if (FontSize != null) panel.setTextSize(Integer.parseInt(FontSize));
                
                if ((FontSize != null)&&(FontSize.length()>0)) panel.setTextSize(iFontSize); //������Ӧ��Ļ
                

                
                String  Alignment = node.getAttribute("Alignment");
                
                if ("Center".equals(Alignment)) panel.setGravity(Gravity.CENTER);//���ֿ���
                if ("Right".equals(Alignment)) panel.setGravity(Gravity.RIGHT);//���ֿ���
               
                String  Pass = node.getAttribute("Pass");
                
                //if ("True".equals(Pass)) panel.setTransformationMethod(PasswordTransformationMethod.getInstance());//��������Ϊ���ɼ���
                if ("True".equals(Pass)) panel.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); //Ч��Ӧ��ͬ��,������ʲô����?
                
                
                
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
                    //panel.setImageResource(); //���ֻ��ָ�� id
            		//String path = Environment.getExternalStorageDirectory()+File.separator+"test1.jpg";
            		//activity.getResources().getAssets().getLocales()
            		InputStream inputStream = activity.getResources().getAssets().open(Src);
            		Bitmap bm = BitmapFactory.decodeStream(inputStream);
            		//Bitmap bm = BitmapFactory.decodeFile(path); 
            		panel.setImageBitmap(bm);
                }
                
                //ͼƬ��Ĭ��ȫ���������,����ѡ��Ϊ�ȱ�������
            	String AutoSize = node.getAttribute("AutoSize");
                
            	if ((AutoSize != null)&&(AutoSize.length()>0))
                {
                    if ("1".equals(AutoSize))
                        panel.setScaleType(ScaleType.FIT_CENTER); //�о� android Ĭ��Ч��������� 
                    //panel.contentMode = UIViewContentModeScaleAspectFit;
                    //UIViewContentModeScaleToFill���Իᵼ��ͼƬ���Ρ�UIViewContentModeScaleAspectFit�ᱣ֤ͼƬ�������䣬����ȫ����ʾ��ImageView�У�����ζ��ImageView���в��ֿհס�UIViewContentModeScaleAspectFillҲ��֤ͼƬ�������䣬�������������ImageView�ģ�����ֻ�в���ͼƬ��ʾ������
                }
                
                create = panel;
                
            }//if
            
            if ((create == null)&&(ClassName.equals("CheckBox")))
            {
            	//EditText panel = new EditText(parent.getContext());
            	CheckBox panel = new CheckBox(parent.getContext());
            	Functions.SetControlFrame(panel, 0, 0, 10, 10); //2.3 �����Ĭ����������¸߶� 30 �ǲ�����,����� 35

            	Functions.AddChildControl(parent, panel);
                

            	//String Text = node.getAttribute("Text");
            	String Text = node.getAttribute("Caption");
            	

                panel.setText(Text);
                //panel.setText("aaa");
                //panel.text = @"aaa";
                
                //[Xml2Control PosFromXml:panel node:node parent:parent];
                
                if ((FontColor != null)&&(FontColor.length()>0)) panel.setTextColor(Functions.Color(FontColor));
                
                //panel.setTextColor(android.graphics.Color.BLACK);
                
                //Ҫ�󲻸ߵ���Ŀ,���ֱ�Ӷ����ɫ����,���������Ը�һ��
                if ((Color != null)&&(Color.length()>0)) panel.setBackgroundColor(Functions.Color(Color));
                //panel.setBackgroundColor(android.graphics.Color.RED);
                
                
                if ((FontSize != null)&&(FontSize.length()>0)) panel.setTextSize(iFontSize); //������Ӧ��Ļ
                

                
                String  Alignment = node.getAttribute("Alignment");
                
                if ("Center".equals(Alignment)) panel.setGravity(Gravity.CENTER);//���ֿ���
                if ("Right".equals(Alignment)) panel.setGravity(Gravity.RIGHT);//���ֿ���
                
                
                create = panel;
                
            }//if            
            
            
            if (create == null) return; //û�д����ؼ�
            
            if ((Name != null)&&(Name.length()>0))
                controls.put(Name, create); //[controls setValue:create forKey:Name]; //NSDictionary �ǲ����������,��ȥȡ NSDictionary ����Ŀؼ�����ֵ
            
            ////if (Color != nil) create.backgroundColor = [Functions Color:Color]; //16�����ַ�������ɫ
            
            Rect newrect = Xml2Control.PosFromXml(create, node, parent, rectParent);
            //CGRect newrect= [Xml2Control PosFromXml:create node:node parent:parent];
            Xml2Control.AlignFromXml(create, node, parent, newrect, rectParent); //����Ҫÿ�ζ�����
            
            
            //UIButton * tmp2 = [dictionary valueForKey:@"tmp"];
            
            //tmp2.backgroundColor = [UIColor blueColor];

            //----------------
            //for (int i=0; i<node.childCount; i++)
            NodeList nodes = node.getChildNodes();
            for(int i=0;i<nodes.getLength();i++)
            {
                ////GDataXMLElement * subnode = node.children[i];
            
                ////[Xml2Control CreateControls:subnode parent:create controls:controls];
                
                //��ȡԪ�ؽڵ�
            	System.out.println("nodes.item: " + nodes.item(i).getClass()); 
            	Node n = nodes.item(i);
            	
            	if (n == null) continue;
            	
            	if (!n.hasAttributes()) continue;
            	
            	//Ԫ��(Element)�ͽ��(Node)������//Ԫ���ǽ���һ��,���⻹�� text ,���Եȵ�,�μ� Element.ELEMENT_NODE ��Դ�붨��
            	if (n.getNodeType() != Element.ELEMENT_NODE) continue; 
            	
                Element subnode=(Element)(nodes.item(i)); //���쳣,��Щ�ڵ��ǲ���ת����
                
                //Xml2Control.CreateControls(subnode, create, controls, rectParent);
                ////Xml2Control.CreateControls(subnode, create, controls, newrect); //����Ҫ���Լ��� rect ��Ϊ�¼��ĸ� rect ҪС��
                
                //���Ͽؼ����п��ܼ�������ı����ϵ�
                if (is_android_create == 1)
                {
                	Xml2Control.CreateControls(subnode, android_create, controls, newrect); //����Ҫ���Լ��� rect ��Ϊ�¼��ĸ� rect ҪС��
                }
                else 
                {
                	Xml2Control.CreateControls(subnode, create, controls, newrect); //����Ҫ���Լ��� rect ��Ϊ�¼��ĸ� rect ҪС��
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







