package lq.retrofit;

/**
 * 接口返回值的包装类（也是真正执行类）
 * @param <R>
 */
public class Call<R> {

    private LQHttpAdapter lqHttpAdapter;
    private MethodAnnotationInfo methodAnnotationInfo;


    Call(LQHttpAdapter lqHttpAdapter, MethodAnnotationInfo methodAnnotationInfo) {
        this.lqHttpAdapter = lqHttpAdapter;
        this.methodAnnotationInfo = methodAnnotationInfo;
    }


    public void execute(CallBack<R> callBack) {
        lqHttpAdapter.httpRequest(methodAnnotationInfo, callBack);
    }

}
