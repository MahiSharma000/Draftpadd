package com.example.draftpad.ui.profile

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.draftpad.R
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentProfileSettingsBinding
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted

class ProfileSettingsFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentProfileSettingsBinding? = null
    private val binding get() = _binding!!
    private val vm: ProfileSettingsViewModel by activityViewModels()

    private lateinit var utils: Utils


    companion object {
        const val REQUEST_IMAGE_GET = 122
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasPermission()) {
            getPermission()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile_settings, container, false)
        binding.viewModel = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun getPermission() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.permission_required),
            REQUEST_IMAGE_GET,
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE
        )
    }

    private fun hasPermission(): Boolean {
        return EasyPermissions.hasPermissions(
            context, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE
        )
    }

    @AfterPermissionGranted(REQUEST_IMAGE_GET)
    private fun selectImage() {
        if (EasyPermissions.hasPermissions(
                context, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE
            )
        ) {
            getImage.launch("image/*")
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.permission_required),
                REQUEST_IMAGE_GET,
                READ_EXTERNAL_STORAGE,
                WRITE_EXTERNAL_STORAGE
            )
        }
    }


    private fun showSnackBar(msg: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error Occurred")
            .setMessage(msg)
            .setPositiveButton("Ok") { _, _ ->

            }.create().show()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Permission Required")
        dialog.setMessage("This permission is required to upload image")
        dialog.setPositiveButton("Ok") { _, _ ->
            getPermission()
        }
        dialog.setNegativeButton("Cancel") { _, _ ->
            dialog.setCancelable(true)
        }
        dialog.show()

    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        showSnackBar("Permission Granted")
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        utils = Utils(requireContext())
        val loggedInUser = utils.getUser()
        loggedInUser.username.let { binding.userName.setText(it) }
        loggedInUser.email.let { binding.editTextEmail.setText(it) }
        vm.getUser(loggedInUser.id.toInt())

        vm.downloadUri.observe(viewLifecycleOwner) {
            binding.imgProfile.load(it) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }

        vm.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ProfileApiStatus.DONE -> {
                    showSnackBar("Profile Updated")
                }
                ProfileApiStatus.ERROR -> {
                    showSnackBar("Error Occurred")
                }
                ProfileApiStatus.LOADING -> {
                    showSnackBar("Loading")
                }
                ProfileApiStatus.NONE -> {

                }
            }
        }

        vm.response.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                when (response.status) {
                    "OK" -> {
                        showSnackBar("Profile Updated")
                    }
                    "ERROR" -> {
                        showSnackBar("Profile Error Occurred")
                    }

                }
            }
        }
        binding.apply {
            doneBt.setOnClickListener {
                var error_count = 0
                if (vm.isImgSelected.value == false) {
                    error_count++
                    showSnackBar("Please select an image")
                }
                if (editTextEmail.text.isEmpty()) {
                    error_count++
                    editTextEmail.error = "Email cannot be empty"
                }
                if (userName.text.isEmpty()) {
                    error_count++
                    userName.error = "Username cannot be empty"
                }
                if (txtFirstName.text.isEmpty()) {
                    error_count++
                    txtFirstName.error = "First Name cannot be empty"
                }
                if (txtLastName.text.isEmpty()) {
                    error_count++
                    txtLastName.error = "Last Name cannot be empty"
                }
                if (txtDob.text.isEmpty()) {
                    error_count++
                    txtDob.error = "Date of Birth cannot be empty"
                }
                if (error_count == 0) {
                    vm.createOrUpdateProfile(
                        requireContext(),
                        loggedInUser.id,
                        txtFirstName.text.toString(),
                        txtLastName.text.toString(),
                        txtDob.text.toString(),
                        txtAbout.text.toString(),
                        txtPhone.text.toString(),
                        0
                    )
                } else {
                    showSnackBar("Please fill all the fields $error_count")
                }
            }
            imgProfile.setOnClickListener {
                selectImage()
            }
            txtchangepwd.setOnClickListener {
                findNavController().navigate(R.id.action_profileSettingsFragment_to_changePassword)
            }
        }
    }

    private val getImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            vm.setImageUri(uri, true)
        }
}