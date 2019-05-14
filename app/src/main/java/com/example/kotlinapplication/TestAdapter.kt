package com.example.kotlinapplication

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class TestAdapter(context: Context) :
    EndlessLoadingRecyclerViewAdapter(context, false) {
    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_load_more, parent, false))
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TestViewHolder(LayoutInflater.from(context).inflate(R.layout.item_test, parent, false))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val testHolder: TestViewHolder = holder as TestViewHolder
        var test = getItem(position, Test::class.java);
        var testResult: Test = test!! as Test

        testHolder.tv.text = testResult.getName()
    }

    class TestViewHolder(itemView: View) : NormalViewHolder(itemView) {
        val tv: TextView = itemView.findViewById(R.id.tv)
    }
}