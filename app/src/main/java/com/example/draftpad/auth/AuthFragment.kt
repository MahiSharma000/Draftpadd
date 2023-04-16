package com.example.draftpad.auth

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.draftpad.R
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.draftpad.MainActivity
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentAuthBinding
import com.example.draftpad.models.LoggedUser
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val vm: LoginViewModel by activityViewModels()
    private val sharedPrefManager: SharedPreferenceManager by lazy {
        SharedPreferenceManager(requireContext())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    /*override fun onResume() {
        super.onResume()
        if (auth.currentUser != null) {
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false)
        binding.viewModel = vm
        binding.lifecycleOwner = this

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginProgress.setVisibility(View.INVISIBLE)

        binding.apply {
            loginbt.setOnClickListener {
                binding.loginProgress.max = 10
                val currentProgress = 6
                loginProgress.setVisibility(View.VISIBLE)
                ObjectAnimator.ofInt(binding.loginProgress, "progress", currentProgress)
                    .setDuration(1000)
                    .start()
                if (txtName.text.toString().isEmpty() || txtPassword.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, "Fill All Details", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    vm.LoginUser(
                        txtName.text.toString(),
                        txtPassword.text.toString()
                    )
                }
                vm.status.observe(viewLifecycleOwner) {
                    when (it) {
                        LoginApiStatus.LOADING -> {
                            binding.loginbt.isEnabled = false
                        }
                        LoginApiStatus.DONE -> {
                            binding.loginbt.isEnabled = true
                            when (vm.response.value?.status) {
                                "OK" -> {
                                    Utils(requireContext()).saveLoggedUser(
                                        LoggedUser(
                                            vm.response.value?.username!!,
                                            vm.response.value?.email!!,
                                            vm.response.value?.id!!
                                        )
                                    )
                                    loginProgress.setVisibility(View.INVISIBLE)
                                    sharedPrefManager.setLoggedIn(true)
                                    startActivity(Intent(activity, MainActivity::class.java))
                                    activity?.finish()

                                }
                                "ERROR" -> {
                                    AlertDialog.Builder(requireContext())
                                        .setTitle("Error")
                                        .setMessage("invalid credentials, please try again")
                                        .setPositiveButton("OK") { dialog, which ->
                                            dialog.dismiss()
                                        }
                                        .show()
                                }
                            }
                        }
                        LoginApiStatus.ERROR -> {
                            binding.loginbt.isEnabled = true
                        }
                        else -> {
                            binding.loginbt.isEnabled = true
                        }
                    }
                }
            }

            txtForgotPwd.setOnClickListener {
            }

            txtSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_authFragment_to_signUpFragment)
            }
        }

        vm.response.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it.status) {
                    "Success" -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Success")
                            .setPositiveButton("OK") { dialog, which ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                    "Error" -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Error")
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