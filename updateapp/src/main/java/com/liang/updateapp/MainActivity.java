package com.liang.updateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.liang.updateapp.updateParser.CustomUpdateParser;
import com.liang.updateapp.updateParser.CustomUpdatePrompter;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.proxy.IUpdateProxy;
import com.xuexiang.xupdate.proxy.impl.DefaultUpdateChecker;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private String mUpdateUrl = "https://gitee.com/xuexiangjys/XUpdate/raw/master/jsonapi/update_test.json";
    private String mUpdateUrlCustom = "https://gitee.com/xuexiangjys/XUpdate/raw/master/jsonapi/update_custom.json";

    private Button btnUpdateDefault;

    //{
    //  "Code": 0,
    //  "Msg": "",
    //  "UpdateStatus": 1,
    //  "VersionCode": 3,
    //  "VersionName": "1.0.2",
    //  "UploadTime": "2018-07-10 17:28:41",
    //  "ModifyContent": "\r\n1、优化api接口。\r\n2、添加使用demo演示。\r\n3、新增自定义更新服务API接口。\r\n4、优化更新提示界面。",
    //  "DownloadUrl": "https://xuexiangjys.oss-cn-shanghai.aliyuncs.com/apk/xupdate_demo_1.0.2.apk",
    //  "ApkSize": 2048,
    //  "ApkMd5": "E4B79A36EFB9F17DF7E3BB161F9BCFD8"
    //}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnUpdateDefault = (Button) findViewById(R.id.btn_update_default);
        btnUpdateDefault.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_default:
                updateAppDefault();
                break;

            default:
                break;
        }
    }

    private void updateAppDefault() {
        XUpdate.newBuild(this)
//                .updateUrl(mUpdateUrl)
                .updateUrl(mUpdateUrlCustom)
                .updateParser(new CustomUpdateParser())
                .updatePrompter(new CustomUpdatePrompter())
                .promptThemeColor(getResources().getColor(R.color.update_theme_color))
                .promptButtonTextColor(getResources().getColor(R.color.white))
                .promptTopResId(R.mipmap.bg_update_top)
                .updateChecker(new DefaultUpdateChecker() {
                    /**
                     * 版本检查之前
                     */
                    @Override
                    public void onBeforeCheck() {
                        super.onBeforeCheck();
                        Toast.makeText(MainActivity.this, "开始检查更新", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onBeforeCheck: =======> 开始检查更新");
                    }

                    /**
                     * 执行网络请求，检查应用的版本信息
                     */
                    @Override
                    public void checkVersion(boolean isGet, @NonNull String url, @NonNull Map<String, Object> params, @NonNull IUpdateProxy updateProxy) {
                        super.checkVersion(isGet, url, params, updateProxy);
                    }

                    /**
                     * 版本检查之后
                     */
                    @Override
                    public void onAfterCheck() {
                        super.onAfterCheck();
                        Toast.makeText(MainActivity.this, "检查更新结束", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onAfterCheck: =======> 检查更新结束");
                    }

                    /**
                     * 查询成功
                     *
                     * @param result      查询结果
                     * @param updateProxy 更新代理
                     */
                    @Override
                    public void processCheckResult(@NonNull String result, @NonNull IUpdateProxy updateProxy) {
                        super.processCheckResult(result, updateProxy);
                        Toast.makeText(MainActivity.this, "检查更新结束 最新版本信息: " + result, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "processCheckResult: =======> 检查更新结束 最新版本信息: " + result);
                    }

                    /**
                     * 未发现新版本
                     *
                     * @param throwable 未发现的原因
                     */
                    @Override
                    public void noNewVersion(Throwable throwable) {
                        super.noNewVersion(throwable);
                        //没有最新版本的处理
                        Toast.makeText(MainActivity.this, "当前已是最新版本!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "noNewVersion: =======> 当前已是最新版本!");
                    }
                })
                .update();
    }
}