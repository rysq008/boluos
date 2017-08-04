package com.delevin.boluolcs.fragmnet;

	import org.json.JSONException;
	import org.json.JSONObject;
	import android.content.Intent;
	import android.text.TextUtils;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.View.OnClickListener;
	import android.view.ViewGroup;
	import android.widget.Button;
	import android.widget.ImageView;
	import android.widget.LinearLayout;
	import android.widget.RelativeLayout;
	import android.widget.TextView;
	import com.delevin.application.Myapplication;
	import com.delevin.boluolcs.base.fragment.BaseFragment;
	import com.delevin.boluolcs.bean.BeanUrl;
	import com.delevin.boluolcs.fragmentactivity.ShareYaoqingActivity;
	import com.delevin.boluolcs.utils.BoluoUtils;
	import com.delevin.boluolcs.utils.NetUtils;
	import com.delevin.boluolcs.utils.ProessDilogs;
	import com.delevin.boluolcs.utils.PullToRefreshView;
	import com.delevin.boluolcs.utils.PullToRefreshView.OnHeaderRefreshListener;
	import com.delevin.boluolcs.utils.QntUtils;
	import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
	import com.delevin.jsandroid.JSAndroidActivity;
	import com.pusupanshi.boluolicai.R;
	import com.pusupanshi.boluolicai.wxapi.ShareActivity;
	
/**
 *     @author 李红涛  @version 创建时间：2016-12-15 下午1:00:06    类说明 
 */
public class FaxianFragment extends BaseFragment implements OnClickListener,OnHeaderRefreshListener {
	private TextView tvFanliShuoming;
	private RelativeLayout linMeiriQiandao, linJifensShangcheng,linHongdongZhongxin;
	private String phone;
	private TextView tvFanli, tvYaoqing;
	private String token;
	private PullToRefreshView pullToRefreshView;
	private LinearLayout layout_V;
	private ImageView img_V;
	@Override
	protected View initView(LayoutInflater inflaters, ViewGroup container) {
		View view = inflaters.inflate(R.layout.boluos_fragment_faxian,
				container, false);
		return view;
	}
	@Override
	protected void getFindById(View view) {
		getShareData();
		layout_V = (LinearLayout) view.findViewById(R.id.faxian_visibility_layout);
		img_V = (ImageView) view.findViewById(R.id.faxian_visibility_image);
		RelativeLayout layoutFanli = (RelativeLayout)view.findViewById(R.id.faxian_ShareLeiji);
		pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.faxian_pull);
		pullToRefreshView.setOnHeaderRefreshListener(this);
		RelativeLayout layoutFan = (RelativeLayout) view.findViewById(R.id.faxian_ShareFan);
		Button btQuyaoqinghaoyou = (Button) view.findViewById(R.id.open_share);
		tvFanliShuoming = (TextView) view.findViewById(R.id.guize_bt);
		linMeiriQiandao = (RelativeLayout) view.findViewById(R.id.meiriqiandao);
		linJifensShangcheng = (RelativeLayout) view.findViewById(R.id.jifenshangcheng);
		linHongdongZhongxin = (RelativeLayout) view.findViewById(R.id.huodongzhongxin);
		tvFanli = (TextView) view.findViewById(R.id.leijifanli);
		tvYaoqing = (TextView) view.findViewById(R.id.leijiyaoqing);
		layoutFan.setOnClickListener(this);
		layoutFanli.setOnClickListener(this);
		btQuyaoqinghaoyou.setOnClickListener(this);
		tvFanliShuoming.setOnClickListener(this);
		linMeiriQiandao.setOnClickListener(this);
		linJifensShangcheng.setOnClickListener(this);
		linHongdongZhongxin.setOnClickListener(this);
	}
	//
	private void getShareData() {
		phone = BoluoUtils.getShareOneData(getActivity(), "phone");
		BoluoUtils.getShareOneData(getActivity(), "memberId");
		token = BoluoUtils.getShareOneData(getActivity(), "login_token");
	}
	@Override
	protected void getData() {
		
		ProessDilogs.getProessAnima(img_V, getActivity());
		
		Myapplication.okhttpManger.sendComplexForm(getActivity(), false,
				
				QntUtils.getURL(BeanUrl.yaoqingMa, phone), null, new Funck4() {
			
					@Override
					public void onResponse(JSONObject result) {
						String code;
						try {
							ProessDilogs.closeAnimation(img_V, layout_V);
							code = result.getString("code");
							if (TextUtils.equals(code, "10000")) {
								
								tvYaoqing.setText(result.getString("people_amount"));
								tvFanli.setText(QntUtils.getFormat(QntUtils.getDouble(result.getString("profit_amount"))));
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.faxian_ShareFan:
			
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				
				String tag = "0";
				String check = "true";
				Intent intent = new Intent(getActivity(),ShareYaoqingActivity.class);
				intent.putExtra("tag", tag);
				intent.putExtra("check", check);
				startActivity(intent);

			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用","确定");
			}
			break;
		case R.id.faxian_ShareLeiji:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				String tagLeiji = "1";
				String checkLeiji = "true";
				Intent intentLeiji = new Intent(getActivity(),
						ShareYaoqingActivity.class);
				intentLeiji.putExtra("tag", tagLeiji);
				intentLeiji.putExtra("check", checkLeiji);
				startActivity(intentLeiji);
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		case R.id.open_share:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				Intent intentQuyaoqinghaoyou = new Intent(getActivity(),
						ShareActivity.class);
				startActivity(intentQuyaoqinghaoyou);
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		case R.id.guize_bt:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				Intent intentgui = new Intent(getActivity(),
						JSAndroidActivity.class);
				intentgui.putExtra("jsUrl", BeanUrl.FanliShuoming);
				intentgui.putExtra("title", "规则说明");
				startActivity(intentgui);
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		case R.id.meiriqiandao:

			Intent qiandao = new Intent(getActivity(), JSAndroidActivity.class);
			qiandao.putExtra("jsUrl", BeanUrl.MeiriQiandao + "?phone=" + phone
					+ "&" + "token=" + token);
			qiandao.putExtra("title", "签到中心");
			qiandao.putExtra("type", "3");
			qiandao.putExtra("right", "rightQ");
			startActivity(qiandao);
			break;
		case R.id.jifenshangcheng:
			Intent jifen = new Intent(getActivity(), JSAndroidActivity.class);
			jifen.putExtra("jsUrl", BeanUrl.JifenShangcheng + "?phone=" + phone
					+ "&" + "token=" + token);
			jifen.putExtra("title", "积分商城");
			startActivity(jifen);
			break;
		case R.id.huodongzhongxin:
			Intent huodong = new Intent(getActivity(), JSAndroidActivity.class);
			huodong.putExtra("jsUrl", BeanUrl.HuodongZhongxin + "?phone="
					+ phone + "&" + "token=" + token);
			huodong.putExtra("title", "活动中心");
			huodong.putExtra("type", "1");
			startActivity(huodong);
			break;
		default:
			break;
		}
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		pullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				pullToRefreshView.onHeaderRefreshComplete();
				getData();
			}

		}, 1000);
	}
}
