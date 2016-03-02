package com.xguanjia.hx.contact.view;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.widget.ListView;

import com.chinamobile.salary.R;
import com.xguanjia.hx.contact.adapter.InternalPersonListAdapter;
import com.xguanjia.hx.contact.bean.PersonBean;

public class InternalPersonListView extends ListView {
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
