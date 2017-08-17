package com.delevin.boluolcs.chat.utils;


import android.content.Context;
import android.widget.ImageView;

import com.delevin.application.Myapplication;
import com.pusupanshi.boluolicai.R;
import com.squareup.picasso.Picasso;

public class UserUtils {
    /**
     * 根据username获取相应user，由于demo没有真实的用户数据，这里给的模拟的数据；
     * @param username
     * @return
     */
//	private static String[] avatar={"http://img5.duitang.com/uploads/item/201507/21/20150721172011_mGYkh.thumb.224_0.jpeg",
//            "http://www.qqxoo.com/uploads/allimg/160208/19291Q227-3.jpg",
//           "http://www.feizl.com/upload2007/2014_02/1402261732574111.jpg",
//           "http://img6.itiexue.net/1314/13143390.jpg",
//           "http://img5q.duitang.com/uploads/item/201505/26/20150526033548_NjZxS.thumb.224_0.jpeg",
//           "http://www.qqxoo.com/uploads/allimg/170314/1423145B3-6.jpg",
//           "http://diy.qqjay.com/u2/2012/1015/ce912cbb8f78ab9f77846dac2797903b.jpg",
//           "http://www.qqxoo.com/uploads/allimg/170314/1423145501-4.jpg",
//           "http://diy.qqjay.com/u2/2014/1208/ac9aa749faa68eecd84ed14b2da0f9e3.jpg",
//           "http://tupian.qqjay.com/tou2/2017/0120/39b35eed7d7000fc214d3f5198032f11.jpg"};
	
    public static User getUserInfo(String username){
        User user = ((Myapplication) Myapplication.getInstance()).getContactList().get(username);
        if(user == null){
            user = new User(username);
        }
            
        if(user != null){
            //demo没有这些数据，临时填�?
            user.setNick(username);
//            user.setAvatar("http://downloads.easemob.com/downloads/57.png");
        }
        return user;
    }
    
    /**
     * 设置用户头像
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
        User user = getUserInfo(username);
        if(user != null){
//        	String  a=avatar[(int)(10.0*Math.random()) + 1];
            Picasso.with(context).load(user.getAvatar()).placeholder(R.drawable.default_avatar).into(imageView);
        }else{
            Picasso.with(context).load(R.drawable.default_avatar).into(imageView);
            
        }
    }
    /**
     * 设置客服头像
     * @param username
     */
    public static void setServersAvatar(Context context, String username, ImageView imageView){
        User user = getUserInfo(username);
        if(user != null){
            Picasso.with(context).load(user.getAvatar()).placeholder(R.drawable.boluo).into(imageView);
//            Picasso.with(context).load("https://m.boluolc.com/upload/images/banner_dir/app_banner_jianguan.jpg").into(imageView);
        }else{
            Picasso.with(context).load(R.drawable.boluo).into(imageView);
//            Picasso.with(context).load("").into(imageView);
        }
    }
    
}
