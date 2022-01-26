package de.sebastiankarel.tutorialapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import de.sebastiankarel.tutorialapplication.R
import de.sebastiankarel.tutorialapplication.databinding.FragmentLoginBinding
import de.sebastiankarel.tutorialapplication.util.EventObserver
import de.sebastiankarel.tutorialapplication.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.isLoggedIn()) findNavController().navigate(R.id.action_loginFragment_to_listFragment)


        view.findViewById<Button>(R.id.register_btn).setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        val tilEmail = view.findViewById<TextInputLayout>(R.id.email_et_layout)
        val tilPassword = view.findViewById<TextInputLayout>(R.id.password_et_layout)
        tilEmail.isErrorEnabled = true
        tilPassword.isErrorEnabled = true

        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                tilEmail.error = " "
                tilPassword.error = it
            } ?: run {
                tilEmail.error = null
                tilPassword.error = null
            }
        }

        viewModel.success.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(R.id.action_loginFragment_to_listFragment)
        })
    }
}