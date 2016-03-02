package com.xguanjia.hx.notice;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.salary.R;
import com.xguanjia.hx.attachment.bean.AttachmentBean;

public class AttrDetailExpandableListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private boolean isClick = false;
	private List<AttachmentBean> attachmentList;

	private int selectIndex = 0;

	public AttrDetailExpandableListAdapter(Context _context, List<AttachmentBean> _attachmentList) {
		this.context = _context;
		this.attachmentList = _attachmentList;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return attachmentList.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		AttachmentBean bean = attachmentList.get(childPosition);
		selectIndex = childPosition;
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.attachment_adapter, null);
			holder = new ViewHolder();
			holder.attachtTypeIv = (ImageView) convertView.findViewById(R.id.attachTypeImg);
			holder.attachNameTv = (TextView) convertView.findViewById(R.id.attachNameView);
			holder.deleteBtn = (Button) convertView.findViewById(R.id.deleteAttachBtn);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
//		holder.attachtTypeIv.setImageResource(bean.);
		holder.attachtTypeIv.setImageResource(bean.getFileIcon());
		holder.attachNameTv.setText(bean.getFileName());
		holder.deleteBtn.setVisibility(View.INVISIBLE);
		holder.deleteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				attachmentList.remove(childPosition);
				setDataChanged(attachmentList);
			}
		});
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return attachmentList.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return attachmentList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return 1;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//		TextView  textView = getGenericView();
//		if(attachmentList.size()>0&&attachmentList!=null){
//			textView.setVisibility(View.VISIBLE);
//		}else{
//			textView.setVisibility(View.GONE);
//		}
		if(!isClick){
			isExpanded = false;
			isClick = true;
		}
		View view = View.inflate(context, R.layout.expandablelv, null);
		TextView tv = (TextView) view.findViewById(R.id.expandablelv_tv);
		ImageView iv = (ImageView) view.findViewById(R.id.expandablelv_iv);
		tv.setText(attachmentList.size()+"个附件（"+attachmentList.size()+"/5个附件）");
		if (isExpanded) {
			iv.setBackgroundResource(R.drawable.up);
		} else {
			iv.setBackgroundResource(R.drawable.down);
		}
		if(attachmentList == null||attachmentList.size()<=0){
			view.setVisibility(View.INVISIBLE);
		}
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public TextView getGenericView() {
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 64);
		TextView textView = new TextView(context);
		textView.setLayoutParams(lp);
		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		textView.setPadding(80, 0, 0, 0);
		textView.setTextColor(Color.rgb(0, 0, 0));
		textView.setTextSize(18);
		textView.setText("附件");
		return textView;
	}

	private class ViewHolder {
		private ImageView attachtTypeIv;
		TextView attachNameTv;
		Button deleteBtn;
	}

	public void setDataChanged(List<AttachmentBean> list) {
		attachmentList = list;
		this.notifyDataSetChanged();
	}
	
	public List<AttachmentBean> getData(){
		return attachmentList;
	}

}
