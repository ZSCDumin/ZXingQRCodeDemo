package com.edu.zscdm.qrcodedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @BindView(R.id.scan_qrcode)
    TextView scanQrcode;
    @BindView(R.id.generate_qrcode)
    TextView generateQrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.scan_qrcode, R.id.generate_qrcode})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_qrcode:
                startActivity(new Intent(MainActivity.this, ScanActivity.class));
                break;
            case R.id.generate_qrcode:
                startActivity(new Intent(MainActivity.this, GenerateActivity.class));
                break;
            default:
                break;
        }
    }
}
