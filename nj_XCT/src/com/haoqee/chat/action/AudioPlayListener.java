package com.haoqee.chat.action;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.haoqee.chat.ChatMainActivity;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.global.FeatureFunction;
import com.chinamobile.salary.R;
import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.entity.TCMessage;
/**
 * 
 * 功能： 按钮控制播放音频. <br />
 * 按钮对应的 TAG 为 音频 的URL地址<br />
 * 
 */
public class AudioPlayListener implements OnClickListener{
	private String url;  //   音频网络地址.
	private AudioPlay mAudioPlay;
	private Context context;
	private AudioPlayStatus playStatus;
	
	public AudioPlayListener(Context context) {
		super();
		this.context = context;
		this.playStatus = new AudioPlayStatus();
		this.mAudioPlay = new AudioPlay(this.context){
			@Override
			protected void onPlayStart(int type) {
				super.onPlayStart(type);
				if(type == 0){
					playStatus.playing();
				}
			}

			@Override
			protected void onPlayStop() {
				super.onPlayStop();
				mAudioPlay.setMessageTag("");
				Log.e("AudioPlayListener", "onPlayStop");
				playStatus.pause();
			}
		};
	}
	
	public AudioPlayStatus getPlayStatus(){
		return playStatus;
	}
	
	public String getMessageTag(){
		return mAudioPlay.getMessageTag();
	}

	@Override
	public void onClick(View v) {
		
		TCMessage msg = (TCMessage) v.getTag();
		if(msg == null){
			return;
		}
		
		if(mAudioPlay.getCurrentUrl().equals(msg.getVoiceMessageBody().getVoiceUrl()) && mAudioPlay.getPlayState()){
			mAudioPlay.stop(true);
			return;
		} else {
			mAudioPlay.stop(true);
		}
		
		mAudioPlay.setMessageTag(msg.getMessageTag());
		
		this.playStatus.setBtn((RelativeLayout)v);
		this.playStatus.playing();
		
		url = msg.getVoiceMessageBody().getVoiceUrl();
		String fileName = null;
		if(!url.startsWith("AUDIO_")){
			fileName = FeatureFunction.generator(url);
		}else{
			fileName = url;
		}
		File file = new File(ReaderImpl.getAudioPath(context), fileName);
		
		if(!file.exists()){
			if(url.startsWith("http://")){
				down(msg);
				return;
			}
		}
		// 为button添加播放状态
		this.mAudioPlay.play(msg, 0, true);
		
		if(!msg.getFromId().equals(Common.getUid(context)) && msg.getVoiceReadState() == 0){
			msg.setVoiceReadState(1);
			TCChatManager.getInstance().updateMessageVoiceReadState(msg);
			
			Intent intent = new Intent(ChatMainActivity.ACTION_READ_VOICE_STATE);
			intent.putExtra(ChatMainActivity.EXTRAS_MESSAGE, msg);
			context.sendBroadcast(intent);
		}
	}
	
	public void stop(){
		mAudioPlay.stop();
	}
	
	public void play(TCMessage messageInfo){
		mAudioPlay.setMessageTag(messageInfo.getMessageTag());
		mAudioPlay.play(messageInfo, 1, true);
	}
	
	public void down(TCMessage msg){
		
	}
	
	public class AudioPlayStatus {
		private AnimationDrawable animBtnPlay;

		private AnimationDrawable tempAnimBtnPlay;
		
		private RelativeLayout btn;

		public AudioPlayStatus() {
			super();
		}

		public void setBtn(RelativeLayout btn) {
			this.btn = btn;
			initParam();
		}

		private void initParam() {
			if(animBtnPlay != null){
				tempAnimBtnPlay = animBtnPlay;
			}
			animBtnPlay = (AnimationDrawable) ((ImageView)this.btn.findViewById(R.id.chat_talk_msg_info_msg_voice)).getDrawable();
			pause();
		}
		
		/**
		 * 等待位置..
		 * 
		 * 作者:fighter <br />
		 * 创建时间:2013-4-19<br />
		 * 修改时间:<br />
		 */
		public void pause() {
			if (btn!= null && animBtnPlay != null){
				animBtnPlay.stop();
				animBtnPlay.selectDrawable(0);
			}
		}

		/**
		 * 播放音频状态.
		 * 
		 * 作者:fighter <br />
		 * 创建时间:2013-4-19<br />
		 * 修改时间:<br />
		 */
		public void playing() {
			if (btn!= null && animBtnPlay != null) {
				if(tempAnimBtnPlay != null && tempAnimBtnPlay.isRunning()){
					tempAnimBtnPlay.stop();
					tempAnimBtnPlay.selectDrawable(0);
				}
				
				animBtnPlay.start();
			}

		}
	}
}
