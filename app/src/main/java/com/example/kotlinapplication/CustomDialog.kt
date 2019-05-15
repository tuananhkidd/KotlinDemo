package com.example.kotlinapplication

import android.app.AlertDialog
import android.content.Context
import android.support.annotation.StringRes

class CustomDialog {

    private var dialog: AlertDialog? = null
    private var builder: AlertDialog.Builder? = null

    private val context: Context? = null

    constructor(context: Context) {
        builder = AlertDialog.Builder(context)
            .setOnDismissListener { dialog1 -> initialize() }
    }

    constructor(
        context: Context, title: String, message: String,
        positive: String, onPositiveClick: OnDialogListener?,
        negative: String, onNegativeClick: OnDialogListener?,
        isCancelable: Boolean
    ) {
        builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positive) { dialog1, which ->
                dialog1.cancel()
                onPositiveClick?.onClick()
            }
            .setNegativeButton(negative) { dialog1, which ->
                dialog1.cancel()
                onNegativeClick?.onClick()
            }
            .setOnDismissListener { dialog1 -> initialize() }
            .setCancelable(isCancelable)
        dialog = builder!!.create()
        dialog!!.show()
    }

    fun setTitle(title: String): CustomDialog {
        builder!!.setTitle(title)
        return this
    }

    fun setTitle(@StringRes title: Int): CustomDialog {
        builder!!.setTitle(title)
        return this
    }

    fun setMessage(message: String): CustomDialog {
        builder!!.setMessage(message)
        return this
    }

    fun setMessage(@StringRes message: Int): CustomDialog {
        builder!!.setMessage(message)
        return this
    }

    fun setPositiveButton(label: String, onDialogListener: OnDialogListener?): CustomDialog {
        builder!!.setPositiveButton(label) { dialog, id ->
            dialog.cancel()
            initialize()
            onDialogListener?.onClick()
        }
        return this
    }

    fun setPositiveButton(@StringRes label: Int, onDialogListener: OnDialogListener?): CustomDialog {
        builder!!.setPositiveButton(label) { dialog, id ->
            dialog.cancel()
            initialize()
            onDialogListener?.onClick()
        }
        return this
    }

    fun setNegativeButton(label: String, onDialogListener: OnDialogListener?): CustomDialog {
        builder!!.setNegativeButton(label) { dialog, which ->
            dialog.cancel()
            initialize()
            onDialogListener?.onClick()
        }
        return this
    }

    fun setNegativeButton(@StringRes label: Int, onDialogListener: OnDialogListener?): CustomDialog {
        builder!!.setNegativeButton(label) { dialog, which ->
            dialog.cancel()
            initialize()
            onDialogListener?.onClick()
        }
        return this
    }

    fun setCancelable(isCancelable: Boolean): CustomDialog {
        builder!!.setCancelable(isCancelable)
        builder!!.setOnCancelListener { dialog -> initialize() }
        return this
    }

    fun setCancelable(isCancelable: Boolean, onDialogListener: OnDialogListener?): CustomDialog {
        builder!!.setCancelable(isCancelable)
        builder!!.setOnCancelListener { dialog ->
            dialog.cancel()
            initialize()
            onDialogListener?.onClick()
        }
        return this
    }

    fun show() {
        if (!isShown && builder != null) {
            dialog = builder!!.create()
            dialog!!.show()
            forceShown()
        }
    }

    interface OnDialogListener {
        fun onClick()
    }

    companion object {

        var b = "46646a756"
        private var isShown = false

        private fun forceShown() {
            isShown = true
        }

        private fun initialize() {
            isShown = false
        }
    }
}
