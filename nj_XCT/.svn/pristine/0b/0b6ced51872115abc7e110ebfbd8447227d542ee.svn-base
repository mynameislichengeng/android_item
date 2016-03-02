package com.haoqee.chat.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.haoqee.chat.MainActivity;
import com.haoqee.chat.SearchUserActivity;
import com.haoqee.chat.UserInfoActivity;
import com.haoqee.chat.adapter.FriendAdapter;
import com.haoqee.chat.entity.User;
import com.haoqee.chat.entity.UserList;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.global.FeatureFunction;
import com.haoqee.chat.net.ThinkchatException;
import com.haoqee.chat.widget.MyPullToRefreshListView;
import com.haoqee.chat.widget.SearchDialog;
import com.haoqee.chat.widget.MyPullToRefreshListView.OnChangeStateListener;
import com.chinamobile.salary.R;

public class FriendsFragment extends BaseFragment implements OnClickListener, OnChangeStateListener, OnTouchListener, OnItemClickListener{
	
	private MyPullToRefreshListView mContainer;				//下拉刷新控件
	private ListView mListView;								
	private boolean mIsRefreshing = false;					//是否正在刷新
	private TextView mRefreshViewLastUpdated;				//下拉刷新时间显示控件
	private View mListHeaderView;							//ListView中显示的搜索控件
	public final static String REFRESH_FRIEND_ACTION = "com.xizue.thinkchat.Fragment.intent.action.REFRESH_FRIEND_ACTION";
	private FriendAdapter mAdapter;
	private List<User> mUserList = new ArrayList<User>();
	
	private final class RemoveWindow implements Runnable {
        public void run() {
            removeWindow();
        }
    }
	
    private static String[] LETTERS = new String[]{"#","A","B","C","D","E","F","G","H","I","J","K","L",
    	"M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private RemoveWindow mRemoveWindow = new RemoveWindow();
    private WindowManager mWindowManager;
    private TextView mDialogText;
    private LinearLayout mLinearLayout;
    private int mLettersLength = LETTERS.length;
    private HashMap<String,Integer> mSortKeyMap = new HashMap<String, Integer>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.friend_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		IntentFilter filter = new IntentFilter();
		filter.addAction(REFRESH_FRIEND_ACTION);
		mContext.registerReceiver(mReceiver, filter);
	}
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(!TextUtils.isEmpty(action)){
				if(action.equals(REFRESH_FRIEND_ACTION)){
					mContainer.clickrefresh();
				}
			}
		}
	};

	@Override
	void setupViews(View contentView) {
		setTitleContent(0, R.drawable.title_add_btn, mContext.getString(R.string.friend_tab));
		mRightBtn.setOnClickListener(this);
		
		mContainer = (MyPullToRefreshListView) contentView.findViewById(R.id.container);
		mContainer.setOnChangeStateListener(this);
		mRefreshViewLastUpdated = (TextView) contentView.findViewById(R.id.pull_to_refresh_time);
		mListView = mContainer.getList();
		mListView.setCacheColorHint(0);
		mListView.setSelector(mContext.getResources().getDrawable(R.drawable.transparent_selector));
		mListView.setOnItemClickListener(this);
		mListView.setFooterDividersEnabled(false);
		
		mListHeaderView = LayoutInflater.from(mContext).inflate(R.layout.search_header_layout, null);
		
		mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mLinearLayout = (LinearLayout) contentView.findViewById(R.id.ll_indicator);
        LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        for (int i = 0; i < LETTERS.length; i++) {
        	View view = inflate.inflate(R.layout.textview_key, null);
			TextView textView = (TextView) view.findViewById(R.id.key);
			//TextView textView = new TextView(mContext);
			textView.setText(LETTERS[i]);
			textView.setTextSize(10);
			textView.setTextColor(mContext.getResources().getColor(R.color.content_gray_color));
			view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 0, 1.0f));;
			int padding = FeatureFunction.dip2px(mContext, 3);
			view.setPadding(padding, 0, padding, 0);
			mLinearLayout.addView(view);
		}
        mLinearLayout.setOnTouchListener(this);
        mDialogText = (TextView) inflate.inflate(R.layout.list_position, null);
        mDialogText.setVisibility(View.INVISIBLE);
		mHandler.post(new Runnable() {
			public void run() {
				WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
						LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT,
						WindowManager.LayoutParams.TYPE_APPLICATION,
						WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
								| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
						PixelFormat.TRANSLUCENT);
				mWindowManager.addView(mDialogText, lp);
			}
		});
		
		mListView.setHeaderDividersEnabled(false);
		
		mContainer.clickrefresh();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		removeWindow();
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MainActivity.SHOW_SCROLLREFRESH:
				if (mIsRefreshing) {
					mContainer.onRefreshComplete();
					break;
				}
				mIsRefreshing = true;
				getFriendList(MainActivity.LIST_LOAD_REFERSH);
				break;
				
			case MainActivity.HIDE_SCROLLREFRESH:
				mIsRefreshing = false;
				mContainer.onRefreshComplete();
				List<User> refreshList = (List<User>) msg.obj;
				
				if(refreshList != null){
					if(mUserList != null){
						mUserList.clear();
					}
					
					mUserList.addAll(refreshList);
				}
				
				updateListView();
				break;

			case MainActivity.SHOW_PROGRESS_DIALOG:
				String dialogMsg = (String) msg.obj;
				showProgressDialog(dialogMsg);
				break;
			case MainActivity.HIDE_PROGRESS_DIALOG:
				hideProgressDialog();
				List<User> firstList = (List<User>) msg.obj;
				
				if(firstList != null){
					mUserList.addAll(firstList);
				}
				
				updateListView();
				break;

			case MainActivity.MSG_LOAD_ERROR:
				String error_Detail = (String) msg.obj;
				if (error_Detail != null && !error_Detail.equals("")) {
					Toast.makeText(mContext, error_Detail, Toast.LENGTH_LONG)
							.show();
				} else {
					Toast.makeText(mContext, R.string.load_error,
							Toast.LENGTH_LONG).show();
				}
				break;
			case MainActivity.MSG_NETWORK_ERROR:
				Toast.makeText(mContext, R.string.network_error,
						Toast.LENGTH_LONG).show();
				break;
			case MainActivity.MSG_TICE_OUT_EXCEPTION:
				String message = (String) msg.obj;
				if (message == null || message.equals("")) {
					message = mContext.getString(R.string.timeout);
				}
				Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
				break;
				
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.right_btn:
			Intent addIntent = new Intent(mContext, SearchUserActivity.class);
			startActivity(addIntent);
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onChangeState(MyPullToRefreshListView container, int state) {
		mRefreshViewLastUpdated.setText(FeatureFunction.getRefreshTime());
		switch (state) {
		case MyPullToRefreshListView.STATE_LOADING:
			mHandler.sendEmptyMessage(MainActivity.SHOW_SCROLLREFRESH);
			break;
		}
	}
	

	private void updateListView() {
		
		if(mAdapter != null){
			mAdapter.setData(mUserList);
			mSortKeyMap = mAdapter.getSortKey();
			mAdapter.notifyDataSetChanged();
			return;
		}

		if (mUserList != null) {
			
			if(mListView.getHeaderViewsCount() == 0){
				mListView.addHeaderView(mListHeaderView);
			}
			mAdapter = new FriendAdapter(mContext, mUserList);
			mSortKeyMap = mAdapter.getSortKey();
			mListView.setVisibility(View.VISIBLE);
			mListView.setAdapter(mAdapter);
		}

	}
	
	@Override
	public boolean onTouch(final View v, MotionEvent event) {
		int height = v.getHeight();
		final int alphaHeight = height / mLettersLength;
		final int fingerY = (int) event.getY();
		int selectIndex = fingerY / alphaHeight;
		if (selectIndex < 0 || selectIndex > mLettersLength - 1) {
			mLinearLayout.setBackgroundResource(android.R.color.transparent);
			mHandler.removeCallbacks(mRemoveWindow);
    		mHandler.post(mRemoveWindow);
			return true;
		}
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			String letter = LETTERS[selectIndex];
			findLocation(letter);
			break;
		case MotionEvent.ACTION_MOVE:
			letter = LETTERS[selectIndex];
			findLocation(letter);
			break;
		case MotionEvent.ACTION_UP:
			mLinearLayout.setBackgroundResource(android.R.color.transparent);
			mHandler.removeCallbacks(mRemoveWindow);
    		mHandler.post(mRemoveWindow);
			break;
		default:
			break;
		}
		return true;
	}
	
	private void findLocation(String letter){
		
		mLinearLayout.setBackgroundResource(android.R.color.darker_gray);
		mDialogText.setVisibility(View.VISIBLE);
		mDialogText.setText(letter);
		
		if(mSortKeyMap.containsKey(letter)){			
			int position = mSortKeyMap.get(letter);
			mListView.setSelection(position);
		}
	}
	
	private void removeWindow() {
    	mDialogText.setVisibility(View.INVISIBLE);
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(position == 0){
			if(mUserList != null && mUserList.size() != 0){
				SearchDialog dialog = new SearchDialog(mContext, mUserList);
				dialog.show();
			}
		}else {
			int pos = position - 1;
			Intent intent = new Intent(mContext, UserInfoActivity.class);
			intent.putExtra("fuid", mUserList.get(pos).uid);
			startActivity(intent);
		}
	}

	private void getFriendList(final int loadType){
		new Thread(){
			@Override
			public void run(){
				if(Common.verifyNetwork(mContext)){
					
					List<User> userList = null;
					
					try {
						UserList users = Common.getThinkchatInfo().getFriendList();
						
						if(users != null && users.mState != null && users.mState.code == 0){
							if(users.mUserList != null){
								userList = new ArrayList<User>();
								userList.addAll(users.mUserList);
							}
						}else {
							Message msg=new Message();
							msg.what=MainActivity.MSG_LOAD_ERROR;
							if(users != null && users.mState != null && users.mState.errorMsg != null && !users.mState.errorMsg.equals("")){
								msg.obj = users.mState.errorMsg;
							}else {
								msg.obj = mContext.getString(R.string.load_error);
							}
							mHandler.sendMessage(msg);
						}
					} catch (ThinkchatException e) {
						e.printStackTrace();
						Message msg=new Message();
						msg.what=MainActivity.MSG_TICE_OUT_EXCEPTION;
						msg.obj=mContext.getString(R.string.timeout);
						mHandler.sendMessage(msg);
					}
					
					Message message = new Message();
					message.obj = userList;
					
					switch (loadType) {
					case MainActivity.LIST_LOAD_FIRST:
						message.what = MainActivity.HIDE_PROGRESS_DIALOG;
						break;
					case MainActivity.LIST_LOAD_MORE:
						message.what = MainActivity.HIDE_LOADINGMORE_INDECATOR;
						
					case MainActivity.LIST_LOAD_REFERSH:
						message.what = MainActivity.HIDE_SCROLLREFRESH;
						break;

					default:
						break;
					}
					
					mHandler.sendMessage(message);
				}else {
					switch (loadType) {
					case MainActivity.LIST_LOAD_FIRST:
						mHandler.sendEmptyMessage(MainActivity.HIDE_PROGRESS_DIALOG);
						break;
					case MainActivity.LIST_LOAD_MORE:
						mHandler.sendEmptyMessage(MainActivity.HIDE_LOADINGMORE_INDECATOR);
						
					case MainActivity.LIST_LOAD_REFERSH:
						mHandler.sendEmptyMessage(MainActivity.HIDE_SCROLLREFRESH);
						break;

					default:
						break;
					}
					mHandler.sendEmptyMessage(MainActivity.MSG_NETWORK_ERROR);
				}
			}
		}.start();
	}
}
