package com.example.draftpad.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.databinding.FragmentFollowerBinding


class FollowerFragment : Fragment() {

    private val vm: FollowerViewModel by activityViewModels()
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(inflater)
        binding.followervm = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        vm.setUserId(arguments?.getInt("user") ?: 1)
        vm.getFollowers()
        vm.followers.observe(viewLifecycleOwner) { followers ->
            binding.rvFollowers.layoutManager = LinearLayoutManager(context)
            binding.rvFollowers.adapter = FollowerAdapter() { follower ->
                Log.e("FollowerFragment", follower.toString())
                (binding.rvFollowers.adapter as FollowerAdapter).notifyDataSetChanged()

            }
            Log.e("BooksFragment", followers.toString())
            (binding.rvFollowers.adapter as FollowerAdapter).submitList(followers)
        }

    }

}

