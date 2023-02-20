package com.example.draftpad.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {

    companion object {
        fun newInstance() = LibraryFragment()
    }

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!
    private var toolbar: Toolbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        toolbar = binding.toolbar
        toolbar?.inflateMenu(R.menu.library_menu)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pagerAdapter = PagerAdapter(childFragmentManager)
        binding.apply {
            viewPager.adapter = pagerAdapter
            librarysr.setupWithViewPager(viewPager)
        }
    }

    class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                   DownloadsFragment.newInstance()
                }
                1 -> {
                    ArchiveFragment.newInstance()
                }
                else -> {
                    ReadingListFragment.newInstance()
                }
            }
        }

        override fun getCount(): Int {
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "Downloads"
                1 -> "Archived"
                else -> "Read Later"
            }
        }
    }
}