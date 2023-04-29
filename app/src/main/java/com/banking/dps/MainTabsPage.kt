package com.banking.dps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainTabsPage : Fragment() {

    private lateinit var bottomNavView: BottomNavigationView
    lateinit var navController: NavController
    private lateinit var navHostFragmentMainTabsPage: FragmentContainerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_tabs_page, container, false)

        navHostFragmentMainTabsPage = view.findViewById(R.id.navHostFragmentMainTabsPage)
        navController = findNavController()
        bottomNavView = view.findViewById(R.id.bottomNavView)
        bottomNavView.setOnItemSelectedListener { item ->
            var fragment: Fragment? = null
            when(item.itemId){
                R.id.homePage -> fragment = HomePage()
                R.id.historyPage -> fragment = HistoryPage()
                R.id.dialUssdPage -> fragment = DialUssdPage()
            }
            fragment?.let {
                childFragmentManager.beginTransaction().replace(R.id.navHostFragmentMainTabsPage, it).commit()
            }
            true
        }
        return view
    }
}