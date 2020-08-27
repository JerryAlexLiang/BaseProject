package com.liang.module_core.widget.toast;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.liang.module_core.R;


/**
 * 创建日期：2018/6/17 on 下午11:54
 * 描述:在该类中设置创建CustomToast对象的方法
 * 作者:yangliang
 */
public class CustomToast {

    public final Toast toast;//Toast对象
    public final View view;//Toast的UI效果
    public final ImageView icon;//图标
    public final TextView message;//内容

    public CustomToast(Context context) {
        toast = new Toast(context);
        view = LayoutInflater.from(context).inflate(R.layout.core_custom_toast_layout, null);
        icon = view.findViewById(R.id.iv_toast_icon);
        message = view.findViewById(R.id.tv_toast_message);
    }

    /**
     * 显示
     */
    public void show() {
        this.toast.show();
    }

    public static class Builder {
        private Bitmap icon;//图标图片
        private int iconID = R.drawable.icon_user_logo;//图标资源ID
        private String message;//内容
        private int backgroundColor = 0x56000000;//背景颜色
        private Context mContext;//上下文
        private int duration = Toast.LENGTH_SHORT;//设置时间
        private CustomToast mine;
        private int gravity = Gravity.NO_GRAVITY;//设置位置
        private int offsetX = 0;//设置偏移度X
//        private int offsetY = 0;//设置偏移度Y
        private int offsetY = 160;//设置偏移度Y
        private boolean isShowIcon;//是否显示图标
        private int textColor = Color.WHITE;//字体颜色

        public Builder(Context context) {
            this.mContext = context;
        }

        /**
         * 设置图标
         *
         * @param bitmap 图标资源
         * @return
         */
        public Builder setIcon(Bitmap bitmap) {
            this.icon = bitmap;
            return this;
        }

        public Builder setIcon(@DrawableRes int resId) {
            this.iconID = resId;
            return this;
        }

        public Builder showIcon(boolean showIcon) {
            this.isShowIcon = showIcon;
            return this;
        }

        /**
         * 设置内容
         *
         * @param message 内容信息
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * 设置背景
         */
        public Builder setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        /**
         * 设置吐司时长
         */
        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        /**
         * 设置位置
         */
        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        /**
         * 偏移量
         */
        public Builder setOffsetX(int offsetX) {
            this.offsetX = offsetX;
            return this;
        }

        public Builder setOffsetY(int offsetY) {
            this.offsetY = offsetY;
            return this;
        }

        /**
         * 设置字体的颜色
         * @param color
         * @return
         */
        public Builder setTextColor(@ColorInt int color){
            this.textColor = color;
            return this;
        }

        /**
         * 创建CustomToast对象
         */
        public CustomToast build() {

            if (null == mine) {
                //创建对象
                mine = new CustomToast(mContext);
            }

            if (isShowIcon) {
                //显示图标
                mine.icon.setVisibility(View.VISIBLE);
                //判断是否显示图标
                if (null != icon) {
                    //设置图片
                    mine.icon.setImageBitmap(icon);
                } else {
                    //设置图片
                    mine.icon.setBackgroundResource(iconID);
                }
            }

            //判断内容是否为空
            if (!message.isEmpty()) {
                //不为空设置内容文字
                mine.message.setText(message);
            } else {
                mine.message.setText("");
            }

            //设置底层背景
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mine.view.setBackground(new BackgroundDrawable(backgroundColor, mContext));
            }
            //设置时长
            mine.toast.setDuration(duration);
            //添加自定义效果
            mine.toast.setView(mine.view);
            //设置偏移量
            mine.toast.setGravity(gravity, offsetX, offsetY);
            //设置字体颜色
            mine.message.setTextColor(textColor);
            return mine;
        }
    }
}
