package com.seeds.androidcodepath.modeles

import com.seeds.androidcodepath.modeles.Notation.Companion.NOTATION_TABLE_NAME
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table


/**
 * Cette classe désigne une notation, c'est à dire les notes donnés à un examinateur sur un exercice à un apprenant
 */
@Entity
@Table(name = NOTATION_TABLE_NAME)
data class Notation(

        @EmbeddedId
        val notationKey: NotationKey? = null,

        @Column(name = NOTATION_QUALITE_RENDU_FIELD)
        var qualiteRendu: Float = 0f,

        @Column(name = NOTATION_RESPECT_DETAILS_FIELD)
        var respectDetails: Float = 0f,

        @Column(name = NOTATION_RESPECT_CONTRAINTES_FIELD)
        var respectContraintes: Float = 0f
) : Serializable {

    companion object {

        const val NOTATION_TABLE_NAME = "Notation"
        const val NOTATION_QUALITE_RENDU_FIELD = "qualite_rendu"
        const val NOTATION_RESPECT_DETAILS_FIELD = "respect_details"
        const val NOTATION_RESPECT_CONTRAINTES_FIELD = "respect_contraintes"

    }

}