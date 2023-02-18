package com.example.draftpad.ui.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.draftpad.ui.library.ReadingListFragment

class SearchAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        if(position== 0){
            return BooksFragment()
        }
        else if (position == 1){
            return ProfileFragment()
        }
            return ReadingListFragment()
    }
}