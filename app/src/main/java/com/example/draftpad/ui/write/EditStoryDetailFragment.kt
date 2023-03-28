package com.example.draftpad.ui.write

import android.Manifest
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentEditStoryDetailBinding
import com.example.draftpad.ui.profile.ProfileSettingsFragment
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted

class EditStoryDetailFragment : Fragment() {
    private var _binding: FragmentEditStoryDetailBinding? = null
    private val binding get() = _binding!!
    private var toolbar: Toolbar? = null
    private val vm: NewStoryViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_story_detail, container, false)
        binding.viewModel = vm
        binding.lifecycleOwner = viewLifecycleOwner

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val catId = EditStoryDetailFragmentArgs.fromBundle(requireArguments()).categoryId
        val category_name = EditStoryDetailFragmentArgs.fromBundle(requireArguments()).categoryName
        val bookId = EditStoryDetailFragmentArgs.fromBundle(requireArguments()).bookId

        bookId.let{
            vm.setBookId(it)
        }

        vm.bookId.observe(viewLifecycleOwner) {
            vm.getSelectedBook()
        }

        binding.toolbar.inflateMenu(R.menu.edit_story_detail_menu)


        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_add -> {
                    findNavController().navigate(R.id.action_editStoryDetailFragment_to_writeStoryFragment)
                    true
                }
                R.id.action_view_as_reader -> {

                    true
                }
                R.id.delete -> {

                    true
                }
                else -> false
            }
        }

        binding.apply {
            imgEditCover.setOnClickListener {
                selectImage()
            }

            editChapter.setOnClickListener {
                findNavController().navigate(R.id.action_editStoryDetailFragment_to_editChaptersFragment)
            }
            txtEditCategory.setOnClickListener {
                        txtEditCategory.text = category_name
            }

            button.setOnClickListener {
                vm.createnewBook(
                    requireContext(),
                    bookTitle.text.toString(),
                    decription.text.toString(),
                    vm.book.value!!.status,
                    vm.book.value!!.user_id,
                    catId
                )
            }

            editChapter.setOnClickListener {
                val dir = EditStoryDetailFragmentDirections.actionEditStoryDetailFragmentToEditChaptersFragment(bookId)
                findNavController().navigate(dir)
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
    private val getImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            vm.setImageUri(uri, true)
            getStringFromUri(uri)
        }

    private fun getStringFromUri(uri: Uri?) {

    }
    private fun showSnackBar(msg: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error Occurred")
            .setMessage(msg)
            .setPositiveButton("Ok") { _, _ ->

            }.create().show()
    }

}