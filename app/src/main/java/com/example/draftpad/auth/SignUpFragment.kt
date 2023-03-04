package com.example.draftpad.auth

import android.app.AlertDialog
import android.app.ProgressDialog.show
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val vm: AuthSignUpViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.viewModel = vm
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            txtSignIn.setOnClickListener {
                findNavController().navigate(R.id.action_signUpFragment_to_authFragment)
            }
            signUpbt.setOnClickListener {
                if (edittxtName.text.toString().isEmpty() || txtEmail.text.toString().isEmpty() || edittxtPassword.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, "Fill All Details", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    vm.signUpUser(
                        edittxtName.text.toString(),
                        txtEmail.text.toString(),
                        edittxtPassword.text.toString()
                    )
                }
            }
            vm.status.observe(viewLifecycleOwner) {
                when (it) {
                    AuthApiStatus.LOADING -> {
                        binding.signUpbt.isEnabled = false
                    }
                    AuthApiStatus.DONE -> {
                        binding.signUpbt.isEnabled = true
                        findNavController().navigate(R.id.action_signUpFragment_to_authFragment)
                    }
                    AuthApiStatus.ERROR -> {
                        binding.signUpbt.isEnabled = true
                    }
                    else -> {
                        binding.signUpbt.isEnabled = true
                    }
                }
            }
        }
        vm.response.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it.status) {
                    "Success" -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Success")
                            .setMessage(it.msg)
                            .setPositiveButton("OK") { dialog, which ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                }
            }
        }
    }
}
