package com.haoqee.chat;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.haoqee.chat.adapter.GroupAdapter;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;
import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.Interface.TCGroupListListener;
import com.haoqee.chatsdk.entity.TCError;
import com.haoqee.chatsdk.entity.TCGroup;
import com.haoqee.chatsdk.entity.TCPageInfo;

/**
 * 搜索群页面
 *
 */
public class SearchGroupActivity extends BaseActivity implements OnClickListener, OnItemClickListener{

	private EditText mNameContent;								//群ID或群名称输入框
	private ListView mListView;									
	private String mKeyword = "";								//搜索关键字
	private boolean mIsLoading = false;							//是否正在加载数据
	private boolean mNoMore;									//是否还有更多数据
	private List<TCGroup> mGroupList = new ArrayList<TCGroup>();	//搜索出来的群列表
	private GroupAdapter mAdapter;								//群适配器 
	private LinearLayout mFootView;								//列表加载更多控件
	private TCPageInfo mPageInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.find_user_layout);
		initComponent();
	}

	/**
	 * 控件初始化
	 */
	private void initComponent(){
		
		setTitleContent(R.drawable.back_btn, 0, mContext.getString(R.string.search_group));
		mLeftBtn.setOnClickListener(this);
		
		mNameContent = (EditText) findViewById(R.id.name);
		mNameContent.setHint(R.string.groupid_or_groupname);
		mNameContent.setOnEditorActionListener(mEditActionLister);
		
		mListView = (ListView) findViewById(R.id.search_list);
		mListView.setCacheColorHint(0);
		mListView.setOnItemClickListener(this);
		mListView.setFooterDividersEnabled(false);
		mListView.setHeaderDividersEnabled(false);
		
		mListView.setSelector(mContext.getResources().getDrawable(R.drawable.transparent_selector));
		mListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case SCROLL_STATE_IDLE:
					if(view.getLastVisiblePosition() == (view.getCount()-1) && !mNoMore && !mIsLoading){
						Message message = new Message();
						message.what = HaoXinProjectActivity.SHOW_LOADINGMORE_INDECATOR;
						message.obj = mFootView; 
						mHandler.sendMessage(message);
					}
					break;

				default:
					break;
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			
		case R.id.left_btn:
			hideSoftKeyboard();
			this.finish();
			break;

		default:
			break;
		}
	}
	
	/**
	 * 点击回车按钮进行搜索
	 */
	private EditText.OnEditorActionListener mEditActionLister = new EditText.OnEditorActionListener() {

		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if(actionId == EditorInfo.IME_ACTION_SEARCH && mNameContent.getVisibility() == View.VISIBLE){
				hideSoftKeyboard();
				mKeyword = mNameContent.getText().toString().trim();
				
				if(TextUtils.isEmpty(mKeyword)){
					String prompt = mContext.getString(R.string.please_input) + mContext.getString(R.string.groupid_or_groupname);
					showToast(prompt);
					return false;
				}
				
				Message message = new Message();
				message.obj = mContext.getString(R.string.add_more_loading);
				message.what = HaoXinProjectActivity.SHOW_PROGRESS_DIALOG;
				mHandler.sendMessage(message);
				
				findGroup(HaoXinProjectActivity.LIST_LOAD_FIRST, mKeyword);
				
				return true;
			}
			return false;
		}
		
	};
	
	public void hideSoftKeyboard(){
		hideSoftKeyboard(getCurrentFocus());
	}
	public void hideSoftKeyboard(View view){
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if(view != null){
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
	
	private void updateListView(){
		

		mListView.setVisibility(View.VISIBLE);
		if(mPageInfo != null){
			int currentPage = mPageInfo.currentPage + 1;
			if(currentPage > mPageInfo.totalPage){
				mNoMore = true;
			} else {
				mNoMore = false;
			}
		}
		
		mAdapter = new GroupAdapter(mContext, mGroupList);
		if (mListView.getFooterViewsCount() == 0){	 			
			mFootView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.hometab_listview_footer, null);
	    	ProgressBar pb = (ProgressBar)mFootView.findViewById(R.id.hometab_addmore_progressbar);
	    	pb.setVisibility(View.GONE);
	    	 		
	    	mListView.addFooterView(mFootView);		
	    	 		
	    	if(mNoMore){
	    		((TextView)mFootView.findViewById(R.id.hometab_footer_text)).setText(mContext.getString(R.string.no_more_data));
	    	}else {
	    	 	((TextView)mFootView.findViewById(R.id.hometab_footer_text)).setText(mContext.getString(R.string.add_more));
	    	}
 		}else {
 			if(mNoMore){
	 			((TextView)mFootView.findViewById(R.id.hometab_footer_text)).setText(mContext.getString(R.string.no_more_data));
	 		}else {
	 			((TextView)mFootView.findViewById(R.id.hometab_footer_text)).setText(mContext.getString(R.string.add_more));
	 		}
		}
		
		mListView.setAdapter(mAdapter); 
	
    }
	
	/**
	 * 查找群访问接口入口函数
	 * @param loadType					加载类型			
	 * @param name						搜索关键字
	 */
	private void findGroup(final int loadType, final String name){
		
		mIsLoading = true;
		int currentPage = 1;
		switch (loadType) {
		case HaoXinProjectActivity.LIST_LOAD_FIRST:
		case HaoXinProjectActivity.LIST_LOAD_REFERSH:
			currentPage = 1;
			break;
		case HaoXinProjectActivity.LIST_LOAD_MORE:
			if(mPageInfo != null){
				currentPage = mPageInfo.currentPage + 1;
				if(currentPage >= mPageInfo.totalPage){
					mNoMore = true;
				} else {
    				mNoMore = false;
    			}
			}
			break;
		default:
			break;
		}
		
		final int page = currentPage;
		
		TCChatManager.getInstance().searchGroup(name, currentPage, new TCGroupListListener() {
			
			@Override
			public void onError(TCError error) {
				mIsLoading = false;
				switch (loadType) {
				case HaoXinProjectActivity.LIST_LOAD_FIRST:
					mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_PROGRESS_DIALOG);
					break;
				case HaoXinProjectActivity.LIST_LOAD_MORE:
					mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_LOADINGMORE_INDECATOR);
					
				case HaoXinProjectActivity.LIST_LOAD_REFERSH:
					mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_SCROLLREFRESH);
					break;

				default:
					break;
				}
				
				Message message = new Message();
				message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
				message.obj = error.errorMessage;
				mBaseHandler.sendMessage(message);
			}
			
			@Override
			public void doComplete() {
				
			}
			
			@Override
			public void doComplete(List groupList, TCPageInfo pageInfo) {
				mIsLoading = false;
				mPageInfo = pageInfo;
				if(mPageInfo == null || page >= mPageInfo.totalPage){
					mNoMore = true;
				} else {
    				mNoMore = false;
    			}
				
				Message message = new Message();
				message.obj = groupList;
				
				switch (loadType) {
				case HaoXinProjectActivity.LIST_LOAD_FIRST:
					message.what = HaoXinProjectActivity.HIDE_PROGRESS_DIALOG;
					break;
				case HaoXinProjectActivity.LIST_LOAD_MORE:
					message.what = HaoXinProjectActivity.HIDE_LOADINGMORE_INDECATOR;
					
				case HaoXinProjectActivity.LIST_LOAD_REFERSH:
					message.what = HaoXinProjectActivity.HIDE_SCROLLREFRESH;
					break;

				default:
					break;
				}
				
				mHandler.sendMessage(message);
			}
			
			@Override
			public void doComplete(List groupList) {
				
			}
			
			@Override
			public void onProgress(int progress) {
				
			}
		});
		
	}
	
	private Handler mHandler = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				
			case HaoXinProjectActivity.SHOW_PROGRESS_DIALOG:
				String dialogMsg = (String)msg.obj;
				showProgressDialog(dialogMsg);
				break;
			case HaoXinProjectActivity.HIDE_PROGRESS_DIALOG:
				baseHideProgressDialog();
				List<TCGroup> groupList = (List<TCGroup>) msg.obj;
				
				if(groupList != null){
					if(mGroupList != null){
						mGroupList.clear();
					}
					
					mGroupList.addAll(groupList);
				}
				updateListView();
				break;
				
			case HaoXinProjectActivity.MSG_LOAD_ERROR:
				hideProgressDialog();
				String error_Detail = (String)msg.obj;
				if(error_Detail != null && !error_Detail.equals("")){
					Toast.makeText(mContext,error_Detail,Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(mContext,mContext.getString(R.string.load_error),Toast.LENGTH_LONG).show();
				}
				break;
			case HaoXinProjectActivity.MSG_NETWORK_ERROR:
				hideProgressDialog();
				Toast.makeText(mContext,R.string.network_error,Toast.LENGTH_LONG).show();
				break;
			case HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION:
				hideProgressDialog();
				String message = (String)msg.obj;
				if (message==null || message.equals("")) {
					message = mContext.getString(R.string.timeout);
				}
				Toast.makeText(mContext,message, Toast.LENGTH_LONG).show();
				break;
				
			case HaoXinProjectActivity.SHOW_LOADINGMORE_INDECATOR:
				LinearLayout footView = (LinearLayout)msg.obj;				
		 		ProgressBar pb = (ProgressBar)footView.findViewById(R.id.hometab_addmore_progressbar);
		 		pb.setVisibility(View.VISIBLE);		 		
		 		TextView more = (TextView)footView.findViewById(R.id.hometab_footer_text);
		 		more.setText(mContext.getString(R.string.add_more_loading));
		 		findGroup(HaoXinProjectActivity.LIST_LOAD_MORE, mKeyword);
				
				break;
				
			case HaoXinProjectActivity.HIDE_LOADINGMORE_INDECATOR:
				if (mFootView != null){
			 		ProgressBar pbar = (ProgressBar)mFootView.findViewById(R.id.hometab_addmore_progressbar);
			 		pbar.setVisibility(View.GONE);
			 		TextView moreView = (TextView)mFootView.findViewById(R.id.hometab_footer_text);
			 		moreView.setText(R.string.add_more);
				}
				
				if(mNoMore){
		 			((TextView)mFootView.findViewById(R.id.hometab_footer_text)).setText(mContext.getString(R.string.no_more_data));
		 		}else {
    	 			((TextView)mFootView.findViewById(R.id.hometab_footer_text)).setText(mContext.getString(R.string.add_more));
    	 		}

				List<TCGroup> moreList = (List<TCGroup>) msg.obj;
				
				if(moreList != null){
					mGroupList.addAll(moreList);
				}

				if (mAdapter != null){
					mAdapter.notifyDataSetChanged();
				}
				
				break;
			}
		}
	};

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(arg2 < mGroupList.size()){
			
			Intent intent = new Intent(mContext, GroupDetailActivity.class);
			intent.putExtra("groupid", mGroupList.get(arg2).getGroupID());
			mContext.startActivity(intent);
			
		}else if(arg2 == mGroupList.size() && !mNoMore && !mIsLoading){
			if(mFootView!=null){
				Message message = new Message();
				message.what = HaoXinProjectActivity.SHOW_LOADINGMORE_INDECATOR;
				message.obj = mFootView; 
				mHandler.sendMessage(message);
			}
		}
	}
	
}
