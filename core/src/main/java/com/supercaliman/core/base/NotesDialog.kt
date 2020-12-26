package com.supercaliman.core.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.supercaliman.core.R

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
        val buttonOk: Button = findViewById(R.id.DIALOG_ok)
        val buttonUndo: Button = findViewById(R.id.DIALOG_undo)
        val msg: TextView = findViewById(R.id.DIALOG_msg)
        buttonOk.setOnClickListener(this)
        buttonUndo.setOnClickListener(this)
        msg.text = message
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