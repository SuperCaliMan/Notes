package com.supercaliman.login

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.supercaliman.core.base.ErrorNotesDialog
import com.supercaliman.core.base.NotesDialog
import com.supercaliman.navigation.Destinations
import com.supercaliman.navigation.NavUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class FragmentLogin : Fragment() {


    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var mActivity: LoginActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mActivity = requireActivity() as LoginActivity
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        BTN_login.attachTextChangeAnimator()
        BTN_login.setOnClickListener {
            if (validateForm()) {
                loginViewModel.signIn(LOGIN_email.text.toString(), LOGIN_password.text.toString())
            }
        }

        loginViewModel.userData.observe(viewLifecycleOwner) { user ->
            user?.let {
                if (it.isEmailVerified) {
                    startActivity(NavUtils.openScreen(requireContext(), Destinations.MAIN_SCREEN))
                    mActivity.finish()
                } else {
                    NotesDialog(requireContext(), getString(R.string.verify_email),
                        onPositiveClick = {
                            loginViewModel.sendVerificationEmail()
                            loginViewModel.logout()
                        },
                        { loginViewModel.logout() }).show()
                }
            }
        }

        loginViewModel.error.observe(viewLifecycleOwner) {
            it?.let { e ->
                BTN_login.hideProgress(R.string.signin)
                ErrorNotesDialog(context = requireContext(), e.message!!).show()
            }
        }

        loginViewModel.loader.observe(viewLifecycleOwner) {
            if (it) {
                BTN_login.showProgress {
                    buttonText = "Loading"
                    progressColor = Color.WHITE

                }
            } else {
                BTN_login.hideProgress(R.string.signin)
            }

        }

        BTN_sign_up.setOnClickListener {
            mActivity.goToPage(1)
        }

    }


    private fun validateForm(): Boolean {
        var valid = true

        val email = LOGIN_email.text.toString()
        if (TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txt_email.error = "Check your email!"
            valid = false
        } else {
            txt_email.error = null
        }

        val password = LOGIN_password.text.toString()
        if (TextUtils.isEmpty(password)) {
            txt_psw.error = "Required."
            valid = false
        } else {
            txt_psw.error = null
        }

        return valid
    }
}