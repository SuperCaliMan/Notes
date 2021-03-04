package com.supercaliman.note

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.supercaliman.navigation.Destinations
import com.supercaliman.navigation.NavUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        splashViewModel.alreadyLogin.observe(this) { user ->
            if (user != null) {
                startActivity(NavUtils.openScreen(this, Destinations.MAIN_SCREEN).apply {
                    flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                })
                overridePendingTransition(0, 0)
                finish()
            } else {
                startActivity(NavUtils.openScreen(this, Destinations.LOGIN_SCREEN).apply {
                    flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                })
                overridePendingTransition(0, 0)
                finish()
            }
        }

    }


}