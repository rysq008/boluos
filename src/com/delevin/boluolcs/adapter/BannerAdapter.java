package com.delevin.boluolcs.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 *     @author 李红涛  @version 创建时间：2016-12-16 下午1:46:26    类说明 
 */
public class BannerAdapter extends PagerAdapter {

	private ArrayList<View> list;

	public BannerAdapter(Context context, ArrayList<View> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}

	@Override
	public void destroyItem(View arg0, int position, Object arg2) {
		// TODO Auto-generated method stub
		// ((ViewPager) arg0).removeView(list.get(position%list.size()));
	}

	@Override
	public Object instantiateItem(View arg0, int position) {
		// TODO Auto-generated method stub
        View view = this.list.get(position % list.size());

        //判断其父容器是否存在，如存在，先和此子控件解除关系
        ViewPager parent = (ViewPager) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        ((ViewGroup)arg0).addView(view);
        return view;
        
//		try {
//			((ViewPager) arg0).addView(list.get(position % list.size()), 0);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list.get(position % list.size());
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

}
