package com.liang.module_weather.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.liang.module_core_java.utils.ToastUtil
import com.liang.module_weather.R
import kotlinx.android.synthetic.main.fragment_place.*

/**
 * 创建日期:2020/8/26 on 5:21 PM
 * 描述: 城市搜索界面
 * 作者: 杨亮
 */
class PlaceFragment : Fragment() {

    //1、使用lazy寒暑表这种懒加载技术来获取PlaceViewModel的实例
    //这是一种非常棒的写法，允许我们在整个类中随时使用viewModel这个变量，而完全不用关心它何时初始化、是否为空等前提条件
    private val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //searchPlaceEdit.addTextChangedListener { editable ->
        //            val content = editable.toString()
        //            if (content.isNotEmpty()) {
        //                viewModel.searchPlaces(content)
        //            } else {
        //                recyclerView.visibility = View.GONE
        //                bgImageView.visibility = View.VISIBLE
        //                viewModel.placeList.clear()
        //                adapter.notifyDataSetChanged()
        //            }
        //        }
        //        viewModel.placeLiveData.observe(this, Observer{ result ->
        //            val places = result.getOrNull()
        //            if (places != null) {
        //                recyclerView.visibility = View.VISIBLE
        //                bgImageView.visibility = View.GONE
        //                viewModel.placeList.clear()
        //                viewModel.placeList.addAll(places)
        //                adapter.notifyDataSetChanged()
        //            } else {
        //                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
        //                result.exceptionOrNull()?.printStackTrace()
        //            }
        //        })


//        editSearchView.setOnSearchTextChangedListener { editable ->
//            val content = editable.toString()
//            if (content.isNotEmpty()) {
//                viewModel.searchPlaces(content)
//                ToastUtil.onShowTrueToast("搜索$content")
//            } else {
//
//
//            }
//        }

        editSearchView.setOnSearchClickListener {
            val content = editSearchView.text.toString().trim()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
                ToastUtil.onShowToast("搜索: $content")
            } else {


            }
        }

//        viewModel.placeLiveData.observe(this, Observer { result ->
//            val places = result.getOrNull()
//            if (places != null) {
//                viewModel.placeList.clear()
//                viewModel.placeList.addAll(places)
//                println("======>    qqqq  " + viewModel.placeList.toString())
//            }
//        })


//        viewModel.placeLiveData.observe(this) { result ->
//            val places = result.getOrNull()
//            if (places != null) {
//                viewModel.placeList.clear()
//                viewModel.placeList.addAll(places)
//                println("======>    qqqq  " + viewModel.placeList.toString())
//            }
//        }

        viewModel.placeLiveData.observe(this, Observer { result ->
            val places = result.getOrNull()
            if (places != null) {
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)


                println("======>    qqqq  " + viewModel.placeList.toString())

            } else {
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
                println("======>    qqqq2  " + viewModel.placeList.toString()+"未能查询到任何地点")
            }
        })

    }
}