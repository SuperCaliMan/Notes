package com.supercaliman.login

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.supercaliman.core.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var viewPager: ViewPager2


    fun goToPage(page: Int) {
        viewPager.setCurrentItem(page, true)
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_login


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager = pager
        viewPager.adapter = FragmentAdapter(this)

        val tabLayout: TabLayout = tab_layout
        TabLayoutMediator(tabLayout, pager) { tab, pos ->
            when (pos) {
                0 -> tab.text = getString(R.string.signin).toUpperCase(Locale.ITALIAN)
                1 -> tab.text = getString(R.string.signup).toUpperCase(Locale.ITALIAN)
            }
        }.attach()

        /*
        supportFragmentManager.beginTransaction().add(R.id.fragment_host,FragmentLogin())
            .addToBackStack(null)
            .commit()*/

        //binding.googleSingIn.setOnClickListener(this)


        /*
        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.CLIENT_SECRET_WEB_GOOGLE)
            .requestEmail()
            .build()
        // [END config_signin]

        googleSignInClient = GoogleSignIn.getClient(this,gso)

        val currentUser = auth.currentUser
        updateUI(currentUser)*/


    }


/*
    // [START onactivityresult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Timber.d("firebaseAuthWithGoogle:%s", account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Timber.w( "Google sign in failed $e")
                // [START_EXCLUDE]
                updateUI(null)
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]*/
/*
    override fun onClick(v: View) {

        when(v.id){
            R.id.login -> signIn(binding.loginMain.email.text.toString(),binding.loginMain.password.text.toString())
            R.id.signup -> {
                supportFragmentManager.beginTransaction().add(R.id.login_container,FragmentNewAccount(),)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }*/
/*
    private fun signIn(email: String, password: String) {
        Timber.d("signIn: $email")

        if (!validateForm()) {
            return
        }

        showProgressBar()

        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d("signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.w("signInWithEmail:failure ${task.exception}")
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // [START_EXCLUDE]
                if (!task.isSuccessful) {
                    //binding.status.text = "R.string.failed"
                }
                hideProgressBar()
                // [END_EXCLUDE]
            }
        // [END sign_in_with_email]
    }*/

/*
    private fun googleSingIn(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d("signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.d("signInWithCredential:failure ${task.exception}")
                    // ...
                    val view = binding.root
                    Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // ...
            }
    }*/


/*
    private fun validateForm(): Boolean {
        var valid = true

        val email = binding.loginMain.email.text.toString()
        if (TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.loginMain.txtEmail.error = "Check your email!"
            valid = false
        } else {
            binding.loginMain.txtEmail.error = null
        }

        val password = binding.loginMain.password.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.loginMain.txtPsw.error = "Required."
            valid = false
        } else {
            binding.loginMain.txtPsw.error = null
        }

        return valid
    }

    private fun updateUI(user: FirebaseUser?) {
        hideProgressBar()
        if (user != null) {
            Timber.d("Email: ${user.email}, ${user.isEmailVerified}")
            Timber.d("User id: ${user.uid}")


            if (user.isEmailVerified) {
                startActivity(NavUtils.openScreen(this,Destinations.MAIN_SCREEN))
                finish()
            } else {
                Timber.d("No verify Email")
            }
        } else {
            Timber.d("User null")
        }
    }*/

    /*
    companion object {
        private const val RC_SIGN_IN = 9001
    }*/

}