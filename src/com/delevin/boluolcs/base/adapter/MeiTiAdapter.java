package com.delevin.boluolcs.base.adapter;

import java.util.List;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.delevin.boluolcs.bean.BeanMeiTi;
import com.delevin.boluolcs.utils.AndroidUtils;
import com.delevin.boluolcs.utils.QntUtils;
import com.pusupanshi.boluolicai.R;

/**
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-3-7 上午10:23:53 类说明
 */
public class MeiTiAdapter extends MyBaseAdapter<BeanMeiTi> {

	public MeiTiAdapter(Context mContext, List<BeanMeiTi> listDatas,
			int mLayoutId) {
		super(mContext, listDatas, mLayoutId);
	}

	@Override
	public void holdItem(ViewHolder holder, BeanMeiTi item) {
		TextView title = holder.getView(R.id.meiTi_title);
		TextView time = holder.getView(R.id.meiTi_posttime);
		ImageView img = holder.getView(R.id.meiTi_img);
		AndroidUtils.getImg(mContext, item.getImage(), img,
				R.drawable.boluo_center, R.drawable.boluo_fail);
		time.setText(QntUtils.getYMD(item.getPosttime()));
		title.setText(item.getTitle());
	}

	public void getData() {
	}
}
