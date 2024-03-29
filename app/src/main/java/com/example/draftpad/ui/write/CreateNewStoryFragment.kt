package com.example.draftpad.ui.write

import android.Manifest
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.draftpad.R
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentCreateNewStoryBinding
import com.example.draftpad.ui.profile.ProfileSettingsFragment
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted


class CreateNewStoryFragment : Fragment() {
    private var _binding:FragmentCreateNewStoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewStoryViewModel by activityViewModels()


    companion object {
        const val REQUEST_IMAGE_SET = 122
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasPermission()) {
            getPermission()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_new_story, container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val category_id = CreateNewStoryFragmentArgs.fromBundle(requireArguments()).category
        val category_name = CreateNewStoryFragmentArgs.fromBundle(requireArguments()).categoryName
        binding.cat.text = category_name
        viewModel.downloadUri.observe(viewLifecycleOwner) {
            binding.imgCover.load(it) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
        binding.apply {
            txtChangeCover.setOnClickListener {
                selectImage()
            }
            nextBt.setOnClickListener {
                if (txtTitle.text.toString().isEmpty() || txtDescription.text.toString()
                        .isEmpty() || txtSelectCategory.text.toString()
                        .isEmpty() || vm!!.isImgSelected.value == false
                ) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Error")
                        .setMessage("Please fill all the fields")
                        .setPositiveButton("OK") { dialog, which ->
                            dialog.dismiss()
                        }
                        .show()
                } else {
                    viewModel.createnewBook(
                        requireContext(),
                        0,
                        txtTitle.text.toString(),
                        txtDescription.text.toString(),
                        0,
                        Utils(requireContext()).getUser().id.toInt(),
                        category_id,
                    )
                    Toast.makeText(requireContext(), "Saved in draft", Toast.LENGTH_SHORT).show()
                }

            }
            vm?.status?.observe(viewLifecycleOwner) {
                when (it) {
                    NewStoryApiStatus.LOADING -> {
                        binding.nextBt.isEnabled = false
                    }
                    NewStoryApiStatus.DONE -> {
                        binding.nextBt.isEnabled = true
                        val dir =
                            CreateNewStoryFragmentDirections.actionCreateNewStoryFragmentToWriteStoryFragment(
                                viewModel.response.value!!.id,
                                binding.txtTitle.text.toString(),
                                binding.txtDescription.text.toString(),
                                category_id
                            )
                        findNavController().navigate(dir)
                    }
                    NewStoryApiStatus.ERROR -> {
                        binding.nextBt.isEnabled = true
                    }
                    else -> {
                        binding.nextBt.isEnabled = true
                    }
                }
            }


        }
        viewModel.response.observe(viewLifecycleOwner)
        {
            if (it != null) {
                when (it.status) {
                    "Success" -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Success")
                            .setMessage(it.msg)
                            .setPositiveButton("OK") { dialog, which ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                }
            }
        }

    }

    private fun hasPermission(): Boolean {
        return EasyPermissions.hasPermissions(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private fun getPermission() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.permission_required),
            ProfileSettingsFragment.REQUEST_IMAGE_GET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    @AfterPermissionGranted(ProfileSettingsFragment.REQUEST_IMAGE_GET)
    private fun selectImage() {
        if (EasyPermissions.hasPermissions(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            getImage.launch("image/*")
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.permission_required),
                ProfileSettingsFragment.REQUEST_IMAGE_GET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
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

    fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        showSnackBar("Permission Granted")
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun showSnackBar(msg: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error Occurred")
            .setMessage(msg)
            .setPositiveButton("Ok") { _, _ ->

            }.create().show()
    }

    private val getImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            viewModel.setImageUri(uri, true)
            getStringFromUri(uri)
        }

    private fun getStringFromUri(uri: Uri?) {

    }
}