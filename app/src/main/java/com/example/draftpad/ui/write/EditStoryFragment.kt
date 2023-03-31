package com.example.draftpad.ui.write

import android.content.Context
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.R
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentEditBinding
import com.example.draftpad.databinding.FragmentEditStoryBinding
import com.example.draftpad.ui.search.BookAdapter


class EditStoryFragment : Fragment() {
    companion object {
       // fun newInstance() = EditStoryFragment()
    }


    private var _binding: FragmentEditStoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditStoryViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditStoryBinding.inflate(inflater, container, false)
        binding.editStoryvm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
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
                0 -> EditFragment("published")
                1 -> EditFragment("drafts")
                else -> EditFragment("published")
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

            viewModel.getResult(filterName, Utils(requireContext()).getUser().id.toInt())

            when (filterName) {
                "published" -> {
                    Log.e("EditStoryFragment", viewModel.publishedbooks.value.toString())
                    viewModel.publishedbooks.observe(viewLifecycleOwner) { books ->
                        binding2.editrv.layoutManager = LinearLayoutManager(context)
                        binding2.editrv.adapter = BookAdapter() { book ->
                            try {
                                val dir =
                                    EditStoryFragmentDirections.actionEditStoryFragmentToEditStoryDetailFragment(
                                        book.id,
                                        book.title,
                                        book.description
                                    )
                                findNavController().navigate(dir)

                            } catch (e: Exception) {
                                Log.e("Error", e.toString())
                            }
                        }
                        Log.e("EditStoryFragment", books.toString())
                        (binding2.editrv.adapter as BookAdapter).submitList(books)
                    }
                }

                "drafts" -> {
                    Log.e("EditStoryFragment", viewModel.draftbooks.value.toString())
                    viewModel.draftbooks.observe(viewLifecycleOwner) { books ->
                        binding2.editrv.layoutManager = LinearLayoutManager(context)
                        binding2.editrv.adapter = BookAdapter() { book ->
                            Log.e("Book", book.toString())
                            try {
                                val dir =
                                    EditStoryFragmentDirections.actionEditStoryFragmentToEditStoryDetailFragment(
                                        book.id,
                                        book.title,
                                        book.description
                                    )
                                findNavController().navigate(dir)

                            } catch (e: Exception) {
                                Log.e("Error", e.toString())
                            }
                        }
                        Log.e("EditStoryFragment", books.toString())
                        (binding2.editrv.adapter as BookAdapter).submitList(books)
                    }

                }
            }
        }
    }

}

