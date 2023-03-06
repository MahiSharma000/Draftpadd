package com.example.draftpad.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageAndVideo.equals
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly.equals
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentBooksBinding
import com.example.draftpad.databinding.FragmentSearchNextBinding


class BooksFragment : Fragment() {

    private val vm: BookViewModel by activityViewModels()
    private var _binding: FragmentBooksBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBooksBinding.inflate(inflater)
        binding.bookvm = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       // val bundle = BooksFragmentArgs.fromBundle(requireArguments())

        /*if(bundle == null){
            Log.d("BookFragment", "Bundle is null")
            return
        }*/

       // val category = bundle.category

       // Log.d("BookFragment", "Category is $category")

       /* binding.apply {
            vm.categories.observe(viewLifecycleOwner) { categories ->
                this.rvBook.layoutManager = LinearLayoutManager(context)
                this.rvBook.adapter = CategoryAdapter() { category ->
                   vm.setBook(category)
                }
                Log.e("BookFragment", categories.toString())
                (binding.rvBook.adapter as CategoryAdapter).submitList(categories)
            }
        }*/


        binding.apply {
            vm.books.observe(viewLifecycleOwner) { books ->
                this.rvBook.layoutManager = LinearLayoutManager(context)
                this.rvBook.adapter = BookAdapter() { book ->
                    vm.setBooks(book)
                }
                Log.e("BookFragment", books.toString())
                (binding.rvBook.adapter as BookAdapter).submitList(books)
            }
        }


    }


}