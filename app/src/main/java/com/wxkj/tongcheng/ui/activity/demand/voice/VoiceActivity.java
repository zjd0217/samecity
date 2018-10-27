package com.wxkj.tongcheng.ui.activity.demand.voice;

import android.content.Context;
import android.media.MediaRecorder;
import android.view.MotionEvent;
import android.widget.TextView;
import butterknife.BindView;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.util.FileUtil;
import com.wxkj.tongcheng.util.Util;

import java.io.File;
import java.io.IOException;

public class VoiceActivity extends MvpBaseActivity<VoicePresenter> implements VoiceView {

    @BindView(R.id.mTvVoice)
    TextView mTvVoice;

    private MediaRecorder mMediaRecorder;
    private String recorderPath = null;
    private long startTime = 0;
    private boolean byUser = false;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_voice).build();
    }

    @Override
    protected int titleLayoutId() {
        return 0;
    }

    @Override
    protected String titleString() {
        return null;
    }

    @Override
    protected void initData() {
        super.initData();
        findViewById(R.id.mIvClose).setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected VoicePresenter initPresenter() {
        return new VoicePresenter();
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void setListener() {
        super.setListener();
        mTvVoice.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                startTime = System.currentTimeMillis();
                startRecorder();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                stopRecorder();
            }
            return true;
        });
    }

    private void startRecorder() {
        File file = new File(getApplicationContext().getCacheDir(), "recorder");
        if (!file.exists()) {
            file.mkdirs();
        }
        recorderPath = file.getAbsolutePath() + "/sound" + System.currentTimeMillis() + ".mp3";
        File sFile = new File(recorderPath);
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mMediaRecorder.setOutputFile(sFile.getAbsolutePath());
        try {
            sFile.createNewFile();
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaRecorder.start();
    }

    private void stopRecorder() {
        long time = System.currentTimeMillis() - startTime;
        if (mMediaRecorder != null) {
            try {
                mMediaRecorder.setOnErrorListener(null);
                mMediaRecorder.setOnInfoListener(null);
                mMediaRecorder.setPreviewDisplay(null);
                mMediaRecorder.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
        if (time > 1000) {
            recorderCode();
        } else {
            FileUtil.deleteFolderFile(recorderPath);
            recorderPath = null;
        }
    }

    private void recorderCode() {
        String base64Code = Util.mp3ToBase64(recorderPath);
        if (presenter != null) {
            presenter.upload(base64Code);
        }
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(byUser, msg);
    }

    @Override
    public void uploadSuccess(SimpleResponseEntity entity) {
        byUser = true;
        showErrorMsg(true, "发布需求成功");
        onBackPressed();
    }
}