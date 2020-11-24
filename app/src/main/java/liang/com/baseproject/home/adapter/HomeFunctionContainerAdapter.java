package liang.com.baseproject.home.adapter;

import android.content.res.ColorStateList;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.liang.model_middleware.impl.ServiceProvider;
import com.liang.module_core.constant.Constant;
import com.liang.module_core.utils.SPUtils;
import com.liang.module_core.utils.SettingUtils;
import com.liang.module_core.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import liang.com.baseproject.R;
import liang.com.baseproject.entity.HomeFunctionBean;

/**
 * 创建日期: 2020/9/14 on 3:43 PM
 * 描述: 首页功能入口列表
 * 作者: 杨亮
 */
public class HomeFunctionContainerAdapter extends BaseQuickAdapter<HomeFunctionBean, HomeFunctionContainerAdapter.MyViewHolder> {

    public HomeFunctionContainerAdapter() {
        super(R.layout.rv_home_function_container_item);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void convert(@NonNull MyViewHolder helper, HomeFunctionBean item) {
        if (item != null) {
            helper.tvFunctionName.setText(item.getFunctionName());
            helper.ivFunctionImage.setImageResource(item.getFunctionIconId());

            if (SettingUtils.getInstance().isDarkTheme()) {
                helper.ivFunctionImage.setImageTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorBlue)));
                helper.ivFunctionImage.setBackgroundColor(getContext().getResources().getColor(R.color.translucent_background));
            } else {
                int actionBarColorInt = (int) SPUtils.get(getContext(), Constant.ACTIONBAR_COLOR_THEME, 0);
                switch (actionBarColorInt) {
                    case Constant.ACTIONBAR_COLOR_BLUE:
                        helper.ivFunctionImage.setImageTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.title_bar_blue)));
                        break;

                    case Constant.ACTIONBAR_COLOR_BLACK:
                        helper.ivFunctionImage.setImageTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.black)));
                        break;

                    case Constant.ACTIONBAR_COLOR_RED:
                        helper.ivFunctionImage.setImageTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.title_bar_red)));
                        break;

                    case Constant.ACTIONBAR_COLOR_GREEN:
                        helper.ivFunctionImage.setImageTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.title_bar_green)));
                        break;
                }
            }

            helper.itemView.setOnClickListener(v -> {
                switch (item.getFunctionId()) {
                    case 0:
                        ServiceProvider.getWeatherService().startWeatherActivity(getContext());
                        break;

                    case 1:
                        ServiceProvider.getDatePickerService().startDatePickerDemoActivity(getContext());
                        break;

                    case 2:
                        ToastUtil.showShortToast("开眼Video");
                        ServiceProvider.getEyeModuleService().startEyePetizerActivity(getContext());
                        break;

                    case 3:
                        ToastUtil.showShortToast("更多");
                        break;

                    default:
                        break;
                }
            });
        }
    }

    static class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.iv_function_image)
        ImageView ivFunctionImage;
        @BindView(R.id.tv_function_name)
        TextView tvFunctionName;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
} 
