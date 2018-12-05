package lq.test.bean;

public class UserInfo {


    /**
     * status : 200
     * data : {"username":"LQ","age":24,"sex":"男"}
     * message : 操作成功
     */

    private int status;
    private DataBean data;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * username : LQ
         * age : 24
         * sex : 男
         */

        private String username;
        private int age;
        private String sex;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "username='" + username + '\'' +
                    ", age=" + age +
                    ", sex='" + sex + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "status=" + status +
                ", data=" + data.toString() +
                ", message='" + message + '\'' +
                '}';
    }
}
