package com.xguanjia.hx.payroll.adapter;

import java.util.ArrayList;
import java.util.List;

import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.payroll.activity.PayrollFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	private List<String> nameTop = new ArrayList<String>();
	private List<Fragment> fragments = new ArrayList<Fragment>();

	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return nameTop.get(position % nameTop.size());
	}

	@Override
	public int getCount() {
		return nameTop.size();
	}

	public void setData(List<String> nameTop) {
		this.nameTop = nameTop;
	}

	public void setFragments(List<Fragment> fragments) {
		this.fragments = fragments;
	}
	

}
