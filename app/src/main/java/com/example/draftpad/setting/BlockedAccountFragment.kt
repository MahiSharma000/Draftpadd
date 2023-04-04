package com.example.draftpad.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentBlockedAccountBinding
import com.example.draftpad.ui.profile.AuthorProfileViewModel
import com.example.draftpad.ui.search.CategoryAdapter


class BlockedAccountFragment : Fragment() {

    private var _binding: FragmentBlockedAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthorProfileViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlockedAccountBinding.inflate(inflater)
        binding.blocked = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel.getBlocked(Utils(requireContext()).getUser().id.toInt())
            viewModel.blockList.observe(viewLifecycleOwner) { blockedList ->
                blockedrv.layoutManager = LinearLayoutManager(context)
                blockedrv.adapter = BlockedAdapter() { blocked->
                    val dir = BlockedAccountFragmentDirections.actionBlockedAccountFragmentToAuthorProfileFragment(blocked.user_id)
                    findNavController().navigate(dir)

                }
                (binding.blockedrv.adapter as BlockedAdapter).submitList(blockedList)
            }
        }
    }
}