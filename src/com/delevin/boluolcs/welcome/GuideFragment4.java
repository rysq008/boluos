package com.delevin.boluolcs.welcome;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.delevin.boluolcs.base.fragment.BaseFragment;
import com.delevin.boluolcs.main.MainActivity;
import com.pusupanshi.boluolicai.R;

public class GuideFragment4 extends BaseFragment {

	@Override
	protected View initView(LayoutInflater inflaters, ViewGroup container) {
		View view = inflaters.inflate(R.layout.boluos_guide4, container, false);
		ImageView view2 = (ImageView) view.findViewById(R.id.guridButton);
		view2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				startActivity(new Intent(getActivity(), MainActivity.class));

				getActivity().finish();
			}
		});

		return view;
	}

	@Override
	protected void getFindById(View view) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

}
