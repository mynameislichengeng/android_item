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

import com.haoqee.chat.entity.Server;
import com.haoqee.chat.global.Common;
import com.chinamobile.salary.R;

public class ServerAdapter extends BaseAdapter{
	
	private final LayoutInflater mInflater;
	private List<Server> mData;
	private Context mContext;
	public boolean mIsCancel = false;
	
	public ServerAdapter(Context context, List<Server> data){
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
	
	public void setData(List<Server> data){
		mData = data;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
        ViewHolder holder;  
        
        if (convertView==null) {  
        	convertView=mInflater.inflate(R.layout.server_item, null);   
            holder=new ViewHolder();  
            
            holder.mServerNameView = (TextView) convertView.findViewById(R.id.servername);
            holder.mDeleteBtn = (ImageView) convertView.findViewById(R.id.deletebtn);
            holder.mCurrentIcon = (ImageView) convertView.findViewById(R.id.currentIcon);
            convertView.setTag(holder);  
        }else {
        	holder=(ViewHolder) convertView.getTag();  
		}
        
        final Server server = mData.get(position);
        
        
        holder.mServerNameView.setText(server.mServerName);
        
        if(server.mIsCurrent){
        	holder.mCurrentIcon.setVisibility(View.VISIBLE);
        }else {
        	holder.mCurrentIcon.setVisibility(View.GONE);
		}
        
        if(server.mIsDefault){
        	holder.mDeleteBtn.setVisibility(View.GONE);
        }else {
        	
        	holder.mDeleteBtn.setVisibility(mIsCancel? View.VISIBLE : View.GONE);
		}
        
        holder.mDeleteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(mData.get(position).mIsCurrent){
					for (int i = 0; i < mData.size(); i++) {
						if(mData.get(i).mIsDefault){
							Common.saveCurrentServer(mContext, mData.get(i));
							break;
						}
					}
				}
				
				mData.remove(position);
				ServerAdapter.this.notifyDataSetChanged();
				
				Common.saveServerList(mContext, mData);
				
			}
		});
		
        return convertView;
	}

	
	final static class ViewHolder {  
		TextView mServerNameView;
		ImageView mDeleteBtn;
		ImageView mCurrentIcon;
        
        @Override
        public int hashCode() {
			return this.mServerNameView.hashCode() + mDeleteBtn.hashCode() + mCurrentIcon.hashCode();
        }
    } 

}
