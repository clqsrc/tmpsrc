package com.sister;


//--------------------------------------------------
//����ӿ�//�������Լ����ļ���//��ʵ���������� delphi �ĺ���ָ�����Ͷ���
//public interface OnClickListener {
//public void OnClick(Button b);
//}
//--------------------------------------------------

public interface HttpThreadControlEvent 
{
	//�ɹ����¼�
	public void OnHttpData(HttpThreadControl httpThread);
	public void OnHttpError(HttpThreadControl httpThread);

}//

