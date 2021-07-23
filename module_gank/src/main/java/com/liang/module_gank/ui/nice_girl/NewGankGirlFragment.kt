package com.liang.module_gank.ui.nice_girl

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.liang.module_core.jetpack.MVVMBaseFragment
import com.liang.module_core.utils.GsonUtils
import com.liang.module_core.utils.LogUtil
import com.liang.module_gank.R

/**
 * 创建日期:2021/7/23 on 4:50 PM
 * 描述: Gank新版api 美女颜如玉
 * 作者: 杨亮
 */
class NewGankGirlFragment : MVVMBaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = NewGankGirlFragment()
    }

    private lateinit var viewModel: NewGankGirlViewModel

    override fun createViewLayoutId(): Int {
        return R.layout.new_gank_girl_fragment
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
        viewModel = ViewModelProvider(this).get(NewGankGirlViewModel::class.java)

        viewModel.getNiceGankGirlData(1)

        observe()

    }

    //viewModel.placeLiveData.observe(this) { result ->
    //            val places = result.getOrNull()
    //            if (places != null) {
    //                rvCityPlaces.visibility = View.VISIBLE
    //                viewModel.placeList.clear()
    //                viewModel.placeList.addAll(places)
    //                placeAdapter.notifyDataSetChanged()
    //                ToastUtil.onShowTrueToast(context, "查找到${places.size}条数据")
    //                LogUtil.d(TAG, viewModel.placeList.toString())
    //            } else {
    //                ToastUtil.onShowErrorToast(context, "未能查询到任何地点")
    //            }
    //        }
    private fun observe() {
        viewModel.niceGankGirlBeanLiveData.observe(viewLifecycleOwner) { result ->
            val girls = result.getOrNull()
            if (girls != null) {
                viewModel.girlsList.clear()
                viewModel.girlsList.addAll(girls)

                LogUtil.d("nice", "data: " + GsonUtils.toJson(viewModel.girlsList))
            }

        }
    }

}