package com.example.draftpad.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.recreate
import androidx.navigation.fragment.findNavController
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            txtProfileSetting.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_profileSettingsFragment)
            }
            txtBlocked.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_blockedAccountFragment)
            }
            btnPremiumSetting.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_premiumFragment)
            }
            btnOn.setOnClickListener{
                //setTheme(R.style.Theme_DraftPadDark)
                //recreate()
            }
            btnOff.setOnClickListener {
                //setTheme(R.style.Theme_DraftPadLight)
                //recreate()
            }
        }
    }


}