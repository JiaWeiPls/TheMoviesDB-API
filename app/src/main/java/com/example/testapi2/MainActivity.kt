package com.example.testapi2

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.example.testapi2.ui.details.SingleMovie
import com.example.testapi2.ui.main.ViewPagerAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpTabs()

//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }


    }

    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Fragment1(), resources.getString(R.string.tab_text_1))
        adapter.addFragment(Fragment1(), resources.getString(R.string.tab_text_2))
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = adapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
//        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_local_movies_24)
//        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_favorite_24)
    }
}