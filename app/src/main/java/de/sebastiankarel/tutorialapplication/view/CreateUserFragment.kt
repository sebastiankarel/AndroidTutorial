package de.sebastiankarel.tutorialapplication.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.sebastiankarel.tutorialapplication.R
import de.sebastiankarel.tutorialapplication.databinding.FragmentCreateUserBinding
import de.sebastiankarel.tutorialapplication.util.EventObserver
import de.sebastiankarel.tutorialapplication.viewmodel.CreateUserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CreateUserFragment : Fragment() {

    private val viewModel: CreateUserViewModel by sharedViewModel()

    private val requestPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        if (it.all { it.value }) {
            findNavController().navigate(R.id.action_createUserFragment_to_cameraFragment)
        } else {
            Toast.makeText(requireContext(), "User permission not granted!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentCreateUserBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.take_photo_btn)?.setOnClickListener {
            if (allPermissionsGranted()) {
                findNavController().navigate(R.id.action_createUserFragment_to_cameraFragment)
            } else {
                requestPermissionsLauncher.launch(REQUIRED_PERMISSION)
            }
        }

        viewModel.error.observe(viewLifecycleOwner, EventObserver {
            (activity as MainActivity).showErrorSnackbar(it)
        })

        viewModel.success.observe(viewLifecycleOwner, EventObserver {
            if (it) findNavController().navigate(R.id.action_createUserFragment_to_listFragment)
        })
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSION.all {
        ContextCompat.checkSelfPermission(requireActivity().baseContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}