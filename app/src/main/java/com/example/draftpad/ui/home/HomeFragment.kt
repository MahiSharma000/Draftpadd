package com.example.draftpad.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.HorizontalScrollView
import androidx.appcompat.widget.Toolbar
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.AuthActivity
import com.example.draftpad.R
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentHomeBinding
import com.example.draftpad.ui.search.BookAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var toolbar: Toolbar? = null
    private lateinit var auth: FirebaseAuth
    private val vm: HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewmodel = vm
        binding.lifecycleOwner = viewLifecycleOwner
        val root: View = binding.root
        binding.toolbar.inflateMenu(R.menu.home_menu)
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.try_premium -> {
                    findNavController().navigate(R.id.action_navigation_home_to_premiumFragment)
                    true
                }
                R.id.user_profile -> {
                    findNavController().navigate(R.id.action_navigation_home_to_userProfileFragment)
                    true
                }
                R.id.settings -> {
                    findNavController().navigate(R.id.action_navigation_home_to_settingsFragment)
                    true
                }
                R.id.log_out -> {
                    Utils(requireContext()).logout()
                    startActivity(Intent(activity, AuthActivity::class.java))
                    activity?.finish()
                    true
                }
                else -> false
            }
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getBooks()
        vm.books.observe(viewLifecycleOwner) { books ->
            binding.rvhome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvhome.adapter = BookAdapter() { book ->
                Log.e("BooksFragment", book.toString())
                (binding.rvhome.adapter as BookAdapter).notifyDataSetChanged()
                val dir = HomeFragmentDirections.actionNavigationHomeToReadFragment(book.id)
                findNavController().navigate(dir)
            }
            Log.e("HomeFragment", books.toString())
            (binding.rvhome.adapter as BookAdapter).submitList(books)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}