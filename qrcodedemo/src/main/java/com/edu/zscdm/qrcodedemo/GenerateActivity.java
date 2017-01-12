package com.edu.zscdm.qrcodedemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.DisplayUtils;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class GenerateActivity extends Activity {

    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.tv_create)
    TextView tvCreate;
    @BindView(R.id.tv_createlogo)
    TextView tvCreatelogo;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.iv_qr)
    ImageView ivQr;
    @BindView(R.id.tv_read)
    TextView tvRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        ButterKnife.bind(this);
    }


    /**
     * 校验输入框是否有内容
     * 没有内容返回true，有内容返回false
     * @ return
     */
    private boolean checkIsEmpty() {
        return TextUtils.isEmpty(etInput.getText().toString().trim());
    }

    /**
     * 创建普通二维码
     */
    private void createQRCode() {
        //生成二维码，第一个参数为要生成的文本，第二个参数为生成尺寸，第三个参数为生成回调
        QRCodeEncoder.encodeQRCode(etInput.getText().toString().trim(), DisplayUtils.dp2px(GenerateActivity.this, 160), new QRCodeEncoder.Delegate() {
            /**
             * 生成成功
             * @ param bitmap
             */
            @Override
            public void onEncodeQRCodeSuccess(Bitmap bitmap) {
                ivQr.setImageBitmap(bitmap);
            }

            /**
             * 生成失败
             */
            @Override
            public void onEncodeQRCodeFailure() {
                Toast.makeText(GenerateActivity.this, "生成中文二维码失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 创建带Logo的二维码
     */
    private void createQRCodeWithLogo() {

        //生成二维码，第一个参数为要生成的文本，第二个参数为生成尺寸，第三个参数为生成二维码颜色，第四个参数为logo资源，第五个参数为生成回调
        QRCodeEncoder.encodeQRCode(etInput.getText().toString().trim(), DisplayUtils.dp2px(GenerateActivity.this, 160), Color.parseColor("#000000"), ((BitmapDrawable) ivLogo.getDrawable()).getBitmap(), new QRCodeEncoder.Delegate() {
            @Override
            public void onEncodeQRCodeSuccess(Bitmap bitmap) {
                ivQr.setImageBitmap(bitmap);
            }

            @Override
            public void onEncodeQRCodeFailure() {
                Toast.makeText(GenerateActivity.this, "生成带logo的中文二维码失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 解析
     */
    public void decodeQRCode() {
        Bitmap bitmap = ((BitmapDrawable) ivQr.getDrawable()).getBitmap();
        decode(bitmap, "解析二维码失败");
    }

    /**
     * 解析二维码,可以解析二维码、带logo二维码、条形码
     * @ param bitmap
     * @ param err
     */
    private void decode(Bitmap bitmap, final String err) {
        QRCodeDecoder.decodeQRCode(bitmap, new QRCodeDecoder.Delegate() {
            @Override
            public void onDecodeQRCodeSuccess(String result) {
                Toast.makeText(GenerateActivity.this, result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDecodeQRCodeFailure() {
                Toast.makeText(GenerateActivity.this, err, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.tv_create, R.id.tv_createlogo, R.id.tv_read})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_create:            //创建二维码
                if (!checkIsEmpty())
                    createQRCode();
                else {
                    Toast.makeText(GenerateActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_createlogo:       //创建带logo
                if (!checkIsEmpty())
                    createQRCodeWithLogo();
                else {
                    Toast.makeText(GenerateActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_read:      //识别
                decodeQRCode();
                break;
            default:
                break;
        }
    }
}
