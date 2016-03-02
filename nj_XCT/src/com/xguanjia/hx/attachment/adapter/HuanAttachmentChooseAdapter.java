package com.xguanjia.hx.attachment.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
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
import com.xguanjia.hx.attachment.bean.HuanAttachmentBean;
import com.xguanjia.hx.common.Constants;

public class HuanAttachmentChooseAdapter extends BaseAdapter {
	private Context _context;

	private List<HuanAttachmentBean> attachmentList;

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	private DisplayImageOptions options;
	private ViewHolder holder;

	public HuanAttachmentChooseAdapter(Context context) {
		this._context = context;
		attachmentList = new ArrayList<HuanAttachmentBean>();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.file_picture_icon)
				.showImageForEmptyUri(R.drawable.file_picture_icon).cacheInMemory()
				.cacheOnDisc().build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	}

	@Override
	public int getCount() {
		return attachmentList.size();
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
					R.layout.attachment_grid_item, null);
			holder = new ViewHolder();
			holder.fileImg = (ImageView) convertView
					.findViewById(R.id.fileDefaultImage);
			holder.fileNameView = (TextView) convertView
					.findViewById(R.id.fileNameView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.fileImg.setVisibility(View.VISIBLE);
		holder.fileNameView.setVisibility(View.VISIBLE);
		HuanAttachmentBean bean = attachmentList.get(position);
		holder.fileNameView.setText(bean.getFileName());
		if("jpg".equals(bean.getFileExt())||"png".equals(bean.getFileExt())){
			SharedPreferences sf = _context.getSharedPreferences(
					Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
			imageLoader.displayImage("http://" + sf.getString("ip", Constants.IP)
					+ bean.getFileUrl(), holder.fileImg, options);
			bean.setIntentType("image/*");
		}else if("apk".equals(bean.getFileExt())||"ipa".equals(bean.getFileExt())){
			holder.fileImg.setBackgroundResource(R.drawable.file_apk_icon);
			bean.setIntentType("application/vnd.android.package-archive");
		}else if("txt".equals(bean.getFileExt())||"text".equals(bean.getFileExt())){
			holder.fileImg.setBackgroundResource(R.drawable.file_txt_icon);
			bean.setIntentType("text/plain");
		}else if("rar".equals(bean.getFileExt())){
			holder.fileImg.setBackgroundResource(R.drawable.file_compressed_icon_rar);
			bean.setIntentType("application/x-rar-compressed");
		}else if("zip".equals(bean.getFileExt())){
			holder.fileImg.setBackgroundResource(R.drawable.file_compressed_icon_7);
			bean.setIntentType("application/zip");
		}else if("pdf".equals(bean.getFileExt())){
			holder.fileImg.setBackgroundResource(R.drawable.file_pdf_icon);
			bean.setIntentType("application/pdf");
		}else if("ppt".equals(bean.getFileExt())||"pptx".equals(bean.getFileExt())){
			holder.fileImg.setBackgroundResource(R.drawable.file_ppt_icon);
			bean.setIntentType("application/vnd.ms-powerpoint");
		}else if("xls".equals(bean.getFileExt())||"xlsx".equals(bean.getFileExt())){
			holder.fileImg.setBackgroundResource(R.drawable.file_xls_icon);
			bean.setIntentType("application/vnd.ms-excel");
		}else if("doc".equals(bean.getFileExt())||"docx".equals(bean.getFileExt())){
			holder.fileImg.setBackgroundResource(R.drawable.file_word_icon);
			bean.setIntentType("application/msword");
		}else if("java".equals(bean.getFileExt())){
			holder.fileImg.setBackgroundResource(R.drawable.file_java_icon);
			bean.setIntentType("application/java");
		}
		return convertView;
	}

	private class ViewHolder {
		public TextView fileNameView;
		public ImageView fileImg;
	}

	public void dataNotifyChange(List<HuanAttachmentBean> list) {
		this.attachmentList = list;
		this.notifyDataSetChanged();
	}

	public static int getWidthPixels(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.widthPixels;
	}
}
