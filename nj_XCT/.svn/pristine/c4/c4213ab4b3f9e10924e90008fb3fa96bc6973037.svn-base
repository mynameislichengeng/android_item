package com.xguanjia.hx.payroll.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.payroll.bean.FilesBean;
import com.chinamobile.salary.R;

public class AttachmentAdapter extends BaseAdapter {
	private Context _context;

	private List<FilesBean> attachmentList;

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	private DisplayImageOptions options;
	private ViewHolder holder;

	public AttachmentAdapter(Context context) {
		this._context = context;
		attachmentList = new ArrayList<FilesBean>();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.load1)
				.showImageForEmptyUri(R.drawable.load1).cacheInMemory()
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		System.out.println("当前位置==========》" + position);
		if (convertView == null) {
			convertView = LayoutInflater.from(_context).inflate(
					R.layout.attachment_grid_item1, null);
			holder = new ViewHolder();
			holder.fileImg = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		SharedPreferences sf = _context.getSharedPreferences(
				Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		imageLoader.displayImage("http://" + sf.getString("ip", Constants.IP)
				+ "/attachFiles/"+attachmentList.get(position).getFilePath(), holder.fileImg,
				options);
		return convertView;
	}

	private class ViewHolder {
		public ImageView fileImg;
	}

	public void dataNotifyChange(List<FilesBean> list) {
		this.attachmentList = list;
		this.notifyDataSetChanged();
	}
}
