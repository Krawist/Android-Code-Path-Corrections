package cm.seeds.appcorrectionandroidpath.database

import android.annotation.SuppressLint
import android.app.Application
import androidx.room.*
import cm.seeds.appcorrectionandroidpath.modeles.Work

@SuppressLint("RestrictedApi")
@Database(
        version = 14,
        entities = [Work::class],
        exportSchema = true
)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun database(application: Application): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(application).also { INSTANCE = it }
                }

        private fun buildDatabase(application: Application) =
                Room.databaseBuilder(application, AppDatabase::class.java, "squarescm.db")
                        .fallbackToDestructiveMigration()
                        .build()

    }

    abstract fun dao(): Dao
}