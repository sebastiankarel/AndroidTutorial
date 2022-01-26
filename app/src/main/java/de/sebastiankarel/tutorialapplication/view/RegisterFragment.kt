package de.sebastiankarel.tutorialapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import de.sebastiankarel.tutorialapplication.R
import de.sebastiankarel.tutorialapplication.databinding.FragmentRegisterBinding
import de.sebastiankarel.tutorialapplication.util.EventObserver
import de.sebastiankarel.tutorialapplication.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tilName = view.findViewById<TextInputLayout>(R.id.name_et_layout)
        val tilEmail = view.findViewById<TextInputLayout>(R.id.email_et_layout)
        val tilPassword = view.findViewById<TextInputLayout>(R.id.password_et_layout)
        tilName.isErrorEnabled = true
        tilEmail.isErrorEnabled = true
        tilPassword.isErrorEnabled = true

        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                tilName.error = " "
                tilEmail.error = " "
                tilPassword.error = it
            } ?: run {
                tilName.error = null
                tilEmail.error = null
                tilPassword.error = null
            }
        }

        viewModel.success.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(it.first, it.second))
        })
    }
}