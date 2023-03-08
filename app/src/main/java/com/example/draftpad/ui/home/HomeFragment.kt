package com.example.draftpad.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.draftpad.AuthActivity
import com.example.draftpad.MainActivity
import com.example.draftpad.R
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var toolbar: Toolbar? = null
    private lateinit var auth: FirebaseAuth

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
                R.id.content_preferences -> {
                    findNavController().navigate(R.id.action_navigation_home_to_contentPreferenceFragment)
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




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}