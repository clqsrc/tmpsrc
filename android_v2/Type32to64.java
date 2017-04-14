package apublic;

//import android.R.string;
import java.nio.*;

import android.R.integer;


//������ unit Type32to64;


/*
java ��int �ķ�Χ��
��������            ��С       ��Χ                                             Ĭ��ֵ 

byte(�ֽ�) 	        8         -128 - 127                                           0
shot(������)        16      -32768 - 32768                                         0
int(����)           32   -2147483648-2147483648                                    0
long(������)        64   -9233372036854477808-9233372036854477808                  0        
float(������)       32  -3.40292347E+38-3.40292347E+38                            0.0f
double(˫����)	    64  -1.79769313486231570E+308-1.79769313486231570E+308        0.0d
char(�ַ���)        16         �� \u0000 - u\ffff ��                             ��\u0000 ��
boolean(������)     1         true/false                                         false
*/


public class Type32to64 
{
	//java ����ʵ��û���޷��� 16 λ������
	public static short uint16(short i)
	{
		return i;
		
	}//

	public static int uint32(int i)
	{
		return i;
		
	}//
	
	//8λ���޷��ź����������,һ��ҪС��
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
	
	//8λ���޷��ź����������,һ��ҪС��
	public static byte int8(byte i)
	{
		return i;
		
	}//
	
	public static long int64(long i)
	{
		return i;
		
	}//	

	//16���Ƶ��ַ���
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
	//��������Ϊ���������ķ����Բ���,�����Ƕ�����ԭʼ���ݲ�������,��������  java.nio.ByteBuffer  �ķ�װ,��Ϊԭʼ  java.nio.ByteBuffer �ӿںܲ�����
	
	//ByteBuffer buf = ByteBuffer(12);//�Ƚ�����,�����ǲ��е�
	ByteBuffer buf = ByteBuffer.allocate(1024); //���Զ�������?//�����,����Ҫ���ȼ���ô�С
	
	public Type32to64(int size)
	{
		buf = ByteBuffer.allocate(size); 
	}//
	
	//java Ĭ���������ֽ�����,��ͨ pc ��Ҫ��һ��
	public void SetByteOrder_PC() 
	{
		buf.order(ByteOrder.LITTLE_ENDIAN);
	}//

	//java Ĭ���������ֽ�����,��ͨ pc ��Ҫ��һ��//�ָ�Ϊ�����ֽ���
	public void SetByteOrder_Net() 
	{
		buf.order(ByteOrder.BIG_ENDIAN);
	}//
	
	//д��һ�� 16 λ����
	public void WriteInt16(short i)
	{
		//buf.asShortBuffer().put(i);
		
		//asShortBuffer ���Ѿ��ƶ���ָ��,���Զ��д����û���õ�,��д���Ŀ�ͷλ��
		//Functions.ShowMessage(Integer.toString(buf.position()), null);
		
		////Functions.ShowMessage("put begin" + Integer.toString(buf.position()), null);

		buf.putShort(i);
		
		Functions.ShowMessage("put end" + Integer.toString(buf.limit()), null);
		
		
	}//
	
	//д��һ�� 32 λ����
	public void WriteInt32(int i)
	{

		buf.putInt(i);
		
		
	}//	
	
	//д��һ�� 8 λ����
	public void WriteInt8(Byte i)
	{

		buf.put(i);
		
		
	}//
	
	//д��һ��ָ�����ȵ��ַ���,��ñ�֤ 0 ��β�Ա���� C ����
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
		
		buf2[len-1] = 0; //��֤ 0 ��β
		
		buf.put(buf2);
		
		
	}//	
	
	//д��һ���ֽ�����
	public void WriteBytes(byte [] bytes)
	{
		
		buf.put(bytes);
		
		
	}//		
	
	
		
	
	//��ȡһ�� 16 λ����
	public short ReadInt16()
	{
		//buf.rewind();//ָ���Ƶ�����ͷ
		//short i = buf.asShortBuffer().get();
		
		//ָ�����ƶ��˵�,���Զ�ȡǰҪ���ƶ�λ�õ���ȷ�ĵط�

		////Functions.ShowMessage("get begin" + Integer.toString(buf.position()), null);
		
		short i = buf.getShort();
		
		////Functions.ShowMessage("get end" + Integer.toString(buf.position()), null);
		
		return i;
	}//
	
	//����ָ���ƶ�����ǰ
	public void Seek0()
	{
		buf.rewind();//ָ���Ƶ�����ͷ
		//short i = buf.asShortBuffer().get();

	}//	
	
	//���ݽضϵ���ǰλ��//����,Ӱ���ε���
	public void Trunc()
	{
		buf.limit(buf.position()); //������ƶ�λ�õ�,���Զ�ȡǰ��Ҫ�ƶ�����ǰ��
		
	}//
	
	//��ȡȫ������//Ҫд�����ݺ���������,��Ϊֻ�ܶ�ȡ������д��λ�ô�
	public byte[] GetBuf()
	{
		//this.Trunc();
		//buf.limit(2); //������ƶ�λ�õ�,���Զ�ȡǰ��Ҫ�ƶ�����ǰ��
		//byte[] r = buf.array();
		
		//byte[] r2 = new byte[buf.limit()];
		byte[] r2 = new byte[buf.position()];
		buf.rewind();//ָ���Ƶ�����ͷ
		buf.get(r2);

		//Functions.ShowMessage("all:" + Integer.toString(r.length), null);
		Functions.ShowMessage("all2:" + Integer.toString(r2.length), null);

		return r2;
	}//		
	
}//







