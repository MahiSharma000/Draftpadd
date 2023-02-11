package com.example.draftpad.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.draftpad.MainActivity
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentEPLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class EPLoginFragment : Fragment() {
    private var _binding: FragmentEPLoginBinding? = null
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
    ): View {
        _binding = FragmentEPLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLogin.setOnClickListener { loginUser() }
        }

    }

    private fun loginUser() {
        disableButtons()
        binding.apply {
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            if (email.isEmpty()) {
                binding.editEmail.setError("Please provide email")
                return
            }
            if (password.isEmpty()) {
                binding.editPassword.setError("Please provide email")
                return
            }
            auth.signInWithEmailAndPassword(email, password).addOnFailureListener {
                updateUI(it.message, null)
            }.addOnSuccessListener {
                updateUI("success", it.user)
            }
        }
    }

    private fun disableButtons() {
        binding.btnLogin.isEnabled = false
        binding.btnRegister.isEnabled = false
    }

    private fun enableButtons() {
        binding.btnLogin.isEnabled = true
        binding.btnRegister.isEnabled = true
    }

    private fun updateUI(message: String?, user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        } else {
            message?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
            enableButtons()
        }

    }

    companion object {

    }
}