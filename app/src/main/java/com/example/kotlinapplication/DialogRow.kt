package com.example.kotlinapplication

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView

class DialogRow : RelativeLayout {
    private var mContext: Context? = null
    internal lateinit var tvTitle: TextView
    internal lateinit var tvDetail: TextView

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
        setParams(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
        setParams(attrs)
    }

    private fun init(context: Context) {
        mContext = context
        val view = LayoutInflater.from(context).inflate(R.layout.item_dialog, this, true)
        tvDetail = view.findViewById(R.id.tv_detail)
        tvTitle = view.findViewById(R.id.tv_title)
    }

    private fun setParams(attrs: AttributeSet) {
        val a = mContext!!.theme.obtainStyledAttributes(attrs, R.styleable.DialogRow, 0, 0)

        val title = a.getString(R.styleable.DialogRow_dr_title)
        setTitle(title)

        val detail = a.getString(R.styleable.DialogRow_dr_detail)
        setDetail(detail)

        a.recycle()
    }


    fun setTitle(title: String?): DialogRow {
        tvTitle.text = title
        return this
    }

    fun setDetail(detail: String?): DialogRow {
        if (!TextUtils.isEmpty(detail)) {
            tvDetail.text = detail
        }
        return this
    }

}
