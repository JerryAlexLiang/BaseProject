package liang.com.baseproject.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.liang.module_core.jetpack.JetPackActivity
import com.liang.module_core.jetpack.MVVMBaseActivity
import com.liang.module_core.mvp.MVPBasePresenter
import com.liang.module_core.utils.CopyUtils
import com.liang.module_core.utils.setOnClickListener
import com.liang.module_core.utils.setVisible
import liang.com.baseproject.R


/**
 * 创建日期：5/16/21 on 4:15 PM
 * 描述: 自定义CrashActivity
 * 作者: liangyang
 */
class MyCrashActivity : JetPackActivity() {

    private lateinit var scrollViewLog: ScrollView
    private lateinit var ivBug: ImageView
    private lateinit var tvShowLog: TextView
    private lateinit var tvShowLog2: TextView
    private lateinit var tvExit: TextView
    private lateinit var tvRestart: TextView
    private lateinit var tvLog: TextView

    private var isLogShown = false

    private var configFromIntent: CaocConfig? = null

    override fun isRegisterEventBus(): Boolean {
        return false
    }

    override fun isSetRefreshHeader(): Boolean {
        return false
    }

    override fun isSetRefreshFooter(): Boolean {
        return false
    }

    override fun createPresenter(): MVPBasePresenter<*>? {
        return null
    }

    override fun provideContentViewId(): Int {
        return R.layout.activity_my_crash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configFromIntent = CustomActivityOnCrash.getConfigFromIntent(intent)
        if (configFromIntent == null) {
            finish()
            return
        }

        tvShowLog = findViewById<TextView>(R.id.tvShowLog) as TextView
        tvShowLog2 = findViewById<TextView>(R.id.tvShowLog2) as TextView
        tvExit = findViewById<TextView>(R.id.tvExit) as TextView
        tvRestart = findViewById<TextView>(R.id.tvRestart) as TextView
        tvLog = findViewById<TextView>(R.id.tvLog) as TextView
        ivBug = findViewById<ImageView>(R.id.ivBug) as ImageView
        scrollViewLog = findViewById<ScrollView>(R.id.scrollViewLog) as ScrollView


        initListener()

    }

    private fun initListener() {

        setOnClickListener(tvShowLog, tvShowLog2, tvExit, tvRestart) {
            when (this) {
                tvShowLog -> {
                    showAndCopyCrashLog()
                }

                tvShowLog2 -> {
                    showAndCopyCrashLog()
                }
                tvExit -> {
                    //退出应用
                    CustomActivityOnCrash.closeApplication(this@MyCrashActivity, configFromIntent!!)
                }
                tvRestart -> {
                    //重启应用
                    CustomActivityOnCrash.restartApplication(this@MyCrashActivity, configFromIntent!!)
                }
            }
        }
    }

    private fun showAndCopyCrashLog() {
        val crashLog = tvLog.text.toString()

        if (TextUtils.isEmpty(crashLog)) {
            isLogShown = true
            tvShowLog.text = "复制日志"
            tvShowLog2.text = "复制日志"
            tvShowLog.setTextColor(resources.getColor(R.color.text_surface))
            ivBug.setVisible(View.GONE)
            scrollViewLog.setVisible(View.VISIBLE)
            tvLog.text = CustomActivityOnCrash.getAllErrorDetailsFromIntent(this, intent)
        } else {
            CopyUtils.copyText(crashLog)
            tvShowLog.text = "日志已复制"
            tvShowLog2.text = "日志已复制"
            tvShowLog.setTextColor(resources.getColor(R.color.red))
            tvShowLog.isEnabled = false
            tvShowLog2.isEnabled = false
        }

    }
}