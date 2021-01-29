package com.liang.module_core.jetpack

import android.app.Activity
import android.content.Intent
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
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
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
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24f, resources.displayMetrics).toInt())
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
                baseActionBar?.setBackgroundColor(resources.getColor(R.color.title_bar_blue))
                if (baseCollapsingToolBar != null) {
                    baseCollapsingToolBar.setContentScrimColor(resources.getColor(R.color.title_bar_blue))
                    baseCollapsingToolBar.setStatusBarScrimColor(resources.getColor(R.color.title_bar_blue))
                }
            }
            Constant.ACTIONBAR_COLOR_RED -> {
                baseActionBar?.setBackgroundColor(resources.getColor(R.color.title_bar_red))
                if (baseCollapsingToolBar != null) {
                    baseCollapsingToolBar.setContentScrimColor(resources.getColor(R.color.title_bar_red))
                    baseCollapsingToolBar.setStatusBarScrimColor(resources.getColor(R.color.title_bar_red))
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
                baseActionBar?.setBackgroundColor(resources.getColor(R.color.title_bar_green))
                if (baseCollapsingToolBar != null) {
                    baseCollapsingToolBar.setContentScrimColor(resources.getColor(R.color.title_bar_green))
                    baseCollapsingToolBar.setStatusBarScrimColor(resources.getColor(R.color.title_bar_green))
                }
            }
        }
    }

    private fun getSplashTheme(relativeLayout: RelativeLayout?) {
        val actionBarColorInt = SPUtils.get(this@MVVMBaseActivity, Constant.ACTIONBAR_COLOR_THEME, 0) as Int
        Log.d(TAG, "setActionBarTheme: $actionBarColorInt")
        when (actionBarColorInt) {
            Constant.ACTIONBAR_COLOR_BLUE -> relativeLayout?.setBackgroundColor(resources.getColor(R.color.title_bar_blue))
            Constant.ACTIONBAR_COLOR_RED -> relativeLayout?.setBackgroundColor(resources.getColor(R.color.title_bar_red))
            Constant.ACTIONBAR_COLOR_BLACK -> relativeLayout?.setBackgroundColor(Color.BLACK)
            Constant.ACTIONBAR_COLOR_WHITE -> relativeLayout?.setBackgroundColor(Color.WHITE)
            Constant.ACTIONBAR_COLOR_TRANSLATE -> relativeLayout?.setBackgroundColor(Color.TRANSPARENT)
            Constant.ACTIONBAR_COLOR_GREEN -> relativeLayout?.setBackgroundColor(resources.getColor(R.color.title_bar_green))
        }
    }

    private fun getSmartRefreshPrimaryColorsTheme(smartRefreshLayout: SmartRefreshLayout?, isSetRefreshHeader: Boolean, isSetRefreshFooter: Boolean) {
        val actionBarColorInt = SPUtils.get(this@MVVMBaseActivity, Constant.ACTIONBAR_COLOR_THEME, 0) as Int
        Log.d(TAG, "setActionBarTheme: $actionBarColorInt")

//        smartRefreshLayout.setRefreshHeader(new MaterialHeader(MyApplication.getAppContext())); //经典Swip
//        smartRefreshLayout.setRefreshHeader(new WaterDropHeader(MyApplication.getAppContext())); //弹性水滴效果
//        smartRefreshLayout.setRefreshHeader(new WaveSwipeHeader(MyApplication.getAppContext()));//下坠水滴效果
//        smartRefreshLayout.setRefreshHeader(new BezierCircleHeader(MyApplication.getAppContext())); //水滴下拉
//        smartRefreshLayout.setRefreshHeader(new PhoenixHeader(MyApplication.getAppContext()));  //大楼动画
//        smartRefreshLayout.setRefreshHeader(new TaurusHeader(MyApplication.getAppContext()));  //飞机滑翔动画效果
//        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(MyApplication.getAppContext()));   //经典带时间的刷新
//        smartRefreshLayout.setRefreshHeader(new TwoLevelHeader(MyApplication.getAppContext()));   //经典带时间的刷新
//        smartRefreshLayout.setRefreshHeader(new BezierRadarHeader(MyApplication.getAppContext()));  //雷达动画
//        smartRefreshLayout.setRefreshHeader(new DeliveryHeader(MyApplication.getAppContext()));   //快递交付动画
//        smartRefreshLayout.setRefreshHeader(new DropBoxHeader(MyApplication.getAppContext())); //礼物盒子动画效果
//        smartRefreshLayout.setRefreshHeader(new FalsifyHeader(MyApplication.getAppContext()));  //无动画效果
//        smartRefreshLayout.setRefreshHeader(new FlyRefreshHeader(MyApplication.getAppContext()));
//        smartRefreshLayout.setRefreshHeader(new FunGameBattleCityHeader(MyApplication.getAppContext())); //子弹游戏效果
//        smartRefreshLayout.setRefreshHeader(new FunGameHitBlockHeader(MyApplication.getAppContext()));   //碰球游戏效果
//        smartRefreshLayout.setRefreshHeader(new StoreHouseHeader(MyApplication.getAppContext()));  //StoreHouse文字渐变效果
        if (smartRefreshLayout != null) {
            if (isSetRefreshHeader) {
                //下拉刷新沉浸式水滴头部View
                smartRefreshLayout.setRefreshHeader(MaterialHeader(BaseApplication.getAppContext())) //经典Swip
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
    /*
        //官网给的一些设置属性方法
        //下面示例中的值等于默认值
        RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        refreshLayout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）

        refreshLayout.setHeaderHeight(100);//Header标准高度（显示下拉高度>=标准高度 触发刷新）
        refreshLayout.setHeaderHeightPx(100);//同上-像素为单位 （V1.1.0删除）
        refreshLayout.setFooterHeight(100);//Footer标准高度（显示上拉高度>=标准高度 触发加载）
        refreshLayout.setFooterHeightPx(100);//同上-像素为单位 （V1.1.0删除）

        refreshLayout.setFooterHeaderInsetStart(0);//设置 Header 起始位置偏移量 1.0.5
        refreshLayout.setFooterHeaderInsetStartPx(0);//同上-像素为单位 1.0.5 （V1.1.0删除）
        refreshLayout.setFooterFooterInsetStart(0);//设置 Footer 起始位置偏移量 1.0.5
        refreshLayout.setFooterFooterInsetStartPx(0);//同上-像素为单位 1.0.5 （V1.1.0删除）

        refreshLayout.setHeaderMaxDragRate(2);//最大显示下拉高度/Header标准高度
        refreshLayout.setFooterMaxDragRate(2);//最大显示下拉高度/Footer标准高度
        refreshLayout.setHeaderTriggerRate(1);//触发刷新距离 与 HeaderHeight 的比率1.0.4
        refreshLayout.setFooterTriggerRate(1);//触发加载距离 与 FooterHeight 的比率1.0.4

        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        refreshLayout.setEnableAutoLoadMore(true);//是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setEnablePureScrollMode(false);//是否启用纯滚动模式
        refreshLayout.setEnableNestedScroll(false);//是否启用嵌套滚动
        refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        refreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
        refreshLayout.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
        refreshLayout.setEnableFooterTranslationContent(true);//是否上拉Footer的时候向上平移列表或者内容
        refreshLayout.setEnableLoadMoreWhenContentNotFull(true);//是否在列表不满一页时候开启上拉加载功能
        refreshLayout.setEnableFooterFollowWhenLoadFinished(false);//是否在全部加载结束之后Footer跟随内容1.0.4
        refreshLayout.setEnableOverScrollDrag(false);//是否启用越界拖动（仿苹果效果）1.0.4

        refreshLayout.setEnableScrollContentWhenRefreshed(true);//是否在刷新完成时滚动列表显示新的内容 1.0.5
        refreshLayout.srlEnableClipHeaderWhenFixedBehind(true);//是否剪裁Header当时样式为FixedBehind时1.0.5
        refreshLayout.srlEnableClipFooterWhenFixedBehind(true);//是否剪裁Footer当时样式为FixedBehind时1.0.5

        refreshLayout.setDisableContentWhenRefresh(false);//是否在刷新的时候禁止列表的操作
        refreshLayout.setDisableContentWhenLoading(false);//是否在加载的时候禁止列表的操作

        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener());//设置多功能监听器
        refreshLayout.setScrollBoundaryDecider(new ScrollBoundaryDecider());//设置滚动边界判断
        refreshLayout.setScrollBoundaryDecider(new ScrollBoundaryDeciderAdapter());//自定义滚动边界

        refreshLayout.setRefreshHeader(new ClassicsHeader(context));//设置Header
        refreshLayout.setRefreshFooter(new ClassicsFooter(context));//设置Footer
        refreshLayout.setRefreshContent(new View(context));//设置刷新Content（用于非xml布局代替addView）1.0.4

        refreshLayout.autoRefresh();//自动刷新
        refreshLayout.autoLoadMore();//自动加载
        refreshLayout.autoRefresh(400);//延迟400毫秒后自动刷新
        refreshLayout.autoLoadMore(400);//延迟400毫秒后自动加载
        refreshLayout.finishRefresh();//结束刷新
        refreshLayout.finishLoadMore();//结束加载
        refreshLayout.finishRefresh(3000);//延迟3000毫秒后结束刷新
        refreshLayout.finishLoadMore(3000);//延迟3000毫秒后结束加载
        refreshLayout.finishRefresh(false);//结束刷新（刷新失败）
        refreshLayout.finishLoadMore(false);//结束加载（加载失败）
        refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
        refreshLayout.closeHeaderOrFooter();//关闭正在打开状态的 Header 或者 Footer（1.1.0）
        refreshLayout.resetNoMoreData();//恢复没有更多数据的原始状态 1.0.4（1.1.0删除）
        refreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态 1.0.5
     */
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
}