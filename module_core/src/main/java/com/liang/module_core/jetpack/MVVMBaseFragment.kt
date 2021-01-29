package com.liang.module_core.jetpack

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.liang.module_core.R
import com.liang.module_core.app.BaseApplication
import com.liang.module_core.constant.Constant
import com.liang.module_core.utils.SPUtils
import com.liang.module_core.utils.ToastUtil
import com.liang.module_core.widget.CustomProgressDialog
import com.scwang.smartrefresh.header.PhoenixHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import org.greenrobot.eventbus.EventBus

abstract class MVVMBaseFragment : Fragment(), RequestLifecycle {

    companion object{
        private const val TAG = "MVVMBaseFragment"
    }

    /**
     * 是否已经加载过数据
     */
    private var mHasLoadedData = false

    /**
     * Fragment中由于服务器或网络异常导致加载失败显示的布局。
     */
    private var loadErrorView: View? = null

    /**
     * Fragment中inflate出来的布局。
     */
    protected var rootView: View? = null

    /**
     * 依附的Activity
     */
    lateinit var activity: Activity

    /**
     * Fragment中显示加载等待的控件。
     */
    protected var loading: ProgressBar? = null

    /**
     * 日志输出标志
     */
    protected val TAG: String = this.javaClass.simpleName

    private var mIsRequestDataRefresh = false
    private var mRefreshLayout: SwipeRefreshLayout? = null
    private var customProgressDialog: CustomProgressDialog? = null

    protected abstract fun createViewLayoutId(): Int

    protected abstract fun initView(rootView: View?)

    private val isSetRefresh: Boolean
        get() = false

    /**
     * 是否注册事件分发，默认不绑定
     */
    protected abstract fun isRegisterEventBus(): Boolean
    protected abstract fun isSetRefreshHeader(): Boolean
    protected abstract fun isSetRefreshFooter(): Boolean

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 缓存当前依附的activity
        activity = getActivity()!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(createViewLayoutId(), container, false)
        initView(rootView)
        if (isSetRefresh) {
            setupSwipeRefresh(rootView!!)
        }
        val smartRefreshLayout = rootView?.findViewById<SmartRefreshLayout>(R.id.smart_refresh_layout)
        smartRefreshLayout?.let { getSmartRefreshPrimaryColorsTheme(it, isSetRefreshHeader(), isSetRefreshFooter())  }
        loading = view?.findViewById(R.id.loading)

        //本地化存储操作(取出) - 设置状态栏颜色
        val baseActionBar = rootView?.findViewById<FrameLayout>(R.id.base_actionbar)
        if (baseActionBar != null) {
            getActionBarTheme(baseActionBar, null)
        }
        val baseToolBar = rootView?.findViewById<CollapsingToolbarLayout>(R.id.collapsing_layout)
        if (baseToolBar != null) {
            getActionBarTheme(null, baseToolBar)
        }
        return rootView
    }

    private fun setupSwipeRefresh(view: View) {
        mRefreshLayout = view.findViewById<View>(R.id.swipe_refresh_layout) as SwipeRefreshLayout
        if (mRefreshLayout != null) {
            mRefreshLayout!!.setColorSchemeResources(R.color.refresh_progress_1,
                    R.color.refresh_progress_2, R.color.refresh_progress_3)
            mRefreshLayout!!.setProgressViewOffset(true, 0, TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24f, resources.displayMetrics).toInt())
            mRefreshLayout!!.setOnRefreshListener { requestDataRefresh() }
        }
    }

    fun setRefresh(requestDataRefresh: Boolean) {
        if (mRefreshLayout == null) {
            return
        }
        if (!requestDataRefresh) {
            mIsRequestDataRefresh = false
            mRefreshLayout!!.postDelayed({
                if (mRefreshLayout != null) {
                    mRefreshLayout!!.isRefreshing = false
                }
            }, 1000)
        } else {
            mRefreshLayout!!.isRefreshing = true
        }
    }

    private fun requestDataRefresh() {
        mIsRequestDataRefresh = true
    }

    private fun getSmartRefreshPrimaryColorsTheme(smartRefreshLayout: SmartRefreshLayout?, isSetRefreshHeader: Boolean, isSetRefreshFooter: Boolean) {
        val actionBarColorInt = SPUtils.get(BaseApplication.getAppContext(), Constant.ACTIONBAR_COLOR_THEME, 0) as Int

        if (smartRefreshLayout != null) {
            if (isSetRefreshHeader) {
                //下拉刷新沉浸式水滴头部View
//                smartRefreshLayout.setRefreshHeader(new MaterialHeader(MyApplication.getAppContext()));
                smartRefreshLayout.setRefreshHeader(PhoenixHeader(BaseApplication.getAppContext()))
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
            Constant.ACTIONBAR_COLOR_BLACK -> smartRefreshLayout?.setPrimaryColorsId(R.color.black, R.color.white)
            Constant.ACTIONBAR_COLOR_WHITE -> smartRefreshLayout?.setPrimaryColorsId(R.color.black, R.color.white)
            Constant.ACTIONBAR_COLOR_TRANSLATE -> smartRefreshLayout?.setPrimaryColorsId(R.color.transparent, R.color.white)
            Constant.ACTIONBAR_COLOR_GREEN -> smartRefreshLayout?.setPrimaryColorsId(R.color.assist, R.color.white)
        }
    }

    override fun onResume() {
        super.onResume()
        //当Fragment在屏幕上可见并且没有加载过数据时调用
        if (!mHasLoadedData) {
            loadDataOnce()
            mHasLoadedData = true
        }
    }

    /**
     * 页面首次可见时调用一次该方法，在这里可以请求网络数据等。
     */
    open fun loadDataOnce() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        if (rootView?.parent != null) (rootView?.parent as ViewGroup).removeView(rootView)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun showProgressDialog(content: String?, cancelable: Boolean) {
        if (customProgressDialog == null) {
            customProgressDialog = CustomProgressDialog(activity, content, cancelable)
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
                false, content, resources.getColor(R.color.toast_bg), resources.getColor(R.color.text_invert), Gravity.CENTER, Toast.LENGTH_SHORT)
    }

    fun onShowTrueToast(content: String?) {
        ToastUtil.setCustomToast(BaseApplication.getAppContext(), BitmapFactory.decodeResource(resources, R.drawable.core_icon_true),
                true, content, resources.getColor(R.color.toast_bg), resources.getColor(R.color.text_invert), Gravity.CENTER, Toast.LENGTH_SHORT)
    }

    fun onShowErrorToast(content: String?) {
        ToastUtil.setCustomToast(BaseApplication.getAppContext(), BitmapFactory.decodeResource(resources, R.drawable.core_icon_wrong),
                true, content, resources.getColor(R.color.toast_bg), resources.getColor(R.color.text_invert), Gravity.CENTER, Toast.LENGTH_SHORT)
    }

    fun finishPage() {
        if (activity != null) {
            activity!!.finish()
        }
    }

    /**
     * 开始加载，将加载等待控件显示。
     */
    @CallSuper
    override fun startLoading() {
        loading?.visibility = View.VISIBLE
        hideLoadErrorView()
    }

    /**
     * 加载完成，将加载等待控件隐藏。
     */
    @CallSuper
    override fun loadFinished() {
        loading?.visibility = View.GONE
    }

    /**
     * 加载失败，将加载等待控件隐藏。
     */
    @CallSuper
    override fun loadFailed(msg: String?) {
        loading?.visibility = View.GONE
    }

    /**
     * 当Fragment中的加载内容服务器返回失败或网络异常，通过此方法显示提示界面给用户。
     *
     * @param tip 界面中的提示信息
     * @param block 点击屏幕重新加载，回调处理。
     */
    protected fun showLoadErrorView(tip: String, block: View.() -> Unit) {
        if (loadErrorView != null) {
            loadErrorView?.visibility = View.VISIBLE
            return
        }
        if (rootView != null) {
            val viewStub = rootView?.findViewById<ViewStub>(R.id.loadErrorView)
            if (viewStub != null) {
                loadErrorView = viewStub.inflate()
                val loadErrorText = loadErrorView?.findViewById<TextView>(R.id.loadErrorText)
                loadErrorText?.text = tip
                val loadErrorRootView = loadErrorView?.findViewById<View>(R.id.rl_web_error_container)
                loadErrorRootView?.setOnClickListener {
                    it?.block()
                }
            }
        }
    }

    /**
     * 将load error view进行隐藏。
     */
    private fun hideLoadErrorView() {
        loadErrorView?.visibility = View.GONE
    }

    /**
     * 本地化存储操作(存入) - 设置状态栏颜色
     *
     * @param ACTIONBAR_COLOR
     */
    fun setActionBarTheme(ACTIONBAR_COLOR: Int) {
        SPUtils.put(this@MVVMBaseFragment.context, Constant.ACTIONBAR_COLOR_THEME, ACTIONBAR_COLOR)
    }


    /**
     * 本地化存储操作(取出) - 设置状态栏颜色
     * //toolbar_layout  CollapsingToolbarLayout  app:contentScrim="?attr/colorPrimary"  app:statusBarScrim="?attr/colorPrimary"
     */
    fun getActionBarTheme(baseActionBar: FrameLayout?, baseCollapsingToolBar: CollapsingToolbarLayout?) {
        val actionBarColorInt = SPUtils.get(this@MVVMBaseFragment.context, Constant.ACTIONBAR_COLOR_THEME, 0) as Int
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
}