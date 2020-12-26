package com.supercaliman.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2


    fun goToPage(page: Int) {
        viewPager.setCurrentItem(page, true)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewPager = pager
        viewPager.adapter = FragmentAdapter(this)

        val tabLayout: TabLayout = tab_layout
        TabLayoutMediator(tabLayout, pager) { tab, pos ->
            when (pos) {
                0 -> tab.text = getString(R.string.signin).toUpperCase(Locale.ITALIAN)
                1 -> tab.text = getString(R.string.signup).toUpperCase(Locale.ITALIAN)
            }
        }.attach()
    }
}