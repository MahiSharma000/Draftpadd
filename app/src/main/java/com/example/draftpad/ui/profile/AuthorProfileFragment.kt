package com.example.draftpad.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.fragment.app.activityViewModels
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentAuthorProfileBinding


class AuthorProfileFragment : Fragment() {
    private val vm: AuthorProfileViewModel by activityViewModels()
    private var _binding: FragmentAuthorProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthorProfileBinding.inflate(inflater)
        binding.authorProfileVm = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = AuthorProfileFragmentArgs.fromBundle(requireArguments()).userId
        Log.d("AuthorProfileFragment", "onViewCreated: $userId")
        vm.getAuthorId(userId)
    }

}