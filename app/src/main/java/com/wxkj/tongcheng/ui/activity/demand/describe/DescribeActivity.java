package com.wxkj.tongcheng.ui.activity.demand.describe;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lwkandroid.rtpermission.RTPermission;
import com.lwkandroid.rtpermission.listener.OnPermissionResultListener;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.DescribeAttributeAdapter;
import com.wxkj.tongcheng.adapter.DescribePicAdapter;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.demand.SelectTypeDemandEntity;
import com.wxkj.tongcheng.ui.activity.demand.attribute.AttributeListActivity;
import com.wxkj.tongcheng.ui.activity.demand.category.CategoryEntity;
import com.wxkj.tongcheng.ui.fragment.dialogfragment.DescribeSubmitSuccessDialogFragment;
import com.wxkj.tongcheng.util.FileUtil;
import com.wxkj.tongcheng.util.Util;
import com.wxkj.tongcheng.view.ScrollEditText;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DescribeActivity extends MvpBaseActivity<DescribePresenter> implements DescribeView {

    //简化版
    public static final int ACTION_SIMPLIFY = 1;
    //复杂版
    public static final int ACTION_COMMON = 2;
    public static final String ACTION_KEY = "action_key";
    private int action = 0;
    private boolean byUser = false;

    @BindView(R.id.mTvArea)
    TextView mTvArea;

    @BindView(R.id.mLayoutSoundRecording)
    FrameLayout mLayoutSoundRecording;

    @BindView(R.id.mTvShowHide)
    TextView mTvShowHide;

    @BindView(R.id.mPicRecyclerView)
    RecyclerView mPicRecyclerView;

    @BindView(R.id.mEtNickname)
    EditText mEtNickname;

    @BindView(R.id.mEtMobile)
    EditText mEtMobile;

    @BindView(R.id.mTvClassify)
    TextView mTvClassify;

    @BindView(R.id.mLayoutPlayer)
    RelativeLayout mLayoutPlayer;

    @BindView(R.id.mTvVoiceTime)
    TextView mTvVoiceTime;

    @BindView(R.id.mAttributesRecyclerView)
    RecyclerView mAttributesRecyclerView;

    @BindView(R.id.mLayoutDynamicAttributes)
    LinearLayout mLayoutDynamicAttributes;

    @BindView(R.id.mEtAddress)
    EditText mEtAddress;

    @BindView(R.id.mEtDescribe)
    ScrollEditText mEtDescribe;

    private DescribePicAdapter mPicAdapter;

    private SelectTypeDemandEntity mLevelType1;
    private CategoryEntity.TypeEntity mLevelType2;
    private CategoryEntity mCategoryEntity;

    private MediaRecorder mMediaRecorder;
    private String recorderPath = null;
    private long startTime = 0;
    private DescribeAttributeAdapter mAttributeAdapter;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_describe).build();
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
        mPicRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        mAttributesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    protected DescribePresenter initPresenter() {
        return new DescribePresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        action = intent.getIntExtra(ACTION_KEY, ACTION_SIMPLIFY);
        mLevelType1 = (SelectTypeDemandEntity) intent.getSerializableExtra("typeLevel1");
        mLevelType2 = (CategoryEntity.TypeEntity) intent.getSerializableExtra("typeLevel2");
        mCategoryEntity = (CategoryEntity) intent.getSerializableExtra("categoryEntity");
        if (action == ACTION_SIMPLIFY) {
            mLayoutDynamicAttributes.setVisibility(View.GONE);
            findViewById(R.id.mLayoutLabel).setVisibility(View.GONE);
            findViewById(R.id.mViewLine).setVisibility(View.GONE);
        } else {
            mLayoutDynamicAttributes.setVisibility(View.VISIBLE);
            findViewById(R.id.mLayoutLabel).setVisibility(View.VISIBLE);
            findViewById(R.id.mViewLine).setVisibility(View.VISIBLE);
        }
        mPicAdapter = new DescribePicAdapter(getApplicationContext(), () -> {
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .imageSpanCount(4)
                    .selectionMode(PictureConfig.MULTIPLE)
                    .previewImage(true)
                    .isCamera(true)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        });
        mPicRecyclerView.setAdapter(mPicAdapter);
        mTvClassify.setText(mLevelType1.type_name + "   " + mLevelType2.type_name);
        if (action == ACTION_COMMON) {
            List<CategoryEntity.AttributeEntity> mAttributeList = new ArrayList<>();
            for (CategoryEntity.AttributeEntity attribute : mCategoryEntity.attribute) {
                if (attribute.type_id == mLevelType2.type_id) {
                    mAttributeList.add(attribute);
                }
            }
            if (mAttributeList.size() > 0) {
                mAttributeAdapter = new DescribeAttributeAdapter(getApplicationContext(), mAttributeList, mCategoryEntity.value, new DescribeAttributeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(List<CategoryEntity.ValueEntity> mList, String title) {
                        String json = new Gson().toJson(mList);
                        Intent i = new Intent(getApplicationContext(), AttributeListActivity.class);
                        i.putExtra("json", json);
                        i.putExtra("title", title);
                        startActivityForResult(i, 1000);
                    }
                });
                mAttributesRecyclerView.setAdapter(mAttributeAdapter);
                mLayoutDynamicAttributes.setVisibility(View.VISIBLE);
            } else {
                mLayoutDynamicAttributes.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        findViewById(R.id.mIvBack).setOnClickListener(v -> onBackPressed());
        mTvArea.setOnClickListener(v -> showErrorMsg(true, "选择地址"));
        mLayoutSoundRecording.setOnClickListener(v -> showErrorMsg(true, "开始录音"));
        if (action == ACTION_COMMON) {
            mTvShowHide.setOnClickListener(v -> {
                String tag = mTvShowHide.getTag().toString();
                if (TextUtils.equals(tag, "1")) {
                    mTvShowHide.setTag("0");
                    mTvShowHide.setText("展开");
                    mTvShowHide.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_red_down_arrow, 0);
                    mAttributesRecyclerView.setVisibility(View.GONE);
                } else {
                    mTvShowHide.setTag("1");
                    mTvShowHide.setText("收起");
                    mTvShowHide.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_red_top_arrow, 0);
                    mAttributesRecyclerView.setVisibility(View.VISIBLE);
                }
            });
        }
        findViewById(R.id.mIvClearNickname).setOnClickListener(v -> mEtNickname.setText(""));
        findViewById(R.id.mIvClearMobile).setOnClickListener(v -> mEtMobile.setText(""));
        findViewById(R.id.mIvClearVoice).setOnClickListener(v -> showErrorMsg(true, "删除录音"));
        mLayoutSoundRecording.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                checkPermission();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                stopRecorder();
            }
            return true;
        });
        findViewById(R.id.mLayoutVoice).setOnClickListener(v -> {
            if (recorderPath != null && recorderPath.length() > 0) {
                Util.playerMp3(recorderPath);
            }
        });
        findViewById(R.id.mIvClearVoice).setOnClickListener(v -> {
            FileUtil.deleteFolderFile(recorderPath);
            mLayoutSoundRecording.setVisibility(View.VISIBLE);
            mLayoutPlayer.setVisibility(View.GONE);
            recorderPath = null;
        });
        findViewById(R.id.mTvSubmit).setOnClickListener(v -> {
            new DescribeSubmitSuccessDialogFragment().show(getSupportFragmentManager(), "DescribeSubmitSuccessDialogFragment");

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList != null && selectList.size() > 0) {
                    List<String> mList = new ArrayList<>();
                    for (LocalMedia media : selectList) {
                        mList.add(media.getPath());
                    }
                    if (mPicAdapter != null) {
                        mPicAdapter.addItems(mList);
                    }
                }
            } else if (requestCode == 1000 && data != null) {
                CategoryEntity.ValueEntity entity = (CategoryEntity.ValueEntity) data.getSerializableExtra("entity");
                mAttributeAdapter.setValueList(entity);
            }
        }
    }

    private void checkPermission() {
        new RTPermission.Builder().permissions(Manifest.permission.RECORD_AUDIO)
                .start(this, new OnPermissionResultListener() {
                    @Override
                    public void onAllGranted(String[] allPermissions) {
                        startTime = System.currentTimeMillis();
                        startRecorder();
                    }

                    @Override
                    public void onDeined(String[] dinedPermissions) {

                    }
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
            mLayoutSoundRecording.setVisibility(View.GONE);
            mLayoutPlayer.setVisibility(View.VISIBLE);
            mTvVoiceTime.setText(time / 1000 + "s");
        } else {
            FileUtil.deleteFolderFile(recorderPath);
            recorderPath = null;
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
    public int getLevelType1() {
        return mLevelType1.type_id;
    }

    @Override
    public int getLevelType2() {
        return mLevelType2.type_id;
    }

    @Override
    public String getInfoTitle() {
        return null;
    }

    @Override
    public String getNickname() {
        return mEtNickname.getText().toString().trim();
    }

    @Override
    public String getMobile() {
        return mEtMobile.getText().toString().trim();
    }

    @Override
    public String getTimeOverdue() {
        return null;
    }

    @Override
    public String getBaiduPos() {
        return null;
    }

    @Override
    public String getAreaId() {
        return null;
    }

    @Override
    public String getAddress() {
        return mEtAddress.getText().toString().trim();
    }

    @Override
    public String getDesc() {
        return mEtDescribe.getText().toString().trim();
    }

    @Override
    public String getInfoVoice() {
        if (recorderPath != null) {
            return Util.mp3ToBase64(recorderPath);
        }
        return null;
    }
}
