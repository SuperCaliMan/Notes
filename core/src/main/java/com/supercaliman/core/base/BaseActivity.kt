package com.supercaliman.core.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.supercaliman.core.R
import kotlinx.android.synthetic.main.activity_base.*


abstract class BaseActivity : AppCompatActivity() {

    private val baseViewModel: BaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        BASE_stub.layoutResource = getLayoutResourceId()
        BASE_stub.inflate()
        baseViewModel.loader.observe(this@BaseActivity) { value ->
            value?.let {
                Log.d("TAG", "$it")
                if (it) {
                    showProgressBar()
                } else {
                    hideProgressBar()
                }
            }
        }
    }


    protected open fun showProgressBar() {
        if (!BASE_progress_bar.isShown) {
            BASE_progress_bar.visibility = View.VISIBLE
        }
    }

    protected open fun hideProgressBar() {
        BASE_progress_bar.visibility = View.GONE
    }

    /**
     * Provide id of the main layout
     */
    protected abstract fun getLayoutResourceId(): Int

}