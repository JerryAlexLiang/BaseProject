package liang.com.baseproject.mine;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMapException;
import com.amap.api.maps.offlinemap.OfflineMapActivity;
import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.bigkoo.alertview.AlertView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.luck.picture.lib.entity.LocalMedia;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import liang.com.baseproject.Constant.Constant;
import liang.com.baseproject.R;
import liang.com.baseproject.activity.MainHomeActivity;
import liang.com.baseproject.activity.SinglePictureActivity;
import liang.com.baseproject.base.MVPBaseFragment;
import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.event.LoginEvent;
import liang.com.baseproject.login.activity.LoginActivity;
import liang.com.baseproject.login.entity.Userbean;
import liang.com.baseproject.mine.activity.ReadLaterActivity;
import liang.com.baseproject.setting.activity.AppSettingActivity;
import liang.com.baseproject.utils.AnimationUtils;
import liang.com.baseproject.utils.LogUtil;
import liang.com.baseproject.utils.PictureSelectorUtils;
import liang.com.baseproject.utils.ToastUtil;
import liang.com.baseproject.utils.UserLoginUtils;
import liang.com.baseproject.widget.popupwindow.CustomPopupWindow;

import static com.luck.picture.lib.config.PictureConfig.MULTIPLE;
import static com.luck.picture.lib.config.PictureConfig.TYPE_IMAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends MVPBaseFragment implements CustomPopupWindow.ViewInterface {

    private static final String TAG = MineFragment.class.getSimpleName();
    @BindView(R.id.iv_blur)
    ImageView ivBlur;
    @BindView(R.id.civ_user_icon)
    CircleImageView civUserIcon;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_id)
    TextView tvUserId;
    @BindView(R.id.ll_user_id)
    LinearLayout llUserId;
    @BindView(R.id.tv_user_coin)
    TextView tvUserCoin;
    @BindView(R.id.tv_user_level)
    TextView tvUserLevel;
    @BindView(R.id.ll_user_coin)
    LinearLayout llUserCoin;
    @BindView(R.id.user_info_container)
    RelativeLayout userInfoContainer;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.rl_user_info)
    RelativeLayout rlUserInfo;
    Unbinder unbinder1;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;
    @BindView(R.id.ll_read_later)
    LinearLayout llReadLater;
    @BindView(R.id.rl_page_container)
    RelativeLayout rlPageContainer;
    @BindView(R.id.ll_offline_map)
    LinearLayout llOfflineMap;
    Unbinder unbinder2;
    private MainHomeActivity mActivity;
    private boolean login;

    private CustomPopupWindow customPopupWindow;
    private String imagePath;
    private AlertView alertView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainHomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder2 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置SmartRefreshLayout容器越界回弹
        initSmartRefreshLayoutPureScroll();
        //加载数据
        initUserInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder2.unbind();
    }

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    protected MVPBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_four;
    }

    @Override
    protected void initView(View rootView) {
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected boolean isSetRefreshHeader() {
        return false;
    }

    @Override
    protected boolean isSetRefreshFooter() {
        return false;
    }

    /**
     * 接收登录消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        if (isDetached()) {
            return;
        }
        boolean login = event.isLogin();
        Userbean loginUserBean = UserLoginUtils.getInstance().getLoginUserBean();
        LogUtil.e(TAG, "登录用户信息Event: " + new Gson().toJson(loginUserBean) + "  是否已登录: " + login);
        //更新用户UI
        initUserInfo();
        //请求获取积分接口
    }


    private void initUserInfo() {
        if (UserLoginUtils.getInstance().isLogin()) {
            //已登录，填充数据
            Userbean loginUserBean = UserLoginUtils.getInstance().getLoginUserBean();
            LogUtil.e(TAG, "登录用户信息: " + new Gson().toJson(loginUserBean));

            String localUserIcon = UserLoginUtils.getInstance().getLocalUserIcon();
            String localBg = UserLoginUtils.getInstance().getLocalBg();

            LogUtil.e(TAG, "头像地址: " + localUserIcon + "\n" + "背景地址: " + localBg);

            if (!TextUtils.isEmpty(localUserIcon)) {
                Glide.with(mActivity).asBitmap().load(localUserIcon).into(civUserIcon);
            } else {
                Glide.with(mActivity).asBitmap().load(R.drawable.icon_user_logo).into(civUserIcon);
            }

            if (!TextUtils.isEmpty(localBg)) {
                RequestOptions options = new RequestOptions()
                        .transform(new BlurTransformation(20));
                Glide.with(mActivity).asBitmap().apply(options).load(localBg).into(ivBlur);
            } else {
                Glide.with(mActivity).asBitmap().load(R.drawable.icon_header).into(ivBlur);
            }

            tvUserName.setText(loginUserBean.getUsername());
            tvUserId.setText(loginUserBean.getId() + "");
            llUserId.setVisibility(View.VISIBLE);
            llUserCoin.setVisibility(View.VISIBLE);
        } else {
            //未登录，初始化视图
            civUserIcon.setImageResource(R.drawable.icon_user_logo);
            civUserIcon.setCircleBackgroundColorResource(R.color.translate);
            Glide.with(mActivity).asBitmap().load(R.drawable.icon_header).into(ivBlur);
            tvUserName.setText(getString(R.string.go_to_login));
            llUserId.setVisibility(View.GONE);
            llUserCoin.setVisibility(View.GONE);
        }
    }

    /**
     * 设置SmartRefreshLayout容器越界回弹
     */
    private void initSmartRefreshLayoutPureScroll() {
        smartRefreshLayout.setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                ivBlur.getLayoutParams().height = rlUserInfo.getMeasuredHeight() + offset;
                ivBlur.requestLayout();
            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {

            }

            @Override
            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {
                ivBlur.getLayoutParams().height = rlUserInfo.getMeasuredHeight() - offset;
                ivBlur.requestLayout();
            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

            }
        });

    }

    @OnLongClick({R.id.civ_user_icon, R.id.rl_user_info})
    public boolean onViewLongClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_user_icon:
                if (UserLoginUtils.getInstance().doIfNeedLogin()) {
                    String localUserIcon = UserLoginUtils.getInstance().getLocalUserIcon();
                    if (!TextUtils.isEmpty(localUserIcon)) {
                        //弹出选择框(包含查看大图功能)
                        showTakePhotoWindow(0);
//                    AnimationUtils.pageShowScaleAnimator(getActivity(), rlPageContainer);
//                    AnimationUtils.pageShowScaleAnimator(getActivity(), rlPageContainer);
                    } else {
                        //弹出选择框(不包含查看大图功能)
                        showTakePhotoWindow(1);
//                    AnimationUtils.pageShowScaleAnimator(getActivity(), rlPageContainer);
//                    AnimationUtils.pageShowScaleAnimator(getActivity(), rlPageContainer);
                    }
                }
                break;

            case R.id.rl_user_info:
                if (UserLoginUtils.getInstance().doIfNeedLogin()) {
                    String localBg = UserLoginUtils.getInstance().getLocalBg();
                    if (!TextUtils.isEmpty(localBg)) {
                        //弹出选择框(包含查看大图功能)
                        showTakePhotoWindow(2);
                    } else {
                        //弹出选择框(不包含查看大图功能)
                        showTakePhotoWindow(3);
                    }
                }
                break;

            default:
                break;
        }
        //设置返回true,拦截后不触发单点响应事件
        return true;
    }

    private void showTakePhotoWindow(int flag) {
        //flag: 0 包含查看大图功能, 1 不包含查看大图功能
        if (customPopupWindow != null && customPopupWindow.isShowing()) {
            return;
        }
        View upView = LayoutInflater.from(getContext()).inflate(R.layout.pick_image_custom_popupwindow_layout, null);
        //测量View的宽高
        CustomPopupWindow.measureWidthAndHeight(upView);
        customPopupWindow = new CustomPopupWindow.Builder(getContext())
                .setView(R.layout.pick_image_custom_popupwindow_layout)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.5f)  //取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this, flag)
                .create();
        customPopupWindow.showAtLocation(upView, Gravity.BOTTOM, 0, 0);

        AnimationUtils.pageShowScaleAnimator(mActivity, mActivity.getMainPageContainer());

        customPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                AnimationUtils.pageHideScaleAnimator(rlPageContainer);
                AnimationUtils.pageHideScaleAnimator(mActivity.getMainPageContainer());
            }
        });
    }

    @Override
    public void getChildView(View view, int layoutResId, int flag) {

        TextView mPopupWindowTitle = view.findViewById(R.id.tv_popup_window_title);
        TextView mSelectPhotoBtn = view.findViewById(R.id.btn_select_photo);
        TextView mTakePhotoBtn = view.findViewById(R.id.btn_take_photo);
        TextView mRecordVideoBtn = view.findViewById(R.id.btn_record_video);
        TextView mLookImageBtn = view.findViewById(R.id.btn_look_image);
        TextView mRecoverBgBtn = view.findViewById(R.id.btn_recover_bg);
        TextView mDismissWindowBtn = view.findViewById(R.id.btn_dismiss_window);

        View viewLineOne = view.findViewById(R.id.view_line_one);
        View viewLineTwo = view.findViewById(R.id.view_line_two);
        View viewLineThree = view.findViewById(R.id.view_line_three);
        View viewLineFour = view.findViewById(R.id.view_line_four);
        View viewLineFive = view.findViewById(R.id.view_line_five);

        mPopupWindowTitle.setText("上传头像");
        mRecordVideoBtn.setVisibility(View.GONE);
        viewLineThree.setVisibility(View.GONE);

        //flag: 0 包含查看大图功能, 1 不包含查看大图功能
        if (flag == 0) {
            viewLineFour.setVisibility(View.VISIBLE);
            mLookImageBtn.setVisibility(View.VISIBLE);

            mSelectPhotoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customPopupWindow != null) {
                        customPopupWindow.dismiss();
                    }
//                AnimationUtils.pageHideScaleAnimator(rlPageContainer);
                    AnimationUtils.pageHideScaleAnimator(mActivity.getMainPageContainer());

                    PictureSelectorUtils.openGallery(MineFragment.this, Constant.REQUEST_CODE_SELECT_USER_ICON, MULTIPLE, TYPE_IMAGE,
                            true, false, true, 9);
                }
            });

            mTakePhotoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customPopupWindow != null) {
                        customPopupWindow.dismiss();
                    }
//                AnimationUtils.pageHideScaleAnimator(rlPageContainer);
                    AnimationUtils.pageHideScaleAnimator(mActivity.getMainPageContainer());
                }
            });

            mLookImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customPopupWindow != null) {
                        customPopupWindow.dismiss();
                    }
//                AnimationUtils.pageHideScaleAnimator(rlPageContainer);
                    AnimationUtils.pageHideScaleAnimator(mActivity.getMainPageContainer());

                    String localUserIcon = UserLoginUtils.getInstance().getLocalUserIcon();
                    SinglePictureActivity.actionStart(mActivity, localUserIcon, System.currentTimeMillis() + "");
                }
            });

            mDismissWindowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customPopupWindow != null) {
                        customPopupWindow.dismiss();
                    }
//                AnimationUtils.pageHideScaleAnimator(rlPageContainer);
                    AnimationUtils.pageHideScaleAnimator(mActivity.getMainPageContainer());
                }
            });
        } else if (flag == 1) {
            viewLineFour.setVisibility(View.GONE);
            mLookImageBtn.setVisibility(View.GONE);

            mSelectPhotoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customPopupWindow != null) {
                        customPopupWindow.dismiss();
                    }
//                AnimationUtils.pageHideScaleAnimator(rlPageContainer);
                    AnimationUtils.pageHideScaleAnimator(mActivity.getMainPageContainer());

                    PictureSelectorUtils.openGallery(MineFragment.this, Constant.REQUEST_CODE_SELECT_USER_ICON, MULTIPLE, TYPE_IMAGE,
                            false, false, true, 9);
                }
            });

            mTakePhotoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customPopupWindow != null) {
                        customPopupWindow.dismiss();
                    }
//                AnimationUtils.pageHideScaleAnimator(rlPageContainer);
                    AnimationUtils.pageHideScaleAnimator(mActivity.getMainPageContainer());
                }
            });

            mDismissWindowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customPopupWindow != null) {
                        customPopupWindow.dismiss();
                    }
//                AnimationUtils.pageHideScaleAnimator(rlPageContainer);
                    AnimationUtils.pageHideScaleAnimator(mActivity.getMainPageContainer());
                }
            });

        } else if (flag == 2) {
            viewLineFour.setVisibility(View.VISIBLE);
            mLookImageBtn.setVisibility(View.VISIBLE);

            viewLineFive.setVisibility(View.VISIBLE);
            mRecoverBgBtn.setVisibility(View.VISIBLE);

            mRecoverBgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customPopupWindow != null) {
                        customPopupWindow.dismiss();
                    }
                    AnimationUtils.pageHideScaleAnimator(mActivity.getMainPageContainer());

                    UserLoginUtils.getInstance().setLocalBg("");
                    Glide.with(mActivity).asBitmap().load(R.drawable.icon_header).into(ivBlur);
                    //更新DrawerLayout用户信息
                    new LoginEvent(true).post();
                }
            });

            mSelectPhotoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customPopupWindow != null) {
                        customPopupWindow.dismiss();
                    }
                    AnimationUtils.pageHideScaleAnimator(mActivity.getMainPageContainer());

                    PictureSelectorUtils.openGallery(MineFragment.this, Constant.REQUEST_CODE_SELECT_BG, MULTIPLE, TYPE_IMAGE,
                            false, false, true, 9);
                }
            });

            mTakePhotoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customPopupWindow != null) {
                        customPopupWindow.dismiss();
                    }
                    AnimationUtils.pageHideScaleAnimator(mActivity.getMainPageContainer());
                }
            });

            mLookImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customPopupWindow != null) {
                        customPopupWindow.dismiss();
                    }
                    AnimationUtils.pageHideScaleAnimator(mActivity.getMainPageContainer());
                    String localBg = UserLoginUtils.getInstance().getLocalBg();
                    SinglePictureActivity.actionStart(mActivity, localBg, System.currentTimeMillis() + "");
                }
            });

            mDismissWindowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customPopupWindow != null) {
                        customPopupWindow.dismiss();
                    }
                    AnimationUtils.pageHideScaleAnimator(mActivity.getMainPageContainer());
                }
            });
        } else if (flag == 3) {
            viewLineFour.setVisibility(View.GONE);
            mLookImageBtn.setVisibility(View.GONE);

            mSelectPhotoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customPopupWindow != null) {
                        customPopupWindow.dismiss();
                    }
                    AnimationUtils.pageHideScaleAnimator(mActivity.getMainPageContainer());

                    PictureSelectorUtils.openGallery(MineFragment.this, Constant.REQUEST_CODE_SELECT_BG, MULTIPLE, TYPE_IMAGE,
                            false, false, true, 9);
                }
            });

            mTakePhotoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customPopupWindow != null) {
                        customPopupWindow.dismiss();
                    }
                    AnimationUtils.pageHideScaleAnimator(mActivity.getMainPageContainer());
                }
            });

            mDismissWindowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customPopupWindow != null) {
                        customPopupWindow.dismiss();
                    }
                    AnimationUtils.pageHideScaleAnimator(mActivity.getMainPageContainer());
                }
            });
        }

        //选项Button点击监听事件
//        mSelectPhotoBtn.setOnClickListener(this);
//        mTakePhotoBtn.setOnClickListener(this);
//        mLookImageBtn.setOnClickListener(this);
//        mDismissWindowBtn.setOnClickListener(this);

//        //触摸隐藏PopupWindow
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (customPopupWindow != null) {
//                    customPopupWindow.dismiss();
//                }
//                AnimationUtils.pageHideScaleAnimator( rlPageContainer);
//                //必须返回true，拦截事件
//                return true;
//            }
//        });

    }

    @OnClick({R.id.civ_user_icon, R.id.user_info_container, R.id.ll_setting, R.id.ll_read_later, R.id.rl_user_info, R.id.ll_offline_map})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_user_icon:
                if (UserLoginUtils.getInstance().isLogin()) {
                    onShowToast("跳转个人中心");
                } else {
                    if (Build.VERSION.SDK_INT > 20) {
                        Bundle options = ActivityOptions.makeSceneTransitionAnimation(mActivity, civUserIcon, "navUserIocn").toBundle();
                        Intent intent = new Intent(mActivity, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent, options);
                    } else {
                        LoginActivity.actionStart();
                    }
                }
                break;

            case R.id.rl_user_info:

                break;

            case R.id.user_info_container:
                boolean doIfNeedLogin = UserLoginUtils.getInstance().doIfNeedLogin();
                if (doIfNeedLogin) {
                    ToastUtil.showShortToast("开发中...");
                }
                break;

            case R.id.ll_setting:
                AppSettingActivity.actionStart(mActivity);
                break;

            case R.id.ll_read_later:
                ReadLaterActivity.actionStart(mActivity);
                break;

            case R.id.ll_offline_map:
                startActivity(new Intent(mActivity, OfflineMapActivity.class));
                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_CODE_SELECT_USER_ICON:
                    List<LocalMedia> localMedia = PictureSelectorUtils.forResult(resultCode, data);
                    if (localMedia != null) {
                        LocalMedia media = localMedia.get(0);
                        if (media.isCut() && !media.isCompressed()) {
                            //裁剪未压缩
                            imagePath = media.getCutPath();
                            long cutImageSize = new File(media.getCutPath()).length() / 1024;
                            LogUtil.d(TAG, "裁剪地址: ---> 大小:  " + cutImageSize + "k" + "   裁剪地址:" + media.getCutPath());
                        } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                            //压缩过，或者裁剪同时压缩过，以最终压缩过的图片为准
                            imagePath = media.getCompressPath();
                            long compressImageSize = new File(media.getCompressPath()).length() / 1024;
                            LogUtil.d(TAG, "压缩地址: ---> 大小:  " + compressImageSize + "k" + "   压缩地址:" + media.getCutPath());
                        } else {
                            //原图
                            imagePath = media.getPath();
                            long imageSize = new File(media.getPath()).length() / 1024;
                            LogUtil.d(TAG, "原图地址: ---> 大小:  " + imageSize + "k" + "   原图地址:" + media.getPath());
                        }
                        //UI
                        if (!imagePath.isEmpty()) {
                            Glide.with(mActivity).asBitmap().load(imagePath).into(civUserIcon);
                            UserLoginUtils.getInstance().setLocalUserIcon(imagePath);
                            //更新DrawerLayout用户信息
                            new LoginEvent(true).post();
                        }
                    }
                    break;

                case Constant.REQUEST_CODE_SELECT_BG:
                    List<LocalMedia> localMediaBg = PictureSelectorUtils.forResult(resultCode, data);
                    if (localMediaBg != null) {
                        LocalMedia media = localMediaBg.get(0);
                        if (media.isCut() && !media.isCompressed()) {
                            //裁剪未压缩
                            imagePath = media.getCutPath();
                            long cutImageSize = new File(media.getCutPath()).length() / 1024;
                            LogUtil.d(TAG, "裁剪地址: ---> 大小:  " + cutImageSize + "k" + "   裁剪地址:" + media.getCutPath());
                        } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                            //压缩过，或者裁剪同时压缩过，以最终压缩过的图片为准
                            imagePath = media.getCompressPath();
                            long compressImageSize = new File(media.getCompressPath()).length() / 1024;
                            LogUtil.d(TAG, "压缩地址: ---> 大小:  " + compressImageSize + "k" + "   压缩地址:" + media.getCutPath());
                        } else {
                            //原图
                            imagePath = media.getPath();
                            long imageSize = new File(media.getPath()).length() / 1024;
                            LogUtil.d(TAG, "原图地址: ---> 大小:  " + imageSize + "k" + "   原图地址:" + media.getPath());
                        }
                        //UI
                        if (!imagePath.isEmpty()) {
                            RequestOptions options = new RequestOptions()
                                    .transform(new BlurTransformation(20, 3));
                            Glide.with(mActivity).asBitmap().apply(options).load(imagePath).into(ivBlur);
                            UserLoginUtils.getInstance().setLocalBg(imagePath);
                            //更新DrawerLayout用户信息
                            new LoginEvent(true).post();
                        }
                    }
                    break;
            }
        }
    }

}
