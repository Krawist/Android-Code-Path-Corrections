package cm.seeds.appcorrectionandroidpath.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import cm.seeds.appcorrectionandroidpath.modeles.Work

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWorks(works : List<Work>)

    @Query("SELECT * FROM work ORDER BY workerId")
    fun getAllWorks() : LiveData<List<Work>>

    @Query("SELECT * FROM work ORDER BY workerId")
    suspend fun getWorks() : List<Work>



}