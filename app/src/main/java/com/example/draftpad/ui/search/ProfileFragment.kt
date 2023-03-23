package com.example.draftpad.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.databinding.FragmentProfileBinding
import com.example.draftpad.databinding.FragmentSearchNextBinding


class ProfileFragment : Fragment() {

    private val vm: ProfileViewModel by activityViewModels()
    private var _binding: FragmentProfileBinding? = null
    private var _bind : FragmentSearchNextBinding? = null
    private val bind get() = _bind!!
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater)
        binding.viewModel = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = ProfileFragmentArgs.fromBundle(requireArguments()).name
        name.let {
            vm.setName(it)
        }
        vm.name.observe(viewLifecycleOwner) {
            vm.getProfiles()
        }

        vm.profiles.observe(viewLifecycleOwner) { profiles ->
            binding.profilerv.layoutManager = LinearLayoutManager(context)
            binding.profilerv.adapter = ProfileAdapter() { profile ->
                val dir =
                    ProfileFragmentDirections.actionProfileFragmentToAuthorProfileFragment(profile.user_id)
                findNavController().navigate(dir)
            }
            Log.e("ProfileFragment", profiles.toString())
            (binding.profilerv.adapter as ProfileAdapter).submitList(profiles)
        }

    }

}