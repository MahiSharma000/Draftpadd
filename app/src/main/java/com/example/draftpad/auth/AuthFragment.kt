package com.example.draftpad.auth

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
import com.example.draftpad.databinding.FragmentAuthBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val vm: LoginViewModel by activityViewModels()

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

        binding.apply {
            txtSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_authFragment_to_signUpFragment)
            }
            imgPhone.setOnClickListener {
                findNavController().navigate(R.id.action_authFragment_to_phoneLoginFragment2)
            }
            sLogInBt.setOnClickListener {
                if (R.id.txtName.toString() == "" || R.id.txtPassword.toString() == "") {
                    Snackbar.make(binding.root, "Fill All Details", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    vm.LoginUser(
                        txtName.text.toString(),
                        txtPassword.text.toString()
                    )
                }

                //startActivity(Intent(activity, MainActivity::class.java))

                vm.status.observe(viewLifecycleOwner) {
                    when (it) {
                        LoginApiStatus.LOADING -> {
                            binding.sLogInBt.isEnabled = false
                        }
                        LoginApiStatus.DONE -> {
                            binding.sLogInBt.isEnabled = true
                            startActivity(Intent(activity, MainActivity::class.java))
                            activity?.finish()
                            //findNavController().navigate(R.id.action_authFragment_to_fragment_home)
                        }
                        LoginApiStatus.ERROR -> {
                            binding.sLogInBt.isEnabled = true
                        }
                        else -> {
                            binding.sLogInBt.isEnabled = true
                        }
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

    companion object {

    }
}