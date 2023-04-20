package com.example.draftpad.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentAuthIntroBinding


class AuthIntroFragment : Fragment() {
    private var _binding: FragmentAuthIntroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAuthIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            LoginBtIntro.setOnClickListener {
                findNavController().navigate(R.id.action_authIntroFragment_to_authFragment)
            }
            signUpbtintro.setOnClickListener {
                findNavController().navigate(R.id.action_authIntroFragment_to_signUpFragment)
            }
        }
    }
}