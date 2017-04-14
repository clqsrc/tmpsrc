package com.sister;


//--------------------------------------------------
//定义接口//必须在自己的文件中//其实就是类似于 delphi 的函数指针类型定义
//public interface OnClickListener {
//public void OnClick(Button b);
//}
//--------------------------------------------------

public interface HttpThreadControlEvent 
{
	//成功的事件
	public void OnHttpData(HttpThreadControl httpThread);
	public void OnHttpError(HttpThreadControl httpThread);

}//

