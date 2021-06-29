package com.seeds.androidcodepath.modeles

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class NotationKey(

        @Column(name = CODE_APPRENANT_FIELD)
        val codeApprenant : String = "",

        @Column(name = USER_EMAIL_FIELD)
        val userEmail : String = "",

        @Column(name = CODE_WORK_FIELD)
        val codeWork : String = ""
) : Serializable {
    companion object{

        const val CODE_APPRENANT_FIELD = "code_apprenant"
        const val USER_EMAIL_FIELD = "user_email"
        const val CODE_WORK_FIELD = "code_work"

    }
}