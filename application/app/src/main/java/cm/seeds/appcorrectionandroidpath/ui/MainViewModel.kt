package cm.seeds.appcorrectionandroidpath.ui

import android.app.Application
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
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {

    val apiService = ApiServiceBuilder.getService(ApiService.BASE_URL, ApiService::class.java)
    val dao = AppDatabase.database(application).dao()
    val connectedUser = MutableLiveData<User>()
    val notatioListener = MutableLiveData<RequestResult<Notation>>()

    val works = dao.getAllWorks()


    init {
        viewModelScope.launch {
            val works = dao.getWorks()
            if(works.isNullOrEmpty()){
                val newWorks = listOf(
                    Work(
                        workerId = "PATHER 1",
                        realisationLayoutId = R.layout.reproduction10_worker1,
                        realisationImageId = R.drawable.reproduction_10_origin,
                        workName = "Reproduction 10",
                        qualiteRenduOver = 5.0,
                        respectDetailsOver = 5.0,
                        respectContraintesOver = 0.0
                    ),
                    Work(
                        workerId = "PATHER 1",
                        realisationLayoutId = R.layout.reproduction2_worker1,
                        realisationImageId = R.drawable.reproduction2,
                        workName = "Reproduction 2",
                        evaluate = true,
                        qualiteRenduOver = 10.0,
                        respectDetailsOver = 5.0,
                        respectContraintesOver = 0.0
                    ),
                    Work(
                        workerId = "PATHER 1",
                        realisationLayoutId = R.layout.reproduction5_worker1,
                        realisationImageId = R.drawable.reproduction5,
                        workName = "Reproduction 5",
                        qualiteRenduOver = 5.0,
                        respectDetailsOver = 5.0,
                        respectContraintesOver = 0.0
                    ),
                    Work(
                        workerId = "PATHER 1",
                        realisationLayoutId = R.layout.reproduction6_worker1,
                        realisationImageId = R.drawable.reproduction6_origin,
                        workName = "Reproduction 6",
                        qualiteRenduOver = 2.5,
                        respectDetailsOver = 2.5,
                        respectContraintesOver = 0.0
                    ),
                    Work(
                        workerId = "PATHER 1",
                        realisationLayoutId = R.layout.reproduction7_worker1,
                        realisationImageId = R.drawable.reproduction7_origin,
                        workName = "Reproduction 7",
                        evaluate = true,
                        qualiteRenduOver = 2.5,
                        respectDetailsOver = 2.5,
                        respectContraintesOver = 0.0
                    ),
                )
                dao.saveWorks(newWorks)
            }
        }

    }


    fun evaluate(notation: Notation, work: Work) {

        notation.notationKey?.apply {
            codeApprenant = work.workerId
            codeWork = work.workId
        }

        viewModelScope.launch {
            try {
                notatioListener.value = RequestResult.loading()
                val result = apiService.saveNotation(notation)
                if(result.code == 100){
                    notatioListener.value = RequestResult.success(data = result.data)

                    work.apply {
                        evaluate = true
                        this.notation = result.data
                    }

                    saveWork(work)
                }else{
                    notatioListener.value = RequestResult.error(msg = result.message?:"")
                }

            }catch (e : Exception){
                notatioListener.value = RequestResult.error(msg = e.stackTraceToString())
            }
        }

    }

    private fun saveWork(work: Work){
        viewModelScope.launch {
            dao.saveWorks(listOf(work))
        }
    }
}