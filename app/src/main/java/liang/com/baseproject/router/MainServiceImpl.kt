package liang.com.baseproject.router

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.liang.model_middleware.router.AppRouter
import com.liang.model_middleware.service.MainService
import liang.com.baseproject.activity.CameraActivity
import liang.com.baseproject.activity.ServiceActivity
import liang.com.baseproject.fragment.JuheNewsTabFragment
import liang.com.baseproject.testlaboratory.FiltrateActivity
import liang.com.baseproject.testlaboratory.MapTestActivity
import org.jetbrains.anko.startActivity

/**
 * 创建日期: 2021/1/18 on 2:30 PM
 * 描述: 通过依赖注入解耦:服务管理 实现接口
 * 作者: 杨亮
 */
@Route(path = AppRouter.MODULE_MAIN_HOME)
class MainServiceImpl : MainService {

    override fun getJuheNewsTabFragment(): Fragment {
        return JuheNewsTabFragment.newInstance(null)
    }

    override fun openFiltrateActivity(context: Context) {
        return FiltrateActivity.actionStart(context)
    }

    override fun openMapTestActivity(context: Context) {
        return MapTestActivity.actionStart(context)
    }

    override fun openCustomSimpleCameraActivity(context: Context) {
        context.startActivity<CameraActivity>()
    }

    override fun openServiceAIDLActivity(context: Context) {
        return ServiceActivity.actionStart(context)
    }

    override fun init(context: Context?) {
    }
}