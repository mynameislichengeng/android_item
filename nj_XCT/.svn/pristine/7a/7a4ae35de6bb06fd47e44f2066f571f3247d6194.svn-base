package com.xguanjia.hx.filecabinet.adaptor;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.salary.R;
import com.xguanjia.hx.contact.bean.PersonBean;

/**
 * 组织机构联系人适配器 
 * @author 杨通杆
 * @date 2012-08-31
 * 
 */
public class InternalPersonListAdapter extends BaseAdapter {
	private Context context;
	private List<PersonBean> personList; 
    
	public InternalPersonListAdapter(Context context, List<PersonBean> list) {
		this.context = context;
		if (personList == null) {
			personList = new ArrayList<PersonBean>();
		}
		this.personList = list;
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
		holder = new ViewHolder();
		if ("".equals(bean.getGroupId())) {
			if (convertView == null || convertView.getId() == 1) {
				convertView = LayoutInflater.from(context).inflate(R.layout.internal_selected_list_item, null);
				convertView.setId(0);
				holder.contactNameBgTv = (TextView) convertView.findViewById(R.id.contactNameBgTv);
				holder.txtView_constant = (TextView) convertView.findViewById(R.id.txtView_constant);
				holder.phoneNumView = (TextView) convertView.findViewById(R.id.phoneNumView);
				holder.cbSelectedPerson = (CheckBox)convertView.findViewById(R.id.cb_selected_person);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.contactNameBgTv.setText(bean.getName().substring(bean.getName().length() - 1, bean.getName().length()));
			holder.txtView_constant.setText(bean.getName());
			holder.phoneNumView.setText(bean.getMobile());
			if(SelectedUserID.getUserId(bean.getUserId())!= null){
				holder.cbSelectedPerson.setChecked(true);
			}else{
				holder.cbSelectedPerson.setChecked(false);
			}				
			holder.cbSelectedPerson.setOnClickListener(new CheckBoxEditorActionListener(bean));	
		} else {
			if (convertView == null || convertView.getId() == 0) {
				convertView = LayoutInflater.from(context).inflate(R.layout.internalcontact_list_item, null);
				convertView.setId(1);
				holder.orgImageIcon = (TextView) convertView.findViewById(R.id.orgImageIconText);
				holder.organizationalTv = (TextView) convertView.findViewById(R.id.organizationalTv);
				holder.orgImageIv = (ImageView) convertView.findViewById(R.id.orgImageIv);
				holder.orgImageIv.setImageResource(R.drawable.bg_right);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.organizationalTv.setText(bean.getName());
			holder.orgImageIcon.setText(bean.getName().substring(bean.getName().length() - 1, bean.getName().length()));
		}

		return convertView;
	}
	
	/**
	 * CheckBox 编辑事件监听器
	 * @author ytg
	 * @date 2012-08-31
	 */
	class CheckBoxEditorActionListener implements OnClickListener {
		private PersonBean bean;
		public CheckBoxEditorActionListener(PersonBean bean) {
			super();
			this.bean = bean;
		}
		@Override
		public void onClick(View v) {
			boolean flag = ((CheckBox)v).isChecked();
			//bean.setShareChecked(flag);
			if(flag){				
				SelectedUserID.selectedUser(bean.getUserId());
			}else{
				SelectedUserID.unSelectedUser(bean.getUserId());
			}
		}		
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
	
	public static class ViewHolder {
		public View convertView;
		public TextView orgImageIcon;
		public TextView organizationalTv, contactNameBgTv, txtView_constant, phoneNumView, personCountTv;
		public ImageView orgImageIv;
		public CheckBox cbSelectedPerson;
	}

}
