package com.delevin.boluolcs.adapter;

import java.util.List;

import android.content.Context;
import android.widget.ImageView;

import com.delevin.boluolcs.base.adapter.MyBaseAdapter;
import com.delevin.boluolcs.base.adapter.ViewHolder;
import com.delevin.boluolcs.bean.BeanjieKuan;
import com.delevin.boluolcs.utils.AndroidUtils;
import com.pusupanshi.boluolicai.R;

/**
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-4-21 下午1:39:46 类说明
 */

public class HetongAdapter extends MyBaseAdapter<BeanjieKuan> {
	public HetongAdapter(Context mContext, List<BeanjieKuan> listDatas,
			int mLayoutId) {
		super(mContext, listDatas, mLayoutId);
	}

	@Override
	public void holdItem(ViewHolder holder, BeanjieKuan item) {
		ImageView img = holder.getView(R.id.hetong_imgs);
		AndroidUtils.getImg(mContext, item.getPath(), img,
				R.drawable.boluo_center, R.drawable.boluo_fail);
	}

}
