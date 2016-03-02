package com.haoqee.chat;


import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.haoqee.chat.entity.Version;

/**
 * 页面基类
 *
 */
public class BaseActivity extends IndexActivity {
	public final static String TAG = "BaseActivity";
	
	private Version mVersion;
	public final static int SHOW_UPGRADE_DIALOG = 100011;
	protected AlertDialog mUpgradeNotifyDialog;
	
	 public Handler mUpgradeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
				
			case SHOW_UPGRADE_DIALOG:
				showUpgradeDialog();
				break;
            }
        }
    };
    
    private void showUpgradeDialog() {
		/*LayoutInflater factor = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View serviceView = factor.inflate(R.layout.client_dialog, null);
		TextView titleTextView = (TextView) serviceView.findViewById(R.id.title);
		titleTextView.setText(mContext.getString(R.string.check_new_version));
		TextView contentView = (TextView) serviceView.findViewById(R.id.updatelog);
		contentView.setText(mVersion.description);
		Button okBtn = (Button)serviceView.findViewById(R.id.okbtn);
		okBtn.setText(mContext.getString(R.string.upgrade));
		okBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				showDownloadApkDilog();
				
				if (mUpgradeNotifyDialog != null){
					mUpgradeNotifyDialog.dismiss();
					mUpgradeNotifyDialog = null;
					
					if(mVersion.updateType == 2){
						sendBroadcast(new Intent(MainActivity.SYSTEM_EXIT));
					}
				}
			}
		});
		
		Button cancelBtn = (Button)serviceView.findViewById(R.id.cancelbtn);
		cancelBtn.setText(mContext.getString(R.string.cancel));
		cancelBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (mUpgradeNotifyDialog != null){
					mUpgradeNotifyDialog.dismiss();
					mUpgradeNotifyDialog = null;
					
					if(mVersion.updateType == 2){
						sendBroadcast(new Intent(MainActivity.SYSTEM_EXIT));
					}
				}
			}
		});
		
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		mUpgradeNotifyDialog = builder.create();
		mUpgradeNotifyDialog.setCancelable(false);
		mUpgradeNotifyDialog.show();
		mUpgradeNotifyDialog.setContentView(serviceView);
		FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(mWidth - FeatureFunction.dip2px(mContext, 20), LayoutParams.WRAP_CONTENT);
		layout.setMargins(FeatureFunction.dip2px(mContext, 10), 0, FeatureFunction.dip2px(mContext, 10), 0);
		serviceView.setLayoutParams(layout);*/
	}

	private void showDownloadApkDilog() {
		if (mVersion != null) {
			try {
				Uri uri = Uri.parse(mVersion.downloadUrl);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			} catch (Exception e) {
			}
		}
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
	@Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*Version version = DamiCommon.getVersionResult(mContext);
        boolean isUpdate = false;
        if(version != null){
        	long time = System.currentTimeMillis();
        	if(time - version.updateTime > 24 * 60 * 60 * 1000 || version.updateType == 2){
        		isUpdate = true;
        	}
        }else {
        	isUpdate = true;
		}
        
        if(isUpdate){
        	checkUpgrade();
        }*/
    }
    
    @Override
	protected void onPause() {
		super.onPause();
	}
    
    private void checkUpgrade(){
		new Thread(){
            @Override
            public void run() {
        		/*if(DamiCommon.verifyNetwork(mContext)){
        			try {
        				Version version = new Version();
        				version.updateTime = System.currentTimeMillis();
        				DamiCommon.saveVersionResult(mContext, mVersion);
        				
        				VersionInfo versionInfo = DamiCommon.getDamiInfo().checkUpgrade();
        				
    					if(versionInfo != null && versionInfo.mState != null && versionInfo.mState.code == 0){
    						mVersion = versionInfo.mVersion;
    						mVersion.updateTime = System.currentTimeMillis();
    						DamiCommon.saveVersionResult(mContext, mVersion);
    						if(mVersion != null && mVersion.mHasNewVersion){
    							mUpgradeHandler.sendEmptyMessage(SHOW_UPGRADE_DIALOG);
    						}
    					}
    				} catch (DamiException e) {
    					e.printStackTrace();
    				}
        		}else {
				}*/
            }
		}.start();
	}
    
}
