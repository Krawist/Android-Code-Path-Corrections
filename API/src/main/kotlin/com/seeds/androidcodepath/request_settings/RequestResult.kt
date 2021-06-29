package fr.smartds.smartdsmarketplace.api.request_settings

class RequestResult<out T>(val data : T?, val code : Int, val status : Status, val message : String?) {

    companion object{

        const val RESULT_CODE_ALL_IS_CORRECT = 100


        const val RESULT_UNDEFINED_ERROR = 200
        const val MESSAGE_UNDEFINED_ERROR = "Erreur du serveur"


        const val RESULT_CODE_USER_EXIST = 201
        const val MESSAGE_USER_EXIST = "cet utilisateur existe déja"


        const val RESULT_CODE_BAD_INFORMATION = 202
        const val MESSAGE_BAD_INFORMATIONS = "Les informations fournies ne sont pas correctes"

        const val RESULT_CODE_BAD_CREDENTIALS = 203
        const val MESSAGE_BAD_CREDENTIALS = "Les identifiants fournies ne sont pas corrects"

        const val RESULT_CODE_USER_DONT_EXIST = 204
        const val MESSAGE_USER_DONT_EXIST = "Ce compte n'existe pas"

        const val RESULT_CODE_USER_DELETED = 205
        const val MESSAGE_USER_DELETED = "Ce compte a été supprimé"

        const val RESULT_CODE_USER_NOT_ACTIVATE = 206
        const val MESSAGE_USER_NOT_ACTIVATED = "Ce compte n'est pas encore activé"

        const val RESULT_CODE_OPERATION_NOT_ALLOWED = 207
        const val MESSAGE_OPERATION_NOT_ALLOWED = "Opération non permise"

        const val RESULT_CODE_CODE_EXPIRATION_EXPIRED = 208
        const val MESSAGE_CODE_EXPIRATION_EXPIRED = "Ce code d'activation a expiré"

        const val RESULT_CODE_EMAIL_NOT_SEND = 209
        const val MESSAGE_EMAIL_NOT_SEND = "E-mail non envoyé"

        const val RESULT_CODE_USER_NOT_CONNECTED = 210
        const val MESSAGE_USER_NOT_CONNECTED = "Utilisateur non connecté"

        const val RESULT_CODE_USER_NOT_FOUND = 211
        const val MESSAGE_USER_NOT_FOUND = "Utilisateur introuvable"


        fun <T> error(code : Int, message: String?) : RequestResult<T> {
            return RequestResult(null,code, Status.ERROR, message)
        }

        fun <T> success(data : T, code : Int) : RequestResult<T> {
            return RequestResult(data,code,Status.SUCCESS,"")
        }

    }

}