package cm.seeds.appcorrectionandroidpath.modeles

import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import kotlin.random.Random

/**
 * Cette classe contient toutes les informations néccéssaires à la présentations et à l'évaluation du travail d'un apprenant
 */
@Entity
data class Work(

    @PrimaryKey
    var workId : String = "",
    val workerId : String = "",
    val workName : String = "",
    @LayoutRes val realisationLayoutId : Int = 0,
    @DrawableRes val realisationImageId : Int = 0,
    var evaluate : Boolean = false,
    var notation: Notation? = null,
    var workType : Int = WORK_TYPE_REPRODUCTION,
    val qualiteRenduOver : Double = 0.0,
    val respectContraintesOver : Double = 0.0,
    val respectDetailsOver : Double = 0.0
) : Serializable{

    companion object{
        const val WORK_TYPE_REPRODUCTION = 1
        const val WORK_TYPE_CREATION = 2
    }

    init {
        workId = workerId + workName
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Work

        if (workId != other.workId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = workId.hashCode()
        result = 31 * result + workerId.hashCode()
        result = 31 * result + workName.hashCode()
        result = 31 * result + realisationLayoutId
        result = 31 * result + realisationImageId
        result = 31 * result + evaluate.hashCode()
        return result
    }
}
