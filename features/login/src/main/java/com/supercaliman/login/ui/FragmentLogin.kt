package com.supercaliman.login.ui

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.supercaliman.core.base.ErrorNotesDialog
import com.supercaliman.core.base.NotesDialog
import com.supercaliman.login.LoginViewModel
import com.supercaliman.login.R
import com.supercaliman.login.domain.UiUserMapper
import com.supercaliman.navigation.Destinations
import com.supercaliman.navigation.NavUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentLogin : Fragment() {


    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var btnLogin: Button
    private lateinit var btnSingUp: Button
    private lateinit var loginPsw: TextInputEditText
    private lateinit var loginEmail: TextInputEditText
    private lateinit var txtEmail: TextInputLayout
    private lateinit var txtPsw: TextInputLayout
    private lateinit var mActivity: LoginActivity

    private val userModelMapper = UiUserMapper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mActivity = requireActivity() as LoginActivity
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnLogin = view.findViewById(R.id.BTN_login)
        btnSingUp = view.findViewById(R.id.BTN_sign_up)
        loginPsw = view.findViewById(R.id.LOGIN_password)
        loginEmail = view.findViewById(R.id.LOGIN_email)
        txtEmail = view.findViewById(R.id.txt_email)
        txtPsw = view.findViewById(R.id.txt_psw)

        btnLogin.attachTextChangeAnimator()
        btnLogin.setOnClickListener {
            if (validateForm()) {
                loginViewModel.signIn(loginEmail.text.toString(), loginPsw.text.toString())
            }
        }

        loginViewModel.userData.observe(viewLifecycleOwner) { user ->
            user?.let {
                if (it.isEmailVerified) {
                    startActivity(
                        NavUtils.openScreen(requireContext(), Destinations.MAIN_SCREEN).apply {
                            putExtra("User", userModelMapper.toDTOModel(it))
                        })
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
                btnLogin.hideProgress(R.string.signin)
                ErrorNotesDialog(context = requireContext(), e.message!!).show()
            }
        }

        loginViewModel.loader.observe(viewLifecycleOwner) {
            if (it) {
                btnLogin.showProgress {
                    buttonText = "Loading"
                    progressColor = Color.WHITE

                }
            } else {
                btnLogin.hideProgress(R.string.signin)
            }

        }

        btnSingUp.setOnClickListener {
            mActivity.goToPage(1)
        }

    }


    private fun validateForm(): Boolean {
        var valid = true

        val email = loginEmail.text.toString()
        if (TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.error = "Check your email!"
            valid = false
        } else {
            txtEmail.error = null
        }

        val password = loginPsw.text.toString()
        if (TextUtils.isEmpty(password)) {
            txtPsw.error = "Required."
            valid = false
        } else {
            txtPsw.error = null
        }

        return valid
    }
}