package cm.seeds.appcorrectionandroidpath.ui.realisation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import cm.seeds.appcorrectionandroidpath.databinding.FragmentRealisationBinding
import cm.seeds.appcorrectionandroidpath.helper.gone
import cm.seeds.appcorrectionandroidpath.helper.show
import cm.seeds.appcorrectionandroidpath.modeles.Notation
import cm.seeds.appcorrectionandroidpath.modeles.NotationKey
import cm.seeds.appcorrectionandroidpath.modeles.Work
import cm.seeds.appcorrectionandroidpath.ui.MainViewModel
import cm.seeds.appcorrectionandroidpath.ui.realisation.fragment_item.RealisationImageFragment
import cm.seeds.appcorrectionandroidpath.ui.realisation.fragment_item.RealisationLayoutFragment
import cm.seeds.appcorrectionandroidpath.viewmodel.ViewModelFactory

class RealisationFragment : Fragment() {

    private lateinit var realisationViewModel: RealisationViewModel
    private lateinit var databinding : FragmentRealisationBinding
    private lateinit var mainViewModel : MainViewModel

    companion object{
        const val WORK_TO_SHOW = "WORK_TO_SHOW"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        realisationViewModel = ViewModelProvider(this).get(RealisationViewModel::class.java)
        mainViewModel = ViewModelProvider(requireActivity(), ViewModelFactory(requireActivity().application)).get(MainViewModel::class.java)

        val work = arguments?.getSerializable(WORK_TO_SHOW)

        if(work!=null){
            realisationViewModel.setWork(work as Work)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        databinding = FragmentRealisationBinding.inflate(inflater,container,false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachObserevers()

        addACtionsOnViews()

    }

    private fun addACtionsOnViews() {

        databinding.buttonEvaluate.setOnClickListener {
            if(databinding.cardEvaluation.visibility == VISIBLE){
                openOrCloseEvaluation(false)
            }else{
                openOrCloseEvaluation(true)
            }
        }

        databinding.buttonCloseExamination.setOnClickListener {
            openOrCloseEvaluation(false)
        }

        databinding.buttonValidateExamination.setOnClickListener {

            if(allIsCorrect()){
                val notation = Notation(
                    qualiteRendu = try {
                        databinding.edittextQualiteRendu.text.toString().toFloat()
                    }catch (e : Exception){
                        0f
                    },

                    respectContraintes= try {
                        databinding.edittextRespectContraintes.text.toString().toFloat()
                    }catch (e : Exception){
                        0f
                    },

                    respectDetails = try {
                        databinding.edittextRespectDetails.text.toString().toFloat()
                    }catch (e : Exception){
                        0f
                    },

                    notationKey = NotationKey(userEmail = mainViewModel.connectedUser.value?.userEmail?:"")
                )
                mainViewModel.evaluate(notation, realisationViewModel.work.value!!)
            }
        }
    }

    private fun allIsCorrect(): Boolean {

        var allIsCorrect = true

        val work = realisationViewModel.work.value!!

        val qualiteRendu = try {
            databinding.edittextQualiteRendu.text.toString().toDouble()
        }catch (e : Exception){
            0.0
        }

        val respectContraintes= try {
            databinding.edittextRespectContraintes.text.toString().toDouble()
        }catch (e : Exception){
            0.0
        }

        val respectDetails = try {
            databinding.edittextRespectDetails.text.toString().toDouble()
        }catch (e : Exception){
            0.0
        }

        if(qualiteRendu>work.qualiteRenduOver){
            allIsCorrect = false
            databinding.layoutEdittextQualiteRendu.error = "Impossible de donner plus de ${work.qualiteRenduOver}"
        }



        when(work.workType){

            Work.WORK_TYPE_CREATION -> {
                if(respectContraintes>work.respectContraintesOver){
                    allIsCorrect = false
                    databinding.layoutEdittextRespectContarintes.error = "Impossible de donner plus de ${work.respectContraintesOver}"
                }
            }

            Work.WORK_TYPE_REPRODUCTION -> {
                if(respectDetails>work.respectDetailsOver){
                    allIsCorrect = false
                    databinding.layoutEdittextRespectDetails.error = "Impossible de donner plus de ${work.respectDetailsOver}"
                }
            }

        }

        return allIsCorrect

    }

    private fun openOrCloseEvaluation(open : Boolean){
        if(open){
            databinding.cardEvaluation.show()
            databinding.buttonEvaluate.hide()
        }else{
            databinding.cardEvaluation.gone()
            databinding.buttonEvaluate.show()
        }
    }

    private fun attachObserevers() {

        realisationViewModel.work.observe(viewLifecycleOwner,{
            if(it!=null){
                databinding.viewpager.adapter = WorkAdapter(it)
                setupWorkCorrections(it)
            }
        })

    }

    private fun setupWorkCorrections(work: Work) {
        databinding.layoutEdittextQualiteRendu.suffixText = "/${work.qualiteRenduOver}"
        databinding.layoutEdittextRespectContarintes.suffixText = "/${work.respectContraintesOver}"
        databinding.layoutEdittextRespectDetails.suffixText = "/${work.respectDetailsOver}"

        work.notation?.let {
            databinding.edittextRespectDetails.setText(it.respectDetails.toString())
            databinding.edittextRespectContraintes.setText(it.respectContraintes.toString())
            databinding.edittextQualiteRendu.setText(it.qualiteRendu.toString())
        }

        when(work.workType){

            Work.WORK_TYPE_REPRODUCTION -> databinding.layoutEdittextRespectContarintes.gone()
            Work.WORK_TYPE_CREATION -> databinding.layoutEdittextRespectDetails.gone()

        }
    }


    inner class WorkAdapter(private val work: Work) : FragmentStatePagerAdapter(childFragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

        override fun getCount(): Int {
            return 2
        }

        override fun getItem(position: Int): Fragment {
            return when(position){

                0 -> RealisationImageFragment.newInstance(work)

                1 -> RealisationLayoutFragment.newInstance(work)

                else -> throw Exception("")

            }
        }
    }
}