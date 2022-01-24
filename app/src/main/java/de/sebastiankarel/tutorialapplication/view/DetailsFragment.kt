package de.sebastiankarel.tutorialapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import de.sebastiankarel.tutorialapplication.R
import de.sebastiankarel.tutorialapplication.databinding.FragmentDetailsBinding
import de.sebastiankarel.tutorialapplication.util.EventObserver
import de.sebastiankarel.tutorialapplication.util.ImageLoader
import de.sebastiankarel.tutorialapplication.viewmodel.DetailsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModel()

    private lateinit var imageView: ImageView
    private val imageLoader: ImageLoader by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.onDelete = View.OnClickListener {
            viewModel.deleteUser(args.id)
        }
        imageView = binding.profileImg
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.error.observe(viewLifecycleOwner, EventObserver {
            (activity as MainActivity).showErrorSnackbar(it)
        })

        viewModel.success.observe(viewLifecycleOwner, EventObserver {
            if (it) findNavController().navigate(R.id.action_detailsFragment_to_listFragment)
        })

        viewModel.user.observe(viewLifecycleOwner) {
            imageLoader.loadImage(imageView, it.photoId)
        }

        viewModel.fetchUser(args.id)
    }
}