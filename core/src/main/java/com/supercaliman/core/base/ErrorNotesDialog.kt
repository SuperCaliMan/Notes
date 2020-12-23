package com.supercaliman.core.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import com.supercaliman.core.R
import kotlinx.android.synthetic.main.note_dialog.*

class ErrorNotesDialog(
    context: Context,
    private var message: String,
    private val onPositiveClick: (v: View) -> Unit = {}
) : Dialog(context), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.error_note_dialog)
        DIALOG_ok.setOnClickListener(this)
        message += " :("
        DIALOG_msg.text = message
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