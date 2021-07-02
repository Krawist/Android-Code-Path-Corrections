package cm.seeds.appcorrectionandroidpath.modeles

import java.io.Serializable

data class Notation(
    
    val notes : List<Note> = listOf(),
    var remarque : String = "",
    var codeApprenant : String = "",
    var userEmail : String = "",
    var codeWork : String = ""
) : Serializable