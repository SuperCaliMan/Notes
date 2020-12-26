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
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.supercaliman.core.base.ErrorNotesDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_new_account.*

@AndroidEntryPoint
class FragmentNewAccount : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            user?.let {
                (requireActivity() as LoginActivity).goToPage(0)
                cleanUi()
            }
        }

        loginViewModel.error.observe(viewLifecycleOwner) {
            it?.let { e ->
                BTN_sign_up.hideProgress(R.string.signin)
                ErrorNotesDialog(context = requireContext(), e.message!!).show()
            }
        }

        loginViewModel.loader.observe(viewLifecycleOwner) {
            if (it) {
                BTN_sign_up.showProgress {
                    buttonText = "Loading"
                    progressColor = Color.WHITE

                }
            } else {
                BTN_sign_up.hideProgress(R.string.signin)
            }

        }

    }

    private fun cleanUi() {
        username.text = null
        email.text = null
        password.text = null
        check_therm.isChecked = false
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
        if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txt_email.error = null
        } else {
            txt_email.error = "Check your email!"
            valid = false
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