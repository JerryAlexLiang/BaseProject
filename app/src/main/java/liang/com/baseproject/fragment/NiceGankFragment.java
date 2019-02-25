package liang.com.baseproject.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import liang.com.baseproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NiceGankFragment extends Fragment {


    public NiceGankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nice_gank, container, false);
    }

}
