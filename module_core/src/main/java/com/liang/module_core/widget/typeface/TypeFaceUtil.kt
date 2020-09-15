/*
 * Copyright (c) 2020. vipyinzhiwei <vipyinzhiwei@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liang.module_core.widget.typeface

import android.graphics.Typeface
import com.liang.module_core.app.BaseApplication

/**
 * 创建日期:2020/9/15 on 1:59 PM
 * 描述: 自定义字体工具类
 * 作者: 杨亮
 */
object TypeFaceUtil {

    const val FZLL_TYPEFACE = 1

    const val FZDB1_TYPEFACE = 2

    const val FUTURA_TYPEFACE = 3

    const val DIN_TYPEFACE = 4

    const val LOBSTER_TYPEFACE = 5

    private var fzlLTypeface: Typeface? = null

    private var fzdb1Typeface: Typeface? = null

    private var futuraTypeface: Typeface? = null

    private var dinTypeface: Typeface? = null

    private var lobsterTypeface: Typeface? = null

    fun getFzlLTypeface() = if (fzlLTypeface == null) {
        try {
            Typeface.createFromAsset(BaseApplication.mContext.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        } catch (e: RuntimeException) {
            Typeface.DEFAULT
        }
    } else {
        fzlLTypeface!!
    }

    fun getFzdb1Typeface() = if (fzdb1Typeface == null) {
        try {
            Typeface.createFromAsset(BaseApplication.mContext.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        } catch (e: RuntimeException) {
            Typeface.DEFAULT
        }
    } else {
        fzdb1Typeface!!
    }

    fun getFuturaTypeface() = if (futuraTypeface == null) {
        try {
            Typeface.createFromAsset(BaseApplication.mContext.assets, "fonts/Futura-CondensedMedium.ttf")
        } catch (e: RuntimeException) {
            Typeface.DEFAULT
        }
    } else {
        futuraTypeface!!
    }

    fun getDinTypeface() = if (dinTypeface == null) {
        try {
            Typeface.createFromAsset(BaseApplication.mContext.assets, "fonts/DIN-Condensed-Bold.ttf")
        } catch (e: RuntimeException) {
            Typeface.DEFAULT
        }

    } else {
        dinTypeface!!
    }

    fun getLobsterTypeface() = if (lobsterTypeface == null) {
        try {
            Typeface.createFromAsset(BaseApplication.mContext.assets, "fonts/Lobster-1.4.otf")
        } catch (e: RuntimeException) {
            Typeface.DEFAULT
        }

    } else {
        lobsterTypeface!!
    }

}