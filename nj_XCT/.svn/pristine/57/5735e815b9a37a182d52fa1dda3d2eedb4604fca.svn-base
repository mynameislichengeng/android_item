package com.haoqee.chat.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoqee.chat.entity.User;
import com.haoqee.chat.global.ImageLoader;
import com.chinamobile.salary.R;

/**
 * 用户列表适配器
 *
 */
public class UserAdapter extends BaseAdapter{
	
	private final LayoutInflater mInflater;
	private List<User> mData;					//用户列表
	private ImageLoader mImageLoader;			
	private Context mContext;
	private boolean mIsShowCheckBox = false;
	
	public UserAdapter(Context context, List<User> data){
		mInflater = (LayoutInflater)context.getSystemService(
	            Context.LAYOUT_INFLATER_SERVICE);
		mContext = context;
		mData = data;
		mImageLoader = new ImageLoader();
		
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public HashMap<String, Bitmap> getImageBuffer(){
		return mImageLoader.getImageBuffer();
	}
	
	public void setIsShowCheckBox(boolean isShow){
		mIsShowCheckBox = isShow;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder;  
        
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.user_item, null);
			holder = new ViewHolder();
			holder.mHeaderView = (ImageView) convertView.findViewById(R.id.headerimage);
			holder.mScreenName = (TextView) convertView.findViewById(R.id.username);
			holder.mSignView = (TextView) convertView.findViewById(R.id.sign);
			holder.mCheckBox = (CheckBox) convertView.findViewById(R.id.checkperson);
            
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder)convertView.getTag();
		}
		
        
        User user = mData.get(position);
        
        if(mIsShowCheckBox){
        	holder.mCheckBox.setVisibility(View.VISIBLE);
        }else {
        	holder.mCheckBox.setVisibility(View.GONE);
		}
        
		if (user != null) {
			holder.mScreenName.setText(user.nickName);
			
			if(user.sign != null && !user.sign.equals("")){
				holder.mSignView.setText(user.sign);
			}else {
				holder.mSignView.setText(mContext.getString(R.string.default_sign));
			}
			
			if(!TextUtils.isEmpty(user.mSmallHead)){
				holder.mHeaderView.setTag(user.mSmallHead);
				
				if(mImageLoader.getImageBuffer().get(user.mSmallHead) == null){
					holder.mHeaderView.setImageBitmap(null);
	                holder.mHeaderView.setImageResource(R.drawable.default_header);
				}
	        	mImageLoader.getBitmap(mContext, holder.mHeaderView, null, user.mSmallHead, 0, false, false);
	        }else {
	        	holder.mHeaderView.setImageResource(R.drawable.default_header);
			}
		}
		
		holder.mCheckBox.setChecked(user.isShow);
        
        return convertView;
	}

	
	final static class ViewHolder {  
		private ImageView mHeaderView;
		private TextView mScreenName;
		private TextView mSignView;
		private CheckBox mCheckBox;
        
        @Override
        public int hashCode() {
			return this.mHeaderView.hashCode() + mScreenName.hashCode() + mSignView.hashCode() + mCheckBox.hashCode(); 
        }
    } 

}
