package com.example.draftpad.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.R
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentAuthorProfileBinding
import com.example.draftpad.ui.search.BookAdapter


class AuthorProfileFragment : Fragment() {
    private val vm: AuthorProfileViewModel by activityViewModels()
    private var _binding: FragmentAuthorProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthorProfileBinding.inflate(inflater)
        binding.authorProfileVm = vm
        binding.lifecycleOwner = viewLifecycleOwner
        binding.toolbarProfile.inflateMenu(R.menu.profile_menu)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var flag = 0
        val user_id = AuthorProfileFragmentArgs.fromBundle(requireArguments()).userId
        vm.getAuthorId(user_id)
        vm.getFollower(user_id)
        vm.getBooks(user_id)
        vm.checkfollow(Utils(requireContext()).getUser().id.toInt(), user_id)
        try {
            if (vm.checkFollow.value!!.status == "OK") {
                binding.btnfollow.text = "Following"
                binding.btnfollow.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
                flag = 1
            } else {
                binding.btnfollow.text = "Follow"
                flag = 0
            }
        } catch (e: Exception) {
            Log.e("Follow Button", "$e")
        }

        vm.followers.observe(viewLifecycleOwner) { profiles ->
            binding.apply {
                rvfollowers.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                rvfollowers.adapter = FollowerAdapter() { profile ->
                    val dir = AuthorProfileFragmentDirections.actionAuthorProfileFragmentSelf(
                        profile.id.toString().toInt()
                    )
                    findNavController().navigate(dir)

                }
                (binding.rvfollowers.adapter as FollowerAdapter).submitList(profiles)
            }

        }

        vm.books.observe(viewLifecycleOwner) { books ->
            binding.apply {
                rvBooks.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                rvBooks.adapter = BookAdapter() {book ->
                    val dir = AuthorProfileFragmentDirections.actionAuthorProfileFragmentToReadFragment(book.id)
                    findNavController().navigate(dir)
                }
                (binding.rvBooks.adapter as BookAdapter).submitList(books)
            }

        }

        binding.toolbarProfile.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.block -> {
                    if (item.title == "Block") {
                        item.title = "Unblock"
                        vm.blockAuthor(Utils(requireContext()).getUser().id.toInt(), user_id)
                        Toast.makeText(context, "Blocked", Toast.LENGTH_SHORT).show()
                    } else {
                        item.title = "Block"
                        vm.blockAuthor(Utils(requireContext()).getUser().id.toInt(), user_id)
                        Toast.makeText(context, "Unblocked", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            true
        }

        try {
            binding.apply {

                txtwork.setText(vm.author.value!!.book_written.toString())
                txtFollower.setText(vm.author.value!!.followers.toString())
                btnfollow.setOnClickListener {
                    if (flag == 0) {
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
                        flag = 1
                        btnfollow.text = "Following"
                        btnfollow.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))

                    } else {
                        vm.updateProfile(
                            requireContext(),
                            user_id.toString(),
                            vm.author.value!!.first_name,
                            vm.author.value!!.last_name,
                            vm.author.value!!.dob,
                            vm.author.value!!.about,
                            vm.author.value!!.phone,
                            vm.author.value!!.followers - 1,
                        )
                        vm.unfollowAuthor(
                            Utils(requireContext()).getUser().id.toInt(),
                            user_id
                        )
                        flag == 0
                        btnfollow.text = "Follow"
                        btnfollow.setBackgroundColor(resources.getColor(R.color.colorPrimary))

                    }

                }
            }
        } catch (e: Exception) {
            Log.e("Followers", "$e")
        }
    }

}

