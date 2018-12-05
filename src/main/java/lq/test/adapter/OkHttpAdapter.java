package lq.test.adapter;

import com.google.gson.Gson;
import lq.retrofit.*;
import okhttp3.*;
import okhttp3.Call;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 集成LQRetrofit的适配器：对OkHttp网络框架的适配器 的demo
 */
public class OkHttpAdapter implements LQHttpAdapter {

    private static final OkHttpClient okHttpClient;
    private static final Gson gson;

    static {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        gson = new Gson();
    }

    @Override
    public void httpRequest(MethodAnnotationInfo annotationInfo, CallBack callBack) {
        Request.Builder builder = new Request.Builder().url(annotationInfo.getUrl());
        switch (annotationInfo.getRequestType()) {
            case RequestType.GET:
                Map<String, String> headers = annotationInfo.getHeaders();
                builder.get();
                if (headers != null && headers.size() > 0) {
                    builder.headers(Headers.of(headers));
                }
                executeCallback(builder.build(),callBack,annotationInfo);
                break;
            case RequestType.POST:
                //执行了okhttp的post请求...........
                Request request = null;
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                Map<String, String> params = annotationInfo.getParams();
                String body = annotationInfo.getBody();
                if (params!=null&&body!=null&&body.length()>0){
                    throw new IllegalStateException("params and body can only exist one");
                }
                if (params!=null&&params.size()>0){
                    params.forEach(formBodyBuilder::add);
                    request = builder.post(formBodyBuilder.build()).build();
                }
                if (body!=null&&body.length()>0){
                    request = builder.post(RequestBody.create(MediaType.parse("application/json"),body)).build();
                }
                if (request==null){
                    throw new IllegalStateException("params and body must specify one");
                }
                executeCallback(request,callBack,annotationInfo);
                break;
            case RequestType.PUT:
                //执行了okhttp的put请求...........
                break;
            case RequestType.DELETE:
                //执行了okhttp的delete请求...........
                break;
            case RequestType.PATCH:
                //执行了okhttp的patch请求...........
                break;
            case RequestType.HEAD:
                //执行了okhttp的head请求...........
                break;
            case RequestType.OPTIONS:
                //执行了okhttp的options请求...........
                break;
            default:
                break;
        }
    }

    private void executeCallback(Request request,CallBack callBack,MethodAnnotationInfo annotationInfo){
        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callBack.fail(e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody body = response.body();
                        if (body != null) {
                            String json = body.string();
                            callBack.success(gson.fromJson(json, annotationInfo.getResultType()));
                            body.close();
                        } else {
                            callBack.fail("body is null");
                        }
                    }
                });
    }


}
