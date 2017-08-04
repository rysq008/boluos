package com.delevin.boluolcs.adapter;

import java.util.List;

import android.content.Context;
import android.widget.TextView;

import com.delevin.boluolcs.base.adapter.MyBaseAdapter;
import com.delevin.boluolcs.base.adapter.ViewHolder;
import com.delevin.boluolcs.bean.BeanPdfList;
import com.pusupanshi.boluolicai.R;

public class PdfListAdapter extends MyBaseAdapter<BeanPdfList> {

	public PdfListAdapter(Context mContext, List<BeanPdfList> listDatas,
			int mLayoutId) {
		super(mContext, listDatas, mLayoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void holdItem(ViewHolder holder, BeanPdfList item) {
		// TODO Auto-generated method stub
		TextView tvName = holder.getView(R.id.item_pdf_list_tv_name);
		tvName.setText(item.getName());

	}

}
