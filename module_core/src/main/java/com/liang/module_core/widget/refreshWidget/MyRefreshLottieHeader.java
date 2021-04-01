package com.liang.module_core.widget.refreshWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.liang.module_core.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * 创建日期: 2021/4/1 on 11:13 AM
 * 描述: Lottie动画实现SmartRefreshLayout刷新头部动画
 * 作者: 杨亮
 */
public class MyRefreshLottieHeader extends LinearLayout implements RefreshHeader {

    private LottieAnimationView mAnimationView;

    public MyRefreshLottieHeader(Context context) {
        super(context);
        initView(context);
    }

    public MyRefreshLottieHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRefreshLottieHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.core_refresh_header_loading_lottie, this);
        mAnimationView = view.findViewById(R.id.refreshHeaderLottie);
    }

    /**
     * 注意不能为null
     */
    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        mAnimationView.playAnimation();
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        mAnimationView.cancelAnimation();
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

    }

    /**
     * 动态更换Lottie动画文件
     */
    public void setAnimationViewJson(String animName){
        mAnimationView.setAnimation(animName);
    }

    public void setAnimationViewJson(Animation anim){
        mAnimationView.setAnimation(anim);
    }
}
