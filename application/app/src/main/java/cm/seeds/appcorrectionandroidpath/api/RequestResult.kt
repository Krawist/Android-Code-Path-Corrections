package cm.seeds.retrofitrequestandnavigation.retrofit

import com.google.gson.annotations.SerializedName

data class RequestResult<out T>(
    @SerializedName("body")
    val data : T?,
    val message : String?,
    val code : Int,
    val link : String,
    val status: Status?
) {

    companion object{

        fun <T> success(data: T?,code: Int = 100,message: String = "", link: String = ""): RequestResult<T> {
            return RequestResult(
                    data = data,
                    message= message,
                    code= code,
                    link = link,
                    status = Status.SUCCESS)
        }

        fun <T> error(msg: String, data: T? = null, code: Int = 0, link: String = ""): RequestResult<T> {
            return RequestResult(
                    data = data,
                    message = msg,
                    code = code,
                    link = link,
                    Status.ERROR)
        }

        fun <T> loading(data: T? = null, link: String = ""): RequestResult<T> {
            return RequestResult(data = data,message = "",code = 0,link = link,status = Status.LOADING)
        }

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RequestResult<*>

        if (data != other.data) return false
        if (message != other.message) return false
        if (code != other.code) return false
        if (link != other.link) return false
        if (status != other.status) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data?.hashCode() ?: 0
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + code
        result = 31 * result + link.hashCode()
        result = 31 * result + (status?.hashCode() ?: 0)
        return result
    }


}