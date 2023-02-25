package com.example.draftpad.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentAuthBinding
import com.example.draftpad.databinding.FragmentSignUpBinding
import com.example.draftpad.auth.AuthSignUpViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope



class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

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
            txtSignIn.setOnClickListener {
                findNavController().navigate(R.id.action_signUpFragment_to_authFragment)
            }
            sLogInBt.setOnClickListener {


                findNavController().navigate(R.id.action_signUpFragment_to_authFragment)
            }
        }
    }
}