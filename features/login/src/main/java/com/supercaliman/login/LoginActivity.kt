package com.supercaliman.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.supercaliman.navigation.Destinations
import com.supercaliman.navigation.NavUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_login.setOnClickListener {
            startActivity(NavUtils.openScreen(this, Destinations.MAIN_SCREEN))
            finish()
        }
    }
}