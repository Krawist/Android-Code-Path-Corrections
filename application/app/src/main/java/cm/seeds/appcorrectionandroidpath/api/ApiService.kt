package cm.seeds.appcorrectionandroidpath.api

import cm.seeds.appcorrectionandroidpath.modeles.Notation
import cm.seeds.retrofitrequestandnavigation.retrofit.RequestResult
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    companion object {
        const val BASE_URL = "http://192.168.100.12:9091/"
    }

    @POST("notations/save")
    suspend fun saveNotation(@Body notation: Notation) : RequestResult<Notation>

}