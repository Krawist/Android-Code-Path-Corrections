package cm.seeds.appcorrectionandroidpath.database

import androidx.room.TypeConverter
import cm.seeds.appcorrectionandroidpath.modeles.Notation
import com.google.gson.Gson

class TypeConverter {

    companion object{

        @TypeConverter
        @JvmStatic
        fun fromStringToNotation(string: String?) : Notation?{
            return Gson().fromJson(string,Notation::class.java)
        }

        @TypeConverter
        @JvmStatic
        fun fromNotationToString(notation: Notation?) : String?{
            return Gson().toJson(notation)
        }

    }

}