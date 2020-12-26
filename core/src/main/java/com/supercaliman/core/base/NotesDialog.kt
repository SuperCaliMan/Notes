package com.supercaliman.core.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import com.supercaliman.core.R
import kotlinx.android.synthetic.main.note_dialog.*

class NotesDialog(
    context: Context,
    private val message: String,
    private val onPositiveClick: (v: View) -> Unit = {},
    private val onNegativeClick: (v: View) -> Unit = {}
) : Dialog(context), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.note_dialog)
        DIALOG_ok.setOnClickListener(this)
        DIALOG_undo.setOnClickListener(this)
        DIALOG_msg.text = message
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.DIALOG_ok -> {
                onPositiveClick(v)
                dismiss()
            }
            R.id.DIALOG_undo -> {
                onNegativeClick(v)
                dismiss()
            }
        }

    }
}