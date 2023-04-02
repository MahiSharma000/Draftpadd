package com.example.draftpad.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentSearchNextBinding
import com.example.draftpad.databinding.FragmentSearchResultBinding

class SearchNextFragment : Fragment() {
    private val viewModel: SearchViewModel by activityViewModels()
    private var _binding: FragmentSearchNextBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchNextBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // if fragment is attached to the activity

        binding.txtsearchByName.setText(viewModel.query.value)
        binding.viewPager.adapter = SearchNextViewPagerAdapter(childFragmentManager)
        binding.tabLayout2.setupWithViewPager(binding.viewPager)
        binding.txtsearchByName.addTextChangedListener {
            // clear the stories, profiles and reading lists
            viewModel.clearSearchResult()
            viewModel.setQuery(getName())
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    fun getName(): String {
        return binding.txtsearchByName.text.toString()
    }

    class SearchNextViewPagerAdapter(
        fm: FragmentManager
    ) : FragmentStatePagerAdapter(
        fm
    ) {
        override fun getCount(): Int = 2

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> SearchResultFragment("stories")
                1 -> SearchResultFragment("profiles")
                else -> SearchResultFragment("stories")
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "Stories"
                1 -> "Profiles"
                else -> "Stories"
            }
        }
    }

    class SearchResultFragment(
        private val filterName: String
    ) : Fragment() {

        private var _binding2: FragmentSearchResultBinding? = null
        private val binding2 get() = _binding2!!
        private val viewModel: SearchViewModel by activityViewModels()
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View? {
            _binding2 =
                DataBindingUtil.inflate(inflater, R.layout.fragment_search_result, container, false)
            binding2.viewModel = viewModel
            binding2.lifecycleOwner = viewLifecycleOwner
            return binding2.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            viewModel.query.observe(viewLifecycleOwner) {
                if (viewModel.profileList.value.isNullOrEmpty() && viewModel.bookList.value.isNullOrEmpty()) {
                    viewModel.getSearchResult(it, filterName)
                }
            }

            when (filterName) {
                "stories" -> {
                    viewModel.bookList.observe(viewLifecycleOwner) { books ->
                        binding2.searchRv.layoutManager = LinearLayoutManager(context)
                        binding2.searchRv.adapter = BookAdapter() { book ->
                            val dir =
                                SearchNextFragmentDirections.actionSearchNextFragmentToReadFragment(
                                    book.id
                                )
                            findNavController().navigate(dir)
                        }
                        Log.e("SearchNextFragment", books.toString())
                        (binding2.searchRv.adapter as BookAdapter).submitList(books)
                    }
                }
                "profiles" -> {
                    viewModel.profileList.observe(viewLifecycleOwner) { profiles ->
                        binding2.searchRv.layoutManager = LinearLayoutManager(context)
                        binding2.searchRv.adapter = ProfileAdapter() { profile ->
                            val dir =
                                SearchNextFragmentDirections.actionSearchNextFragmentToAuthorProfileFragment(
                                    profile.user_id
                                )
                            findNavController().navigate(dir)
                        }
                        Log.e("ProfileFragment", profiles.toString())
                        (binding2.searchRv.adapter as ProfileAdapter).submitList(profiles)
                    }
                }
            }
        }
    }
}

