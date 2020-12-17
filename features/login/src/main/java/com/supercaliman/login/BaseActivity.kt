package com.supercaliman.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.supercaliman.login.databinding.ActivityBaseBinding

open class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_base)
    }


    open fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    open fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

}