package com.liang.updateapp.updateParser;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.liang.updateapp.CustomUpdateDialogActivity;
import com.liang.updateapp.CustomUpdateDialogFragment;
import com.xuexiang.xupdate.entity.PromptEntity;
import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.logs.UpdateLog;
import com.xuexiang.xupdate.proxy.IUpdatePrompter;
import com.xuexiang.xupdate.proxy.IUpdateProxy;
import com.xuexiang.xupdate.proxy.impl.DefaultPrompterProxyImpl;

/**
 * 创建日期: 2021/5/31 on 2:39 PM
 * 描述: 自定义版本更新提示器 CustomUpdateDialogActivity
 * 作者: 杨亮
 */
public class CustomUpdatePrompter implements IUpdatePrompter {

    /**
     * 显示版本更新提示
     *
     * @param updateEntity 更新信息
     * @param updateProxy  更新代理
     * @param promptEntity 提示界面参数
     */
    @Override
    public void showPrompt(@NonNull UpdateEntity updateEntity, @NonNull IUpdateProxy updateProxy, @NonNull PromptEntity promptEntity) {
        Context context = updateProxy.getContext();
        if (context == null) {
            UpdateLog.e("showPrompt failed, context is null!");
            return;
        }
        if (context instanceof FragmentActivity) {
            CustomUpdateDialogFragment.show(((FragmentActivity) context).getSupportFragmentManager(), updateEntity, new DefaultPrompterProxyImpl(updateProxy), promptEntity);
        } else {
            CustomUpdateDialogActivity.show(context, updateEntity, new DefaultPrompterProxyImpl(updateProxy), promptEntity);
        }
    }
}
