package com.thejan.starwars.network

import com.thejan.starwars.helper.BASE_URL
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Created by Thejan Thrimanna on 11/12/21.
 */
object ServiceGenerator {
    private const val TIMEOUT = 10L

    private fun getClient(): Retrofit {
        val x509TrustManager = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }

        val trustManagers = arrayOf<TrustManager>(x509TrustManager)
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustManagers, null)
        val networkCacheInterceptor = createCacheInterceptor()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, java.util.concurrent.TimeUnit.SECONDS)
            .addNetworkInterceptor(networkCacheInterceptor)
            .addInterceptor(loggingInterceptor)
            .sslSocketFactory(sslContext.socketFactory, x509TrustManager)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun createCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            var cacheControl = CacheControl.Builder()
                .maxAge(1, java.util.concurrent.TimeUnit.MINUTES)
                .build()

            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }
    }

    val instance: ApiInterface by lazy{
        val retrofit = getClient()
        retrofit.create(ApiInterface::class.java)
    }
}