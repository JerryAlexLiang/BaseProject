package com.liang.module_laboratory.doubleRecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.liang.module_laboratory.R;

import java.util.ArrayList;


public class ChildFragment extends Fragment {

    private ArrayList<String> mDataList = new ArrayList<String>();

    RecyclerView childRecyclerView;

    public ChildFragment() {
    }

    public static ChildFragment newInstance() {
        ChildFragment fragment = new ChildFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        childRecyclerView = view.findViewById(R.id.child_recycler_view);
        childRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();

        for (int i = 0; i < 50; i++) {
            mDataList.add("child: " + i);
        }

        adapter.addData(mDataList);
        childRecyclerView.setAdapter(adapter);

        childRecyclerView.getAdapter().notifyDataSetChanged();

        childRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void stopNestedScrolling() {
        if (childRecyclerView != null) {
            childRecyclerView.stopScroll();
        }
    }
}