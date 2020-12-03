package com.liang.module_eyepetizer.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.liang.module_core.jetpack.MVVMBaseActivity
import com.liang.module_core.utils.GsonUtils
import com.liang.module_core.utils.LogUtil
import com.liang.module_eyepetizer.R
import com.liang.module_eyepetizer.logic.model.test.BaseCustomViewModel
import com.liang.module_eyepetizer.logic.model.test.LocalJsonAdapter
import com.liang.module_eyepetizer.logic.model.test.Test
import kotlinx.android.synthetic.main.activity_test_local_data.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class TestLocalDataActivity : MVVMBaseActivity() {

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
        return R.layout.activity_test_local_data
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var viewModels: MutableList<BaseCustomViewModel> = mutableListOf()

        val json = jiexiJson()
        val str = json.json

        LogUtil.d("eye", "eye data local source:  $str")


        //这个Bean是json返回的实体类
        //List<Bean> shops = gson.fromJson(str, new TypeToken<List<Bean>>() {}.getType());
        val fromJson = Gson().fromJson<Test>(str, Test::class.java)
        val singleCardTwo = fromJson.singleCardTwo
        val singleCardOne = fromJson.singleCardOne
        val multiCard = fromJson.multiCard

//        val sort1 = singleCardTwo.sort
//        val sort2 = singleCardOne.sort
//        val sort3 = multiCard.sort

        if (multiCard != null) {
            viewModels.add(multiCard)
        }

        if (singleCardOne != null) {
            viewModels.add(singleCardOne)
        }

        if (singleCardTwo != null) {
            viewModels.add(singleCardTwo)
        }

        for (viewModel in viewModels) {
            if (viewModel is Test.MultiCardBean){
                viewModel.sortIndex = viewModel.sort
            }
            if (viewModel is Test.SingleCardOneBean){
                viewModel.sortIndex = viewModel.sort
            }
            if (viewModel is Test.SingleCardTwoBean){
                viewModel.sortIndex = viewModel.sort
            }
        }

        //按照sort大小排序（升序sortBy     倒序data.sortByDescending）
        viewModels.sortBy { it.sortIndex }

        LogUtil.d("eye", "eye data local :  " + GsonUtils.toJson(viewModels))

        val linearLayoutManager = LinearLayoutManager(this)
        rvLocalJson.layoutManager = linearLayoutManager
        val localJsonAdapter = LocalJsonAdapter()
        localJsonAdapter.addData(viewModels)
        rvLocalJson.adapter = localJsonAdapter

        LogUtil.d("eye", "eye data local2 :  " + GsonUtils.toJson(localJsonAdapter.data))

    }

    class jiexiJson {
        //获取assets资源管理器
        //将json数据变成字符串
        val json: String
            get() {
                //将json数据变成字符串
                val stringBuilder = StringBuilder()
                try {
                    //获取assets资源管理器
//                    val `is`: InputStream = this@jiexiJson.javaClass.classLoader!!.getResourceAsStream("assets/" + "testBean2.json")
                    val `is`: InputStream = this@jiexiJson.javaClass.classLoader!!.getResourceAsStream("assets/" + "testBean3.json")
                    val streamReader = InputStreamReader(`is`)
                    val bf = BufferedReader(streamReader)
                    var line: String?
                    while (bf.readLine().also { line = it } != null) {
                        stringBuilder.append(line)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                return stringBuilder.toString()
            }
    }


}