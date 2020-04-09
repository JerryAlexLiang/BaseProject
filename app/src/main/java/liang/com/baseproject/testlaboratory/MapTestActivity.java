package liang.com.baseproject.testlaboratory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import liang.com.baseproject.Constant.Constant;
import liang.com.baseproject.R;
import liang.com.baseproject.map.MapLocationActivity;
import liang.com.baseproject.utils.SettingUtils;
import liang.com.baseproject.utils.TitleBarUtils;
import liang.com.baseproject.widget.CustomScrollRelativeLayout;

public class MapTestActivity extends AppCompatActivity {

    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.collapsingLayout)
    CollapsingToolbarLayout collapsingLayout;
    @BindView(R.id.appBar)
    AppBarLayout appBarLayout;
    @BindView(R.id.jumpUrl)
    TextView jumpUrl;
    @BindView(R.id.nestScrollView)
    NestedScrollView nestScrollView;
    @BindView(R.id.loadingTime)
    TextView loadingTime;
    @BindView(R.id.mapView2)
    MapView mapView2;
    @BindView(R.id.custom_scroll_container2)
    CustomScrollRelativeLayout customScrollContainer2;
    @BindView(R.id.base_toolbar_left_icon)
    ImageView baseToolbarLeftIcon;
    @BindView(R.id.base_toolbar_left_tv)
    TextView baseToolbarLeftTv;
    @BindView(R.id.base_actionbar_left2_icon)
    ImageView baseActionbarLeft2Icon;
    @BindView(R.id.toolbar_back_layout)
    RelativeLayout toolbarBackLayout;
    @BindView(R.id.base_toolbar_title)
    TextView baseToolbarTitle;
    @BindView(R.id.base_toolbar_right_icon)
    ImageView baseToolbarRightIcon;
    @BindView(R.id.base_toolbar_right_tv)
    TextView baseToolbarRightTv;
    @BindView(R.id.toolbar_right_layout)
    RelativeLayout toolbarRightLayout;
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    private AMap aMap;
    private AMap aMap2;

    private boolean mDarkTheme;

    private CoordinatorLayout.LayoutParams layoutParams;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MapTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_test);
        //设置沉浸式系统栏-深色状态栏字体
        TitleBarUtils.changeStatusBarTextColor(this, true);
        ButterKnife.bind(this);
        initView();

        mDarkTheme = SettingUtils.getInstance().isDarkTheme();
        mapView.onCreate(savedInstanceState);
        mapView2.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        if (aMap2 == null) {
            aMap2 = mapView2.getMap();
        }

        aMap2.animateCamera(CameraUpdateFactory.newLatLngZoom(Constant.HANZGHOU, 16));

        if (mDarkTheme) {
            //黑夜模式 - 夜景地图模式
            aMap.setMapType(AMap.MAP_TYPE_NIGHT);
            aMap2.setMapType(AMap.MAP_TYPE_NIGHT);
        } else {
            //标准模式 - 矢量地图模式
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);
            aMap2.setMapType(AMap.MAP_TYPE_NORMAL);
        }

        //自定义的RelativeLayout-CollapsingToolbarLayout-滑动冲突
        customScrollContainer2.setCollapsingToolbarLayout(collapsingLayout);

        layoutParams = (CoordinatorLayout.LayoutParams) nestScrollView.getLayoutParams();
        layoutParams.setMargins(30, 0, 30, 0);
//        layoutParams.setMargins(0, 0, 0, 0);
        nestScrollView.setLayoutParams(layoutParams);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                float a = (float) 30 / appBarLayout.getTotalScrollRange();
                int side = (int) Math.rint(a * i + 30);
//                layoutParams.setMargins(side, 0, side, 0);
                layoutParams.setMargins(0, 0, 0, 0);
                nestScrollView.setLayoutParams(layoutParams);
                if (Math.abs(i) > 0) {
                    float alpha = (float) Math.abs(i) / appBarLayout.getTotalScrollRange();
                    appBarLayout.setAlpha(alpha);
                    nestScrollView.getBackground().mutate().setAlpha(Math.round(alpha * 255));
                } else {
                    appBarLayout.setAlpha(0);
                    nestScrollView.getBackground().mutate().setAlpha(0);
                }
            }
        });

    }

    private void initView() {
        baseToolbarLeftIcon.setVisibility(View.VISIBLE);
        baseToolbarRightIcon.setVisibility(View.VISIBLE);
        collapsingLayout.setTitle("Location");
        collapsingLayout.setCollapsedTitleGravity(Gravity.CENTER);
        collapsingLayout.setExpandedTitleGravity(Gravity.CENTER);
        //设置CollapsingToolbarLayout扩展状态标题栏颜色
        collapsingLayout.setExpandedTitleColor(Color.YELLOW);
        //设置CollapsingToolbarLayout收缩状态标题栏颜色
        collapsingLayout.setCollapsedTitleTextColor(Color.WHITE);
    }

    @OnClick({R.id.base_toolbar_left_icon, R.id.jumpUrl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_toolbar_left_icon:
                finish();
                break;

            case R.id.jumpUrl:
                MapLocationActivity.actionStart(MapTestActivity.this);
                break;
        }
    }
}
