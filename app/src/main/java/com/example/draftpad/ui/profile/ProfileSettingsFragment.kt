package com.example.draftpad.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.draftpad.R
import androidx.compose.runtime.remember as remember

class ProfileSettingsFragment : Fragment() {

    private var _binding: ProfileSettingsFragment? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProfileSettingsFragment.inflate(inflater, R.layout.fragment_auth, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            imgProfile.setOnClickListener {
                //image picker
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, 0)

                //show image in imgProfile
                imgProfile.setImageURI(data?.data)
            }

        }

        //isme abhi or code krna hai image pic krne k loiye

}