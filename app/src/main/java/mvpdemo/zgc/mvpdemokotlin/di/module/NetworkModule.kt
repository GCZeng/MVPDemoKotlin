package mvpdemo.zgc.mvpdemokotlin.di.module


import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import mvpdemo.zgc.mvpdemokotlin.app.API
import mvpdemo.zgc.mvpdemokotlin.service.ApiService
import mvpdemo.zgc.mvpdemokotlin.util.LogUtil
import mvpdemo.zgc.mvpdemokotlin.util.http.HttpLogInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Nick on 2017/1/4
 */
@Module
class NetworkModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {


        return OkHttpClient.Builder()
                .connectTimeout((60 * 1000).toLong(), TimeUnit.MILLISECONDS)
                .readTimeout((60 * 1000).toLong(), TimeUnit.MILLISECONDS)
                //                .addInterceptor(new HttpLoggingInterceptor(message -> LogUtil.d(message))
                //                        .setLevel(HttpLoggingInterceptor.Level.BODY))
//                .addInterceptor(HttpLogInterceptor({ message -> LogUtil.d(message) })
//                        .setLevel(HttpLogInterceptor.Level.BODY))
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(API.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


}
