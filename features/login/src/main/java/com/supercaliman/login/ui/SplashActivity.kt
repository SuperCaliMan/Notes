package com.supercaliman.login.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.supercaliman.core.domain.dto.User
import com.supercaliman.login.LoginViewModel
import com.supercaliman.login.R
import com.supercaliman.navigation.Destinations
import com.supercaliman.navigation.NavUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val user = loginViewModel.checkUserSignIn()
        if (user != null && user.isEmailVerified) {
            startActivity(NavUtils.openScreen(this, Destinations.MAIN_SCREEN).apply {
                putExtra(
                    "User",
                    User(
                        user.uuid,
                        user.providerId,
                        user.displayName,
                        user.photoUrl,
                        user.email,
                        user.isEmailVerified
                    )
                )
            })
            finish()
        } else {
            startActivity(NavUtils.openScreen(this, Destinations.LOGIN_SCREEN))
            finish()
        }
    }
}