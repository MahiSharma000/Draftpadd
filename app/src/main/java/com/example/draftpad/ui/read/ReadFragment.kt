package com.example.draftpad.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.draftpad.databinding.FragmentReadBinding


class ReadFragment : Fragment() {

    private val vm: ReadViewModel by activityViewModels()
    private var _binding: FragmentReadBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReadBinding.inflate(inflater)
        binding.readVm = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ReadFragmentArgs.fromBundle(requireArguments()).bookId.let {
            vm.setBookId(it)
        }
        vm.bookId.observe(viewLifecycleOwner) {
            vm.getSelectedBook()
        }
        vm.book.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(context, "l${it}", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRead.setOnClickListener {
            val action =
                ReadFragmentDirections.actionReadFragmentToChapterFragment(vm.book.value?.id ?: 1)
            findNavController().navigate(action)
        }

    }

}
