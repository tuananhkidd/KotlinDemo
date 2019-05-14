package com.example.kotlinapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener, RecyclerViewAdapter.OnItemClickListener {

    var adapter: TestAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = TestAdapter(this)
        adapter!!.addModels(fakeData(), false)
        adapter!!.addOnItemClickListener(this)
        adapter!!.setLoadingMoreListener(this)
        rcv_test.adapter = adapter

        btn_create.setOnClickListener{
            adapter!!.addModels(fakeData(), false)
        }

        btn_clear.setOnClickListener{
            adapter!!.clear()
        }
    }

    override fun onLoadMore() {
        adapter!!.showLoadingItem(true)
        Handler().postDelayed(Runnable {
            adapter!!.hideLoadingItem()
            adapter!!.addModels(fakeData(), false)
        }, 1000)
    }

    fun fakeData(): ArrayList<Test> {
        var data: ArrayList<Test> = ArrayList()
        for (i in 0..15) {
            data.add(Test("Test ${i}"))
        }
        return data
    }

    override fun onItemClick(
        adapter1: RecyclerView.Adapter<*>,
        viewHolder: RecyclerView.ViewHolder?,
        viewType: Int,
        position: Int
    ) {
        val test: Test? = adapter!!.getItem(position, Test::class.java)
        Toast.makeText(applicationContext, test?.getName(), Toast.LENGTH_LONG).show()
    }
}
