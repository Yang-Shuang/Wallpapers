package com.yang.wallpapers.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yang.wallpapers.entity.BaseRequestEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by
 * yangshuang on 2018/7/23.
 */

public class NetWorkUtils {

    private static Retrofit retrofit;
    private static RequestService service;

    public static void init() {

        if (retrofit == null || service == null) {
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(AppConfigConst.HOST_TYPE == 0 ? AppConfigConst.BING_HOST : AppConfigConst.ADESK_HOST);
            builder.addConverterFactory(ConvertUtil.INSTANCE);
            okhttp3.OkHttpClient.Builder clientBuilder = new okhttp3.OkHttpClient.Builder();

            clientBuilder.connectTimeout(15000L, TimeUnit.MILLISECONDS);
            clientBuilder.readTimeout(30000L, TimeUnit.MILLISECONDS);
            OkHttpClient client = clientBuilder.build();
            builder.client(client);
            retrofit = builder.build();
            service = retrofit.create(RequestService.class);
        }
    }

    public static void GET(BaseRequestEntity entity, final RequestListener listeners) {
        if (retrofit == null || service == null) init();
        try {
            HashMap<String, String> params = JsonParser.bean2Map(entity);
            Call<String> call = service.GET(entity.getUrl(), params);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    LogUtil.e("onResponse",response.body());
                    listeners.onSuccess(response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    LogUtil.e("onFailure",t.getMessage());
                    listeners.onSuccess(t.getMessage());
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface RequestListener {
        void onSuccess(String str);

        void onError(String str);
    }

    private interface RequestService {
        @GET
        Call<String> GET(@Url String var1, @QueryMap Map<String, String> var2);
    }
}
