package com.delevin.boluolcs.fragmnet;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.delevin.application.Myapplication;
import com.delevin.boluolcs.activity.AddNumberActivity;
import com.delevin.boluolcs.activity.MyBankActivity;
import com.delevin.boluolcs.activity.MyHeadPicActivity;
import com.delevin.boluolcs.activity.MyMoreActivity;
import com.delevin.boluolcs.activity.MySafetyManagmentActivity;
import com.delevin.boluolcs.activity.MyTouziActivity;
import com.delevin.boluolcs.activity.MyZijinActivity;
import com.delevin.boluolcs.activity.PayBindActivity;
import com.delevin.boluolcs.activity.TiXianActivity;
import com.delevin.boluolcs.base.fragment.BaseFragment;
import com.delevin.boluolcs.bean.BeanFirstEvent;
import com.delevin.boluolcs.bean.BeanMy;
import com.delevin.boluolcs.bean.BeanUrl;
import com.delevin.boluolcs.fragmentactivity.MyAssetsActivity;
import com.delevin.boluolcs.fragmentactivity.MyRedPacketActivity;
import com.delevin.boluolcs.utils.AndroidUtils;
import com.delevin.boluolcs.utils.BoluoUtils;
import com.delevin.boluolcs.utils.NetUtils;
import com.delevin.boluolcs.utils.OkhttpManger.Funck4;
import com.delevin.boluolcs.utils.ProessDilogs;
import com.delevin.boluolcs.utils.PullToRefreshView;
import com.delevin.boluolcs.utils.PullToRefreshView.OnHeaderRefreshListener;
import com.delevin.boluolcs.utils.QntUtils;
import com.pusupanshi.boluolicai.R;

import de.greenrobot.event.EventBus;

/**
 *  @author 李红涛  @version 创建时间：2016-12-15 下午12:59:53    类说明 
 */
public class MyFragment extends BaseFragment implements OnClickListener {

	private TextView total_money_txt; // 总资产
	private TextView balance_money_txt; // 余额
	private TextView time_txt; // 时间
	private int apm;
	private RelativeLayout manyLayout;
	private String phone;
	private String pay_bind;
	private String id_bind;
	private CheckBox checkBox;
	private BeanMy beanMy;
	private ImageView imgHeadPic;
	private String strRemain_balanc, strTotal_money;
	private RelativeLayout rlNotBnak, rlYesBank;
	private Button btnBangdingBankCard;
	private TextView redPacketNumBerView;
	private String type;
	private MaterialRefreshLayout pullToRefreshView;
	private LinearLayout layout_V;
	private ImageView img_V;

	@Override
	protected View initView(LayoutInflater inflaters, ViewGroup container) {
		View view = inflaters.inflate(R.layout.boluos_fragment_my, container,
				false);
		apm = AndroidUtils.getTime();
		return view;
	}

	@Override
	protected void getFindById(View view) {
		EventBus.getDefault().register(this);
		pullToRefreshView = (MaterialRefreshLayout) view
				.findViewById(R.id.my_pull);
		pullToRefreshView
				.setMaterialRefreshListener(new MaterialRefreshListener() {

					@Override
					public void onRefresh(
							MaterialRefreshLayout materialRefreshLayout) {
						// TODO Auto-generated method stub
						getData();
					}

					@Override
					public void onRefreshLoadMore(
							MaterialRefreshLayout materialRefreshLayout) {
						// TODO Auto-generated method stub
						super.onRefreshLoadMore(materialRefreshLayout);
					}
				});

		redPacketNumBerView = (TextView) view
				.findViewById(R.id.my_redPacketNumBer);
		rlNotBnak = (RelativeLayout) view.findViewById(R.id.my_not_bankcard);
		layout_V = (LinearLayout) view.findViewById(R.id.mys_visibility_layout);
		img_V = (ImageView) view.findViewById(R.id.mys_visibility_image);
		rlYesBank = (RelativeLayout) view.findViewById(R.id.my_yes_bankcard);
		btnBangdingBankCard = (Button) view
				.findViewById(R.id.My_bangdingbankcard);
		btnBangdingBankCard.setOnClickListener(this);
		total_money_txt = (TextView) view.findViewById(R.id.my_total_money);
		balance_money_txt = (TextView) view.findViewById(R.id.my_balance_money);
		checkBox = (CheckBox) view.findViewById(R.id.my_eyes_checkbox);
		imgHeadPic = (ImageView) view.findViewById(R.id.my_img_headpic);
		time_txt = (TextView) view.findViewById(R.id.my_time);
		if (apm == 0)
			time_txt.setText("上午好");
		else
			time_txt.setText("下午好");
		RelativeLayout totalLayout = (RelativeLayout) view
				.findViewById(R.id.my_total_layout);
		TextView paytView = (TextView) view.findViewById(R.id.my_pay);
		TextView tixianView = (TextView) view.findViewById(R.id.my_tixian);
		RelativeLayout touziLayout = (RelativeLayout) view
				.findViewById(R.id.my_touzi_layout);
		RelativeLayout moneyLayout = (RelativeLayout) view
				.findViewById(R.id.my_money_layout);
		RelativeLayout pageLayout = (RelativeLayout) view
				.findViewById(R.id.my_page_layout);
		RelativeLayout bankLayout = (RelativeLayout) view
				.findViewById(R.id.my_bank_layout);
		RelativeLayout safeLayout = (RelativeLayout) view
				.findViewById(R.id.my_safe_layout);
		manyLayout = (RelativeLayout) view.findViewById(R.id.my_many_layout);
		totalLayout.setOnClickListener(this);
		paytView.setOnClickListener(this);
		tixianView.setOnClickListener(this);
		touziLayout.setOnClickListener(this);
		moneyLayout.setOnClickListener(this);
		pageLayout.setOnClickListener(this);
		bankLayout.setOnClickListener(this);
		safeLayout.setOnClickListener(this);
		manyLayout.setOnClickListener(this);
		getshareData();
		imgHeadPic.setOnClickListener(this);
		checkBox.setChecked(false);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					option(true);
				} else {
					option(false);
				}
			}
		});

		if (TextUtils.equals(pay_bind, "1")) {
			checkBox.setVisibility(View.VISIBLE);
			rlNotBnak.setVisibility(View.GONE);
			rlYesBank.setVisibility(View.VISIBLE);
		} else {
			checkBox.setVisibility(View.GONE);
			rlNotBnak.setVisibility(View.VISIBLE);
			rlYesBank.setVisibility(View.GONE);
		}
	}

	public void onEventMainThread(BeanFirstEvent event) {

		String msg = event.getMsg();
		if (TextUtils.equals(msg, "payOrTian")) {
			getshareData();
			getData();
		}
	}

	private void getshareData() {
		if (getActivity() != null) {
			Map<String, String> data = BoluoUtils.getShareData(getActivity());
			phone = data.get("phone");
			pay_bind = data.get("pay_bind");
			id_bind = data.get("id_bind");
		} else {
			Toast.makeText(getActivity(), "kongzhizhen", Toast.LENGTH_SHORT)
					.show();
		}

	}

	private void option(boolean isChecked) {

		if (isChecked) {
			total_money_txt.setText("*****");
			balance_money_txt.setText("*****");
		} else {
			if (!TextUtils.isEmpty(strTotal_money)) {
				initData(beanMy);
			} else {
				total_money_txt.setText("---");
				balance_money_txt.setText("-");
			}
		}
	}

	@Override
	protected void getData() {
		ProessDilogs.getProessAnima(img_V, getActivity());
		if (TextUtils.equals(pay_bind, "1")) {
			checkBox.setVisibility(View.VISIBLE);
			rlNotBnak.setVisibility(View.GONE);
			rlYesBank.setVisibility(View.VISIBLE);
		} else {
			checkBox.setVisibility(View.GONE);
			rlNotBnak.setVisibility(View.VISIBLE);
			rlYesBank.setVisibility(View.GONE);
		}
		Myapplication.okhttpManger.sendComplexForm(getActivity(), false,
				QntUtils.getURL(BeanUrl.MY_STRING, phone), null, new Funck4() {

					@Override
					public void onResponse(JSONObject result) {
						try {
							String code = result.getString("code");
							ProessDilogs.closeAnimation(img_V, layout_V);
							if (TextUtils.equals(code, "10000")) {
								beanMy = new BeanMy();
								beanMy.getMyData(result, beanMy);
								Map<String, String> map = new HashMap<String, String>();
								map.put("is_vip", beanMy.getIs_vip());
								BoluoUtils.getShareCommit(getActivity(), map);
								redPacketNumBerView.setText("("
										+ beanMy.getHas_hongbao_num() + ")");
								initData(beanMy);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							pullToRefreshView.finishRefresh();
						}
					}
				});
	}

	private double getZong(String freezeamount, String remain_balance,
			String uncollectedamount) {

		double i = QntUtils.getDouble(freezeamount);
		double j = QntUtils.getDouble(remain_balance);
		double k = QntUtils.getDouble(uncollectedamount);
		double z = i + j + k;
		return z;

	}

	// 控件赋值
	private void initData(BeanMy beanMy) {
		double total_money = getZong(beanMy.getFreezeamount(),
				beanMy.getRemain_balance(), beanMy.getUncollectedamount());
		strTotal_money = QntUtils.getFormat(total_money);
		strRemain_balanc = beanMy.getRemain_balance();
		total_money_txt.setText(QntUtils.getFormat(total_money));
		balance_money_txt.setText(QntUtils.getFormat(QntUtils.getDouble(beanMy
				.getRemain_balance())));
		type = beanMy.getType();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		// 总资产跳转
		case R.id.my_total_layout:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {

				Intent intent = new Intent(getActivity(),
						MyAssetsActivity.class);
				intent.putExtra("freezeamount", beanMy.getFreezeamount());
				intent.putExtra("remain_balance", strRemain_balanc);
				intent.putExtra("uncollectedamount",
						beanMy.getUncollectedamount());
				intent.putExtra("total_money", strTotal_money);
				intent.putExtra("rechargeamount", beanMy.getRechargeamount());
				intent.putExtra("remainamount", beanMy.getRemainamount());
				intent.putExtra("z_activity_interest",
						beanMy.getZ_activity_interest());
				intent.putExtra("z_hongbao", beanMy.getZ_hongbao());
				intent.putExtra("z_interest", beanMy.getZ_interest());
				intent.putExtra("z_yaoqing_shouyi",
						beanMy.getZ_yaoqing_shouyi());
				intent.putExtra("sum_profit", beanMy.getSum_profit());
				startActivity(intent);
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		// 充值跳转
		case R.id.my_pay:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				if (TextUtils.equals(pay_bind, "1")) {

					startActivity(new Intent(getActivity(),
							PayBindActivity.class));

				} else {

					startActivity(new Intent(getActivity(),
							AddNumberActivity.class));
				}
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}

			break;
		// 提现跳转
		case R.id.my_tixian:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				if (TextUtils.equals(pay_bind, "1")) {
					startActivity(new Intent(getActivity(),
							TiXianActivity.class));
				} else {
					startActivity(new Intent(getActivity(),
							AddNumberActivity.class));
				}
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		// 我的投资
		case R.id.my_touzi_layout:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				startActivity(new Intent(getActivity(), MyTouziActivity.class));
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		// 资金记录
		case R.id.my_money_layout:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				startActivity(new Intent(getActivity(), MyZijinActivity.class));
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}

			break;
		// 我的红包
		case R.id.my_page_layout:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				startActivity(new Intent(getActivity(),
						MyRedPacketActivity.class));
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}

			break;
		// 我的银行卡
		case R.id.my_bank_layout:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				if (TextUtils.equals(id_bind, "1")) {

					if (TextUtils.equals(pay_bind, "1")) {

						startActivity(new Intent(getActivity(),
								MyBankActivity.class));
					} else {

					}
				}
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		// 安全管理
		case R.id.my_safe_layout:
			startActivity(new Intent(getActivity(),
					MySafetyManagmentActivity.class));
			break;
		// 更多
		case R.id.my_many_layout:
			Intent intent = new Intent(getActivity(), MyMoreActivity.class);
			intent.putExtra("type", type);
			startActivity(intent);
			break;
		// 个人中心
		case R.id.my_img_headpic:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				startActivity(new Intent(getActivity(), MyHeadPicActivity.class));
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		// 个人中心
		case R.id.My_bangdingbankcard:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				startActivity(new Intent(getActivity(), AddNumberActivity.class));
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}

			break;
		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
