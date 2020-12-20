package com.supercaliman.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_new_account.*
import timber.log.Timber

@AndroidEntryPoint
class FragmentNewAccount : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_new_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        BTN_sign_up.setOnClickListener {
            if (validateForm()) {
                loginViewModel.createUser(
                    username.text.toString(),
                    email.text.toString(),
                    password.text.toString()
                )
            }
        }



        loginViewModel.userData.observe(viewLifecycleOwner) { user ->
            user.let {
                Toast.makeText(
                    requireContext(),
                    "${it.displayName} Check you Email!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        loginViewModel.error.observe(viewLifecycleOwner) {
            Timber.e(it)
        }

    }

    private fun validateForm(): Boolean {
        var valid = true

        val username = username.text.toString()
        if (TextUtils.isEmpty(username)) {
            txt_username.error = "Check your username!"
            valid = false
        } else {
            txt_username.error = null
        }

        val email = email.text.toString()
        if (TextUtils.isEmpty(email)) {
            txt_email.error = "Check your email!"
            valid = false
        } else {
            txt_email.error = null
        }

        val password = password.text.toString()
        if (TextUtils.isEmpty(password)) {
            txt_psw.error = "Required."
            valid = false
        } else if (password.length < 6) {
            txt_psw.error = "Password is too short."
            valid = false
        } else {
            txt_psw.error = null
        }

        val checkTherm = !check_therm.isChecked
        if (checkTherm) {
            check_therm.error = "Required."
            valid = false
        } else {
            check_therm.error = null
        }

        return valid
    }
}