package com.delevin.boluolcs.umeng.share;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.interfaces.ShareCallBack;
import com.pusupanshi.boluolicai.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;

/**
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-4-6 下午1:47:00 类说明
 */
public class ShareUtils {
	private static UMShareListener mShareListener;
	private static ShareAction mShareAction;

	/**
	 * initShare(Activity,分享标题，分享副标题，分享url,分享头标);
	 * 
	 * */
	public static <T> void initShare(ShareCallBack callBack,
			final Activity activity, final String title, final String message,
			final String url, final int icon) {
		mShareListener = new CustomShareListener<T>(activity, callBack);
		/* 增加自定义按钮的分享面板 */
		mShareAction = new ShareAction(activity).setDisplayList(
				SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ,
				SHARE_MEDIA.QZONE)
		// .addButton("umeng_sharebutton_copy", "umeng_sharebutton_copy",
		// "umeng_socialize_copy", "umeng_socialize_copy")
		// .addButton("umeng_sharebutton_copyurl", "umeng_sharebutton_copyurl",
		// "umeng_socialize_copyurl", "umeng_socialize_copyurl")
				.setShareboardclickCallback(new ShareBoardlistener() {
					@Override
					public void onclick(SnsPlatform snsPlatform,
							SHARE_MEDIA share_media) {
						if (snsPlatform.mShowWord
								.equals("umeng_sharebutton_copy")) {
							Toast.makeText(activity, "复制文本按钮",
									Toast.LENGTH_LONG).show();
						} else if (snsPlatform.mShowWord
								.equals("umeng_sharebutton_copyurl")) {
							Toast.makeText(activity, "复制链接按钮",
									Toast.LENGTH_LONG).show();

						} else {// Defaultcontent.text +
							new ShareAction(activity)
									.withTitle(title)
									.withText(message)
									.setPlatform(share_media)
									.withTargetUrl(BeanUrl.URLZ + url)
									.withMedia(new UMImage(activity,R.drawable.share_icon))
									.setCallback(mShareListener).share();
						}
					}
				});
	}

	private static class CustomShareListener<T> implements UMShareListener {

		// private WeakReference<ShareActivity> mActivity;
		private WeakReference<T> mActivity;
		private ShareCallBack callBack;

		private CustomShareListener(Activity activity, ShareCallBack callBack) {
			WeakReference<T> mActivity = new WeakReference(activity);
			this.mActivity = mActivity;
			this.callBack = callBack;
		}

		@Override
		public void onResult(SHARE_MEDIA platform) {

			if (platform.name().equals("WEIXIN_FAVORITE")) {
				Toast.makeText((Context) mActivity.get(), platform + " 收藏成功啦",
						Toast.LENGTH_SHORT).show();
			} else {
				if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
						&& platform != SHARE_MEDIA.EMAIL
						&& platform != SHARE_MEDIA.FLICKR
						&& platform != SHARE_MEDIA.FOURSQUARE
						&& platform != SHARE_MEDIA.TUMBLR
						&& platform != SHARE_MEDIA.POCKET
						&& platform != SHARE_MEDIA.PINTEREST
						&& platform != SHARE_MEDIA.LINKEDIN
						&& platform != SHARE_MEDIA.INSTAGRAM
						&& platform != SHARE_MEDIA.GOOGLEPLUS
						&& platform != SHARE_MEDIA.YNOTE
						&& platform != SHARE_MEDIA.EVERNOTE) {
					callBack.onrespone();

					Toast.makeText((Context) mActivity.get(),
							platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
				}

			}
		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
					&& platform != SHARE_MEDIA.EMAIL
					&& platform != SHARE_MEDIA.FLICKR
					&& platform != SHARE_MEDIA.FOURSQUARE
					&& platform != SHARE_MEDIA.TUMBLR
					&& platform != SHARE_MEDIA.POCKET
					&& platform != SHARE_MEDIA.PINTEREST
					&& platform != SHARE_MEDIA.LINKEDIN
					&& platform != SHARE_MEDIA.INSTAGRAM
					&& platform != SHARE_MEDIA.GOOGLEPLUS
					&& platform != SHARE_MEDIA.YNOTE
					&& platform != SHARE_MEDIA.EVERNOTE) {
				Toast.makeText((Context) mActivity.get(), platform + " 分享失败啦",
						Toast.LENGTH_SHORT).show();
				if (t != null) {
					Log.d("throw", "throw:" + t.getMessage());
				}
			}

		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {

			Toast.makeText((Context) mActivity.get(), platform + " 分享取消了",
					Toast.LENGTH_SHORT).show();
		}
	}

	// 分享Activity 回掉调用
	public static void getShareOnActivityResult(Activity activity,
			int requestCode, int resultCode, Intent data) {
		UMShareAPI.get(activity)
				.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 屏幕横竖屏切换时避免出现window leak的问题
	 */
	public static void getOnConfigurationChanged() {
		mShareAction.close();
	}

	// 点击分享
	public static void getOpen() {
		ShareBoardConfig config = new ShareBoardConfig();
		config.setShareboardPostion(ShareBoardConfig.BG_SHAPE_NONE);
		config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
		config.setCancelButtonVisibility(false);
		mShareAction.open();
	}
}
