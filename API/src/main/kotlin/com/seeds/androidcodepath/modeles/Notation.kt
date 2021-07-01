package com.seeds.androidcodepath.modeles

import com.seeds.androidcodepath.modeles.Notation.Companion.NOTATION_TABLE_NAME
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import javax.persistence.*


/**
 * Cette classe désigne une notation, c'est à dire les notes donnés à un examinateur sur un exercice à un apprenant
 */
@Entity
@Table(name = NOTATION_TABLE_NAME)
data class Notation(

    @Column(name = NOTATION_REMARQUES_FIELD)
    var remarque: String = "",

    @Column(name = CODE_APPRENANT_FIELD)
    var codeApprenant : String = "",

    @Column(name = USER_EMAIL_FIELD)
    var userEmail : String = "",

    @Column(name = CODE_WORK_FIELD)
    var codeWork : String = ""

) : Serializable {

    @Column(name = NOTATION_KEY_FIELD)
    @Id @NotNull
    var key: Int = 0

    init {
        key = (codeApprenant + userEmail + codeWork).hashCode()
    }

    @OneToMany(targetEntity = Note::class, mappedBy = "noteKey")
    val notes: List<Note>? = null

    companion object {
        const val NOTATION_TABLE_NAME = "Notation"
        const val NOTATION_REMARQUES_FIELD = "remarques"
        const val NOTATION_KEY_FIELD = "notation_key"
        const val CODE_APPRENANT_FIELD = "code_apprenant"
        const val USER_EMAIL_FIELD = "user_email"
        const val CODE_WORK_FIELD = "code_work"
    }
}