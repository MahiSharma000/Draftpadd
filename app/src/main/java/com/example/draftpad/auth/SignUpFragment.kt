package com.example.draftpad.auth

import android.app.AlertDialog
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
import kotlin.math.sign


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
                if (R.id.txtName.toString() == "" || R.id.txtEmail.toString() == "" || R.id.edittxtPassword.toString() == "") {
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
        }
        vm.response.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it.status) {
                    "ERROR" ->{
                        Snackbar.make(binding.root, it.msg, Snackbar.LENGTH_SHORT)
                            .show()
                    }
                    "OK" ->{
                        binding.signUpbt.isEnabled = true
                        findNavController().navigate(R.id.action_signUpFragment_to_authFragment)
                    }
                }
            }
        }
    }
}
