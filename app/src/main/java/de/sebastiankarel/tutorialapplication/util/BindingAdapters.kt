package de.sebastiankarel.tutorialapplication.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("rv_adapter")
fun rvAdapter(rv: RecyclerView, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
    val llm = LinearLayoutManager(rv.context)
    val itemDecor = DividerItemDecoration(rv.context, llm.orientation)
    rv.layoutManager = llm
    rv.addItemDecoration(itemDecor)
    rv.adapter = adapter
}