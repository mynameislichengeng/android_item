package com.xguanjia.hx.contact.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.salary.R;
import com.xguanjia.hx.contact.bean.PersonBean;
import com.xguanjia.hx.contact.service.PersonService;

/**
 * 组织机构联系人适配器
 * 
 * @author dolphin
 * 
 */
public class InternalPersonListAdapter extends BaseAdapter {
	private Context context;
	private List<PersonBean> personList;
	private PersonService personService;

	public InternalPersonListAdapter(Context context, List<PersonBean> list) {
		this.context = context;
		if (personList == null) {
			personList = new ArrayList<PersonBean>();
		}
		this.personList = list;
		personService = new PersonService(context);
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
		if ("".equals(bean.getGroupId())) {
			if (convertView == null || convertView.getId() == 1) {
				convertView = LayoutInflater.from(context).inflate(R.layout.internalperson_list_item, null);
				holder = new ViewHolder();
				convertView.setId(0);
				holder.contactNameBgTv = (TextView) convertView.findViewById(R.id.contactNameBgTv);
				holder.txtView_constant = (TextView) convertView.findViewById(R.id.txtView_constant);
				holder.phoneNumView = (TextView) convertView.findViewById(R.id.phoneNumView);
				holder.listPostTv = (TextView) convertView.findViewById(R.id.listPostTv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.contactNameBgTv.setText(bean.getName().trim().substring(bean.getName().trim().length() - 1, bean.getName().trim().length()));
			holder.txtView_constant.setText(bean.getName());
			holder.phoneNumView.setText(bean.getMobile());
			holder.listPostTv.setText(bean.getPost());
		} else {
			if (convertView == null || convertView.getId() == 0) {
				convertView = LayoutInflater.from(context).inflate(R.layout.internalcontact_list_item, null);
				convertView.setId(1);
				holder = new ViewHolder();
				holder.orgImageIcon = (ImageView) convertView.findViewById(R.id.orgImageIcon);
				holder.organizationalTv = (TextView) convertView.findViewById(R.id.organizationalTv);
				holder.orgImageIv = (ImageView) convertView.findViewById(R.id.orgImageIv);
				holder.orgImageIv.setImageResource(R.drawable.bg_right);
				holder.personCountTv = (TextView) convertView.findViewById(R.id.personCountTv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.organizationalTv.setText(bean.getName());
			// 查看该组织机构下的直属子节点
			List<PersonBean> childList = personService.queryChildOrg(bean.getGroupId());
			if (childList != null && !childList.isEmpty()) {
				holder.personCountTv.setText(childList.size() + "");
			}
		}

		return convertView;
	}

	public static class ViewHolder {
		public ImageView orgImageIcon;
		public TextView organizationalTv, contactNameBgTv, txtView_constant, phoneNumView, personCountTv, listPostTv;
		public ImageView orgImageIv;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public List<PersonBean> getPersonList() {
		return personList;
	}

	public void setPersonList(List<PersonBean> personList) {
		this.personList = personList;
	}

}
