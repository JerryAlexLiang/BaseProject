package com.liang.mydatepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.liang.mydatepicker.datepicker.CustomDatePicker;
import com.liang.mydatepicker.utils.DateFormatUtils;
import com.liang.mydatepicker.utils.DateUtils;

/**
 * 创建日期：2020/8/3 on 3:30 PM
 * 描述: 日期时间选择器Demo
 * 作者: liangyang
 */
public class DatePickerActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvSelectedDate, mTvSelectedTime;
    private CustomDatePicker mDatePicker, mDatePickerFirst, mTimerPicker;
    private TextView tvDate, tvDateFirst;
    private TextView tvTime;
    private String selectedDate;
    private String selectedDateFirst;
    private Button button;
    private String parmDate;
    private String parmDateFirst;
    private String parmTime;
    private TextView mTvSelectedDateFirst;
    private String oldDate;
    private String todayDate;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DatePickerActivity.class);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        setTitle("组件化-ARouter路由-DatePicker");

        button = (Button) findViewById(R.id.btn_submit);
        tvDate = (TextView) findViewById(R.id.tv_content_date);
        tvDateFirst = (TextView) findViewById(R.id.tv_content_date_first);
        tvTime = (TextView) findViewById(R.id.tv_content_time);

        findViewById(R.id.ll_date).setOnClickListener(this);
        mTvSelectedDate = findViewById(R.id.tv_selected_date);
        initDatePicker();

        findViewById(R.id.ll_date_first).setOnClickListener(this);
        mTvSelectedDateFirst = (TextView) findViewById(R.id.tv_selected_date_first);

        //获取当前日期
        todayDate = DateUtils.getOldDate(0);
        parmDate = todayDate.replaceAll("[[\\s-:punct:]]", "");
        ;

        //获取6天前的日期
        oldDate = DateUtils.getOldDate(-6);
        parmDateFirst = oldDate.replaceAll("[[\\s-:punct:]]", "");
        initDatePickerFirst();

        findViewById(R.id.ll_time).setOnClickListener(this);
        mTvSelectedTime = findViewById(R.id.tv_selected_time);
        initTimerPicker();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInterval = Integer.valueOf(parmDate) - Integer.valueOf(parmDateFirst);
                System.out.println("-=-=-=-=->>>>5   " + timeInterval);
                if (parmDate != null && parmDateFirst != null && timeInterval < 7 && timeInterval >= 0) {
                    Toast.makeText(DatePickerActivity.this, "选中" + parmDate + "\n" + parmDateFirst
                            + "\n" + "时间间隔:  " + timeInterval + " 天" + "\n" + "启始共: " + (timeInterval + 1) + " 天", Toast.LENGTH_SHORT).show();
                    System.out.println("-=-=-=-=->>>>4   " + parmDate + "     " + parmDateFirst);
                } else if (timeInterval > 6) {
                    Toast.makeText(DatePickerActivity.this, "启始共: " + (timeInterval + 1) + " 天"
                            + "\n" + "时间区间大于7天,请从新选择!", Toast.LENGTH_SHORT).show();
                } else if (timeInterval < 0) {
                    Toast.makeText(DatePickerActivity.this, "启始共: " + (timeInterval + 1) + " 天"
                            + "\n" + "时间周期不符合规范，请重新选择!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatePicker.onDestroy();
        mDatePickerFirst.onDestroy();
        mTimerPicker.onDestroy();

    }

    private void initDatePickerFirst() {
        long beginTimestampFirst = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestampFirst = System.currentTimeMillis();

        mTvSelectedDateFirst.setText(oldDate);

        //通过时间戳初始化日期，毫秒级别
        mDatePickerFirst = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                selectedDateFirst = DateFormatUtils.long2Str(timestamp, false);
                mTvSelectedDateFirst.setText(selectedDateFirst);
                tvDateFirst.setText("开始选中日期:  " + DateFormatUtils.long2Str(timestamp, false));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("-=-=-=-=->>>>3  " + tvDate.getText().toString());
                        parmDateFirst = selectedDateFirst.replaceAll("[[\\s-:punct:]]", "");
                    }
                });
            }
        }, beginTimestampFirst, endTimestampFirst);
        // 不允许点击屏幕或物理返回键关闭
        mDatePickerFirst.setCancelable(false);
        // 不显示时和分
        mDatePickerFirst.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePickerFirst.setScrollLoop(false);
        // 不允许滚动动画
        mDatePickerFirst.setCanShowAnim(false);
    }

    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = System.currentTimeMillis();

        mTvSelectedDate.setText(DateFormatUtils.long2Str(endTimestamp, false));

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                selectedDate = DateFormatUtils.long2Str(timestamp, false);
                mTvSelectedDate.setText(selectedDate);
                tvDate.setText("选中日期:  " + DateFormatUtils.long2Str(timestamp, false));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("-=-=-=-=->>>>2   " + tvDate.getText().toString());
                        parmDate = selectedDate.replaceAll("[[\\s-:punct:]]", "");
                    }
                });

            }
        }, beginTimestamp, endTimestamp);
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false);

    }

    private void initTimerPicker() {
        String beginTime = "2018-10-17 18:00";
        String endTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);

        mTvSelectedTime.setText(endTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(final long timestamp) {
                mTvSelectedTime.setText(DateFormatUtils.long2Str(timestamp, true));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("-=-=-=-=->>>>5   " + tvTime.getText().toString());
                        parmTime = DateFormatUtils.long2Str(timestamp, true);
                    }
                });
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mTimerPicker.setCancelable(true);
        // 显示时和分
        mTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mTimerPicker.setScrollLoop(true);
        // 允许滚动动画
        mTimerPicker.setCanShowAnim(true);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_date) {
            // 日期格式为yyyy-MM-dd
            mDatePicker.show(mTvSelectedDate.getText().toString());
        } else if (id == R.id.ll_date_first) {
            mDatePickerFirst.show(oldDate);
        } else if (id == R.id.ll_time) {
            // 日期格式为yyyy-MM-dd HH:mm
            mTimerPicker.show(mTvSelectedTime.getText().toString());
        }
    }

}