package com.edu.zscdm.qrcodedemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanActivity extends Activity implements QRCodeView.Delegate {

    @BindView(R.id.zx_view)
    ZXingView mQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);

        //设置结果处理
        mQR.setResultHandler(this);
        //开始读取二维码
        mQR.startSpot();
    }

    /**
     * 扫描二维码成功
     * @ param result
     */
    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(ScanActivity.this, result, Toast.LENGTH_SHORT).show();
        //震动
        vibrate();
        //停止预览
        mQR.stopCamera();
    }

    /**
     * 打开相机出错
     */
    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(ScanActivity.this, "打开相机出错！请检查是否开启权限！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 震动
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //启动相机
        mQR.startCamera();
    }

    @Override
    protected void onStop() {
        mQR.stopCamera();
        super.onStop();
    }
}
