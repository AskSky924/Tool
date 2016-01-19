package com.jingqubao.tool;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * Created by AskSky on 2016/1/12.
 * 文本显示
 */
public class TextShowActivity extends Activity {

    private TextView mTitle;
    private TextView mDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_show);

        initView();
        initData();
    }

    private void initData() {
        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("desc");

        mTitle.setText(title);
        if (TextUtils.isEmpty(desc)) {
            mDesc.setText("暂无信息！");
        } else {
            mDesc.setText(desc);
        }
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.text_show_title);
        mDesc = (TextView) findViewById(R.id.text_show_body);
    }

    public void back(View view) {
        finish();
    }
}
