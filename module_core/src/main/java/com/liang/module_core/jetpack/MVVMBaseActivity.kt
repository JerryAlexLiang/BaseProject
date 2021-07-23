package com.liang.module_core.jetpack

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.liang.module_core.R
import com.liang.module_core.app.BaseApplication
import com.liang.module_core.constant.Constant
import com.liang.module_core.utils.SPUtils
import com.liang.module_core.utils.ToastUtil
import com.liang.module_core.widget.CustomProgressDialog
import com.liang.module_core.widget.refreshWidget.MyRefreshLottieHeader
import com.scwang.smartrefresh.header.*
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.BezierRadarHeader
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.header.TwoLevelHeader
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * 创建日期:2020/9/15 on 9:52 AM
 * 描述: MVVMBaseActivity - 通用标题栏模式
 * 作者: 杨亮
 */
abstract class MVVMBaseActivity : AppCompatActivity() {

    private var mRefreshLayout: SwipeRefreshLayout? = null
    private var mIsRequestDataRefresh = false
    var mActivity: Activity? = null
    private var customProgressDialog: CustomProgressDialog? = null

    private var lottieFileName = ""
    private var myRefreshLottieHeader: MyRefreshLottieHeader? = null

    /**
     * 是否注册事件分发，默认不绑定
     */
    protected abstract fun isRegisterEventBus(): Boolean
    protected abstract fun isSetRefreshHeader(): Boolean
    protected abstract fun isSetRefreshFooter(): Boolean

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        mActivity = this
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        mActivity = this
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        super.setContentView(view, params)
        mActivity = this
    }

    /**
     * 判断子Activity是否需要刷新功能
     *
     * @return false
     */
    private val isSetRefresh: Boolean
        get() = false

    protected abstract fun provideContentViewId(): Int //用于引入布局文件

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //全透明状态栏(不带阴影)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            //            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        } else {
            //半透明状态栏(带阴影)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this)
        }

        //引入布局文件
        setContentView(provideContentViewId()) //布局

        //本地化存储操作(取出) - 设置状态栏颜色
        val baseActionBar = findViewById<FrameLayout>(R.id.base_actionbar)
        if (baseActionBar != null) {
            getActionBarTheme(baseActionBar, null)
        }
        val baseToolBar = findViewById<CollapsingToolbarLayout>(R.id.collapsing_layout)
        if (baseToolBar != null) {
            getActionBarTheme(null, baseToolBar)
        }
        val splashRelativeLayout = findViewById<RelativeLayout>(R.id.splash_container_layout)
        splashRelativeLayout?.let { getSplashTheme(it) }

        //初始化Header
        myRefreshLottieHeader = MyRefreshLottieHeader(BaseApplication.getAppContext())

        val smartRefreshLayout = findViewById<SmartRefreshLayout>(R.id.smart_refresh_layout)
        smartRefreshLayout?.let { getSmartRefreshPrimaryColorsTheme(it, isSetRefreshHeader(), isSetRefreshFooter()) }
        if (isSetRefresh) {
            //判断子Activity是否需要刷新功能
            setupSwipeRefresh()
        }
    }

    /**
     * 判断子Activity是否需要刷新功能
     */
    private fun setupSwipeRefresh() {
        mRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        if (mRefreshLayout != null) {
            //设置下拉刷新进度条颜色变化
            mRefreshLayout!!.setColorSchemeResources(R.color.refresh_progress_1, R.color.refresh_progress_2,
                    R.color.refresh_progress_3)
            //第一个参数scale是刷新圆形进度是否缩放,true表示缩放,圆形进度图会从小到大展示出来,为false就不缩放
            //第二个参数start和end是刷新进度条展示的相对于默认的展示位置,start和end组成一个范围，在这个y轴范围就是那个圆形进度ProgressView展示的位置
            mRefreshLayout!!.setProgressViewOffset(true, 0, TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24f, resources?.displayMetrics).toInt())
            mRefreshLayout!!.setOnRefreshListener { requestDataRefresh() }
        }
    }

    private fun requestDataRefresh() {
        mIsRequestDataRefresh = true
    }

    /**
     * 刷新数据
     */
    fun setRefresh(requestDataRefresh: Boolean) {
        if (mRefreshLayout == null) {
            return
        }
        if (!requestDataRefresh) {
            //不刷新
            mIsRequestDataRefresh = false
            mRefreshLayout!!.postDelayed({
                if (mRefreshLayout != null) {
                    mRefreshLayout!!.isRefreshing = false
                }
            }, 1000)
        } else {
            //刷新效果
            mRefreshLayout!!.isRefreshing = true
        }
    }

    /**
     * 本地化存储操作(存入) - 设置状态栏颜色
     *
     * @param ACTIONBAR_COLOR
     */
    fun setActionBarTheme(ACTIONBAR_COLOR: Int) {
        SPUtils.put(this@MVVMBaseActivity, Constant.ACTIONBAR_COLOR_THEME, ACTIONBAR_COLOR)
    }

    /**
     * 本地化存储操作(取出) - 设置状态栏颜色
     * //toolbar_layout  CollapsingToolbarLayout  app:contentScrim="?attr/colorPrimary"  app:statusBarScrim="?attr/colorPrimary"
     */
    fun getActionBarTheme(baseActionBar: FrameLayout?, baseCollapsingToolBar: CollapsingToolbarLayout?) {
        val actionBarColorInt = SPUtils.get(this@MVVMBaseActivity, Constant.ACTIONBAR_COLOR_THEME, 0) as Int
        Log.d(TAG, "setActionBarTheme: $actionBarColorInt")
        when (actionBarColorInt) {
            Constant.ACTIONBAR_COLOR_BLUE -> {
                resources?.let { baseActionBar?.setBackgroundColor(it.getColor(R.color.title_bar_blue)) }
                if (baseCollapsingToolBar != null) {
                    resources?.let { baseCollapsingToolBar.setContentScrimColor(it.getColor(R.color.title_bar_blue)) }
                    resources?.let { baseCollapsingToolBar.setStatusBarScrimColor(it.getColor(R.color.title_bar_blue)) }
                }
            }
            Constant.ACTIONBAR_COLOR_RED -> {
                resources?.let { baseActionBar?.setBackgroundColor(it.getColor(R.color.title_bar_red)) }
                if (baseCollapsingToolBar != null) {
                    resources?.let { baseCollapsingToolBar.setContentScrimColor(it.getColor(R.color.title_bar_red)) }
                    resources?.let { baseCollapsingToolBar.setStatusBarScrimColor(it.getColor(R.color.title_bar_red)) }
                }
            }
            Constant.ACTIONBAR_COLOR_BLACK -> {
                baseActionBar?.setBackgroundColor(Color.BLACK)
                if (baseCollapsingToolBar != null) {
                    baseCollapsingToolBar.setContentScrimColor(Color.BLACK)
                    baseCollapsingToolBar.setStatusBarScrimColor(Color.BLACK)
                }
            }
            Constant.ACTIONBAR_COLOR_WHITE -> {
                baseActionBar?.setBackgroundColor(Color.WHITE)
                if (baseCollapsingToolBar != null) {
                    baseCollapsingToolBar.setContentScrimColor(Color.WHITE)
                    baseCollapsingToolBar.setStatusBarScrimColor(Color.WHITE)
                }
            }
            Constant.ACTIONBAR_COLOR_TRANSLATE -> {
                baseActionBar?.setBackgroundColor(Color.TRANSPARENT)
                if (baseCollapsingToolBar != null) {
                    baseCollapsingToolBar.setContentScrimColor(Color.TRANSPARENT)
                    baseCollapsingToolBar.setStatusBarScrimColor(Color.TRANSPARENT)
                }
            }
            Constant.ACTIONBAR_COLOR_GREEN -> {
                resources?.let { baseActionBar?.setBackgroundColor(it.getColor(R.color.title_bar_green)) }
                if (baseCollapsingToolBar != null) {
                    resources?.let { baseCollapsingToolBar.setContentScrimColor(it.getColor(R.color.title_bar_green)) }
                    resources?.let { baseCollapsingToolBar.setStatusBarScrimColor(it.getColor(R.color.title_bar_green)) }
                }
            }
        }
    }

    private fun getSplashTheme(relativeLayout: RelativeLayout?) {
        val actionBarColorInt = SPUtils.get(this@MVVMBaseActivity, Constant.ACTIONBAR_COLOR_THEME, 0) as Int
        Log.d(TAG, "setActionBarTheme: $actionBarColorInt")
        when (actionBarColorInt) {
            Constant.ACTIONBAR_COLOR_BLUE -> resources?.let { relativeLayout?.setBackgroundColor(it.getColor(R.color.title_bar_blue)) }
            Constant.ACTIONBAR_COLOR_RED -> resources?.let { relativeLayout?.setBackgroundColor(it.getColor(R.color.title_bar_red)) }
            Constant.ACTIONBAR_COLOR_BLACK -> relativeLayout?.setBackgroundColor(Color.BLACK)
            Constant.ACTIONBAR_COLOR_WHITE -> relativeLayout?.setBackgroundColor(Color.WHITE)
            Constant.ACTIONBAR_COLOR_TRANSLATE -> relativeLayout?.setBackgroundColor(Color.TRANSPARENT)
            Constant.ACTIONBAR_COLOR_GREEN -> resources?.let { relativeLayout?.setBackgroundColor(it.getColor(R.color.title_bar_green)) }
        }
    }

    private fun getSmartRefreshPrimaryColorsTheme(smartRefreshLayout: SmartRefreshLayout?, isSetRefreshHeader: Boolean, isSetRefreshFooter: Boolean) {
        val actionBarColorInt = SPUtils.get(this@MVVMBaseActivity, Constant.ACTIONBAR_COLOR_THEME, 0) as Int
        val refreshHeaderStyle = SPUtils.get(BaseApplication.getAppContext(), Constant.REFRESH_HEADER_STYLE, Constant.REFRESH_HEADER_34115_ROCKET_LUNCH) as String

        if (smartRefreshLayout != null) {
            if (isSetRefreshHeader) {
                setRefreshHeader(smartRefreshLayout, refreshHeaderStyle)
            }

            if (isSetRefreshFooter) {
                //上滑加载更多三点渐变动画底部View
//                smartRefreshLayout.setRefreshFooter(new BallPulseFooter(MyApplication.getAppContext()).setSpinnerStyle(SpinnerStyle.Scale));
                smartRefreshLayout.setRefreshFooter(ClassicsFooter(BaseApplication.getAppContext()))
            }
        }
        when (actionBarColorInt) {
            Constant.ACTIONBAR_COLOR_BLUE -> smartRefreshLayout?.setPrimaryColorsId(R.color.colorBlue, R.color.white)
            Constant.ACTIONBAR_COLOR_RED -> smartRefreshLayout?.setPrimaryColorsId(R.color.accent, R.color.white)
            Constant.ACTIONBAR_COLOR_BLACK, Constant.ACTIONBAR_COLOR_WHITE -> smartRefreshLayout?.setPrimaryColorsId(R.color.black, R.color.white)
            Constant.ACTIONBAR_COLOR_TRANSLATE -> smartRefreshLayout?.setPrimaryColorsId(R.color.transparent, R.color.white)
            Constant.ACTIONBAR_COLOR_GREEN -> smartRefreshLayout?.setPrimaryColorsId(R.color.assist, R.color.white)
        }
    }

    /**
     * 替换RefreshHeader
     */
    open fun setRefreshHeader(smartRefreshLayout: SmartRefreshLayout, refreshHeaderStyle: String?) {
        when (refreshHeaderStyle) {
            Constant.REFRESH_HEADER_34115_ROCKET_LUNCH -> {
                lottieFileName = "lottie/34115-rocket-lunch.json"
                myRefreshLottieHeader!!.setAnimationViewJson(lottieFileName)
                setLottieRefreshHeader(smartRefreshLayout)
            }

            Constant.REFRESH_HEADER_28402_TEMPLO -> {
                lottieFileName = "lottie/28402-templo.json"
                myRefreshLottieHeader!!.setAnimationViewJson(lottieFileName)
                setLottieRefreshHeader(smartRefreshLayout)
            }

            Constant.MaterialHeader -> smartRefreshLayout.setRefreshHeader(MaterialHeader(BaseApplication.getAppContext()))

            Constant.WaterDropHeader -> smartRefreshLayout.setRefreshHeader(WaterDropHeader(BaseApplication.getAppContext()))

            Constant.WaveSwipeHeader -> smartRefreshLayout.setRefreshHeader(WaveSwipeHeader(BaseApplication.getAppContext()))

            Constant.BezierCircleHeader -> smartRefreshLayout.setRefreshHeader(BezierCircleHeader(BaseApplication.getAppContext()))

            Constant.PhoenixHeader -> smartRefreshLayout.setRefreshHeader(PhoenixHeader(BaseApplication.getAppContext()))

            Constant.TaurusHeader -> smartRefreshLayout.setRefreshHeader(TaurusHeader(BaseApplication.getAppContext()))

            Constant.FlyRefreshHeader -> smartRefreshLayout.setRefreshHeader(FlyRefreshHeader(BaseApplication.getAppContext()))

            Constant.ClassicsHeader -> smartRefreshLayout.setRefreshHeader(ClassicsHeader(BaseApplication.getAppContext()))

            Constant.BezierRadarHeader -> smartRefreshLayout.setRefreshHeader(BezierRadarHeader(BaseApplication.getAppContext()))

            Constant.DeliveryHeader -> smartRefreshLayout.setRefreshHeader(DeliveryHeader(BaseApplication.getAppContext()))

            Constant.DropBoxHeader -> smartRefreshLayout.setRefreshHeader(DropBoxHeader(BaseApplication.getAppContext()))

            Constant.FunGameBattleCityHeader -> smartRefreshLayout.setRefreshHeader(FunGameBattleCityHeader(BaseApplication.getAppContext()))

            Constant.FunGameHitBlockHeader -> smartRefreshLayout.setRefreshHeader(FunGameHitBlockHeader(BaseApplication.getAppContext()))

            Constant.StoreHouseHeader -> smartRefreshLayout.setRefreshHeader(StoreHouseHeader(BaseApplication.getAppContext()))

            Constant.TwoLevelHeader -> smartRefreshLayout.setRefreshHeader(TwoLevelHeader(BaseApplication.getAppContext()))
        }
    }

    /**
     * 设置自定义RefreshHeader
     */
    private fun setLottieRefreshHeader(smartRefreshLayout: SmartRefreshLayout) {
        smartRefreshLayout.setHeaderMaxDragRate(2f)
        smartRefreshLayout.setRefreshHeader(myRefreshLottieHeader!!)
    }

    /**
     * 全局替换RefreshHeader
     */
    open fun changeRefreshHeaderStyle(refreshHeaderStyle: String?) {
        when (refreshHeaderStyle) {
            Constant.REFRESH_HEADER_34115_ROCKET_LUNCH -> SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.REFRESH_HEADER_34115_ROCKET_LUNCH)

            Constant.REFRESH_HEADER_28402_TEMPLO -> SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.REFRESH_HEADER_28402_TEMPLO)

            Constant.MaterialHeader -> SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.MaterialHeader)

            Constant.WaterDropHeader -> SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.WaterDropHeader)

            Constant.WaveSwipeHeader -> SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.WaveSwipeHeader)

            Constant.BezierCircleHeader ->
                //水滴下拉
                SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.BezierCircleHeader)

            Constant.PhoenixHeader ->
                //大楼动画
                SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.PhoenixHeader)

            Constant.TaurusHeader ->
                //飞机滑翔动画效果
                SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.TaurusHeader)

            Constant.FlyRefreshHeader ->
                //飞机效果2
                SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.FlyRefreshHeader)

            Constant.ClassicsHeader ->
                //经典带时间的刷新
                SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.ClassicsHeader)

            Constant.BezierRadarHeader ->
                //雷达动画
                SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.BezierRadarHeader)

            Constant.DeliveryHeader ->
                //快递交付动画
                SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.DeliveryHeader)

            Constant.DropBoxHeader ->
                //礼物盒子动画效果
                SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.DropBoxHeader)

            Constant.FalsifyHeader ->
                //无动画效果
                SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.FalsifyHeader)

            Constant.FunGameBattleCityHeader ->
                //子弹游戏效果
                SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.FunGameBattleCityHeader)

            Constant.FunGameHitBlockHeader ->
                //碰球游戏效果
                SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.FunGameHitBlockHeader)

            Constant.StoreHouseHeader ->
                //StoreHouse文字渐变效果
                SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.StoreHouseHeader)

            Constant.TwoLevelHeader ->
                //二楼
                SPUtils.put(this@MVVMBaseActivity, Constant.REFRESH_HEADER_STYLE, Constant.TwoLevelHeader)
        }
    }

    /**
     * google官方在安卓6.0以上版本才推出的深色状态栏字体api
     * <item name="android:windowLightStatusBar">true</item>
     */
    fun changeStatusBarTextColor(isBlack: Boolean) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (isBlack) {
                //设置状态栏黑色字体
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                //恢复状态栏白色字体
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
        }
    }

    /**
     * 管理所有的activity资源对象  添加Activity
     */
    fun addActivity(baseActivity: MVVMBaseActivity, target: Class<out Activity?>) {
        if (activities == null) {
            activities = ArrayList()
        }
        activities!!.add(baseActivity)
        Log.d(TAG, "addActivity:   $target")
    }

    fun finishAll() {
        for (activity in activities!!) {
            activity?.finish()
        }
    }

    fun finishPreAll() {
        for (i in 0 until activities!!.size - 2) {
            activities!![i].finish()
        }
    }

    fun startActivity(target: Class<out Activity?>?, bundle: Bundle?, finish: Boolean) {
        val intent = Intent()
        intent.setClass(this, target!!)
        if (bundle != null) {
            intent.putExtra(packageName, bundle)
        }
        startActivity(intent)
        if (finish) {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }

    fun showProgressDialog(content: String?, cancelable: Boolean) {
        if (customProgressDialog == null) {
            customProgressDialog = CustomProgressDialog(mActivity, content, cancelable)
        }
        if (!customProgressDialog!!.isShow) {
            customProgressDialog!!.show()
        }
    }

    fun hideProgressDialog() {
        if (customProgressDialog != null && customProgressDialog!!.isShow) {
            customProgressDialog!!.dismiss()
        }
    }

    fun onShowToast(content: String?) {
        ToastUtil.setCustomToast(BaseApplication.getAppContext(), BitmapFactory.decodeResource(resources, R.drawable.core_icon_true),
                false, content, ContextCompat.getColor(BaseApplication.getAppContext(), R.color.toast_bg),
                ContextCompat.getColor(BaseApplication.getAppContext(), R.color.text_invert), Gravity.CENTER, Toast.LENGTH_SHORT)
    }

    fun onShowTrueToast(content: String?) {
        ToastUtil.setCustomToast(BaseApplication.getAppContext(), BitmapFactory.decodeResource(resources, R.drawable.core_icon_true),
                true, content, ContextCompat.getColor(BaseApplication.getAppContext(), R.color.toast_bg),
                ContextCompat.getColor(BaseApplication.getAppContext(), R.color.text_invert), Gravity.CENTER, Toast.LENGTH_SHORT)
    }

    fun onShowErrorToast(content: String?) {
        ToastUtil.setCustomToast(BaseApplication.getAppContext(), BitmapFactory.decodeResource(resources, R.drawable.core_icon_wrong),
                true, content, ContextCompat.getColor(BaseApplication.getAppContext(), R.color.toast_bg),
                ContextCompat.getColor(BaseApplication.getAppContext(), R.color.text_invert), Gravity.CENTER, Toast.LENGTH_SHORT)
    }

    fun jumpToActivity(intent: Intent?) {
        startActivity(intent)
    }

    fun jumpToActivity(activity: Class<*>?) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    fun jumpToActivity(activity: Class<*>?, requestCode: Int) {
        val intent = Intent(this, activity)
        startActivityForResult(intent, requestCode)
    }

    fun jumpToActivity(intent: Intent?, requestCode: Int) {
        startActivityForResult(intent, requestCode)
    }

    fun fullScreen(enable: Boolean) {
        if (enable) {
            if (Build.VERSION.SDK_INT >= 19) {
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            }
        } else {
            if (Build.VERSION.SDK_INT >= 19) {
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            }
        }
    }

    companion object {
        private val TAG = MVVMBaseActivity::class.java.simpleName

        /**
         * 管理所有的activity资源对象
         */
        private var activities: MutableList<MVVMBaseActivity>? = null
    }

    open fun replaceFragment(newFragment: Fragment) {
        replaceFragment(newFragment, null, false)
    }

    open fun replaceFragment(newFragment: Fragment, arguments: Bundle?, isAddStack: Boolean) {
        if (isFinishing) {
            return
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom)
        if (arguments != null) {
            newFragment.arguments = arguments
        }
        transaction.replace(R.id.fragment_container, newFragment)
        if (isAddStack) {
            transaction.addToBackStack(null)
        }
        if (!isDestroyed) {
            transaction.commitAllowingStateLoss()
        }
    }

    /**
     * 禁止跟随系统字体大小调节
     */
    override fun getResources(): Resources? {
        //还原字体大小
        val res = super.getResources()
        val config = Configuration()
        config.setToDefaults()
        res.updateConfiguration(config, res.displayMetrics)
        return res
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        if (newConfig.fontScale != 1f) {
            //非默认值
            resources
        }
        super.onConfigurationChanged(newConfig)
    }
}