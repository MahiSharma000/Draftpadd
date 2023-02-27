package com.example.draftpad.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentLibraryBinding
import com.example.draftpad.databinding.FragmentUserProfileBinding
import com.example.draftpad.ui.library.ArchiveFragment
import com.example.draftpad.ui.library.DownloadsFragment
import com.example.draftpad.ui.library.LibraryFragment
import com.example.draftpad.ui.library.ReadingListFragment


class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pagerAdapter = UserProfileFragment.PagerAdapter(childFragmentManager)
        binding.apply {
            viewPager.adapter = pagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    AboutFragment.newInstance()
                }
                1 -> {
                    FollowerFragment.newInstance()
                }
                else -> {
                    FollowingFragment.newInstance()
                }
            }
        }

        override fun getCount(): Int {
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "ABOUT"
                1 -> "FOLLOWERS"
                else -> "FOLLOWING"
            }
        }
    }

    companion object {
        fun newInstance() = UserProfileFragment()
    }
}