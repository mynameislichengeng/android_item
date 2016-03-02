package com.xguanjia.hx.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.chinamobile.salary.R;

//弹出框管理类
public class MAMessage  {
	

	//不提供实例化，只提供静态工具接口
	private MAMessage() {}
	
	//弹出消息框：标题，内容；确定
	public static void ShowMessage(final Context context, String title, final String detail, int icon) {
		//MAlertDialog alertDialog = new MAlertDialog(context);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle(title);
		alertDialog.setMessage(detail);
		
		alertDialog.setNegativeButton(R.string.make_sure, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				}
			
		});
		
		alertDialog.show();
	}
	//弹出消息框：标题，内容；确定
	public static void ShowMessage(final Context context, String title, String detail) {
		MAMessage.ShowMessage(context, title, detail, -1);
	}
	//弹出消息框：标题，内容；确定
	public static void ShowMessage(Context context, int title, int detail) {
//		if(((Activity)context).isTaskRoot()){
			MAMessage.ShowMessage(context, context.getString(title), context.getString(detail), -1);
//		}
		
	}
	//弹出消息框：标题，内容；确定
	public static void ShowMessage(Context context, int title, String detail) {
//		if(((Activity)context).isTaskRoot()){
			MAMessage.ShowMessage(context, context.getString(title), detail, -1);
//		}
		
	}
	
//	//弹出消息框：标题，内容；确定,取消
//	public static MAlertDialog ShowMessageForCommand(final Context context, String title, String detail) {
//		final MAlertDialog alertDialog = new MAlertDialog(context);
//		alertDialog.setTitle(title);
//		alertDialog.setMessage(detail);
//		return alertDialog;
//	}
	

	//弹出消息框：标题，内容；确定,取消
	public static AlertDialog ShowMessageForCommand(final Context context, int title, int detail, int icon) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (icon > 0) builder.setIcon(icon);
		if (title > 0) builder.setTitle(title);
		if (detail > 0) builder.setMessage(detail);
		builder.setPositiveButton(R.string.make_sure, new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			//执行确定按钮动作
     		//((MCBaseActivity)context).onPopupMessageConfirm();
			}
		});
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				//执行取消按钮动作
			//((MCBaseActivity)context).onPopupMessageCancel();
			}
		});
		return builder.create();
	}
}
