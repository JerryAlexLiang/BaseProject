package com.liang.module_laboratory

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.NavigatorProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.liang.module_core.jetpack.MVVMBaseActivity
import kotlinx.android.synthetic.main.activity_bottom_navigation.*

class BottomNavigationActivity : MVVMBaseActivity() {

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

        bottomNavigationView.setupWithNavController(findNavController(R.id.nav_host_fragment))

    }
}