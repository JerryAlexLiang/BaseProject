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

package com.liang.updateapp;

import java.io.Serializable;

/**
 * 创建日期:2021/5/31 on 11:04 AM
 * 描述: 自定义版本检查的结果
 * 作者: 杨亮
 */
public class CustomResult implements Serializable {

    public boolean hasUpdate;    //是否有新版本

    public boolean isIgnorable;  //是否可忽略该版本

    public int versionCode;      //最新版本code

    public String versionName;   //最新版本名称

    public String updateLog;     //更新内容

    public String apkUrl;        //下载地址

    public long apkSize;         //下载文件的大小【单位：KB】

    public int updateStatus;     //0代表不更新，1代表有版本更新，不需要强制升级，2代表有版本更新，需要强制升级

    //就是用户不更新的话，程序将无法正常使用。只需要服务端返回UpdateStatus字段为2即可。
    //当然如果你自定义请求返回api的话，只需要设置UpdateEntity的mIsForce字段为true即可。


    public boolean isHasUpdate() {
        return hasUpdate;
    }

    public CustomResult setHasUpdate(boolean hasUpdate) {
        this.hasUpdate = hasUpdate;
        return this;
    }

    public boolean isIgnorable() {
        return isIgnorable;
    }

    public CustomResult setIgnorable(boolean ignorable) {
        isIgnorable = ignorable;
        return this;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public CustomResult setVersionCode(int versionCode) {
        this.versionCode = versionCode;
        return this;
    }

    public String getVersionName() {
        return versionName;
    }

    public CustomResult setVersionName(String versionName) {
        this.versionName = versionName;
        return this;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public CustomResult setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
        return this;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public CustomResult setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
        return this;
    }

    public long getApkSize() {
        return apkSize;
    }

    public CustomResult setApkSize(long apkSize) {
        this.apkSize = apkSize;
        return this;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public CustomResult setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
        return this;
    }
}

/*
{
  "hasUpdate": true,
  "isIgnorable": true,
  "versionCode": 3,
  "versionName": "1.0.2",
  "updateLog": "\r\n1、优化api接口。\r\n2、添加使用demo演示。\r\n3、新增自定义更新服务API接口。\r\n4、优化更新提示界面。",
  "apkUrl": "https://xuexiangjys.oss-cn-shanghai.aliyuncs.com/apk/xupdate_demo_1.0.2.apk",
  "apkSize": 4096
}
 */
