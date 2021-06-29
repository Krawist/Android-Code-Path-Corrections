package cm.seeds.appcorrectionandroidpath.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cm.seeds.appcorrectionandroidpath.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var databinding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        databinding = FragmentHomeBinding.inflate(inflater,container,false)
        return databinding.root
    }
}