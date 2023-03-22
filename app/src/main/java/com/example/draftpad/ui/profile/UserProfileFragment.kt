package com.example.draftpad.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import com.example.draftpad.R
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentLibraryBinding
import com.example.draftpad.databinding.FragmentUserProfileBinding
import com.example.draftpad.ui.library.ArchiveFragment
import com.example.draftpad.ui.library.DownloadsFragment
import com.example.draftpad.ui.library.LibraryFragment
import com.example.draftpad.ui.library.ReadingListFragment


class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserProfileViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        binding.userProfilevm =viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val userId = Utils(requireContext()).getUser().id.toInt()
        viewModel.getUserProfile(userId)


    }

    companion object {
        fun newInstance() = UserProfileFragment()
    }
}