package liang.com.baseproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TranslateActivity extends BaseActivity {

    @BindView(R.id.btn_log_out)
    Button btnLogOut;
    @BindView(R.id.base_toolbar_ll)
    LinearLayout baseToolbarLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        ButterKnife.bind(this);
        addActivity(this, TranslateActivity.class);


    }

    @OnClick(R.id.btn_log_out)
    public void onViewClicked() {
        finishAll();
        finish();
    }
}
