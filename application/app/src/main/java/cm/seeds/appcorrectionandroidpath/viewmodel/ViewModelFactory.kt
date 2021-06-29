package cm.seeds.appcorrectionandroidpath.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cm.seeds.appcorrectionandroidpath.ui.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private  val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{

            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(application)  as T

            else -> throw IllegalArgumentException("")

        }
    }
}