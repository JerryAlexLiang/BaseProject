package com.liang.module_eyepetizer.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.liang.module_core.jetpack.MVVMBaseFragment
import com.liang.module_eyepetizer.R

/**
 * 创建日期:2020/9/15 on 7:00 PM
 * 描述: 开眼视频 - 推荐页
 * 作者: 杨亮
 */
class CommendFragment : MVVMBaseFragment() {

    override fun createViewLayoutId(): Int {
        return R.layout.fragment_commend
    }

    override fun initView(rootView: View?) {

    }

    override fun isRegisterEventBus(): Boolean {
        return false
    }

    override fun isSetRefreshHeader(): Boolean {
        return true
    }

    override fun isSetRefreshFooter(): Boolean {
        return true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    companion object {
        @JvmStatic
        fun newInstance() =
                CommendFragment()
    }
}