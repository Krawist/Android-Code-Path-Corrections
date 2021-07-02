package cm.seeds.appcorrectionandroidpath.ui.realisation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import cm.seeds.appcorrectionandroidpath.databinding.FragmentRealisationBinding
import cm.seeds.appcorrectionandroidpath.databinding.ItemNoteBinding
import cm.seeds.appcorrectionandroidpath.helper.gone
import cm.seeds.appcorrectionandroidpath.helper.show
import cm.seeds.appcorrectionandroidpath.modeles.Work
import cm.seeds.appcorrectionandroidpath.ui.MainViewModel
import cm.seeds.appcorrectionandroidpath.ui.realisation.fragment_item.RealisationImageFragment
import cm.seeds.appcorrectionandroidpath.ui.realisation.fragment_item.RealisationLayoutFragment
import cm.seeds.appcorrectionandroidpath.viewmodel.ViewModelFactory
import java.lang.NumberFormatException

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
                mainViewModel.evaluate(realisationViewModel.work.value!!)
            }
        }
    }

    private fun allIsCorrect(): Boolean {

        var allIsCorrect = true

        val work = realisationViewModel.work.value!!

        work.notation?.notes?.forEach {
            if(it.note<0 || it.note>it.noteOver){
                allIsCorrect = false
            }
        }

        val remarques = databinding.edittextRemarques.text.toString()
        if(remarques.isNullOrBlank()){
            databinding.inputLayoutRemarques.error = "Une remarque est absolument néccéssaire"
            allIsCorrect = false
        }else{
            work.notation?.remarque = remarques
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
        databinding.label.text = work.workName
        work.notation?.notes?.forEach { note ->
            val noteDataBinding = ItemNoteBinding.inflate(LayoutInflater.from(requireContext()),databinding.layoutNotes,false)
            noteDataBinding.inputLayout.apply {
                suffixText = "/${note.noteOver}"
                hint = note.noteName
            }
            noteDataBinding.edittext.setText(note.note.toString())
            noteDataBinding.edittext.doAfterTextChanged {
                try {
                    val noteDonner = it.toString().toDouble()
                    if(noteDonner>0 && noteDonner<=note.noteOver){
                        note.note = noteDonner
                        noteDataBinding.inputLayout.error = null
                    }else{
                        noteDataBinding.inputLayout.error = "La  note fourni n'est pas autorisé"
                    }
                }catch (e : NumberFormatException){
                    noteDataBinding.inputLayout.error = "La  note fourni n'est pas autorisé"
                }
            }
            databinding.layoutNotes.addView(noteDataBinding.root)
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