package liang.com.baseproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import liang.com.baseproject.R;
import liang.com.baseproject.app.MyApplication;

public class LoginActivity extends AppCompatActivity {

    public static void actionStart() {
        Intent intent = new Intent(MyApplication.getAppContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getAppContext().startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
