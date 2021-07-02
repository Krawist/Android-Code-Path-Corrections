package cm.seeds.appcorrectionandroidpath.modeles

import cm.seeds.appcorrectionandroidpath.modeles.Work.Companion.WORK_TYPE_CREATION
import cm.seeds.appcorrectionandroidpath.modeles.Work.Companion.WORK_TYPE_REPRODUCTION
import java.io.Serializable

data class Note(
    var noteName : String,
    var noteOver : Double,
    var note : Double = 0.0,
    var noteType : String
) : Serializable{

    companion object{

        const val NOTE_TYPE_QUALITE_RENDU = "Qualité du renddu"
        const val NOTE_TYPE_COMPREHENSION_EXERCICE = "Compréhension du travail"
        const val NOTE_TYPE_PRECISION_DETAILS = "Précision des détails"
        const val NOTE_TYPE_ADPTION_RESULTAT = "Adoptez vous le résultat"

        fun getNotes(typeOfWork : Int, allOver : Double) : List<Note>{
            return when(typeOfWork){

                WORK_TYPE_REPRODUCTION -> {
                    listOf(
                        Note(noteName = NOTE_TYPE_QUALITE_RENDU,noteOver = allOver/2,noteType = NOTE_TYPE_QUALITE_RENDU),
                        Note(noteName = NOTE_TYPE_PRECISION_DETAILS,noteOver = allOver/2,noteType = NOTE_TYPE_PRECISION_DETAILS)
                    )
                }

                WORK_TYPE_CREATION-> {
                    listOf(
                        Note(noteName = NOTE_TYPE_QUALITE_RENDU,noteOver = allOver/3,noteType = NOTE_TYPE_QUALITE_RENDU),
                        Note(noteName = NOTE_TYPE_COMPREHENSION_EXERCICE,noteOver = allOver/3,noteType = NOTE_TYPE_COMPREHENSION_EXERCICE),
                        Note(noteName = NOTE_TYPE_ADPTION_RESULTAT,noteOver = allOver/3,noteType = NOTE_TYPE_ADPTION_RESULTAT)
                    )
                }

                else -> listOf()

            }
        }

    }

}
