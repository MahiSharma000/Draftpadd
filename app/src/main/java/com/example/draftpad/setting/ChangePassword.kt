package com.example.draftpad.setting

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.draftpad.R
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentChangePasswordBinding
import com.example.draftpad.ui.profile.ProfileSettingsViewModel


class ChangePassword : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var utils: Utils
    private val vm: ProfileSettingsViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        utils = Utils(requireContext())
        val loggedInUser = utils.getUser()
        vm.getUser(loggedInUser.id.toInt())
        binding.apply {
            btnchange.setOnClickListener {
                if (editTextTextPassword.text.isEmpty() || editTextTextPassword2.text.isEmpty() || editTextTextPassword3.text.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please fill all the fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (editTextTextPassword2.text.toString() != editTextTextPassword3.text.toString()) {
                        Toast.makeText(
                            requireContext(),
                            "Passwords do not match",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        vm.changeUserPassword(
                            loggedInUser.id.toInt(),
                            editTextTextPassword.text.toString(),
                            editTextTextPassword2.text.toString()
                        )

                        vm.getresponse.observe(viewLifecycleOwner) { response ->
                            if (response != null) {
                                when (response.status) {
                                    "OK" -> {
                                        Toast.makeText(
                                            requireContext(),
                                            "Password Changed Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    "ERROR" -> {
                                        showSnackBar("Password Error Occurred")
                                    }

                                }

                            }
                        }
                    }
                }

            }
        }
    }

    private fun showSnackBar(msg: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error Occurred")
            .setMessage(msg)
            .setPositiveButton("Ok") { _, _ ->

            }.create().show()
    }

}