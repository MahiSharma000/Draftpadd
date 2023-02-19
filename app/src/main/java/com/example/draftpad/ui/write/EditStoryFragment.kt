package com.example.draftpad.ui.write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentEditStoryBinding
import com.example.draftpad.databinding.FragmentLibraryBinding
import com.example.draftpad.ui.library.ArchiveFragment
import com.example.draftpad.ui.library.DownloadsFragment
import com.example.draftpad.ui.library.LibraryFragment
import com.example.draftpad.ui.library.ReadingListFragment


class EditStoryFragment : Fragment() {
    companion object {
        fun newInstance() = EditStoryFragment()
    }
    private var _binding:FragmentEditStoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pagerAdapter = PagerAdapter(childFragmentManager)
        binding.apply {
            viewPager.adapter = pagerAdapter
            editStory.setupWithViewPager(viewPager)
        }
    }

    class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    PublishedFragment.newInstance()
                }
                else -> {
                    DraftFragment.newInstance()
                }
            }
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "PUBLISHED"
                else -> "DRAFTS"
            }
        }
    }

}