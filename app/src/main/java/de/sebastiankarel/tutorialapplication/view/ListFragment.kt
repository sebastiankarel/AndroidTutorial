package de.sebastiankarel.tutorialapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ListAdapter
import de.sebastiankarel.tutorialapplication.R
import de.sebastiankarel.tutorialapplication.databinding.FragmentListBinding
import de.sebastiankarel.tutorialapplication.model.User
import de.sebastiankarel.tutorialapplication.util.EventObserver
import de.sebastiankarel.tutorialapplication.util.ImageLoader
import de.sebastiankarel.tutorialapplication.viewmodel.ListViewModel
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()
    private val adapter: UserListAdapter = UserListAdapter(imageLoader) {
        val directions = ListFragmentDirections.actionListFragmentToDetailsFragment(it)
        findNavController().navigate(directions)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.adapter = adapter as ListAdapter<User, UserListAdapter.ListItemViewHolder>
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.create_user_btn)?.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_createUserFragment)
        }

        viewModel.error.observe(viewLifecycleOwner, EventObserver {
            (activity as MainActivity).showErrorSnackbar(it)
        })

        lifecycleScope.launchWhenResumed {
            viewModel.users().collect {
                adapter.submitList(it)
            }
        }
    }
}