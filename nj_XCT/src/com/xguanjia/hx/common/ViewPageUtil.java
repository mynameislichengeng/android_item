package com.xguanjia.hx.common;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.chinamobile.salary.R;

public class ViewPageUtil {
	private static final String TAG = "ViewPage";
	// 上下文
	private Context context;

	public ViewPageUtil(Context context) {
		super();
		this.context = context;
	}

	public RelativeLayout getRelativeLayout(List<View> listViews) {
		RelativeLayout layout = new RelativeLayout(context);
		LinearLayout peas = new LinearLayout(context);
		//peas.setBackgroundColor(Color.parseColor("#E0E0E0"));
		peas.setBackgroundResource(R.drawable.peas_area_background);
		peas.setGravity(Gravity.CENTER);
		peas.setId(R.id.peas_area);
		peas.setOrientation(LinearLayout.HORIZONTAL);
		List<ImageView> list = new ArrayList<ImageView>();
		for (int i = 0; i < listViews.size(); i++) {
			ImageView iv = new ImageView(context);
			list.add(iv);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 10;
			params.rightMargin = 10;
			if (i == 0) {
				iv.setBackgroundResource(R.drawable.light_peas);
			} else {
				iv.setBackgroundResource(R.drawable.black_peas);
			}
			peas.addView(iv, params);
		}
		LayoutParams params1 = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		layout.addView(peas, params1);
		ViewPager vp = initViewPager(listViews, list);
		LayoutParams params2 = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.BELOW, R.id.peas_area);
		layout.addView(vp, params2);
		return layout;
	}

	/**
	 * 初始化ViewPager
	 */
	public ViewPager initViewPager(List<View> listViews, List<ImageView> list) {
		ViewPager vPage = new ViewPager(context);
		vPage.setAdapter(new ViewPagerAdapter(listViews));
		vPage.setCurrentItem(0);
		vPage.setOnPageChangeListener(new ViewPageChangeListener(list));
		return vPage;
	}

	/**
	 * ViewPager适配器
	 */
	public class ViewPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public ViewPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View v, int position, Object item) {
			((ViewPager) v).removeView(mListViews.get(position));
		}

		@Override
		public void finishUpdate(View v) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View v, int position) {
			((ViewPager) v).addView(mListViews.get(position), 0);
			return mListViews.get(position);
		}

		@Override
		public boolean isViewFromObject(View v, Object obj) {
			return v == (obj);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View v) {
		}
	}

	/**
	 * 页卡切换监听
	 */
	public class ViewPageChangeListener implements OnPageChangeListener {
		private List<ImageView> list;

		public ViewPageChangeListener(List<ImageView> list) {
			super();
			this.list = list;
		}

		@Override
		public void onPageScrollStateChanged(int position) {
			

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				if (i == position) {
					list.get(i).setBackgroundResource(R.drawable.light_peas);
				} else {
					list.get(i).setBackgroundResource(R.drawable.black_peas);
				}
			}
		}

	}
}
