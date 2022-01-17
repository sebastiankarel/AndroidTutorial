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
import de.sebastiankarel.tutorialapplication.databinding.FragmentListBinding
import de.sebastiankarel.tutorialapplication.viewmodel.ListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModel()
    private val adapter: ListItemAdapter = ListItemAdapter {
        val directions = ListFragmentDirections.actionListFragmentToDetailsFragment(it)
        findNavController().navigate(directions)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.adapter = adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button)?.setOnClickListener {
            viewModel.updateListItems()
        }

        viewModel.items.observe(viewLifecycleOwner, {
            adapter.items = it ?: listOf()
        })
    }
}