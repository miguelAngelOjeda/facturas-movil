package coomecipar.example.palermo.di

import android.annotation.SuppressLint
import android.util.Log
import com.example.palermo.BuildConfig
import com.example.palermo.network.ApiInterface
import com.example.palermo.utils.Prefs
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import coomecipar.example.palermo.Credential
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


val networkModule = Kodein.Module {

    bind<Gson>() with singleton {
        GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create()
    }

    bind<OkHttpClient>() with singleton {
        val builder = OkHttpClient.Builder()
        builder.networkInterceptors().add(StethoInterceptor())
        //builder.interceptors().add(instance<Interceptor>())
        builder.connectTimeout(120, TimeUnit.SECONDS)
        builder.writeTimeout(120, TimeUnit.SECONDS)
        builder.readTimeout(120, TimeUnit.SECONDS)
        /*builder.sslSocketFactory(instance())
        builder.hostnameVerifier(object : HostnameVerifier {
            @SuppressLint("BadHostnameVerifier")
            override fun verify(p0: String?, p1: SSLSession?) = true
        })*/
        builder.build()
    }

    bind<SSLSocketFactory>() with singleton {
        val trustManager = arrayOf(object  : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustManager, SecureRandom())
            sslContext.socketFactory
    }

    bind<ApiInterface>() with singleton { instance<Retrofit>().create(ApiInterface::class.java) }

    bind<Retrofit>() with singleton {
        Retrofit.Builder()
                .client(instance())
                .baseUrl("https://app2.financorp.com.py/service-beta/")
                .addConverterFactory(GsonConverterFactory.create(instance<Gson>()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    bind<Interceptor>() with singleton {
        Interceptor { chain ->
            val original = chain.request()
            val originalUrl = original.url()
            val credential = instance<Credential>()
            val prefs = instance<Prefs>()

            val urlBuilder = originalUrl.newBuilder()
            if (BuildConfig.DEBUG) {
                urlBuilder.addQueryParameter("debug", prefs.debugMode.toString())
            } else {
                urlBuilder.addQueryParameter("debug", "false")
            }

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                .url(urlBuilder.build())

            val request = requestBuilder.build()
            Log.d("Retrofit", "Requesting ${request.url().toString()}")
            chain.proceed(request)
        }
    }
}