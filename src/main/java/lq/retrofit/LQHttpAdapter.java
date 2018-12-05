package lq.retrofit;

/**
 * 网络框架适配器 用于集成其它网络框架
 */
public interface LQHttpAdapter {

    void httpRequest(MethodAnnotationInfo annotationInfo,CallBack callBack);

}
