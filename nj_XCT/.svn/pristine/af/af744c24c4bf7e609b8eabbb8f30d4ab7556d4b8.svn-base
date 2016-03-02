package com.xguanjia.hx.filecabinet.views;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.widget.ListView;

import com.chinamobile.salary.R;
import com.xguanjia.hx.contact.bean.PersonBean;
import com.xguanjia.hx.filecabinet.adaptor.InternalPersonListAdapter;

/**
 * 机构人员列表视图
 * @author ytg
 * @date 2012-08-31
 */
public class InternalPersonListView extends ListView{
	private static final String TAG = "InternalPersonListView";
	private InternalPersonListAdapter adapter;
	private List<PersonBean> personList;

	public InternalPersonListView(Context context) {
		super(context);
	}

	public InternalPersonListView(Context context, List<PersonBean> list) {
		super(context);
		this.personList = list;
		this.setDivider(getResources().getDrawable(R.drawable.line));
		adapter = new InternalPersonListAdapter(context, list);
		this.setCacheColorHint(Color.alpha(00000000));
		this.setAdapter(adapter);
	}

	public List<PersonBean> getPersonList() {
		return personList;
	}

	public void setPersonList(List<PersonBean> personList) {
		this.personList = personList;
	}
}
