package com.liang.module_core_java.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

/**
 * 创建日期：2019/3/5 on 19:13
 * 描述: 自定义的RadioGroup,实现radioButton多行多列
 * 作者: liangyang
 */
public class CustomRadioGroup extends LinearLayout {

    // holds the checked id; the selection is empty by default
    private int mCheckedId = -1;

    // tracks children radio buttons checked state
    private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;

    // when true, mOnCheckedChangeListener discards events
    private boolean mProtectFromCheckedChange = false;

    private OnCheckedChangeListener mOnCheckedChangeListener;

    private PassThroughHierarchyChangeListener mPassThroughListener;

    public CustomRadioGroup(Context context) {
        super(context);
        setOrientation(VERTICAL);
        init();
    }

    public CustomRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mChildOnCheckedChangeListener = new CheckedStateTracker();
        mPassThroughListener = new PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(mPassThroughListener);
    }

    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        // the user listener is delegated to our pass-through listener
        mPassThroughListener.mOnHierarchyChangeListener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // checks the appropriate radio button as requested in the XML file
        if (mCheckedId != -1) {
            mProtectFromCheckedChange = true;

            setCheckedStateForView(mCheckedId, true);

            mProtectFromCheckedChange = false;
            setCheckedId(mCheckedId);
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {

        if (child instanceof RadioButton) {

            final RadioButton button = (RadioButton) child;

            if (button.isChecked()) {

                mProtectFromCheckedChange = true;

                if (mCheckedId != -1) {

                    setCheckedStateForView(mCheckedId, false);

                }
                mProtectFromCheckedChange = false;
                setCheckedId(button.getId());
            }
        } else if (child instanceof ViewGroup) {

            int num = ((ViewGroup) child).getChildCount();

            for (int i = 0; i < num; i++) {//这里要循环

                final RadioButton button = findRadioButton((ViewGroup) (((ViewGroup) child).getChildAt(i)));

                if (button.isChecked()) {

                    mProtectFromCheckedChange = true;

                    if (mCheckedId != -1) {
                        setCheckedStateForView(mCheckedId, false);
                    }
                    mProtectFromCheckedChange = false;
                    setCheckedId(button.getId());
                }
            }
        }
        super.addView(child, index, params);
    }

    /**
     * 查找radioButton控件*
     */
    public RadioButton findRadioButton(ViewGroup group) {

        RadioButton resBtn = null;

        int len = group.getChildCount();

        for (int i = 0; i < len; i++) {
            if (group.getChildAt(i) instanceof RadioButton) {
                resBtn = (RadioButton) group.getChildAt(i);
                break;
            } else if (group.getChildAt(i) instanceof ViewGroup) {
                resBtn = findRadioButton((ViewGroup) group.getChildAt(i));
                break;
            }
        }
        return resBtn;
    }

    /**
     * Sets the selection to the radio button whose identifier is passed in
     * parameter. Using -1 as the selection identifier clears the selection;
     * such an operation is equivalent to invoking {@link #clearCheck()}.
     *
     * @param id the unique id of the radio button to select in this group
     * @see #getCheckedRadioButtonId()
     * @see #clearCheck()
     */
    public void check(int id) {
        if (id != -1 && (id == mCheckedId)) {
            return;
        }

        if (mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, false);
        }

        if (id != -1) {
            setCheckedStateForView(id, true);
        }
        setCheckedId(id);
    }

    private void setCheckedId(int id) {
        mCheckedId = id;

        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(this, mCheckedId);
        }
    }

    private void setCheckedStateForView(int viewId, boolean checked) {

        View checkedView = findViewById(viewId);

        if (checkedView != null && checkedView instanceof RadioButton) {
            ((RadioButton) checkedView).setChecked(checked);
        }

    }

    /**
     * Returns the identifier of the selected radio button in this group. Upon
     * empty selection, the returned value is -1.
     *
     * @return the unique id of the selected radio button in this group
     * @see #check(int)
     * @see #clearCheck()
     */
    public int getCheckedRadioButtonId() {
        return mCheckedId;
    }

    /**
     * Clears the selection. When the selection is cleared, no radio button in
     * this group is selected and {@link #getCheckedRadioButtonId()} returns
     * null.
     *
     * @see #check(int)
     * @see #getCheckedRadioButtonId()
     */
    public void clearCheck() {
        check(-1);
    }

    /**
     * Register a callback to be invoked when the checked radio button changes
     * in this group.
     *
     * @param listener the callback to call on checked state change
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomRadioGroup.LayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof CustomRadioGroup.LayoutParams;
    }

    @Override
    protected LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    /**
     * This set of layout parameters defaults the width and the height of the
     * children to {@link #WRAP_CONTENT} when they are not specified in the XML
     * file. Otherwise, this class ussed the value read from the XML file.
     * See {@link android.R.styleable#LinearLayout_Layout LinearLayout
     * Attributes} for a list of all child view attributes that this class
     * supports.
     */
    public static class LayoutParams extends LinearLayout.LayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(int w, int h, float initWeight) {
            super(w, h, initWeight);
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        /**
         * Fixes the child's width to
         * {@link android.view.ViewGroup.LayoutParams#WRAP_CONTENT} and the
         * child's height to
         * {@link android.view.ViewGroup.LayoutParams#WRAP_CONTENT} when not
         * specified in the XML file.
         *
         * @param a          the styled attributes set
         * @param widthAttr  the width attribute to fetch
         * @param heightAttr the height attribute to fetch
         */
        @Override
        protected void setBaseAttributes(TypedArray a, int widthAttr, int heightAttr) {
            if (a.hasValue(widthAttr)) {
                width = a.getLayoutDimension(widthAttr, "layout_width");
            } else {
                width = WRAP_CONTENT;
            }
            if (a.hasValue(heightAttr)) {
                height = a.getLayoutDimension(heightAttr, "layout_height");
            } else {
                height = WRAP_CONTENT;
            }
        }
    }

    /**
     * Interface definition for a callback to be invoked when the checked radio
     * button changed in this group.
     */
    public interface OnCheckedChangeListener {
        /**
         * Called when the checked radio button has changed. When the selection
         * is cleared, checkedId is -1.
         *
         * @param group     the group in which the checked radio button has changed
         * @param checkedId the unique identifier of the newly checked radio button
         */
        public void onCheckedChanged(CustomRadioGroup group, int checkedId);
    }

    private class CheckedStateTracker implements CompoundButton.OnCheckedChangeListener {

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // prevents from infinite recursion
            if (mProtectFromCheckedChange) {
                return;
            }

            mProtectFromCheckedChange = true;

            if (mCheckedId != -1) {
                setCheckedStateForView(mCheckedId, false);
            }

            mProtectFromCheckedChange = false;

            int id = buttonView.getId();

            setCheckedId(id);
        }
    }

    /**
     * A pass-through listener acts upon the events and dispatches them to
     * another listener. This allows the table layout to set its own internal
     * hierarchy change listener without preventing the user to setup his.
     */
    private class PassThroughHierarchyChangeListener implements ViewGroup.OnHierarchyChangeListener {

        private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

        public void onChildViewAdded(View parent, View child) {

            if (parent == CustomRadioGroup.this && child instanceof RadioButton) {

                int id = child.getId();

                // generates an id if it's missing
                if (id == View.NO_ID) {

                    id = child.hashCode();

                    child.setId(id);
                }

                ((RadioButton) child).setOnCheckedChangeListener(mChildOnCheckedChangeListener);

            } else if (parent == CustomRadioGroup.this && child instanceof ViewGroup) {

                int num = ((ViewGroup) child).getChildCount();

                for (int i = 0; i < num; i++) {//这里要循环

                    RadioButton btn = findRadioButton((ViewGroup) (((ViewGroup) child).getChildAt(i)));

                    int id = btn.getId();

                    // generates an id if it's missing
                    if (id == View.NO_ID) {
                        id = btn.hashCode();
                        btn.setId(id);
                    }

                    btn.setOnCheckedChangeListener(mChildOnCheckedChangeListener);
                }
            }

            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewAdded(parent, child);
            }

        }

        public void onChildViewRemoved(View parent, View child) {

            if (parent == CustomRadioGroup.this && child instanceof RadioButton) {

                ((RadioButton) child).setOnCheckedChangeListener(null);

            } else if (parent == CustomRadioGroup.this

                    && child instanceof ViewGroup) {

                int num = ((ViewGroup) child).getChildCount();

                for (int i = 0; i < num; i++) {//这里要循环
                    findRadioButton((ViewGroup) (((ViewGroup) child).getChildAt(i))).setOnCheckedChangeListener(null);
                }
            }

            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
            }

        }

    }
}
