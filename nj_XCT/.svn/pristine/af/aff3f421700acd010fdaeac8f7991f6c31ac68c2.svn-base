package com.xguanjia.hx.contact.adapter;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.chinamobile.salary.R;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.contact.bean.PersonBean;
import com.xguanjia.hx.contact.service.PersonAllService;

public class ContactAllOrganizeAdapter extends BaseAdapter {

	private List<PersonBean> _personList;
	private PersonAllService personSelfService;
	private Context _context;
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	private DisplayImageOptions options;

	private SharedPreferences sf1;
	public ContactAllOrganizeAdapter(List<PersonBean> personList, Context context) {
		this._personList = personList;
		this._context = context;
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_avatar1).showImageForEmptyUri(R.drawable.default_avatar1).cacheInMemory().cacheOnDisc().displayer(
				new RoundedBitmapDisplayer(10)).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		personSelfService = new PersonAllService(context);
	}

	@Override
	public int getCount() {
		return _personList.size();
	}

	@Override
	public Object getItem(int position) {
		return _personList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PersonBean bean = _personList.get(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(_context).inflate(R.layout.contact_organize_item, null);
			holder = new ViewHolder();
			holder.contactFirstLetterText = (ImageView) convertView.findViewById(R.id.contact_firstLetter_text);
			holder.contactUserNameText = (TextView) convertView.findViewById(R.id.contact_userName_text);
			holder.contactPhoneNumText = (TextView) convertView.findViewById(R.id.contact_phoneNum_text);
			holder.contactPostText = (TextView) convertView.findViewById(R.id.contact_post_text);
			holder.departmentCountView = (TextView) convertView.findViewById(R.id.departmentCountView);
			holder.contactArrow = (ImageView) convertView.findViewById(R.id.imgContactArrow);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 组织结构
		if (bean.getGroupId() != null && !"".equals(bean.getGroupId())) {
			List<PersonBean> childList = personSelfService.queryChildOrg(bean.getGroupId());
			holder.departmentCountView.setVisibility(View.VISIBLE);
			holder.contactArrow.setVisibility(View.VISIBLE);
			holder.departmentCountView.setText("(" + childList.size() + ")");
			holder.contactUserNameText.setText(bean.getName());
			holder.contactPhoneNumText.setVisibility(View.GONE);
			holder.contactPostText.setVisibility(View.GONE);
			holder.contactFirstLetterText.setImageResource(R.drawable.oragizehead_img);
		} else {
			holder.departmentCountView.setVisibility(View.GONE);
			holder.contactArrow.setVisibility(View.GONE);
			// 普通人员
			holder.contactUserNameText.setText(bean.getName());
			if (!"".equals(bean.getMobile())) {
				holder.contactPhoneNumText.setVisibility(View.VISIBLE);
				holder.contactPhoneNumText.setText(bean.getMobile());
			} else {
				holder.contactPhoneNumText.setVisibility(View.GONE);
			}
			if (!"".equals(bean.getPost())) {
				holder.contactPostText.setVisibility(View.VISIBLE);
				holder.contactPostText.setText(bean.getPost());
			} else {
				holder.contactPostText.setVisibility(View.GONE);
				holder.contactPostText.setText("");
			}
			sf1 = _context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
			imageLoader.displayImage("http://"+sf1.getString("ip", Constants.IP)+bean.getAvatar(),
					holder.contactFirstLetterText, options);
		}
		return convertView;
	}

	private static class ViewHolder {
		public TextView contactUserNameText, contactPhoneNumText, contactPostText, departmentCountView;
		public ImageView contactFirstLetterText;
		public ImageView contactArrow;
	}

	public void setDataChanged(List<PersonBean> personList) {
		_personList = personList;
		this.notifyDataSetChanged();
	}

}


