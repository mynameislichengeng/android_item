package com.xguanjia.hx.haoxin.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.chinamobile.salary.R;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.SlideView;
import com.xguanjia.hx.common.SlideView.OnSlideListener;
import com.xguanjia.hx.haoxin.bean.HaoXinMsgBean;

public class HaoXinListAdapter extends BaseAdapter implements OnClickListener, OnSlideListener{

	private Context _context;

	private List<HaoXinMsgBean> _msgList;

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	private DisplayImageOptions options;

	private SlideView mLastSlideViewWithStatusOn;
	
	private int selectPosition;
	public HaoXinListAdapter() {
		super();
	}

	public HaoXinListAdapter(List<HaoXinMsgBean> msgList, Context context) {
		super();
		_msgList = msgList;
		_context = context;
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_portrait).showImageForEmptyUri(R.drawable.default_portrait).cacheInMemory().cacheOnDisc().displayer(
				new RoundedBitmapDisplayer(10)).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	}

	@Override
	public int getCount() {
		return _msgList.size();
	}

	@Override
	public Object getItem(int position) {
		return _msgList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HaoXinMsgBean bean = _msgList.get(position);
		ViewHolder holder;
		selectPosition = position;
		SlideView slideView = (SlideView) convertView;
		if (slideView == null) {
			View itemView = LayoutInflater.from(_context).inflate(R.layout.haoxin_list_item, null);
			slideView = new SlideView(_context);
			slideView.setContentView(itemView);
			holder = new ViewHolder(slideView);
			slideView.setOnSlideListener(this);
			slideView.setTag(holder);
		} else {
			holder = (ViewHolder) slideView.getTag();
		}
		holder.messageNameText.setText(bean.getMessageObjName());
		holder.messageTimeText.setText(getShowTime(bean.getMessageCreateTime()));
		holder.messageTitleText.setText(bean.getMessageTitle());
		if ("innerMessage".equals(bean.getMessageAppType())) {
			imageLoader.displayImage("file://" + Constants.SAVE_IMAGE_PATH + "HeadAvatar/" + bean.getAvatar().substring(bean.getAvatar().lastIndexOf("/") + 1, bean.getAvatar().length()), holder.MessageTypeImage, options);
			// holder.MessageTypeImage.setImageResource(R.drawable.msg_type_information);
		} else if ("folderGroupShare".equals(bean.getMessageAppType()) || "folderGroupUpdate".equals(bean.getMessageAppType())) {
//			holder.MessageTypeImage.setImageResource(R.drawable.application_file1);
		} else if ("journal".equals(bean.getMessageAppType())) {
//			holder.MessageTypeImage.setImageResource(R.drawable.application_diary1);
		} else if ("announcement".equals(bean.getMessageAppType())) {
//			holder.MessageTypeImage.setImageResource(R.drawable.application_notice1);
		} else if ("messageBomb".equals(bean.getMessageAppType())) {
//			holder.MessageTypeImage.setImageResource(R.drawable.application_news2);
		} else if ("review".equals(bean.getMessageAppType())) {
//			holder.MessageTypeImage.setImageResource(R.drawable.application_diary1);
		}
		bean.slideView = slideView;
		bean.slideView.reset();
		holder.deleteHolder.setOnClickListener(this);
		return slideView;
	}

	private class ViewHolder {
		public TextView messageNameText, messageTimeText, messageTitleText;
		public ImageView MessageTypeImage;
		public ViewGroup deleteHolder;
		
		ViewHolder(View view) {
			messageNameText = (TextView) view.findViewById(R.id.haoxin_messageName_text);
			messageTimeText = (TextView) view.findViewById(R.id.haoxin_messageTime_text);
			messageTitleText = (TextView) view.findViewById(R.id.haoxin_messageTitle_text);
			MessageTypeImage = (ImageView) view.findViewById(R.id.haoxin_messageType_image);
			deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
		}
	}

	public void setDataChanged(List<HaoXinMsgBean> list) {
		_msgList = list;
		this.notifyDataSetChanged();
	}

	// 得到显示的时间
	private String getShowTime(String time) {
		if (time != null&&!"".equals(time)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String currentDate = sdf.format(new Date());
			String showTime = "";
			if (currentDate.equals(time.substring(0, 10))) {
				showTime = time.substring(11, 16);
			} else if (currentDate.substring(0, 7).equals(time.substring(0, 7))) {
				showTime = time.substring(8, 10) + "日";
			} else if (currentDate.substring(0, 4).equals(time.substring(0, 4))) {
				showTime = time.substring(5, 7) + "月" + time.substring(8, 10) + "日";
			} else {
				showTime = time.substring(0, 4) + "年" + time.substring(5, 7) + "月" + time.substring(8, 10) + "日";
			}
			return showTime;
		} else {
			return "";
		}
	}

	public List<HaoXinMsgBean> get_msgList() {
		return _msgList;
	}

	public void set_msgList(List<HaoXinMsgBean> msgList) {
		_msgList = msgList;
	}

	@Override
	public void onSlide(View view, int status) {
		if (mLastSlideViewWithStatusOn != null
				&& mLastSlideViewWithStatusOn != view) {
			mLastSlideViewWithStatusOn.shrink();
		}
		
		if (status == SLIDE_STATUS_ON) {
			mLastSlideViewWithStatusOn = (SlideView) view;
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.holder) {
			_msgList.remove(selectPosition);
			this.notifyDataSetChanged();
		} else if (v.getId() == selectPosition) {
			_msgList.get(selectPosition).slideView.open();
//			System.out.println("open --------"+position);
			mLastSlideViewWithStatusOn.open();
		}

	}

}
