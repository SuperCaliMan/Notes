package com.supercaliman.core.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.supercaliman.core.R

class ErrorNotesDialog(
    context: Context,
    private var message: String,
    private val onPositiveClick: (v: View) -> Unit = {}
) : Dialog(context), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.error_note_dialog)
        val buttonOk: Button = findViewById(R.id.DIALOG_ok)
        val msg: TextView = findViewById(R.id.DIALOG_msg)
        buttonOk.setOnClickListener(this)
        message += " :("
        msg.text = message
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.DIALOG_ok -> {
                onPositiveClick(v)
                dismiss()
            }
        }

    }
}