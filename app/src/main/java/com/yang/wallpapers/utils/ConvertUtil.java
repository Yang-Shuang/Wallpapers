package com.yang.wallpapers.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by
 * yangshuang on 2018/7/23.
 */

public class ConvertUtil extends Converter.Factory {

    public static final ConvertUtil INSTANCE = new ConvertUtil();

    private ConvertUtil() {
    }

    public Converter<String, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return ConvertUtil.RequestConverterBody.INSTANCE;
    }

    public Converter<ResponseBody, String> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return ConvertUtil.ResponseConverterBody.INSTANCE;
    }

    static final class ResponseConverterBody implements Converter<ResponseBody, String> {
        static final ConvertUtil.ResponseConverterBody INSTANCE = new ConvertUtil.ResponseConverterBody();

        ResponseConverterBody() {
        }

        public String convert(ResponseBody value) throws IOException {
            return value.string();
        }
    }

    static final class RequestConverterBody implements Converter<String, RequestBody> {
        private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private static ConvertUtil.RequestConverterBody INSTANCE = new ConvertUtil.RequestConverterBody();

        private RequestConverterBody() {
        }

        public RequestBody convert(String value) throws IOException {
            return RequestBody.create(MEDIA_TYPE, value);
        }
    }
}
