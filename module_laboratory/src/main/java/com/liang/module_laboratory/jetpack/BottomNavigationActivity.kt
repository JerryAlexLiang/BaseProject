package com.liang.module_laboratory.jetpack

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.liang.module_core.jetpack.MVVMBaseActivity
import com.liang.module_laboratory.R
import kotlinx.android.synthetic.main.activity_bottom_navigation.*

class BottomNavigationActivity : MVVMBaseActivity() {

    private lateinit var mNavController: NavController

    override fun isRegisterEventBus(): Boolean {
        return false
    }

    override fun isSetRefreshHeader(): Boolean {
        return false
    }

    override fun isSetRefreshFooter(): Boolean {
        return false
    }

    override fun provideContentViewId(): Int {
        return R.layout.activity_bottom_navigation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //绑定
//        val navHostFragmentBottom: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragmentBottom.navController
//        bottomNavigationView.setupWithNavController(navController)

//        NavigationUI.setupWithNavController(bottomNavigationView,findNavController(R.id.nav_host_fragment))

        mNavController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(mNavController)

        //根据当前Fragment id 设置 BottomNavigationView的显示隐藏
        mNavController.addOnDestinationChangedListener { _, destination, _ ->
            run {
                val id = destination.id
                if (id == R.id.bottom_navigation_one
                        || id == R.id.bottom_navigation_two
                        || id == R.id.bottom_navigation_three) {
                    bottomNavigationView.visibility = View.VISIBLE
                } else {
                    bottomNavigationView.visibility = View.GONE
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp()
    }

}