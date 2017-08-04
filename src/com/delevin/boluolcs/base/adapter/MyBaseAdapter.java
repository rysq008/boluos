package com.delevin.boluolcs.base.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
	protected LayoutInflater layoutInflater;
	protected Context mContext;
	protected List<T> listDatas;
	protected int mLayoutId;

	public MyBaseAdapter(Context mContext, List<T> listDatas, int mLayoutId) {
		super();
		layoutInflater = LayoutInflater.from(mContext);
		this.mContext = mContext;
		this.listDatas = listDatas;
		this.mLayoutId = mLayoutId;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listDatas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,
				mLayoutId, position);
		holdItem(viewHolder, listDatas.get(position));
		return viewHolder.getConvertView();
	}

	public abstract void holdItem(ViewHolder holder, T item);
}
