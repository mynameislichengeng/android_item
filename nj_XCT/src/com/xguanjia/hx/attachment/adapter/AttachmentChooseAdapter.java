package com.xguanjia.hx.attachment.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.chinamobile.salary.R;
import com.xguanjia.hx.attachment.bean.AttachmentBean;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.Tools;

public class AttachmentChooseAdapter extends BaseAdapter {
	private Context _context;

	private ArrayList<AttachmentBean> attachmentList;

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	private DisplayImageOptions options;

	public AttachmentChooseAdapter(Context context) {
		this._context = context;
		attachmentList = new ArrayList<AttachmentBean>();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.loading)
				.showImageForEmptyUri(R.drawable.loading).cacheInMemory()
				.cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	}

	@Override
	public int getCount() {
		return attachmentList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AttachmentBean bean = attachmentList.get(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(_context).inflate(
					R.layout.attachment_grid_item, null);
			holder = new ViewHolder();
			holder.fileImg = (ImageView) convertView
					.findViewById(R.id.fileDefaultImage);
			holder.fileNameView = (TextView) convertView
					.findViewById(R.id.fileNameView);
			holder.fileSizeView = (TextView) convertView
					.findViewById(R.id.fileSizeView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.fileNameView.setText(bean.getFileName());
		if (!"".equals(bean.getFileSize())) {
			holder.fileSizeView.setText(Tools.FileSizeForm(Long.valueOf(bean
					.getFileSize())));
		}
		if ("arm".equalsIgnoreCase(bean.getFileType())) {
			imageLoader.displayImage("", holder.fileImg);
			holder.fileImg.setBackgroundResource(R.drawable.file_audio_icon);
		} else {
			holder.fileImg.setBackgroundResource(0);
			if (bean.getIntentType().equals("image/*")) {
				String appPath = bean.getAppPath();
				if ("".equals(bean.getAppPath())) {
					appPath = Constants.SAVE_IMAGE_PATH
							+ bean.getServerPath().substring(
									bean.getServerPath().lastIndexOf("/") + 1);
				}
				imageLoader.displayImage("file://" + appPath, holder.fileImg,
						options);
			} else {
				SharedPreferences sf = _context.getSharedPreferences(
						Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
				imageLoader.displayImage(
						"http://" + sf.getString("ip", Constants.IP)
								+ bean.getFileUrl(), holder.fileImg, options);
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

	public ArrayList<AttachmentBean> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(ArrayList<AttachmentBean> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public void changeDataNotify(ArrayList<AttachmentBean> list) {
		this.attachmentList = list;
		this.notifyDataSetChanged();
	}

	private class ViewHolder {
		public TextView fileNameView, fileSizeView;
		public ImageView fileImg;
	}

	public void dataNotifyChange(ArrayList<AttachmentBean> list) {
		this.attachmentList = list;
		this.notifyDataSetChanged();
	}
}
