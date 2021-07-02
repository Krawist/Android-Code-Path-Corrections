package cm.seeds.appcorrectionandroidpath.ui

import android.app.Dialog
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cm.seeds.appcorrectionandroidpath.R
import cm.seeds.appcorrectionandroidpath.adapter.WorkAdapter
import cm.seeds.appcorrectionandroidpath.databinding.ActivityMainBinding
import cm.seeds.appcorrectionandroidpath.helper.getLoadingDialog
import cm.seeds.appcorrectionandroidpath.helper.loadImageInView
import cm.seeds.appcorrectionandroidpath.helper.showMessage
import cm.seeds.appcorrectionandroidpath.helper.showToast
import cm.seeds.appcorrectionandroidpath.modeles.Notation
import cm.seeds.appcorrectionandroidpath.modeles.Work
import cm.seeds.appcorrectionandroidpath.ui.realisation.RealisationFragment.Companion.WORK_TO_SHOW
import cm.seeds.appcorrectionandroidpath.viewmodel.ViewModelFactory
import cm.seeds.retrofitrequestandnavigation.retrofit.Status

class MainActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var loadingDialog : Dialog
    private lateinit var recyclerViewWorks: RecyclerView
    private lateinit var adapterWork: WorkAdapter
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this, ViewModelFactory(application)).get(MainViewModel::class.java)

        dataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        loadingDialog = getLoadingDialog(this)

        initialiseViews()

        setupNavController()

        setupList()

        setupDrawer()

        attachObservers()

    }

    private fun attachObservers() {

        mainViewModel.works.observe(this,{
            adapterWork.submitList(it)
            adapterWork.notifyDataSetChanged()
        })

        mainViewModel.connectedUser.observe(this,{
            val header = dataBinding.navView.getHeaderView(0)
            val imageExaminator = header.findViewById<ImageView>(R.id.image_examinateur)
            val nomEmaniateur = header.findViewById<TextView>(R.id.nom_examinateur)
            val emailEmaniateur = header.findViewById<TextView>(R.id.email_examinateur)

            if(it!=null){
                loadImageInView(imageExaminator,it.userImage)
                nomEmaniateur.text = it.userName
                emailEmaniateur.text = it.userEmail
            }
        })

        mainViewModel.notatioListener.observe(this,{
            when(it.status){

                Status.ERROR -> {
                    loadingDialog.dismiss()
                    showMessage(this@MainActivity,message = it.message)
                }

                Status.LOADING -> {
                    loadingDialog.show()
                }

                Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    showToast(this@MainActivity,"Evaluation enregistré")
                }
            }
        })

    }

    private fun setupDrawer() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.loginFragment){
                dataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }else{
                dataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED)
            }
        }
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun initialiseViews() {
        recyclerViewWorks = dataBinding.navView.getHeaderView(0).findViewById(R.id.recyclerview_list_works)
    }

    private fun setupList() {
        adapterWork = WorkAdapter { any, _, _ ->
            navController.navigate(R.id.realisationFragment, Bundle().apply {
                putSerializable(WORK_TO_SHOW, any as Work)
            })
            dataBinding.drawerLayout.closeDrawer(GravityCompat.START,true)
        }
        recyclerViewWorks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterWork
        }
    }

}