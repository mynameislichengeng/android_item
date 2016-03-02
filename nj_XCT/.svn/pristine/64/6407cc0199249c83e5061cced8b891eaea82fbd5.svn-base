package com.xguanjia.hx.payroll.adapter;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.salary.R;
import com.xguanjia.hx.DesUtil;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.payroll.bean.PayRoll;
import com.xguanjia.hx.payroll.bean.PayRollTypeGroup;
import com.xguanjia.hx.payroll.db.PayRollDb;

public class PayrollListAdapter extends BaseAdapter {
	private Context context;
	private List<PayRoll> payRollLists = new ArrayList<PayRoll>();
	private PayRollDb payRollDb;
	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SEPARATOR = 1;
	private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;

	public PayrollListAdapter(Context context) {
		this.context = context;
		payRollDb = new PayRollDb(context);
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}

	@Override
	public int getViewTypeCount() {
		return super.getViewTypeCount();
	}

	@Override
	public int getCount() {
		return payRollLists.size();
	}

	@Override
	public Object getItem(int position) {

		// // 同一分类内，第一个元素的索引值
		// int categroyFirstIndex = 0;
		//
		// for (PayRoll payRoll : payRollLists) {
		//
		// int size = payRoll.getItemCount();
		// // 在当前分类中的索引值
		// int categoryIndex = position - categroyFirstIndex;
		// // item在当前分类内
		// if (categoryIndex < size) {
		// return category.getItem( categoryIndex );
		// }
		//
		// // 索引移动到当前分类结尾，即下一个分类第一个元素索引
		// categroyFirstIndex += size;
		// }

		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		final PayRoll bean = payRollLists.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.payrolllist_activity_item, null);
			holder = new ViewHolder();
			holder.titileTv = (TextView) convertView.findViewById(R.id.titleTv);
			holder.timeTv = (TextView) convertView.findViewById(R.id.timeTv);
			holder.moneyTv = (TextView) convertView.findViewById(R.id.moneyTv);
			holder.tipImg = (ImageView) convertView.findViewById(R.id.tipImg);
			holder.tvDetial = (TextView) convertView.findViewById(R.id.tvDetial);
			holder.money = (ImageView) convertView.findViewById(R.id.money);
			holder.iv_01 = (ImageView) convertView.findViewById(R.id.iv_01);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (bean.getIsread().equals("0")) {
			holder.tipImg.setVisibility(View.VISIBLE);
		} else {
			holder.tipImg.setVisibility(View.INVISIBLE);
		}
		
		String str_yue = bean.getPayRollTitle();
		String[] st = str_yue.split("份");
		
		try {
			if (bean.getTu_type().equals("0")) {// 全部
				holder.money.setImageResource(R.drawable.a1_mr);
				holder.titileTv.setText(str_yue);
//				holder.titileTv.setText(st[0]+"份月度工资");
			} else if (bean.getTu_type().equals("2")) {// 工资
				holder.money.setImageResource(R.drawable.a1_gz);
				holder.titileTv.setText(st[0]+"份工资项");
			} else if (bean.getTu_type().equals("10")) {// 奖金
				holder.money.setImageResource(R.drawable.a1_jj);
				holder.titileTv.setText(st[0]+"份奖金项");
			} else if (bean.getTu_type().equals("11")) {// 补贴、福利
				holder.money.setImageResource(R.drawable.a1_fl);
				holder.titileTv.setText(st[0]+"份补贴项");
			} else if (bean.getTu_type().equals("12")) {// 报销
				holder.money.setImageResource(R.drawable.a1_bx);
				holder.titileTv.setText(st[0]+"份报销项");
			} else {
				holder.money.setImageResource(R.drawable.a1_mr);
				holder.titileTv.setText(st[0]+"份其他项");
			}

		} catch (Exception e) {

			holder.money.setImageResource(R.drawable.time);
			holder.titileTv.setText("其他项");
		}

		if (position == 0) {
			holder.iv_01.setVisibility(View.VISIBLE);
			holder.tvDetial.setVisibility(View.VISIBLE);
			holder.tvDetial.setText(bean.getPayRollTime());
			holder.tvDetial.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));// 加粗
			holder.tvDetial.getPaint().setFakeBoldText(true);// 加粗
		} else if (bean.getPayRollTime().equals(payRollLists.get(position - 1).getPayRollTime())) {
			holder.tvDetial.setVisibility(View.GONE);
			holder.iv_01.setVisibility(View.GONE);
		} else {
			holder.iv_01.setVisibility(View.VISIBLE);
			holder.tvDetial.setVisibility(View.VISIBLE);
			holder.tvDetial.setText(bean.getPayRollTime());
			holder.tvDetial.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));// 加粗
			holder.tvDetial.getPaint().setFakeBoldText(true);// 加粗
		}

		// holder.titileTv.setText(bean.getPayRollTitle());
		holder.timeTv.setText(bean.getUpdateTime().substring(0, 10));

		// 数据解密
		String decodePayRollMoney = null;
		try {
			DesUtil desUtil = new DesUtil();
			if (bean.getGroupSalaryList().size() != 0) {
				if (!bean.getGroupSalaryList().get(0).getWages().equals("")) {
					decodePayRollMoney = desUtil.decrypt(bean.getGroupSalaryList().get(0).getWages(), "haoqeeJSXCT!@#$%^&*");

				}

			} else {
				if (bean.getPayRollMoney().length() > 10) {

					decodePayRollMoney = desUtil.decrypt(bean.getPayRollMoney(), "haoqeeJSXCT!@#$%^&*");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		holder.moneyTv.setText("￥" + decodePayRollMoney);

		return convertView;
	}

	static class ViewHolder {
		TextView titileTv, timeTv, moneyTv, tvDetial;
		ImageView tipImg, money, iv_01;
	}

	public void setDataChanged(List<PayRoll> payRollBeanLists) {
		this.payRollLists = payRollBeanLists;
		this.notifyDataSetChanged();
	}

}
