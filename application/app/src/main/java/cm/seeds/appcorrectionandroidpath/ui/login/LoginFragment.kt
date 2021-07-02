package cm.seeds.appcorrectionandroidpath.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cm.seeds.appcorrectionandroidpath.R
import cm.seeds.appcorrectionandroidpath.databinding.FragmentHomeBinding
import cm.seeds.appcorrectionandroidpath.databinding.FragmentLoginBinding
import cm.seeds.appcorrectionandroidpath.helper.showMessage
import cm.seeds.appcorrectionandroidpath.modeles.User
import cm.seeds.appcorrectionandroidpath.ui.MainViewModel
import cm.seeds.appcorrectionandroidpath.viewmodel.ViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class LoginFragment : Fragment() {

    private lateinit var databinding : FragmentLoginBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var googleSignInOptions : GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    private val startActivityForResuultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        handleSignInResult(task)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions);

        mainViewModel = ViewModelProvider(requireActivity(),ViewModelFactory(requireActivity().application)).get(MainViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        databinding = FragmentLoginBinding.inflate(inflater,container,false)
        return databinding.root
    }


    private fun signIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResuultLauncher.launch(signInIntent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if(account!=null){
                mainViewModel.setCurrentUser(User(userName = account.displayName,userEmail = account.email, userImage = account.photoUrl?.toString()))
                navigateToEvaluation()
            }else{
                showMessage(requireContext(), message = completedTask.exception?.stackTraceToString())
            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            showMessage(requireContext(), message = e.stackTraceToString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addActionsOnViews()

    }

    private fun addActionsOnViews() {

        databinding.buttonSignIn.setOnClickListener {
            signIn()
            //navigateToEvaluation()
        }

    }

    private fun navigateToEvaluation() {
        findNavController().navigate(R.id.homeFragment)
    }

}