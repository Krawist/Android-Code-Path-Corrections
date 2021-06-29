package cm.seeds.appcorrectionandroidpath.ui.realisation.fragment_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import cm.seeds.appcorrectionandroidpath.databinding.FragmentRealisationImageBinding
import cm.seeds.appcorrectionandroidpath.modeles.Work

class RealisationLayoutFragment : Fragment(){

    @LayoutRes private var layoutRes : Int = 0

    companion object{
        const val LAYOUT_RES = "LAYOUT_RES"

        fun newInstance(work: Work) = RealisationLayoutFragment().apply {
            arguments = Bundle().apply {
                putInt(LAYOUT_RES,work.realisationLayoutId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layoutRes = arguments?.getInt(LAYOUT_RES)?:0

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutRes,container,false)
    }

}