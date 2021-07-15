package com.liang.module_core.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.flyco.tablayout.CommonTabLayout;
import com.liang.module_core.R;
import com.liang.module_core.utils.DensityUtil;
import com.liang.module_core.utils.NoDoubleClickListener;

/**
 * 创建日期: 2021/7/14 on 5:09 PM
 * 描述: 自定义标题栏控件
 * 作者: 杨亮
 */
public class BaseActionBarWidget extends FrameLayout implements View.OnClickListener {

    private View viewAppTitle;
    private MyViewHolder myViewHolder;
    private OnLeftButtonClickListener mLeftButtonClickListener;
    private OnLeft2ButtonClickListener mLeft2ButtonClickListener;
    private OnRightButtonClickListener mRightButtonClickListener;
    private OnSearchEditTextClickListener mSearchEditTextClickListener;

    public BaseActionBarWidget(@NonNull Context context) {
        super(context);
        init();
    }

    public BaseActionBarWidget(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseActionBarWidget(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dpToPx(this.getContext(), 100));
        viewAppTitle = inflater.inflate(R.layout.core_layout_base_actionbar_default, null);
//        this.addView(viewAppTitle, layoutParams);

        setActionBarHeight(LayoutParams.WRAP_CONTENT);
        myViewHolder = new MyViewHolder(this);

        initListener();
    }

    private void initListener() {
        myViewHolder.ivLeft.setOnClickListener(this);
        myViewHolder.ivLeft2.setOnClickListener(this);
        myViewHolder.ivRight.setOnClickListener(this);
        myViewHolder.editSearchView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.base_actionbar_left_icon) {
            if (isFastDoubleClick()) return;
            //这里写点击事件要做的事情
            if (mLeftButtonClickListener != null) {
                mLeftButtonClickListener.onLeftButtonClick(v);
            }

        } else if (id == R.id.base_actionbar_left2_icon) {
            if (isFastDoubleClick()) return;
            if (mLeft2ButtonClickListener != null) {
                mLeft2ButtonClickListener.onLeft2ButtonClick(v);
            }
        } else if (id == R.id.edit_search_view) {
            if (isFastDoubleClick()) return;
            if (mSearchEditTextClickListener != null) {
                mSearchEditTextClickListener.onSearchEditTextClick(v);
            }

        } else if (id == R.id.base_actionbar_right_icon) {
            if (isFastDoubleClick()) return;
            if (mRightButtonClickListener != null) {
                mRightButtonClickListener.onRightButtonClick(v);
            }
        }

    }

    public void initViewsVisible(boolean isLeftButtonVisible, boolean isLeft2ButtonVisible, boolean isLeftTvVisible,
                                 boolean isCenterTitleVisible, boolean isEditSearchViewVisible, boolean isRightIconVisible,
                                 boolean isRightTitleVisible) {
        //左侧返回1
        myViewHolder.ivLeft.setVisibility(isLeftButtonVisible ? View.VISIBLE : View.GONE);
        //左侧返回2
        myViewHolder.ivLeft2.setVisibility(isLeft2ButtonVisible ? View.VISIBLE : View.GONE);
        //左侧文字
        myViewHolder.tvLeft.setVisibility(isLeftTvVisible ? View.VISIBLE : View.GONE);
        //标题
        myViewHolder.tvTitle.setVisibility(isCenterTitleVisible ? View.VISIBLE : View.GONE);
        //搜索框占位
        myViewHolder.editSearchView.setVisibility(isEditSearchViewVisible ? View.VISIBLE : View.GONE);
        //右侧按钮
        myViewHolder.ivRight.setVisibility(isRightIconVisible ? View.VISIBLE : View.GONE);
        //右侧文字
        myViewHolder.tvRight.setVisibility(isRightTitleVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置标题栏高度
     */
    public void setActionBarHeight(int height) {
        this.removeAllViews();
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dpToPx(this.getContext(), height));
        this.addView(viewAppTitle, layoutParams);
    }

    /**
     * 设置标题文字
     */
    public void setActionBarTitle(String title, int textColor) {
        if (!TextUtils.isEmpty(title)) {
            myViewHolder.tvTitle.setText(title);
            myViewHolder.tvTitle.setTextColor(textColor);
        }
    }

    /**
     * 设置左侧文字
     */
    public void setLeftTitle(String title, int textColor) {
        if (!TextUtils.isEmpty(title)) {
            myViewHolder.tvLeft.setText(title);
            myViewHolder.tvLeft.setTextColor(textColor);
        }
    }

    /**
     * 设置右侧文字
     */
    public void setRightTitle(String title, int textColor) {
        if (!TextUtils.isEmpty(title)) {
            myViewHolder.tvRight.setText(title);
            myViewHolder.tvRight.setTextColor(textColor);
        }
    }

    /**
     * 设置左侧图标
     */
    public void setLeftButtonIcon(int sourceID) {
        myViewHolder.ivLeft.setImageResource(sourceID);
    }

    public void setLeft2ButtonIcon(int sourceID) {
        myViewHolder.ivLeft2.setImageResource(sourceID);
    }

    /**
     * 设置右侧图标
     */
    public void setRightButtonIcon(int sourceID) {
        myViewHolder.ivRight.setImageResource(sourceID);
    }

    /**
     * 设置背景颜色
     */
    public void setActionBarBackgroundColor(int color) {
        viewAppTitle.setBackgroundColor(color);
    }

    /**
     * 设置背景图片
     */
    public void setActionBarBackgroundResource(int sourceID) {
        viewAppTitle.setBackgroundResource(sourceID);
    }

    /**
     * 设置背景网络图片
     */
    public void setActionBarNetBackground(Context context, String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        viewAppTitle.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    /**
     * 左侧1点击事件
     */
    public void setOnLeftButtonClickListener(OnLeftButtonClickListener listener) {
        mLeftButtonClickListener = listener;
    }

    /**
     * 左侧2点击事件
     */
    public void setOnLeft2ButtonClickListener(OnLeft2ButtonClickListener listener) {
        mLeft2ButtonClickListener = listener;
    }

    /**
     * 右侧点击事件
     */
    public void setOnRightButtonClickListener(OnRightButtonClickListener listener) {
        mRightButtonClickListener = listener;
    }

    public interface OnLeftButtonClickListener {
        void onLeftButtonClick(View view);
    }

    public interface OnLeft2ButtonClickListener {
        void onLeft2ButtonClick(View view);
    }

    public interface OnSearchEditTextClickListener {
        void onSearchEditTextClick(View view);
    }

    public interface OnRightButtonClickListener {
        void onRightButtonClick(View view);
    }

    private boolean isFastDoubleClick() {
        if (NoDoubleClickListener.isFastDoubleClick()) {
            return true;
        }
        return false;
    }

    static class MyViewHolder {

        FrameLayout frameLayout;
        ImageView ivLeft;
        TextView tvLeft;
        ImageView ivLeft2;
        TextView tvTitle;
        SearchEditText editSearchView;
        CommonTabLayout commonTabLayout;
        TextView tvRight;
        ImageView ivRight;

        public MyViewHolder(View view) {
            frameLayout = view.findViewById(R.id.base_actionbar);
            ivLeft = view.findViewById(R.id.base_actionbar_left_icon);
            tvLeft = view.findViewById(R.id.base_actionbar_left_tv);
            ivLeft2 = view.findViewById(R.id.base_actionbar_left2_icon);
            tvTitle = view.findViewById(R.id.base_actionbar_title);
            editSearchView = view.findViewById(R.id.edit_search_view);
            commonTabLayout = view.findViewById(R.id.commonTabLayout);
            tvRight = view.findViewById(R.id.base_actionbar_right_tv);
            ivRight = view.findViewById(R.id.base_actionbar_right_icon);
        }
    }

}
