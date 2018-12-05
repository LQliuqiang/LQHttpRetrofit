package lq.retrofit;

/**
 * 执行的网络请求后的回调
 * @param <R>
 */
public abstract class CallBack<R> {

    public abstract void success(R r);

    public abstract void fail(String e);

    /**
     * 进度回调
     *
     * @param progress
     * @param total
     */
    public void onProgress(int progress, long total) {

    }
}
