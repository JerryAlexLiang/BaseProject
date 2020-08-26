package com.liang.module_core_java.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liang.module_core_java.R;

/**
 * 创建日期：2018/11/15 on 10:18
 * 描述: 自定义ProgressDialog
 * 作者: liangyang
 */
public class CustomProgressDialog {

    private final Dialog dialog;
    private final TextView textView;
    private final ImageView progressBar;

    public CustomProgressDialog(Context context, String content) {
        this(context, content, false);
    }

    public CustomProgressDialog(Context context, String content, boolean cancelable) {
        //自定义Dialog样式
        dialog = new Dialog(context, R.style.CustomProgressDialog);
        //设置弹窗外围不可点击取消
        dialog.setCancelable(false);
        //得到加载布局
        View view = LayoutInflater.from(context).inflate(R.layout.custom_progress_dialog, null);
        //布局文字
        textView = (TextView) view.findViewById(R.id.progressDialog_text);
        textView.setText(content);
        progressBar = view.findViewById(R.id.progressDialog_progressbar);

        AnimationDrawable animationDrawable = (AnimationDrawable) progressBar.getBackground();
        animationDrawable.start();

        // 设置布局
        dialog.setContentView(view);
    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public boolean isShow() {
        if (dialog != null) {
            return dialog.isShowing();
        } else {
            return false;
        }
    }


}
