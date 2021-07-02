package cm.seeds.appcorrectionandroidpath.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cm.seeds.appcorrectionandroidpath.R
import cm.seeds.appcorrectionandroidpath.api.ApiService
import cm.seeds.appcorrectionandroidpath.api.ApiServiceBuilder
import cm.seeds.appcorrectionandroidpath.database.AppDatabase
import cm.seeds.appcorrectionandroidpath.modeles.Notation
import cm.seeds.appcorrectionandroidpath.modeles.User
import cm.seeds.appcorrectionandroidpath.modeles.Work
import cm.seeds.retrofitrequestandnavigation.retrofit.RequestResult
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {

    val apiService = ApiServiceBuilder.getService(ApiService.BASE_URL, ApiService::class.java)
    val dao = AppDatabase.database(application).dao()
    val connectedUser = MutableLiveData<User>()
    val notatioListener = MutableLiveData<RequestResult<Notation>>()

    var works = MutableLiveData<List<Work>>()

    private val PATHER_1 = "Damaris"
    private val PATHER_2 = "Calypso"

    fun setCurrentUser(user : User){
        this.connectedUser.value = user
        viewModelScope.launch {
            val savedWorks = dao.getWorks()
            if (savedWorks.isNullOrEmpty()) {
                val newWorks = listOf(
                    Work(
                        workerId = PATHER_1,
                        realisationLayoutId = R.layout.reproduction10_worker1,
                        realisationImageId = R.drawable.reproduction_10_origin,
                        workName = "Reproduction 10",
                        workType = Work.WORK_TYPE_REPRODUCTION,
                        allOver = 10.0,
                        userEmail = user.userEmail?:""
                    ),
                    Work(
                        workerId = PATHER_1,
                        realisationLayoutId = R.layout.reproduction2_worker1,
                        realisationImageId = R.drawable.reproduction2,
                        workName = "Reproduction 2",
                        workType = Work.WORK_TYPE_REPRODUCTION,
                        allOver = 15.0,
                        userEmail = user.userEmail?:""
                    ),
                    Work(
                        workerId = PATHER_1,
                        realisationLayoutId = R.layout.reproduction5_worker1,
                        realisationImageId = R.drawable.reproduction5_origin,
                        workName = "Reproduction 5",
                        workType = Work.WORK_TYPE_REPRODUCTION,
                        allOver = 10.0,
                        userEmail = user.userEmail?:""
                    ),
                    Work(
                        workerId = PATHER_1,
                        realisationLayoutId = R.layout.reproduction6_worker1,
                        realisationImageId = R.drawable.reproduction6_origin,
                        workName = "Reproduction 6",
                        workType = Work.WORK_TYPE_REPRODUCTION,
                        allOver = 5.0,
                        userEmail = user.userEmail?:""
                    ),
                    Work(
                        workerId = PATHER_1,
                        realisationLayoutId = R.layout.reproduction7_worker1,
                        realisationImageId = R.drawable.reproduction7_origin,
                        workName = "Reproduction 7",
                        workType = Work.WORK_TYPE_REPRODUCTION,
                        allOver = 5.0,
                        userEmail = user.userEmail?:""
                    ),

                    Work(
                        workerId = PATHER_2,
                        realisationLayoutId = R.layout.representation_4_calypso,
                        realisationImageId = R.drawable.reproduction4,
                        workName = "Reproduction 4",
                        workType = Work.WORK_TYPE_REPRODUCTION,
                        allOver = 10.0,
                        userEmail = user.userEmail?:""
                    ),

                    Work(
                        workerId = PATHER_2,
                        realisationLayoutId = R.layout.reproduction_5_calypso,
                        realisationImageId = R.drawable.reproduction5,
                        workName = "Reproduction 5",
                        workType = Work.WORK_TYPE_REPRODUCTION,
                        allOver = 10.0,
                        userEmail = user.userEmail?:""
                    ),

                    Work(
                        workerId = PATHER_2,
                        realisationLayoutId = R.layout.reproduction_6_calpyso,
                        realisationImageId = R.drawable.reproduction6,
                        workName = "Reproduction 6",
                        workType = Work.WORK_TYPE_REPRODUCTION,
                        allOver = 10.0,
                        userEmail = user.userEmail?:""
                    ),

                    Work(
                        workerId = PATHER_2,
                        realisationLayoutId = R.layout.reproduction_7_calypso,
                        realisationImageId = R.drawable.reproduction7,
                        workName = "Reproduction 7",
                        workType = Work.WORK_TYPE_REPRODUCTION,
                        allOver = 10.0,
                        userEmail = user.userEmail?:""
                    ),

                    Work(
                        workerId = PATHER_2,
                        realisationLayoutId = R.layout.reproduction_9_calypso,
                        realisationImageId = R.drawable.reproduction9,
                        workName = "Reproduction 9",
                        workType = Work.WORK_TYPE_REPRODUCTION,
                        allOver = 10.0,
                        userEmail = user.userEmail?:""
                    ),
                )
                dao.saveWorks(newWorks)
            }

            works.value = dao.getWorks()
        }
    }

    fun evaluate(work: Work) {

        Log.e("TAG",GsonBuilder().setPrettyPrinting().create().toJson(work))
        //work.notation.userEmail = connectedUser.value?.userEmail ?: "arthurngoben@gmail.com"
        viewModelScope.launch {
            try {
                notatioListener.value = RequestResult.loading()
                val result = apiService.saveNotation(work.notation)
                if (result.code == 100) {
                    notatioListener.value = RequestResult.success(data = result.data)

                    work.apply {
                        evaluate = true
                    }

                    saveWork(work)
                } else {
                    notatioListener.value = RequestResult.error(msg = result.message ?: "")
                }

            } catch (e: Exception) {
                notatioListener.value = RequestResult.error(msg = e.stackTraceToString())
            }
        }

    }

    private fun saveWork(work: Work) {
        viewModelScope.launch {
            dao.saveWorks(listOf(work))
            works.value = dao.getWorks()
        }
    }
}