package lq.test;


import lq.retrofit.CallBack;
import lq.retrofit.LQRetrofit;
import lq.test.adapter.OkHttpAdapter;
import lq.test.bean.CommentBean;
import lq.test.bean.UserInfo;
import lq.test.bean.UserListInfo;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        LQRetrofit lqRetrofit = new LQRetrofit.Builder(new OkHttpAdapter())
                .setBaseUrl("http://119.23.224.205:8080/LqCsdnServer/lq-retrofit").build();

        NetService netService = lqRetrofit.create(NetService.class);

        // 获取单个用户信息
        netService
                .queryUser("LQ")
                .execute(new CallBack<UserInfo>() {
                    @Override
                    public void success(UserInfo userInfo) {
                        System.out.println("获取单个用户信息success");
                        System.out.println("success:" + userInfo.toString());
                        System.out.println("---------------------------------------------------");
                    }

                    @Override
                    public void fail(String e) {
                        System.out.println("获取单个用户信息:"+e);
                    }
                });


        //注册用户信息
        netService
                .registerUser("liuqiang","123456","M",24)
                .execute(new CallBack<CommentBean>() {
                    @Override
                    public void success(CommentBean commentBean) {
                        System.out.println("注册用户信息success");
                        System.out.println(commentBean.toString());
                        System.out.println("---------------------------------------------------");
                    }

                    @Override
                    public void fail(String e) {
                        System.out.println("注册用户信息:"+e);
                    }
                });

        //获取所有用户信息
        netService.queryUsers(2,8)
                 .execute(new CallBack<UserListInfo>() {
                     @Override
                     public void success(UserListInfo userListInfo) {
                         System.out.println("获取所有用户信息success");
                         List<UserInfo.DataBean> data = userListInfo.getData();
                         for (UserInfo.DataBean dataBean:data){
                             System.out.println(dataBean.toString());
                         }
                         System.out.println("---------------------------------------------------");
                     }

                     @Override
                     public void fail(String e) {
                         System.out.println("获取所有用户信息:"+e);
                     }
                 });

    }

}
