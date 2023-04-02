package com.example.draftpad.ui.library

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.R
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentLibraryBinding
import com.example.draftpad.databinding.FragmentLibraryDetailBinding
import com.example.draftpad.ui.search.BookAdapter

class LibraryFragment : Fragment() {

    companion object {
        fun newInstance() = LibraryFragment()
    }

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!
    private var toolbar: Toolbar? = null
    private val vm: LibraryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        toolbar = binding.toolbar
        toolbar?.inflateMenu(R.menu.library_menu)
        binding.libraryVm = vm
        binding.lifecycleOwner = viewLifecycleOwner
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    LibraryDetailFragment("Downloads")
                }
                else -> {
                    LibraryDetailFragment("Read Later")
                }
            }
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "Downloads"
                else -> "Read Later"
            }
        }
    }

    class LibraryDetailFragment(
        private val filterName: String
    ) : Fragment() {
        private var _binding2: FragmentLibraryDetailBinding? = null
        private val binding2 get() = _binding2!!
        private val vm: LibraryViewModel by activityViewModels()
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View? {
            _binding2 =
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.fragment_library_detail,
                    container,
                    false
                )
            binding2.viewModel = vm
            binding2.lifecycleOwner = viewLifecycleOwner
            return binding2.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            vm.getResult(filterName, Utils(requireContext()).getUser().id.toInt())

            when (filterName) {
                "Downloads" -> {

                }

                "Read Later" -> {
                    Log.e("Library", vm.readLaterBooks.value.toString())
                    vm.readLaterBooks.observe(viewLifecycleOwner) { books ->
                        binding2.rvLibrary.layoutManager = LinearLayoutManager(context)
                        binding2.rvLibrary.adapter = BookAdapter() { book ->
                            try {
                                val dir =
                                    LibraryFragmentDirections.actionNavigationLibraryToReadFragment(
                                        book.id
                                    )
                                findNavController().navigate(dir)

                            } catch (e: Exception) {
                                Log.e("Error", e.toString())
                            }
                        }
                        Log.e("Library", books.toString())
                        (binding2.rvLibrary.adapter as BookAdapter).submitList(books)
                    }

                }
            }
        }
    }
}