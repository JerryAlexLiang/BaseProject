package liang.com.baseproject.mine.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.swipe.SwipeLayout;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import liang.com.baseproject.R;
import liang.com.baseproject.app.MyApplication;
import liang.com.baseproject.entity.TagsBean;
import liang.com.baseproject.mine.entity.ReadLaterBean;
import com.liang.module_core_java.utils.DateUtils;

/**
 * 创建日期：2019/9/9 on 11:07
 * 描述: 稍后阅读列表
 * 作者: liangyang
 */
public class ReadLaterAdapter extends BaseQuickAdapter<ReadLaterBean, BaseViewHolder> {

    private final List<SwipeLayout> mUnCloseSwipLayoutList = new ArrayList<>();

    public ReadLaterAdapter() {
        super(R.layout.rv_swipe_article_list_item);
    }

    /**
     * 关闭所有选项
     */
    public void closeAll(SwipeLayout layout) {
        for (SwipeLayout swipeLayout : mUnCloseSwipLayoutList) {
            if (layout == swipeLayout) {
                continue;
            }
            if (swipeLayout.getOpenStatus() != SwipeLayout.Status.Open) {
                continue;
            }
            swipeLayout.close();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    closeAll(null);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ReadLaterBean item) {
        String author = item.getAuthor();
        String superChapterName = item.getSuperChapterName();
        String chapterName = item.getChapterName();
        String envelopePic = item.getEnvelopePic();
        String link = item.getLink();
        String title = item.getTitle();
        long time = item.getTime();
        String desc = item.getDesc();
        List<TagsBean> tagsBeanList = item.getTagsBeanList();

        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.tv_title, title);
        } else {
            helper.getView(R.id.tv_title).setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(author)) {
            helper.setText(R.id.tv_author, author);
        } else {
            helper.getView(R.id.tv_author).setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(superChapterName)) {
            helper.setText(R.id.tv_super_chapter_name, superChapterName);
        } else {
            helper.getView(R.id.tv_super_chapter_name).setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(chapterName)) {
            helper.setText(R.id.tv_chapter_name, superChapterName);
        } else {
            helper.getView(R.id.tv_chapter_name).setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(envelopePic)) {
            helper.getView(R.id.iv_img).setVisibility(View.VISIBLE);
            RoundedImageView roundedImageView = helper.getView(R.id.iv_img);
            Glide.with(MyApplication.getAppContext()).load(envelopePic).into(roundedImageView);

            if (!TextUtils.isEmpty(desc)){
                helper.getView(R.id.tv_desc).setVisibility(View.VISIBLE);
                TextView tvTitle = helper.getView(R.id.tv_title);
                tvTitle.setSingleLine(true);
                helper.setText(R.id.tv_desc,desc);
            }else {
                helper.getView(R.id.tv_desc).setVisibility(View.GONE);
            }
        } else {
            helper.getView(R.id.iv_img).setVisibility(View.GONE);
            helper.getView(R.id.tv_desc).setVisibility(View.GONE);
            TextView tvTitle = helper.getView(R.id.tv_title);
            tvTitle.setSingleLine(false);
        }

        helper.setText(R.id.tv_time, DateUtils.timeStamp2Date(time, "yyyy-MM-dd"));

        if (tagsBeanList != null && tagsBeanList.size() > 0) {
            helper.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
            if (tagsBeanList.size() > 1) {
                helper.setText(R.id.tv_tag, tagsBeanList.get(0).getName() + "...");
            } else {
                helper.setText(R.id.tv_tag, tagsBeanList.get(0).getName());
            }
        } else {
            helper.getView(R.id.tv_tag).setVisibility(View.GONE);
        }

        SwipeLayout swipeLayout = helper.getView(R.id.swipe_layout);
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
                closeAll(layout);
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                mUnCloseSwipLayoutList.add(layout);
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {
                mUnCloseSwipLayoutList.remove(layout);
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        //点击事件
        helper.addOnClickListener(R.id.rl_page_container, R.id.tv_delete, R.id.tv_edit, R.id.tv_open_browser, R.id.tv_copy);
    }
}
