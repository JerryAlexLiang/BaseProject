package com.liang.module_eyepetizer.ui.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.liang.module_core.jetpack.MVVMBaseActivity
import com.liang.module_eyepetizer.R
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_eye_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity

class EyeSplashActivity : MVVMBaseActivity() {

    companion object {

        fun actionStart(context: Context) {
            val intent = Intent(context, EyeSplashActivity::class.java)
            context.startActivity(intent)
        }

        private const val TAG = "EyePetizerActivity"
    }

    private val job by lazy { Job() }

    private val splashDuration: Long = 3 * 1000L

    private val alphaAnimation by lazy {
        AlphaAnimation(0.5f, 1.0f).apply {
            duration = splashDuration
            fillAfter = true
        }
    }

    private val scaleAnimation by lazy {
        ScaleAnimation(1.0f, 1.05f, 1f, 1.05f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
            duration = splashDuration
            fillAfter = true
        }
    }

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
        return R.layout.activity_eye_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWriteExternalStoragePermission()
    }

    private fun initView() {
        getActionBarTheme(null,null)

        ivSlogan.startAnimation(alphaAnimation)
        ivSplashPicture.startAnimation(scaleAnimation)

        CoroutineScope(job).launch {
            delay(splashDuration)
            startActivity<EyePetizerActivity>()
            finish()
        }
    }

    private fun requestWriteExternalStoragePermission() {
        PermissionX.init(this@EyeSplashActivity).permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onExplainRequestReason { scope, deniedList ->
                    val message = "需要存储权限来处理您的图片信息"
                    scope.showRequestReasonDialog(deniedList, message, "确定", "取消")
                }
                .onForwardToSettings { scope, deniedList ->
                    val message = "需要存储权限来处理您的图片信息"
                    scope.showForwardToSettingsDialog(deniedList, message, "设置", "取消")
                }
                .request { allGranted, grantedList, deniedList ->
                    requestReadPhoneStatePermission()
                }
    }

    private fun requestReadPhoneStatePermission() {
        PermissionX.init(this@EyeSplashActivity).permissions(Manifest.permission.READ_PHONE_STATE)
                .onExplainRequestReason { scope, deniedList ->
                    val message = "开眼视频推荐和观看记录等功能，需要访问手机识别码等信息"
                    scope.showRequestReasonDialog(deniedList, message, "确定", "取消")
                }
                .onForwardToSettings { scope, deniedList ->
                    val message = "眼视频推荐和观看记录等功能，需要访问手机识别码等信息"
                    scope.showForwardToSettingsDialog(deniedList, message, "设置", "取消")
                }
                .request { allGranted, grantedList, deniedList ->
                    initView()
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}