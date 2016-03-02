package com.xguanjia.hx.payroll.adapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.chinamobile.salary.R;
import com.xguanjia.hx.DesUtil;
import com.xguanjia.hx.payroll.bean.ItemBean_grouplist;
import com.xguanjia.hx.payroll.bean.ItemBean_templist;
import com.xguanjia.hx.payroll.bean.Value_ItemBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Item_ditaiAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<Value_ItemBean> valueList = new ArrayList<Value_ItemBean>();
	private ArrayList<ItemBean_templist> itemList = new ArrayList<ItemBean_templist>();
	private ItemBean_grouplist title;

	@Override
	public int getCount() {
		return itemList.size() + 1;
	}

	public Item_ditaiAdapter(Context mContext,
			ArrayList<Value_ItemBean> valueList,
			ArrayList<ItemBean_templist> itemList, ItemBean_grouplist title) {
		super();
		this.mContext = mContext;
		this.valueList = valueList;
		this.itemList = itemList;
		this.title = title;
	}

	@Override
	public Object getItem(int position) {
		if (position == 0) {
			return null;
		} else {
			return itemList.get(position);
		}

	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	VH vh;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			vh = new VH();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.a_item, null);
			vh.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			vh.tv_content_title = (TextView) convertView
					.findViewById(R.id.tv_content_title);
			vh.tv_value = (TextView) convertView
					.findViewById(R.id.tv_content_value);
			convertView.setTag(vh);
		} else {
			vh = (VH) convertView.getTag();
		}
		if (position == 0) {
			vh.tv_content_title.setVisibility(View.GONE);
			vh.tv_value.setVisibility(View.GONE);
			vh.tv_title.setVisibility(View.VISIBLE);
			vh.tv_title.setText(title.getGroupingName());
			return convertView;

		} else {
			vh.tv_content_title.setVisibility(View.VISIBLE);
			vh.tv_value.setVisibility(View.VISIBLE);
			vh.tv_title.setVisibility(View.GONE);
			String ct = itemList.get(position - 1).getSalaryFieldName();
			String point = itemList.get(position - 1).getBasisName();
			// 当title.getSalaryGroupId()为1时，给什么显示什么
			if ("1".equals(title.getSalaryGroupId())) {

				for (int i = 0; i < valueList.size(); i++) {
					Class valueClass = (Class) valueList.get(i).getClass();
					try {
						Field field = valueClass.getDeclaredField(point);
						field.setAccessible(true);
						Object str = field.get(valueList.get(i));
						// 数据解密
						String decodeValue25 = null;
						DesUtil desUtil = new DesUtil();
						decodeValue25 = desUtil.decrypt(str.toString(),
								"haoqeeJSXCT!@#$%^&*");
						vh.tv_value.setText(String.valueOf(decodeValue25));
						vh.tv_content_title.setText(ct);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			} else {//当不是1时，要转换成float形式相加

				Float f_dan = 0.0f;
				for (int i = 0; i < valueList.size(); i++) {
					Class valueClass = (Class) valueList.get(i).getClass();
					try {
						Field field = valueClass.getDeclaredField(point);
						field.setAccessible(true);
						Object str = field.get(valueList.get(i));
						// 数据解密
						String decodeValue25 = null;

						DesUtil desUtil = new DesUtil();

						decodeValue25 = desUtil.decrypt(str.toString(),
								"haoqeeJSXCT!@#$%^&*");
						f_dan = Float.valueOf(decodeValue25) + f_dan;

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				vh.tv_value.setText(String.valueOf(f_dan));
				vh.tv_content_title.setText(ct);
			}

		}

		return convertView;
	}

	static class VH {
		TextView tv_title, tv_content_title, tv_value;
	}

}
