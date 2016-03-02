package com.xguanjia.hx.contact.adapter;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.chinamobile.salary.R;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.PingYinUtil;
import com.xguanjia.hx.contact.bean.PersonBean;

public class ContactListAdapter extends BaseAdapter implements SectionIndexer {
	private List<PersonBean> personList;
	private Context context;

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	private DisplayImageOptions options;
	private SharedPreferences sf1;

	public ContactListAdapter(Context context, List<PersonBean> personList) {
		this.context = context;
		this.personList = personList;
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_avatar1).showImageForEmptyUri(R.drawable.default_avatar1).cacheInMemory().cacheOnDisc().displayer(
				new RoundedBitmapDisplayer(100)).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	}
	
	/**
	 * 刷新联系人列表数据
	 * @param personList
	 */
	public void dataChangeNotify(List<PersonBean> personList) {
		this.personList = personList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return personList.size();
	}

	@Override
	public Object getItem(int position) {
		return personList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PersonBean bean = personList.get(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.contact_list_item, null);
			holder = new ViewHolder();
			holder.contactNameBgTv = (TextView) convertView.findViewById(R.id.contactitem_catalog);
			holder.avatarImage = (ImageView) convertView.findViewById(R.id.contact_firstLetter_text);
			holder.lineImg=(ImageView)convertView.findViewById(R.id.lineImg);
			holder.contactUserNameText = (TextView) convertView.findViewById(R.id.contact_userName_text);
			holder.contactPhoneNumText = (TextView) convertView.findViewById(R.id.contact_phoneNum_text);
			holder.contactPostText = (TextView) convertView.findViewById(R.id.contact_post_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String catalog = bean.getSortIndex();
		if (position == 0) {
			holder.contactNameBgTv.setVisibility(View.VISIBLE);
			holder.lineImg.setVisibility(View.GONE);
			holder.contactNameBgTv.setText(catalog);
		} else {
			PersonBean nextBean = personList.get(position - 1);
			String lastCatalog = nextBean.getSortIndex();
			if (catalog.equals(lastCatalog)) {
				holder.lineImg.setVisibility(View.VISIBLE);
				holder.contactNameBgTv.setVisibility(View.GONE);
			} else {
				holder.lineImg.setVisibility(View.GONE);
				holder.contactNameBgTv.setVisibility(View.VISIBLE);
				holder.contactNameBgTv.setText(catalog);
			}
		}
//		 holder.contactFirstLetterText.setText(bean.getAvatar());
		holder.contactUserNameText.setText(bean.getName());
		holder.contactPhoneNumText.setText(bean.getPost());
		if (!"".equals(bean.getMobile())) {
			holder.contactPostText.setVisibility(View.VISIBLE);
			holder.contactPostText.setText(bean.getMobile());
		} else {
			holder.contactPostText.setVisibility(View.GONE);
		}
		sf1 = context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		imageLoader.displayImage("http://"+sf1.getString("ip", Constants.IP)+bean.getAvatar(), holder.avatarImage, options);
		return convertView;
	}

	public static class ViewHolder {
		public TextView contactNameBgTv;
		public TextView contactFirstLetterText, contactUserNameText, contactPhoneNumText, contactPostText;
		public ImageView avatarImage,lineImg;
	}

	@Override
	public int getPositionForSection(int section) {
		if (section == 35) {
			return 0;
		}
		for (int i = 0; i < personList.size(); i++) {
			String l = PingYinUtil.converterToFirstSpell(personList.get(i).getName()).substring(0, 1);
			char firstChar = l.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	public List<PersonBean> getPersonList() {
		return personList;
	}

	public void setPersonList(List<PersonBean> personList) {
		this.personList = personList;
	}

	public void setDataChanged(List<PersonBean> list) {
		personList = list;
		this.notifyDataSetChanged();
	}

}
