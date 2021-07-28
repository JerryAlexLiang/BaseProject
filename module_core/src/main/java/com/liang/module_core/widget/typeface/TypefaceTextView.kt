package com.liang.module_core.widget.typeface

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.liang.module_core.R

/**
 * 创建日期:2021/3/30 on 10:34 AM
 * 描述: 带有自定义字体TextView
 * 作者: 杨亮
 */
class TypefaceTextView : AppCompatTextView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.TypefaceTextView, 0, 0)
            val typefaceType = typedArray.getInt(R.styleable.TypefaceTextView_typeface, 0)
            typeface = getTypeface(typefaceType)
            typedArray.recycle()
        }
    }

    companion object {
        /**
         * 根据字体类型，获取自定义字体。
         */
        fun getTypeface(typefaceType: Int?) = when (typefaceType) {
            TypeFaceUtil.FZLL_TYPEFACE -> TypeFaceUtil.getFzlLTypeface()
            TypeFaceUtil.FZDB1_TYPEFACE -> TypeFaceUtil.getFzdb1Typeface()
            TypeFaceUtil.FUTURA_TYPEFACE -> TypeFaceUtil.getFuturaTypeface()
            TypeFaceUtil.DIN_TYPEFACE -> TypeFaceUtil.getDinTypeface()
            TypeFaceUtil.LOBSTER_TYPEFACE -> TypeFaceUtil.getLobsterTypeface()
            TypeFaceUtil.XINGKAI_TYPEFACE -> TypeFaceUtil.getXingkaiTypeface()
            TypeFaceUtil.HAPPY_FOUNT_TYPEFACE -> TypeFaceUtil.getHappyFountTypeface()
            else -> Typeface.DEFAULT
        }
    }
}