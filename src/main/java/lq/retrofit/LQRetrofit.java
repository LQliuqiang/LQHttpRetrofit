package lq.retrofit;

import lq.retrofit.http.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.concurrent.ConcurrentHashMap;

public class LQRetrofit {

    private final ConcurrentHashMap<String, MethodAnnotationInfo> methodAnnotationInfoCache = new ConcurrentHashMap<>();

    private LQHttpAdapter lqHttpAdapter;
    private String baseUrl;

    private LQRetrofit(Builder builder) {
        this.lqHttpAdapter = builder.getLQHttpAdapter();
        this.baseUrl = builder.getBaseUrl();
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public <T> T create(Class<T> service) {
        //验证service是否是接口 且接口中要有方法
        Util.validateServiceInterface(service);
        //使用动态代理
        ClassLoader classLoader = service.getClassLoader();
        Class[] classes = {service};
        InvocationHandler invocationHandler = new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //如果是在Object类中声明的方法(也就是自己实现了)，就直接调用
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(proxy, args);
                } else if (method.isDefault()) { //如果是接口中定义的默认方法 抛出异常
                    return Util.invokeDefaultMethod(method, service, proxy, args);
                } else { //开始实现LQRetrofit
                    //获取方法返回值的全类名
                    Type genericReturnType = method.getGenericReturnType();
                    // 判断返回值的类型是否是参数化类型
                    if (genericReturnType instanceof ParameterizedType) {
                        MethodAnnotationInfo methodAnnotationInfo = getMethodAnnotationInfo(method, args, genericReturnType);
                        return new Call<>(lqHttpAdapter, methodAnnotationInfo);
                    } else {
                        throw new IllegalStateException("The return value type of the method must be the Call<T> type");
                    }
                }
            }
        };
        return (T) Proxy.newProxyInstance(classLoader, classes, invocationHandler);
    }

    /**
     * 获取解析后的MethodAnnotationInfo
     *
     * @param method 方法
     * @param args   方法参数
     * @return
     */
    public MethodAnnotationInfo getMethodAnnotationInfo(Method method, Object[] args, Type genericReturnType) {
        String methodName = method.getName();
        MethodAnnotationInfo annotationInfo = methodAnnotationInfoCache.get(methodName);
        if (annotationInfo != null) {
            return annotationInfo;
        } else {
            MethodAnnotationInfo methodAnnotationInfo = parseMethodAnnotationInfo(method, args, genericReturnType);
            methodAnnotationInfoCache.put(methodName, methodAnnotationInfo);
            return methodAnnotationInfo;
        }
    }

    /**
     * 解析接口方法中的注解
     *
     * @param method 方法
     * @param args   方法参数
     * @return
     */
    public MethodAnnotationInfo parseMethodAnnotationInfo(Method method, Object[] args, Type genericReturnType) {
        //获取调用接口方法上的所有注解
        Annotation[] methodAnnotations = method.getAnnotations();
        //解析方法上的所有注解
        if (methodAnnotations.length < 1) {
            throw new IllegalStateException("Please specify @GET or @POST or @PUT or @DELETE");
        }
        ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();// 返回表示此类型实际类型参数的 Type 对象的数组
        Type resultType = typeArguments[0];//接口获取返回值的类型
        //实例化MethodAnnotationInfo
        MethodAnnotationInfo annotationInfo = new MethodAnnotationInfo();
        annotationInfo.setResultType(resultType);
        for (Annotation annotation : methodAnnotations) {
            if (annotation instanceof GET) {
                annotationInfo.setRequestType(RequestType.GET);
                String url = ((GET) annotation).value();
                if (((GET) annotation).useBaseUrl()) {
                    url = getBaseUrl() + url;
                }
                annotationInfo.setUrl(url);
            } else if (annotation instanceof POST) {
                annotationInfo.setRequestType(RequestType.POST);
                String url = ((POST) annotation).value();
                if (((POST) annotation).useBaseUrl()) {
                    url = getBaseUrl() + url;
                }
                annotationInfo.setUrl(url);
            } else if (annotation instanceof DELETE) {
                annotationInfo.setRequestType(RequestType.DELETE);
                String url = ((DELETE) annotation).value();
                if (((DELETE) annotation).useBaseUrl()) {
                    url = getBaseUrl() + url;
                }
                annotationInfo.setUrl(url);
            } else if (annotation instanceof PUT) {
                annotationInfo.setRequestType(RequestType.PUT);
                String url = ((PUT) annotation).value();
                if (((PUT) annotation).useBaseUrl()) {
                    url = getBaseUrl() + url;
                }
                annotationInfo.setUrl(url);
            } else if (annotation instanceof PATCH) {
                annotationInfo.setRequestType(RequestType.PATCH);
                String url = ((PATCH) annotation).value();
                if (((PATCH) annotation).useBaseUrl()) {
                    url = getBaseUrl() + url;
                }
                annotationInfo.setUrl(url);
            } else if (annotation instanceof HEAD) {
                annotationInfo.setRequestType(RequestType.HEAD);
                String url = ((HEAD) annotation).value();
                if (((HEAD) annotation).useBaseUrl()) {
                    url = getBaseUrl() + url;
                }
                annotationInfo.setUrl(url);
            } else if (annotation instanceof OPTIONS) {
                annotationInfo.setRequestType(RequestType.OPTIONS);
                String url = ((OPTIONS) annotation).value();
                if (((OPTIONS) annotation).useBaseUrl()) {
                    url = getBaseUrl() + url;
                }
                annotationInfo.setUrl(url);
            } else if (annotation instanceof Headers) {
                String[] headers = ((Headers) annotation).value();
                if (headers.length > 0) {
                    for (String header : headers) {
                        String[] split = header.split(":");
                        if (split.length != 2) {
                            throw new IllegalArgumentException("Designated header is not standard");
                        } else {
                            annotationInfo.addHeader(split[0], split[1]);
                        }
                    }
                } else {
                    throw new NullPointerException("Headers is not null");
                }
            }
        }
        //解析方法中的参数注解
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations.length > 0) {
            for (int i = 0; i < parameterAnnotations.length; i++) {
                Annotation[] parameterAnnotation = parameterAnnotations[i];
                Annotation annotation = parameterAnnotation[0];
                if (annotation instanceof Header) {
                    String key = ((Header) annotation).value();
                    annotationInfo.addHeader(key, args[i] + "");
                } else if (annotation instanceof Body) {
                    annotationInfo.setBody(args[i] + "");
                } else if (annotation instanceof Param) {
                    String key = ((Param) annotation).value();
                    annotationInfo.addParam(key, args[i] + "");
                } else if (annotation instanceof PathVariable) {
                    annotationInfo.addPath(new KVEntry<>(((PathVariable) annotation).value(),
                            args[i] + ""));
                }
            }
        }
        return annotationInfo;
    }

    public static class Builder {

        private String baseUrl = "";
        private LQHttpAdapter lqHttpAdapter;

        public Builder(LQHttpAdapter lqHttpAdapter) {
            this.lqHttpAdapter = lqHttpAdapter;
        }

        String getBaseUrl() {
            return baseUrl;
        }

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = Util.strIsEmpty(baseUrl) ? "" : baseUrl;
            return this;
        }

        LQHttpAdapter getLQHttpAdapter() {
            return lqHttpAdapter;
        }

        public LQRetrofit build() {
            return new LQRetrofit(this);
        }
    }

}
