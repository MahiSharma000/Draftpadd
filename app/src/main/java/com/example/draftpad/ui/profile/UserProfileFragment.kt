package com.example.draftpad.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentUserProfileBinding


class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthorProfileViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        binding.userProfilevm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = Utils(requireContext()).getUser().id.toInt()
        viewModel.getAuthorId(userId)
        viewModel.getFollower(userId)
        binding.apply {
            viewModel.followers.observe(viewLifecycleOwner) { followers ->
                followerrv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                followerrv.adapter = FollowerAdapter() { follower ->
                    val dir = UserProfileFragmentDirections.actionUserProfileFragmentToAuthorProfileFragment(follower.id)
                    findNavController().navigate(dir)
                }
                (binding.followerrv.adapter as FollowerAdapter).submitList(followers)
            }
        }


    }
    companion object {
        fun newInstance() = UserProfileFragment()
    }
}