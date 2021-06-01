/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liang.updateapp.updateParser;


import androidx.annotation.NonNull;

import com.liang.updateapp.CustomResult;
import com.liang.updateapp.GsonUtils;
import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.listener.IUpdateParseCallback;
import com.xuexiang.xupdate.proxy.IUpdateParser;

/**
 * 创建日期:2021/5/31 on 11:05 AM
 * 描述: 自定义更新解析器
 * 作者: 杨亮
 */
public class CustomUpdateParser implements IUpdateParser {

    @Override
    public UpdateEntity parseJson(String json) throws Exception {
        return getParseResult(json);
    }

    private UpdateEntity getParseResult(String json) {
        CustomResult result = GsonUtils.parseJsonObjectToBean(json, CustomResult.class);
//        result.isIgnorable = true;
        result.apkUrl = "https://www.wanandroid.com/blogimgs/eb31f405-afb9-4df1-8bae-30c60a71d9c2.apk";
        result.updateStatus = 2;
        result.hasUpdate = true;
        if (result != null) {
//            return new UpdateEntity()
//                    .setHasUpdate(result.hasUpdate)
//                    .setIsIgnorable(result.isIgnorable)
//                    .setVersionCode(result.versionCode)
//                    .setVersionName(result.versionName)
//                    .setUpdateContent(result.updateLog)
//                    .setDownloadUrl(result.apkUrl)
////                    .setForce(true)
//                    .setSize(result.apkSize);

            UpdateEntity updateEntity = new UpdateEntity();
            if (result.getUpdateStatus() == 0) {
                updateEntity.setHasUpdate(false);
            } else {
                if (result.getUpdateStatus() == 2) {
                    updateEntity.setForce(true);
                }
                updateEntity
                        .setHasUpdate(result.hasUpdate)
//                        .setIsIgnorable(result.isIgnorable())
                        .setUpdateContent(result.updateLog)
                        .setVersionCode(result.getVersionCode())
                        .setVersionName(result.getVersionName())
                        .setDownloadUrl(result.apkUrl)
                        .setSize(result.getApkSize());
            }
            return updateEntity;


        }
        return null;
    }

    @Override
    public void parseJson(String json, @NonNull IUpdateParseCallback callback) throws Exception {
        //当isAsyncParser为 true时调用该方法, 所以当isAsyncParser为false可以不实现
        callback.onParseResult(getParseResult(json));
    }


    @Override
    public boolean isAsyncParser() {
        return false;
    }

}
