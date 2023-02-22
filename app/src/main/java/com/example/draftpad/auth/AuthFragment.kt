package com.example.draftpad.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.draftpad.R
import android.content.Intent
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.fragment.findNavController
import com.example.draftpad.AuthActivity
import com.example.draftpad.MainActivity
import com.example.draftpad.databinding.FragmentAuthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    override fun onResume() {
        super.onResume()
        if (auth.currentUser != null) {
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            imgPhone.setOnClickListener {
                findNavController().navigate(R.id.action_authFragment_to_phoneLoginFragment2)
            }
            imgGoogle.setOnClickListener {
                findNavController().navigate(R.id.action_authFragment_to_googleFragment)
            }
            sLogInBt.setOnClickListener {
                startActivity(Intent(activity, MainActivity::class.java))
            }
            txtSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_authFragment_to_signUpFragment)
            }
        }

    }

    companion object {

    }
}