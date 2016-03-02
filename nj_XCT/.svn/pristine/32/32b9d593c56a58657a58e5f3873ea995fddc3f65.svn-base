package com.haoqee.chat.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoqee.chat.entity.NotifyType;
import com.haoqee.chat.global.FeatureFunction;
import com.chinamobile.salary.R;
import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.entity.TCNotifyVo;

public class NotifyAdapter extends BaseAdapter{
	
	private final LayoutInflater mInflater;
	private List<TCNotifyVo> mData;
	private Context mContext;
	public boolean mIsCancel = false;
	
	public NotifyAdapter(Context context, List<TCNotifyVo> data){
		mInflater = (LayoutInflater)context.getSystemService(
	            Context.LAYOUT_INFLATER_SERVICE);
		mContext = context;
		mData = data;
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
	
	public void setData(List<TCNotifyVo> data){
		mData = data;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
        ViewHolder holder;  
        
        if (convertView==null) {  
        	convertView=mInflater.inflate(R.layout.notify_item, null);   
            holder=new ViewHolder();  
            
            holder.mContentView = (TextView) convertView.findViewById(R.id.content);
            holder.mTimeView = (TextView) convertView.findViewById(R.id.time);
            holder.mProcessedView = (TextView) convertView.findViewById(R.id.processed);
            holder.mDeleteBtn = (ImageView) convertView.findViewById(R.id.deletebtn);
            convertView.setTag(holder);  
        }else {
        	holder=(ViewHolder) convertView.getTag();  
		}
        
        final TCNotifyVo notify = mData.get(position);
        
        if(notify.getType() == NotifyType.AGREE_ADD_FRIEND){
        	notify.setContent(notify.getUser().getName() + mContext.getString(R.string.agree_add_friend_with_you));
		}else if(notify.getType() == NotifyType.APPLY_FRIEND){
			notify.setContent(notify.getUser().getName() + mContext.getString(R.string.apply_add_friend_with_you));
		}else if(notify.getType() == NotifyType.REFUSE_ADD_FRIEND){
			notify.setContent(notify.getUser().getName() + mContext.getString(R.string.refuse_add_friend_with_you));
		}else if(notify.getType() == NotifyType.DELETE_FRIEND){
			notify.setContent(notify.getUser().getName() + mContext.getString(R.string.unbind_friendship));
		}
        
        holder.mProcessedView.setVisibility(View.GONE);
        holder.mContentView.setText(notify.getContent());
        if(notify.getNotifyReadState() == 1){
        	holder.mContentView.setTextColor(mContext.getResources().getColor(R.color.content_gray_color));
        }else {
        	holder.mContentView.setTextColor(mContext.getResources().getColor(R.color.black));
		}
        
        if(notify.getProcessed() == 1){
        	holder.mProcessedView.setVisibility(View.VISIBLE);
        }
        
        holder.mTimeView.setText(FeatureFunction.getSecondTime(notify.getTime()));
        holder.mDeleteBtn.setVisibility(mIsCancel? View.VISIBLE : View.GONE);
        holder.mDeleteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				TCChatManager.getInstance().deleteNotify(mData.get(position));
				mData.remove(position);
				NotifyAdapter.this.notifyDataSetChanged();
			}
		});
		
        return convertView;
	}

	
	final static class ViewHolder {  
		TextView mContentView;
		TextView mTimeView;
		TextView mProcessedView;
		ImageView mDeleteBtn;
        
        @Override
        public int hashCode() {
			return this.mContentView.hashCode() + mTimeView.hashCode() + 
					mProcessedView.hashCode() + mDeleteBtn.hashCode();
        }
    } 

}
