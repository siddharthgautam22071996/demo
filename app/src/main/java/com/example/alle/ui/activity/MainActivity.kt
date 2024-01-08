package com.example.alle.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.alle.R
import com.example.alle.ui.fragment.DeleteFragment
import com.example.alle.ui.fragment.InfoFragment
import com.example.alle.ui.fragment.ShareFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val shareFragment = ShareFragment()
    private val infoFragment = InfoFragment()
    private val deleteFragment = DeleteFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_tab_activity)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        loadFragment(shareFragment)
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_fragment1 -> {
                    loadFragment(shareFragment)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_fragment2 -> {
                    loadFragment(infoFragment)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_fragment3 -> {
                    loadFragment(deleteFragment)
                    return@OnNavigationItemSelectedListener true
                }

                else -> false
            }
        }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}
