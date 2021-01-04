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
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.supercaliman.core.base.ErrorNotesDialog
import com.supercaliman.login.LoginViewModel
import com.supercaliman.login.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentNewAccount : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var username: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var btnSignUp: Button
    private lateinit var checkTherm: MaterialCheckBox
    private lateinit var txtEmail: TextInputLayout
    private lateinit var txtPsw: TextInputLayout
    private lateinit var txtUsername: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_account, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        username = view.findViewById(R.id.username)
        email = view.findViewById(R.id.email)
        password = view.findViewById(R.id.password)
        btnSignUp = view.findViewById(R.id.BTN_sign_up)
        checkTherm = view.findViewById(R.id.check_therm)
        txtEmail = view.findViewById(R.id.txt_email)
        txtPsw = view.findViewById(R.id.txt_psw)
        txtUsername = view.findViewById(R.id.txt_username)


        btnSignUp.setOnClickListener {
            if (validateForm()) {
                loginViewModel.createUser(
                    username.text.toString(),
                    email.text.toString(),
                    password.text.toString()
                )
            }
        }



        loginViewModel.userData.observe(viewLifecycleOwner) { user ->
            user?.let {
                (requireActivity() as LoginActivity).goToPage(0)
                cleanUi()
            }
        }

        loginViewModel.error.observe(viewLifecycleOwner) {
            it?.let { e ->
                btnSignUp.hideProgress(R.string.signin)
                ErrorNotesDialog(context = requireContext(), e.message!!).show()
            }
        }

        loginViewModel.loader.observe(viewLifecycleOwner) {
            if (it) {
                btnSignUp.showProgress {
                    buttonText = "Loading"
                    progressColor = Color.WHITE

                }
            } else {
                btnSignUp.hideProgress(R.string.signin)
            }

        }

    }

    private fun cleanUi() {
        username.text = null
        email.text = null
        password.text = null
        checkTherm.isChecked = false
    }

    private fun validateForm(): Boolean {
        var valid = true

        val username = username.text.toString()
        if (TextUtils.isEmpty(username)) {
            txtUsername.error = getString(R.string.checl_username)
            valid = false
        } else {
            txtUsername.error = null
        }

        val email = email.text.toString()
        if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.error = null
        } else {
            txtEmail.error = getString(R.string.check_email)
            valid = false
        }

        val password = password.text.toString()
        if (TextUtils.isEmpty(password)) {
            txtPsw.error = getString(R.string.required)
            valid = false
        } else if (password.length < 6) {
            txtPsw.error = getString(R.string.psw_short)
            valid = false
        } else {
            txtPsw.error = null
        }

        if (!checkTherm.isChecked) {
            checkTherm.error = getString(R.string.required)
            valid = false
        } else {
            checkTherm.error = null
        }

        return valid
    }
}