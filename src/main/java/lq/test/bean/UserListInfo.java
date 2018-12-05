package lq.test.bean;

import java.util.List;

public class UserListInfo {


    /**
     * status : 200
     * data : [{"username":"Alice1","age":11,"sex":"女"},{"username":"LQ2","age":12,"sex":"男"},{"username":"Alice3","age":13,"sex":"女"}]
     * message : 操作成功
     */

    private int status;
    private String message;
    private List<UserInfo.DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserInfo.DataBean> getData() {
        return data;
    }

    public void setData(List<UserInfo.DataBean> data) {
        this.data = data;
    }

}
