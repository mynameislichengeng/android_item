package com.xguanjia.hx.payroll.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.xguanjia.hx.attachment.bean.AttachmentBean;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.Tools;
import com.chinamobile.salary.R;

public class AttachmentChooseAdapter extends BaseAdapter {
	private Context _context;

	private List<AttachmentBean> attachmentList;

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	private DisplayImageOptions options;
	private ViewHolder holder;

	public AttachmentChooseAdapter(Context context) {
		this._context = context;
		attachmentList = new ArrayList<AttachmentBean>();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.load)
				.showImageForEmptyUri(R.drawable.load).cacheInMemory()
				.cacheOnDisc().build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	}

	@Override
	public int getCount() {
		return attachmentList.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("当前位置==========》" + position);
		if (convertView == null) {
			convertView = LayoutInflater.from(_context).inflate(
					R.layout.attachment_grid_item2, null);
			holder = new ViewHolder();
			holder.fileImg = (ImageView) convertView
					.findViewById(R.id.fileDefaultImage);
			holder.fileNameView = (TextView) convertView
					.findViewById(R.id.fileNameView);
			holder.fileSizeView = (TextView) convertView
					.findViewById(R.id.fileSizeView);
			holder.addImag = (ImageView) convertView
					.findViewById(R.id.addphoto);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position == attachmentList.size()) {
			holder.fileNameView.setVisibility(View.GONE);
			holder.fileSizeView.setVisibility(View.GONE);
			holder.addImag.setVisibility(View.VISIBLE);
			holder.fileImg.setVisibility(View.GONE);
//			holder.addImag.setImageResource(R.drawable.addphoto);
		} else {
			holder.addImag.setVisibility(View.GONE);
			holder.fileImg.setVisibility(View.VISIBLE);
			holder.fileNameView.setVisibility(View.VISIBLE);
//			holder.fileSizeView.setVisibility(View.VISIBLE);
			AttachmentBean bean = attachmentList.get(position);
			holder.fileNameView.setText(bean.getFileName());
			String path = "";
			if ("".equals(bean.getFileName())
					|| bean.getFileName() == null) {
				path = Constants.SAVE_IMAGE_PATH + bean.getFileName();
			} else {
				path = Constants.SAVE_IMAGE_PATH + bean.getFileName();
			}
			File file = new File(path);
			if (bean.getIntentType().equals("image/*")) {
				if (file.exists()) {
					holder.fileImg.setBackgroundResource(0);
					bean.setFileSize(file.length() + "");
					holder.fileSizeView.setText(Tools.FileSizeForm(Long
							.valueOf(bean.getFileSize())));
					imageLoader.displayImage("file://" + path,
							holder.fileImg, options);
				} else {
					holder.fileImg.setBackgroundResource(0);
					imageLoader.displayImage("file://" + bean.getLocalPath(),
							holder.fileImg, options);
				}
			} else {
				imageLoader.displayImage("", holder.fileImg);
				holder.fileImg.setBackgroundResource(bean.getFileIcon());
			}

		}

		return convertView;
	}

	public Context get_context() {
		return _context;
	}

	public void set_context(Context context) {
		_context = context;
	}

	public void changeDataNotify(List<AttachmentBean> list) {
		this.attachmentList = list;
		this.notifyDataSetChanged();
	}

	private class ViewHolder {
		public TextView fileNameView, fileSizeView;
		public ImageView fileImg, addImag;
	}

	public void dataNotifyChange(ArrayList<AttachmentBean> list) {
		this.attachmentList = list;
		this.notifyDataSetChanged();
	}
}
