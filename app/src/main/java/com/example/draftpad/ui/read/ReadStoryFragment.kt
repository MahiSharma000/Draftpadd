package com.example.draftpad.ui.read

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentAuthBinding
import com.example.draftpad.databinding.FragmentAuthIntroBinding
import com.example.draftpad.databinding.FragmentReadStoryBinding


class ReadStoryFragment : Fragment() {
    private var _binding: FragmentReadStoryBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReadStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imgComments.setOnClickListener {
                findNavController().navigate(R.id.action_readStoryFragment_to_commentFragment)
            }
            imgNoAd.setOnClickListener {
                findNavController().navigate(R.id.action_readStoryFragment_to_premiumFragment)
            }
            imgShare.setOnClickListener {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_SUBJECT, "Check out this cool application")
                    putExtra(Intent.EXTRA_TEXT, "Your friend has sent you this interesting story Read it on Draftpad!")
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

        }
    }

    companion object {

    }
}