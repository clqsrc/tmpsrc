package apublic;

//import android.R.string;
import java.nio.*;

import android.R.integer;


//类似于 unit Type32to64;


/*
java 中int 的范围：
数据类型            大小       范围                                             默认值 

byte(字节) 	        8         -128 - 127                                           0
shot(短整型)        16      -32768 - 32768                                         0
int(整型)           32   -2147483648-2147483648                                    0
long(长整型)        64   -9233372036854477808-9233372036854477808                  0        
float(浮点型)       32  -3.40292347E+38-3.40292347E+38                            0.0f
double(双精度)	    64  -1.79769313486231570E+308-1.79769313486231570E+308        0.0d
char(字符型)        16         ‘ \u0000 - u\ffff ’                             ‘\u0000 ’
boolean(布尔型)     1         true/false                                         false
*/


public class Type32to64 
{
	//java 里其实是没有无符号 16 位整数的
	public static short uint16(short i)
	{
		return i;
		
	}//

	public static int uint32(int i)
	{
		return i;
		
	}//
	
	//8位的无符号很容易溢出的,一定要小心
	public static byte uint8(byte i)
	{
		return i;
		
	}//
	
	public static long uint64(long i)
	{
		return i;
		
	}//
	
	public static byte bool8(Byte i)
	{
		return i;
		
	}//
	
	public static short int16(short i)
	{
		return i;
		
	}//

	public static int int32(int i)
	{
		return i;
		
	}//
	
	//8位的无符号很容易溢出的,一定要小心
	public static byte int8(byte i)
	{
		return i;
		
	}//
	
	public static long int64(long i)
	{
		return i;
		
	}//	

	//16进制的字符串
	public static long Str2Hex(String s)
	{
		//Integer.parseInt("8C",16);
		long i = Integer.parseInt(s, 16);
		return i;
		
	}//	

	public static void test1() 
	{
		byte b1 = Type32to64.int8((byte)200);
		int i1 = (int)Type32to64.Str2Hex("0F");
		
		
		Functions.ShowMessage(Integer.toString(b1), null);
		
	};
	
	//--------------------------------------------------
	//上面是作为类型声明的方便性操作,下面是二进制原始数据操作的类,基本上是  java.nio.ByteBuffer  的封装,因为原始  java.nio.ByteBuffer 接口很不明义
	
	//ByteBuffer buf = ByteBuffer(12);//比较奇特,这样是不行的
	ByteBuffer buf = ByteBuffer.allocate(1024); //会自动扩容吗?//不会的,所以要事先计算好大小
	
	public Type32to64(int size)
	{
		buf = ByteBuffer.allocate(size); 
	}//
	
	//java 默认是网络字节序列,普通 pc 的要改一下
	public void SetByteOrder_PC() 
	{
		buf.order(ByteOrder.LITTLE_ENDIAN);
	}//

	//java 默认是网络字节序列,普通 pc 的要改一下//恢复为网络字节序
	public void SetByteOrder_Net() 
	{
		buf.order(ByteOrder.BIG_ENDIAN);
	}//
	
	//写入一个 16 位整数
	public void WriteInt16(short i)
	{
		//buf.asShortBuffer().put(i);
		
		//asShortBuffer 就已经移动了指针,所以多次写入是没有用的,都写到的开头位置
		//Functions.ShowMessage(Integer.toString(buf.position()), null);
		
		////Functions.ShowMessage("put begin" + Integer.toString(buf.position()), null);

		buf.putShort(i);
		
		Functions.ShowMessage("put end" + Integer.toString(buf.limit()), null);
		
		
	}//
	
	//写入一个 32 位整数
	public void WriteInt32(int i)
	{

		buf.putInt(i);
		
		
	}//	
	
	//写入一个 8 位整数
	public void WriteInt8(Byte i)
	{

		buf.put(i);
		
		
	}//
	
	//写入一个指定长度的字符串,最好保证 0 结尾以便兼容 C 语言
	public void WriteString(String s, int len)
	{
		if (s == null) s = "";
		
		byte [] sbuf = s.getBytes();
		byte [] buf2 = new byte[len];
		
		for (int i=0; i<len; i++)
		{
			if (i<sbuf.length)
				buf2[i]= sbuf[i];
			else
				buf2[i]= 0; 
			
		}
		
		buf2[len-1] = 0; //保证 0 结尾
		
		buf.put(buf2);
		
		
	}//	
	
	//写入一个字节数组
	public void WriteBytes(byte [] bytes)
	{
		
		buf.put(bytes);
		
		
	}//		
	
	
		
	
	//读取一个 16 位整数
	public short ReadInt16()
	{
		//buf.rewind();//指针移到数据头
		//short i = buf.asShortBuffer().get();
		
		//指针是移动了的,所以读取前要先移动位置到正确的地方

		////Functions.ShowMessage("get begin" + Integer.toString(buf.position()), null);
		
		short i = buf.getShort();
		
		////Functions.ShowMessage("get end" + Integer.toString(buf.position()), null);
		
		return i;
	}//
	
	//数据指针移动到最前
	public void Seek0()
	{
		buf.rewind();//指针移到数据头
		//short i = buf.asShortBuffer().get();

	}//	
	
	//数据截断到当前位置//不好,影响多次调用
	public void Trunc()
	{
		buf.limit(buf.position()); //这个会移动位置的,所以读取前还要移动到最前面
		
	}//
	
	//读取全部数据//要写完数据后立即调用,因为只能读取到最后的写入位置处
	public byte[] GetBuf()
	{
		//this.Trunc();
		//buf.limit(2); //这个会移动位置的,所以读取前还要移动到最前面
		//byte[] r = buf.array();
		
		//byte[] r2 = new byte[buf.limit()];
		byte[] r2 = new byte[buf.position()];
		buf.rewind();//指针移到数据头
		buf.get(r2);

		//Functions.ShowMessage("all:" + Integer.toString(r.length), null);
		Functions.ShowMessage("all2:" + Integer.toString(r2.length), null);

		return r2;
	}//		
	
}//







