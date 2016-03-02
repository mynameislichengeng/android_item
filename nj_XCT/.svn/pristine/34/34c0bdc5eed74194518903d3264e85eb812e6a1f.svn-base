package com.xguanjia.hx.filecabinet.adaptor;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.salary.R;
import com.xguanjia.hx.filecabinet.pojo.FileBean;

public class FileListAdaptor extends BaseAdapter {

	private List<FileBean> mData;
	private LayoutInflater inflater;
	private List<View> fileViews;
	private String fileType;
	private Context context;
	
	//xx
	private boolean flag = true;
	 public List<Boolean> mChecked;
	 

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public FileListAdaptor(Context context, List<FileBean> data) {
		this.context = context;
		this.mData = data;
		this.inflater = ((Activity) context).getLayoutInflater();
		fileViews = new ArrayList<View>();
		
		//xx
		mChecked = new ArrayList<Boolean>();
		for(int i = 0;i < mData.size(); i++){
		                mChecked.add(false);
					}
		
	}

	public FileListAdaptor(Context context) {
		this.context = context;
		this.inflater = ((Activity) context).getLayoutInflater();
		fileViews = new ArrayList<View>();
	}

	@Override
	public int getCount() {
		if (null != mData) {
			return mData.size();
		}

		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (null == mData) {
			return null;
		}
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		View itemView = position >= fileViews.size() ? null : fileViews.get(position);
		ViewHolder holder;
		final FileBean fileBean = mData.get(position);
		if (convertView == null) {
			
			
			convertView = inflater.inflate(R.layout.files_item, null);
			 holder = new ViewHolder();
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		
		ImageView iconView = (ImageView) convertView.findViewById(R.id.file_icon);
		ImageView rightView = (ImageView) convertView.findViewById(R.id.file_right);
		TextView fileNameView = (TextView) convertView.findViewById(R.id.file_Name);
		TextView fileSizeView = (TextView) convertView.findViewById(R.id.file_size);
		TextView fileCreatedateView = (TextView) convertView.findViewById(R.id.file_create_date);
		
		//xx管理模式
		final int p = position;
		holder.cb = (CheckBox) convertView.findViewById(R.id.file_right_cb);
		holder.cb.setChecked(false);
		holder.cb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox)v;
				if(!fileBean.getDef().equals("1")){
					
					if(mChecked.get(p)){
						mChecked.set(p, false);
					}else{
						mChecked.set(p, true);
					}
						cb.setChecked(mChecked.get(p));
				}else{
					mChecked.set(p, false);
					cb.setChecked(mChecked.get(p));
					Toast.makeText(context, "默认文件夹不能删除", 0).show();
				}
				
			}
		});
		//true 管理模式 false 普通模式
		if(!flag){
			holder.cb.setVisibility(View.VISIBLE);
//			holder.cb.setFocusable(false);
			rightView.setVisibility(View.GONE);
//			notifyDataSetChanged();
			
		}else{
			holder.cb.setVisibility(View.GONE);
			rightView.setVisibility(View.VISIBLE);
		}
		
		convertView.setTag(holder);

		if (null != fileBean) {
			if (-1 == fileBean.getFileIcon()) {
				iconView.setImageResource(R.drawable.file_directory_icon);
			} else {
				iconView.setImageResource(fileBean.getFileIcon());
			}

			if (null != fileBean.getFileName()) {
				fileNameView.setText(fileBean.getFileName());
			}

			if ("2".equals(fileType)) {
				if (null != fileBean.getShareTime()) {
					fileCreatedateView.setText("时间:" + fileBean.getShareTime());
				}
				if (null != fileBean.getShareUserName()) {
					fileSizeView.setText("分享人:" + fileBean.getShareUserName());
				}
			} else {

				if (null != fileBean.getReleaseDate()) {
					fileCreatedateView.setText(fileBean.getReleaseDate());
				}

				if (fileBean.getIsDirectory().equals("true")) {
					fileSizeView.setText(fileBean.getSubFiles() + "个文件");
					rightView.setImageResource(R.drawable.bg_right);
				} else {
					fileSizeView.setText(fileBean.getFileSize() + "KB");
					rightView.setVisibility(View.GONE);
				}

			}
		}
		return convertView;
	}

	public void setData(List<FileBean> data) {
		this.mData = data;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	//xx
	public class ViewHolder {
        public CheckBox cb = null;
}

}
