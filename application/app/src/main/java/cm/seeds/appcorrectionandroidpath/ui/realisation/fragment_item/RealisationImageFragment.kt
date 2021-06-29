package cm.seeds.appcorrectionandroidpath.ui.realisation.fragment_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import cm.seeds.appcorrectionandroidpath.databinding.FragmentRealisationImageBinding
import cm.seeds.appcorrectionandroidpath.modeles.Work

class RealisationImageFragment : Fragment() {

    @DrawableRes private var imageRes : Int = 0
    private lateinit var dataBinding: FragmentRealisationImageBinding

    companion object{
        const val IMAGE_RES = "IMAGE_RES"

        fun newInstance(work: Work) = RealisationImageFragment().apply {
            arguments = Bundle().apply {
                putInt(IMAGE_RES,work.realisationImageId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageRes = arguments?.getInt(IMAGE_RES)?:0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentRealisationImageBinding.inflate(inflater,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.image.setImageResource(imageRes)

    }
}