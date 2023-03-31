package com.example.draftpad.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.R
import com.example.draftpad.Utils
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
        val user_id = AuthorProfileFragmentArgs.fromBundle(requireArguments()).userId
        Log.d("AuthorProfileFragment", "onViewCreated: $user_id")
        vm.getAuthorId(user_id)
        vm.getFollowers(user_id)
        binding.apply {

            vm.followers.observe(viewLifecycleOwner) {followers ->
                this.rvfollowers.layoutManager = LinearLayoutManager(context)
                this.rvfollowers.adapter = FollowerAdapter(){
                    follower->
                    val dir = AuthorProfileFragmentDirections.actionAuthorProfileFragmentSelf(follower.id.toString().toInt())
                    findNavController().navigate(dir)

                }

            }
            btnfollow.setOnClickListener {
                vm.updateProfile(
                    requireContext(),
                    user_id.toString(),
                    vm.author.value!!.first_name,
                    vm.author.value!!.last_name,
                    vm.author.value!!.dob,
                    vm.author.value!!.about,
                    vm.author.value!!.phone,
                    vm.author.value!!.followers.plus(1),
                )
                vm.postFollow(
                    Utils(requireContext()).getUser().id,
                    user_id.toString()
                )

                btnfollow.text= "Following"
                btnfollow.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))

            }

        }
    }

}

