package com.example.draftpad.auth

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentAuthBinding
import com.example.draftpad.databinding.FragmentSignUpBinding
import com.example.draftpad.auth.AuthSignUpViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val vm: AuthSignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = vm
            txtSignIn.setOnClickListener {
                findNavController().navigate(R.id.action_signUpFragment_to_authFragment)
            }
            sLogInBt.setOnClickListener {
                if (R.id.txtName.toString() == "" || R.id.txtEmail.toString() == "" || R.id.txtPassword.toString() == "") {
                    Snackbar.make(binding.root, "Fill All Details", Snackbar.LENGTH_SHORT)
                        .show()
                } else {

                    vm.signUpUser(
                        txtName.text.toString(),
                        txtEmail.text.toString(),
                        txtPassword.text.toString()
                    )
                }
            }
        }
        vm.status.observe(viewLifecycleOwner) {
            when (it) {
                AuthApiStatus.LOADING -> {
                    binding.sLogInBt.isEnabled = false
                }
                AuthApiStatus.DONE -> {
                    binding.sLogInBt.isEnabled = true
                    findNavController().navigate(R.id.action_signUpFragment_to_authFragment)
                }
                AuthApiStatus.ERROR -> {
                    binding.sLogInBt.isEnabled = true
                    AlertDialog.Builder(requireContext())
                        .setTitle("Error")
                        .setMessage(vm.response.value.toString())
                        .setPositiveButton("OK") { dialog, which ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }
    }
}