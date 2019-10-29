package liang.com.baseproject.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import liang.com.baseproject.R;
import liang.com.baseproject.activity.ThemeSettingActivity;
import liang.com.baseproject.utils.CacheCleanUtil;
import liang.com.baseproject.utils.ToastUtil;

/**
 * 创建日期：2019/2/21 on 17:23
 * 描述: 设置选项内容
 * 作者: liangyang
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        //主题样式
        Preference modifyActionBarTheme = getPreferenceManager().findPreference(getResources().getString(R.string.key_actionbar_theme_modify));
        //缓存及其他
        Preference clearCache = getPreferenceManager().findPreference(getResources().getString(R.string.key_clear_cache));
        Preference notice = getPreferenceManager().findPreference(getResources().getString(R.string.key_notice));
        Preference developer = getPreferenceManager().findPreference(getResources().getString(R.string.key_developer));

        if (modifyActionBarTheme != null) {
            modifyActionBarTheme.setOnPreferenceClickListener(this);
        }

        if (clearCache != null) {
            clearCache.setOnPreferenceClickListener(this);
        }
        if (notice != null) {
            notice.setOnPreferenceClickListener(this);
        }
        if (developer != null) {
            developer.setOnPreferenceClickListener(this);
        }

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case "key_actionbar_theme_modify":
                ThemeSettingActivity.actionStart(getActivity());
                getActivity().finish();
                ToastUtil.onShowToast("更该主题样式~");
                break;

            case "clear_cache":
                showClearCacheDialog();
                break;

            case "notice":
                showNoticeDialog();
                break;

            case "developer":
                ToastUtil.onShowToast("开发中~");
                break;
        }
        return false;
    }

    private void showNoticeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_notice)
                .setMessage("学习使用，仅供参考学习，欢迎来撩!")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showClearCacheDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_clear_cache)
                .setMessage("当前缓存大小: " + CacheCleanUtil.getTotalCacheSize())
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("清除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CacheCleanUtil.cleanAllCache();
                        ToastUtil.onShowTrueToast(getResources().getString(R.string.toast_clear_cache));
                    }
                })
                .show();
    }

}
