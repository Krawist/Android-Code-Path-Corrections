package cm.seeds.appcorrectionandroidpath.ui.realisation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cm.seeds.appcorrectionandroidpath.api.ApiService
import cm.seeds.appcorrectionandroidpath.api.ApiServiceBuilder
import cm.seeds.appcorrectionandroidpath.modeles.Notation
import cm.seeds.appcorrectionandroidpath.modeles.Work

class RealisationViewModel : ViewModel() {

    val work = MutableLiveData<Work>()

    fun setWork(work: Work){
        this.work.value = work
    }
}