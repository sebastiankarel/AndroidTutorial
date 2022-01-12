package de.sebastiankarel.tutorialapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.sebastiankarel.tutorialapplication.R
import de.sebastiankarel.tutorialapplication.viewmodel.ListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModel()
    private val adapter: ListItemAdapter = ListItemAdapter {
        val directions = ListFragmentDirections.actionListFragmentToDetailsFragment(it)
        findNavController().navigate(directions)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter(view)

        view.findViewById<Button>(R.id.button)?.setOnClickListener {
            viewModel.updateListItems()
        }

        viewModel.items.observe(viewLifecycleOwner, {
            adapter.items = it ?: listOf()
        })
    }

    private fun setupAdapter(view: View) {
        val rv = view.findViewById<RecyclerView>(R.id.rv)
        val llm = LinearLayoutManager(rv.context)
        val itemDecor = DividerItemDecoration(rv.context, llm.orientation)
        rv.layoutManager = llm
        rv.addItemDecoration(itemDecor)
        rv.adapter = adapter
    }
}