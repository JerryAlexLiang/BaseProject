package com.liang.module_eyepetizer.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.liang.module_core.app.BaseApplication
import com.liang.module_core.jetpack.MVVMBaseActivity
import com.liang.module_core.utils.GsonUtils
import com.liang.module_core.utils.LogUtil
import com.liang.module_eyepetizer.R
import com.liang.module_eyepetizer.diffUtil.CustomDiffCallBack2
import com.liang.module_eyepetizer.logic.model.test.BaseCustomViewModel
import com.liang.module_eyepetizer.logic.model.test.LocalJsonAdapter
import com.liang.module_eyepetizer.logic.model.test.Test
import com.scwang.smartrefresh.header.MaterialHeader
import kotlinx.android.synthetic.main.activity_test_local_data.*
import kotlinx.android.synthetic.main.eye_layout_web_error.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class TestLocalDataActivity : MVVMBaseActivity() {

    private lateinit var localJsonAdapter: LocalJsonAdapter
    private var viewModels: MutableList<BaseCustomViewModel> = mutableListOf()
    private var viewModelsNew: MutableList<BaseCustomViewModel> = mutableListOf()

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

        val linearLayoutManager = LinearLayoutManager(this)
        rvLocalJson.layoutManager = linearLayoutManager
        localJsonAdapter = LocalJsonAdapter()

        initData()

        smartRefreshLayout.setPrimaryColorsId(com.liang.module_core.R.color.colorBlue, com.liang.module_core.R.color.white)
        //下拉刷新沉浸式水滴头部View
        smartRefreshLayout.setRefreshHeader(MaterialHeader(BaseApplication.getAppContext())) //经典Swip


        smartRefreshLayout.setOnRefreshListener {
            initData2()
        }


    }

    private fun initData2() {
        //http://ww1.sinaimg.cn/large/0065oQSqly1fszxi9lmmzj30f00jdadv.jpg
//        viewModelsNew.clear()

        viewModelsNew.clear()

        val json = jiexiJson2()
        val str = json.json

        smartRefreshLayout.finishRefresh()

        LogUtil.d("eye", "eye data local source*****:  $str")

        //这个Bean是json返回的实体类
        //List<Bean> shops = gson.fromJson(str, new TypeToken<List<Bean>>() {}.getType());
        val fromJson = Gson().fromJson<Test>(str, Test::class.java)
        val singleCardTwo = fromJson.singleCardTwo
        val singleCardOne = fromJson.singleCardOne
        val multiCard = fromJson.multiCard

        if (multiCard != null) {
            viewModelsNew.add(multiCard)
        }

        if (singleCardOne != null) {
            viewModelsNew.add(singleCardOne)
        }

        if (singleCardTwo != null) {
            viewModelsNew.add(singleCardTwo)
        }

        for (viewModel in viewModelsNew) {
            if (viewModel is Test.MultiCardBean) {
                viewModel.sortIndex = viewModel.sort
            }
            if (viewModel is Test.SingleCardOneBean) {
                viewModel.sortIndex = viewModel.sort
            }
            if (viewModel is Test.SingleCardTwoBean) {
                viewModel.sortIndex = viewModel.sort
            }
        }

        //按照sort大小排序（升序sortBy     倒序data.sortByDescending）
        viewModelsNew.sortBy { it.sortIndex }

        LogUtil.d("eye", "eye data local***** :  " + GsonUtils.toJson(viewModelsNew))

//        ////////////////////////////////原生方法//////////////////////////////////////////////
//        //利用DiffUtil.calculateDiff()方法，传入一个规则DiffUtil.Callback对象，和是否检测移动item的 boolean变量，得到DiffUtil.DiffResult 的对象
//        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(CustomDiffCallBack(viewModels, viewModelsNew), true)
//        //利用DiffUtil.DiffResult对象的dispatchUpdatesTo()方法，传入RecyclerView的Adapter
//        diffResult.dispatchUpdatesTo(localJsonAdapter);
//
//        localJsonAdapter.setDiffNewData(diffResult, viewModelsNew)
//
//        ////////////////////////////////原生方法//////////////////////////////////////////////


        ////////////////////////////////BaseQuickAdapter方法//////////////////////////////////////////////
        // 必须设置Diff Callback
        localJsonAdapter.setDiffCallback(CustomDiffCallBack2())
        localJsonAdapter.setDiffNewData(viewModelsNew)


        ////////////////////////////////BaseQuickAdapter方法//////////////////////////////////////////////

        LogUtil.d("eye", "eye data local2***** :  " + GsonUtils.toJson(localJsonAdapter.data))
    }

    private fun initData() {
        val json = jiexiJson()
        val str = json.json

        LogUtil.d("eye", "eye data local source:  $str")

        //这个Bean是json返回的实体类
        //List<Bean> shops = gson.fromJson(str, new TypeToken<List<Bean>>() {}.getType());
        val fromJson = Gson().fromJson<Test>(str, Test::class.java)
        val singleCardTwo = fromJson.singleCardTwo
        val singleCardOne = fromJson.singleCardOne
        val multiCard = fromJson.multiCard

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
            if (viewModel is Test.MultiCardBean) {
                viewModel.sortIndex = viewModel.sort
            }
            if (viewModel is Test.SingleCardOneBean) {
                viewModel.sortIndex = viewModel.sort
            }
            if (viewModel is Test.SingleCardTwoBean) {
                viewModel.sortIndex = viewModel.sort
            }
        }

        //按照sort大小排序（升序sortBy     倒序data.sortByDescending）
        viewModels.sortBy { it.sortIndex }

        LogUtil.d("eye", "eye data local :  " + GsonUtils.toJson(viewModels))

        localJsonAdapter.addData(viewModels)
        rvLocalJson.adapter = localJsonAdapter
//        // 必须设置Diff Callback
//        localJsonAdapter.setDiffCallback(CustomDiffCallBack2())
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

    class jiexiJson2 {
        //获取assets资源管理器
        //将json数据变成字符串
        val json: String
            get() {
                //将json数据变成字符串
                val stringBuilder = StringBuilder()
                try {
                    //获取assets资源管理器
//                    val `is`: InputStream = this@jiexiJson.javaClass.classLoader!!.getResourceAsStream("assets/" + "testBean2.json")
                    val `is`: InputStream = this@jiexiJson2.javaClass.classLoader!!.getResourceAsStream("assets/" + "testBean5.json")
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