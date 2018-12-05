package lq.test;

import lq.retrofit.Call;
import lq.retrofit.http.*;
import lq.test.bean.CommentBean;
import lq.test.bean.UserInfo;
import lq.test.bean.UserListInfo;


public interface NetService {

    /**
     * 获取用户信息 get请求
     */
    @GET(value = "/getUsers?page={page}&pageSize={pageSize}")
    Call<UserListInfo> queryUsers(@PathVariable("page")int page, @PathVariable("pageSize")int pageSize);

    /**
     * 获取所有用户信息 GET请求
     */
    @GET(value = "/getUser")
    Call<UserInfo> queryUser(@Header("username") String username);


    /**
     * 注册用户信息 post请求 不使用LQRetrofit默认配置的BaseUrl
     */
    @POST(value = "/registerUser")
    Call<CommentBean> registerUser(@Param("username")String username, @Param("password")String password,
                                   @Param("sex")String sex, @Param("age")Integer age);

    /**
     * 测试
     */
    @Headers({
            "xxxx:aaa",
            "yyyy:bbbbbxsdsdx"
    })
    @PUT("/updateUser")
    Call<CommentBean> test(@Body String userInfo);

}
