package com.wxkj.tongcheng.ui.activity.home.qr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.zxing.Result;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.zxing.bean.ZxingConfig;
import com.wxkj.tongcheng.zxing.camera.CameraManager;
import com.wxkj.tongcheng.zxing.camera.CaptureActivityHandler;
import com.wxkj.tongcheng.zxing.camera.FinishListener;
import com.wxkj.tongcheng.zxing.common.Constant;
import com.wxkj.tongcheng.zxing.decode.DecodeImgCallback;
import com.wxkj.tongcheng.zxing.decode.DecodeImgThread;
import com.wxkj.tongcheng.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.List;

public class ScanQRCodeActivity extends BaseActivity implements SurfaceHolder.Callback {

    private ViewfinderView mViewfinderView;
    private SurfaceView mSurfaceView;
    private CaptureActivityHandler handler;
    public ZxingConfig config;
    private CameraManager cameraManager;
    private SurfaceHolder surfaceHolder;
    private boolean hasSurface;

    @Override
    protected void initStatusLayout() {
        // 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.BLACK);
        }
        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                config = (ZxingConfig) bundle.get(Constant.INTENT_ZXING_CONFIG);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (config == null) {
            config = new ZxingConfig();
        }
        statusLayoutManager = StatusLayoutManager
                .newBuilder(this)
                .contentView(R.layout.activity_scan_qr_code)
                .build();
        statusLayoutManager.showContent();
        hasSurface = false;
    }

    @Override
    protected void initData() {
        super.initData();
        mViewfinderView = findViewById(R.id.mViewfinderView);
        mViewfinderView.setZxingConfig(config);
        mSurfaceView = findViewById(R.id.mSurfaceView);
        findViewById(R.id.mIvBack).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.mIvLight).setOnClickListener(v -> cameraManager.switchFlashLight(handler));
        findViewById(R.id.mIvPicture).setOnClickListener(v -> selectPic());
    }

    @Override
    protected int titleLayoutId() {
        return 0;
    }

    @Override
    protected String titleString() {
        return null;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public Handler getHandler() {
        return handler;
    }

    public ViewfinderView getViewfinderView() {
        return mViewfinderView;
    }

    public void handleDecode(Result rawResult) {
        Toast.makeText(getApplicationContext(), rawResult.getText(), Toast.LENGTH_SHORT).show();
    }

    public void switchFlashImg(int flashState) {
        if (flashState == Constant.FLASH_OPEN) {
            Toast.makeText(getApplicationContext(), "关闭手电筒", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "打开手电筒", Toast.LENGTH_SHORT).show();
        }
    }

    public void drawViewfinder() {
        mViewfinderView.drawViewfinder();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraManager = new CameraManager(getApplication(), config);
        mViewfinderView.setCameraManager(cameraManager);
        handler = null;
        surfaceHolder = mSurfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            return;
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager);
            }
        } catch (IOException e) {
            e.printStackTrace();
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("扫一扫");
        builder.setMessage("相机被占用或没有相机权限，请检查");
        builder.setPositiveButton("退出", new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        cameraManager.closeDriver();
        if (!hasSurface) {
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    private void selectPic() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .minSelectNum(1)
                .selectionMode(PictureConfig.SINGLE)
                .previewImage(true)
                .isCamera(false)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList != null && selectList.size() > 0) {
                    LocalMedia media = selectList.get(0);
                    new DecodeImgThread(media.getPath(), new DecodeImgCallback() {
                        @Override
                        public void onImageDecodeSuccess(Result result) {
                            handleDecode(result);
                        }

                        @Override
                        public void onImageDecodeFailed() {
                            Toast.makeText(getApplicationContext(), "抱歉，解析失败,换个图片试试.", Toast.LENGTH_SHORT).show();
                        }
                    }).run();
                }
            }
        }
    }
}
