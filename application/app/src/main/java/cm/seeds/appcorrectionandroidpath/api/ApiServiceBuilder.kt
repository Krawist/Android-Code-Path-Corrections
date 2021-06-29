package cm.seeds.appcorrectionandroidpath.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class ApiServiceBuilder {

    companion object{

        private val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .protocols(Arrays.asList(Protocol.HTTP_2, Protocol.HTTP_1_1))
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addInterceptor { chain: Interceptor.Chain ->
                val newRequest = chain.request().newBuilder()
                    .build()

                val response = chain.proceed(newRequest)
                Log.e("TAG"," \n \n \n data "+response.body()?.string() + "\n" +
                        " \n" +
                        " \n" +
                        " ")
                response.close()

                return@addInterceptor chain.proceed(newRequest)
            }
            .build()

        fun <T: Any> getService(baseUrl : String, clazz: Class<T>) : T{
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(clazz)
        }
    }

}