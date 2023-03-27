package com.example.draftpad.ui.write

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentEditBinding
import com.example.draftpad.databinding.FragmentEditStoryBinding
import com.example.draftpad.ui.search.BookAdapter


class EditStoryFragment : Fragment() {
    companion object {
        fun newInstance() = EditStoryFragment()
    }

    private var _binding: FragmentEditStoryBinding? = null
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

    class EditFragment(
        private val filterName: String
    ) : Fragment() {

        private var _binding2: FragmentEditBinding? = null
        private val binding2 get() = _binding2!!
        private val viewModel: EditStoryViewModel by activityViewModels()
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View? {
            _binding2 =
                DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)
            binding2.edit = viewModel
            binding2.lifecycleOwner = viewLifecycleOwner
            return binding2.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            when (filterName) {
                "published" -> {
                    viewModel.books.observe(viewLifecycleOwner) { books ->
                        binding2.editrv.layoutManager = LinearLayoutManager(context)
                        binding2.editrv.adapter = BookAdapter() { book ->

                        }
                        Log.e("SearchNextFragment", books.toString())
                        (binding2.editrv.adapter as BookAdapter).submitList(books)
                    }
                }

                "draft" -> {
                    viewModel.books.observe(viewLifecycleOwner) { books ->
                        binding2.editrv.layoutManager = LinearLayoutManager(context)
                        binding2.editrv.adapter = BookAdapter() { book ->

                        }
                        Log.e("SearchNextFragment", books.toString())
                        (binding2.editrv.adapter as BookAdapter).submitList(books)
                    }

                }
            }
        }
    }

}

