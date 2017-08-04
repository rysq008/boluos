package com.delevin.boluolcs.base.fragment;

import com.delevin.boluolcs.broderecaster.ConnectionChangeReceiver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *     @author 李红涛  @version 创建时间：2016-12-15 下午1:02:42  类说明 
 */
public abstract class BaseFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ConnectionChangeReceiver.registerReceiver(getActivity());
		getActivity();

		View view = initView(inflater, container);

		getFindById(view);

		getData();

		return view;

	}

	protected abstract View initView(LayoutInflater inflaters,
			ViewGroup container);

	protected abstract void getFindById(View view);

	protected abstract void getData();

}
