package com.supercaliman.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.supercaliman.login.databinding.FragmentLoginBinding


class FragmentLogin : Fragment() {

    private var _binding: FragmentLogin? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)

        val activity: LoginActivity = activity as LoginActivity
        binding.signup.setOnClickListener {
            activity.goToPage(1)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}