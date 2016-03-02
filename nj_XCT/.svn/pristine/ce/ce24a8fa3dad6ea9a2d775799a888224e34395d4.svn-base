package com.xguanjia.hx.attachment.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.salary.R;
import com.xguanjia.hx.attachment.bean.AttachmentBean;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.AsyncDownloadFile;
import com.xguanjia.hx.common.MAMessage;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;


public class AttachmentListAdapter extends BaseAdapter {
	private final String TAG = "AttachmentListAdapter";
	private Context context;
	private ProgressDialog pd;
	private List<AttachmentBean> attachmentList;

	public AttachmentListAdapter(Context context) {
		this.context = context;
		attachmentList = new ArrayList<AttachmentBean>();
	}

	public AttachmentListAdapter(Context context, List<AttachmentBean> attachmentList) {
		this.context = context;
		this.attachmentList = attachmentList;
	}

	@Override
	public int getCount() {
		return attachmentList.size();
	}

	@Override
	public Object getItem(int position) {
		return attachmentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final AttachmentBean bean = attachmentList.get(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.attachment_item, null);
			holder = new ViewHolder();
			holder.attachtTypeIv = (ImageView) convertView.findViewById(R.id.attachTypeIv);
			holder.attachNameTv = (TextView) convertView.findViewById(R.id.attachNameTv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.attachtTypeIv.setImageResource(bean.getFileIcon());
		holder.attachNameTv.setText(bean.getFileName());
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				downloadFile(bean);
			}
		});
		return convertView;
	}

	static class ViewHolder {
		ImageView attachtTypeIv;
		TextView attachNameTv;
	}

	/**
	 * 下载文件，下载成功后调用系统工具打开文件
	 * 
	 * @param fileBean
	 */
	public void downloadFile(final AttachmentBean fileBean) {
		if (null == ((BaseActivity) context).getSDcard()) {
			MAMessage.ShowMessage(context, R.string.prompt_message, R.string.please_check_sd);
			return;
		}

		pd = ProgressDialog.show(context, "", "数据获取中", true, true);
		String filePath = ((BaseActivity) context).getSDcard().toString() + "/moa/" + fileBean.getFileName();

		AsyncDownloadFile download = new AsyncDownloadFile(this.context);
		download.setFileUrl(fileBean.getServerPath());
		download.setFilePath(filePath);
		download.setListener(new ServiceSyncListener() {

			@Override
			public void onError(ActionResponse returnObject) {
				Log.i(TAG, "error" + returnObject.getMessage());
				MAMessage.ShowMessage(context, R.string.download_file_faile, returnObject.getMessage());
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
			}

			@Override
			public void onSuccess(ActionResponse returnObject) {
				Log.i(TAG, "success" + returnObject.getMessage());
				if (null == returnObject.getData()) {
					if (((Activity) context).isTaskRoot()) {
						MAMessage.ShowMessage(context, R.string.download_file_faile, returnObject.getMessage());
					}
				}
				File file = (File) returnObject.getData();
				try {
					String intentType = fileBean.getIntentType();
					if (null == intentType || "".equals(intentType)) {
						if (((Activity) context).isTaskRoot()) {
							MAMessage.ShowMessage(context, R.string.download_file_faile, "不支持的格式");
						}

						return;
					}
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(file), intentType);
					context.startActivity(intent);
				} catch (ActivityNotFoundException e) {
					MAMessage.ShowMessage(context, R.string.download_file_faile, returnObject.getMessage());
				}
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
			}

		});
		download.execute("");
	}

}

