package com.supercaliman.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.supercaliman.login.databinding.FragmentNewAccountBinding


class FragmentNewAccount : Fragment(), View.OnClickListener {

    private var _binding: FragmentNewAccountBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewAccountBinding.inflate(inflater, container, false)
        binding.btnsignup.setOnClickListener(this)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnsignup -> {
                validateForm()
            }
        }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val username = binding.username.text.toString()
        if (TextUtils.isEmpty(username)) {
            binding.username.error = "Check your username!"
            valid = false
        } else {
            binding.username.error = null
        }

        val email = binding.email.text.toString()
        if (TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.txtEmail.error = "Check your email!"
            valid = false
        } else {
            binding.txtEmail.error = null
        }

        val password = binding.password.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.txtPsw.error = "Required."
            valid = false
        } else {
            binding.txtPsw.error = null
        }

        val checkTherm = !binding.checkTherm.isChecked
        if (checkTherm) {
            binding.checkTherm.error = "Required."
            valid = false
        } else {
            binding.checkTherm.error = null
        }

        return valid
    }
}