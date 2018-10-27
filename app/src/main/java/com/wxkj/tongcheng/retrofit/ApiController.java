package com.wxkj.tongcheng.retrofit;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.wxkj.tongcheng.MyApp;
import com.wxkj.tongcheng.bean.ResponseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {

    private volatile static ApiService sApiService;

    private ApiController() {
        //声明日志类
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //设定日志级别
//        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //自定义OkHttpClient
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApp.getContext()));

        okHttpClient.cookieJar(cookieJar)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .retryOnConnectionFailure(true);

        sApiService = new Retrofit.Builder()
                .baseUrl(ApiConstants.URL.BASE_URL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class);
    }

    public static ApiService getService() {
        if (sApiService == null) {
            synchronized (ApiController.class) {
                if (sApiService == null) {
                    new ApiController();
                }
            }
        }
        return sApiService;
    }

    public static <T> Function<ResponseModel<T>, ObservableSource<T>> judgeData(Class<T> clazz) {
        return simpleResponseEntityResponseModel -> Observable.create((ObservableOnSubscribe<T>) emitter -> {
            if (simpleResponseEntityResponseModel.getCode() == 1) {
                if (simpleResponseEntityResponseModel.getData() == null) {
                    emitter.onNext(clazz.newInstance());
                } else {
                    emitter.onNext(simpleResponseEntityResponseModel.getData());
                }
            } else {
                switch (simpleResponseEntityResponseModel.getCode()) {
//                    case 6: // USER_NOT_EXIT(6, "用户不存在"),
//                        emitter.onError(new Error(simpleResponseEntityResponseModel.getMsg()));
//                        break;
                    default:
                        emitter.onError(new Error(simpleResponseEntityResponseModel.getMsg()));
                        break;
                }
            }
            emitter.onComplete();
        });
    }

    public static <T> Function<ResponseModel<T>, ObservableSource<T>> judgeListData() {
        return simpleResponseEntityResponseModel -> Observable.create((ObservableOnSubscribe<T>) emitter -> {
            if (simpleResponseEntityResponseModel.getCode() == 1) {
                if (simpleResponseEntityResponseModel.getData() == null) {
                    List<T> l = new ArrayList<>();
                    emitter.onNext((T) l);
                } else {
                    emitter.onNext(simpleResponseEntityResponseModel.getData());
                }
            } else {
                switch (simpleResponseEntityResponseModel.getCode()) {
//                    case 6: // USER_NOT_EXIT(6, "用户不存在"),
//                        emitter.onError(new Error(simpleResponseEntityResponseModel.getMsg()));
//                        break;
                    default:
                        emitter.onError(new Error(simpleResponseEntityResponseModel.getMsg()));
                        break;
                }
            }
            emitter.onComplete();
        });
    }
}
