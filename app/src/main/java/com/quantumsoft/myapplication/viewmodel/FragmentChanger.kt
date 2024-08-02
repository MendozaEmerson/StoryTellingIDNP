package com.quantumsoft.myapplication.viewmodel

import androidx.fragment.app.Fragment

interface FragmentChanger {
    fun changeFragment(fragment: Fragment?)
    fun changeNavigationSelectedItem(itemId: Int)
}