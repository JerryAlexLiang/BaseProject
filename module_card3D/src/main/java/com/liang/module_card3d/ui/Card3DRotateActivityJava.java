package com.liang.module_card3d.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.liang.module_card3d.R;

public class Card3DRotateActivityJava extends AppCompatActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, Card3DRotateActivityJava.class);
        context.startActivity(intent);
    }

    protected static final String TAG = Card3DRotateActivityJava.class.getSimpleName();
    // 当前显示的bitmap对象
    private static Bitmap bitmap;
    // 图片容器
    private ImageView imageView;
    // 开始按下位置
    private int startX;
    // 当前位置
    private int currentX;
    // 当前图片的编号
    private int scrNum;
    // 图片的总数
    private static int maxNum = 52;
    // 资源图片集合
    private int[] srcs = new int[]{R.drawable.p1, R.drawable.p2,
            R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6,
            R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10,
            R.drawable.p11, R.drawable.p12, R.drawable.p13, R.drawable.p14,
            R.drawable.p15, R.drawable.p16, R.drawable.p17, R.drawable.p18,
            R.drawable.p19, R.drawable.p20, R.drawable.p21, R.drawable.p22,
            R.drawable.p23, R.drawable.p24, R.drawable.p25, R.drawable.p26,
            R.drawable.p27, R.drawable.p28, R.drawable.p29, R.drawable.p30,
            R.drawable.p31, R.drawable.p32, R.drawable.p33, R.drawable.p34,
            R.drawable.p35, R.drawable.p36, R.drawable.p37, R.drawable.p38,
            R.drawable.p39, R.drawable.p40, R.drawable.p41, R.drawable.p42,
            R.drawable.p43, R.drawable.p44, R.drawable.p45, R.drawable.p46,
            R.drawable.p47, R.drawable.p48, R.drawable.p49, R.drawable.p50,
            R.drawable.p51, R.drawable.p52};

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card3_d_rotate);
        imageView = (ImageView) findViewById(R.id.ivCard);

        // 初始化当前显示图片编号
        scrNum = 1;

        imageView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // 开始按下位置
                    startX = (int) event.getX();
                    break;

                case MotionEvent.ACTION_MOVE:
                    // 当前位置
                    currentX = (int) event.getX();
                    // 判断手势滑动方向，并且换图片资源
                    if (currentX - startX > 10) {
                        // 向右滑动修改资源
                        modifySrcR();
                    } else if (currentX - startX < -10) {
                        // 向左滑动修改资源
                        modifySrcL();
                    }

                    // 重置起始位置
                    startX = (int) event.getX();
                    break;

            }
            return true;
        });
    }

    // 向右滑动修改资源
    private void modifySrcR() {

        if (scrNum > maxNum) {
            scrNum = 1;
        }

        if (scrNum > 0) {
            bitmap = BitmapFactory.decodeResource(getResources(),
                    srcs[scrNum - 1]);
            imageView.setImageBitmap(bitmap);
            scrNum++;
        }

    }

    // 向左滑动修改资源
    private void modifySrcL() {
        if (scrNum <= 0) {
            scrNum = maxNum;
        }

        if (scrNum <= maxNum) {
            bitmap = BitmapFactory.decodeResource(getResources(),
                    srcs[scrNum - 1]);
            imageView.setImageBitmap(bitmap);
            scrNum--;
        }
    }

}