package com.xguanjia.hx.attachment.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.salary.R;
import com.xguanjia.hx.attachment.adapter.AttachmentOperateAdapter;
import com.xguanjia.hx.attachment.bean.AttachmentOperateTypeBean;
import com.xguanjia.hx.common.Constants;

public class AttachmentOperateDialog extends Dialog{
	
	private Context context;
	
	protected AttachmentOperateDialog(Context context) {
		super(context);
		this.context=context;
	}
	
	 public AttachmentOperateDialog(Context context, int theme) {
	        super(context, theme);
	        this.context=context;
	    }

	
	 public static class Builder {
		 
	        private Context context;
	        private String title;
	        private View contentView;
	        private AttachmentOperateAdapter dataOperateAdapter;
	        private OnItemClickListener itemClickListener;
	 
	        public Builder(Context context) {
	            this.context = context;
	        }
	 
	        /**
	         * Set the Dialog title from resource
	         * @param title
	         * @return
	         */
	        public Builder setTitle(int title) {
	            this.title = (String) context.getText(title);
	            return this;
	        }
	 
	        /**
	         * Set the Dialog title from String
	         * @param title
	         * @return
	         */
	        public Builder setTitle(String title) {
	            this.title = title;
	            return this;
	        }
	 
	        /**
	         * Set a custom content view for the Dialog.
	         * If a message is set, the contentView is not
	         * added to the Dialog...
	         * @param v
	         * @return
	         */
	        public Builder setContentView(View v) {
	            this.contentView = v;
	            return this;
	        }
	 
	 
	        public Builder setItems(AttachmentOperateAdapter dataOperateAdapter,OnItemClickListener itemClickListener){
	        	this.dataOperateAdapter=dataOperateAdapter;
	        	this.itemClickListener=itemClickListener;

	        	return this;
	        }
	        
	        /**
	         * Create the custom dialog
	         */
	        public AttachmentOperateDialog create() {
	            LayoutInflater inflater = (LayoutInflater) context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            // instantiate the dialog with the custom Theme
	            final AttachmentOperateDialog dialog = new AttachmentOperateDialog(context,R.style.Dialog);
	            
	            View layout = inflater.inflate(R.layout.attach_operate, null);
	            
	          
	            dialog.addContentView(layout, new LayoutParams( LayoutParams.WRAP_CONTENT
	                    , LayoutParams.WRAP_CONTENT));
	            // set the dialog title
	            ((TextView) layout.findViewById(R.id.option_title)).setText(title);
	            
	            if (dataOperateAdapter != null) {
	                ListView listView=(ListView)layout.findViewById(R.id.attach_option);
	                listView.setAdapter(dataOperateAdapter);
	                listView.setOnItemClickListener(this.itemClickListener);
	            } else if (contentView != null) {
	                ((LinearLayout) layout.findViewById(R.id.content))
	                        .removeAllViews();
	                ((LinearLayout) layout.findViewById(R.id.content))
	                        .addView(contentView, 
	                                new LayoutParams(
	                                        LayoutParams.FILL_PARENT, 
	                                        LayoutParams.WRAP_CONTENT));
	            }
	            dialog.setContentView(layout);
	            Window dialogwindow= dialog.getWindow();
	            WindowManager.LayoutParams lp= dialogwindow.getAttributes();
	            WindowManager m = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
	            //设置dialog的宽度
	            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
	            lp.width = (int) (d.getWidth() * 0.80); 
	            dialogwindow.setAttributes(lp);
	            dialog.setCanceledOnTouchOutside(true);
	            
	            return dialog;
	        }
	 
	    }

	 
	 public List<AttachmentOperateTypeBean> operateData(int type) {
			List<AttachmentOperateTypeBean> operateList = new ArrayList<AttachmentOperateTypeBean>();
			// 编写邮件
			if (type == Constants.G_ONE) {
				for (int i = 0; i < 3; i++) {
					AttachmentOperateTypeBean bean = new AttachmentOperateTypeBean();
					if (i == 0) {
						bean.setOperateType(context.getString(R.string.stringPickPic));
					} else if (i == 1) {
						bean.setOperateType(context.getString(R.string.stringChoosePic));
					} else if (i == 2) {
						bean.setOperateType(context.getString(R.string.stringOtherFile));
					}
					operateList.add(bean);
				}
			}
			// 在编写界面选择附件操作
			else if (type == Constants.G_TWO) {
				for (int i = 0; i < 2; i++) {
					AttachmentOperateTypeBean bean = new AttachmentOperateTypeBean();
					if (i == 0) {
						bean.setOperateType(context.getString(R.string.stringPreviewFile));
					} else if (i == 1) {
						bean.setOperateType(context.getString(R.string.stringDeleteFile));
					}
					operateList.add(bean);
				}
			}
			// 在详情界面选择附件操作
			else if (type == Constants.G_THREE) {
				for (int i = 0; i < 1; i++) {
					AttachmentOperateTypeBean bean = new AttachmentOperateTypeBean();
					if (i == 0) {
						bean.setOperateType(context.getString(R.string.stringPreviewFile));
					}
					operateList.add(bean);
				}
			}
			return operateList;

		}
	
}
