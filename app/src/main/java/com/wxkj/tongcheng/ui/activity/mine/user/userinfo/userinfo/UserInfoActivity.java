package com.wxkj.tongcheng.ui.activity.mine.user.userinfo.userinfo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lwkandroid.rtpermission.RTPermission;
import com.lwkandroid.rtpermission.listener.OnPermissionResultListener;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.mine.user.userinfo.changeusername.ChangeUserNameActivity;
import com.wxkj.tongcheng.ui.fragment.dialogfragment.CameraAlbumDialog;
import com.wxkj.tongcheng.ui.fragment.dialogfragment.ChooseSexDialog;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.ImageBase64;
import com.wxkj.tongcheng.util.SPUtil;
import com.wxkj.tongcheng.view.datepicker.builder.TimePickerBuilder;
import com.wxkj.tongcheng.view.datepicker.listener.CustomListener;
import com.wxkj.tongcheng.view.datepicker.listener.OnTimeSelectListener;
import com.wxkj.tongcheng.view.datepicker.view.TimePickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * 用户信息 页面
 * Created by cheng on 2018/10/13.
 */

public class UserInfoActivity extends MvpBaseActivity<UserInfoPresenter> implements UserInfoView, View.OnClickListener, OnPermissionResultListener {
    @BindView(R.id.change_head_layout)
    LinearLayout change_head_layout;
    @BindView(R.id.change_user_name_layout)
    LinearLayout change_user_name_layout;
    @BindView(R.id.user_head_img)
    ImageView user_head_img;
    @BindView(R.id.user_name_text)
    TextView user_name_text;
    @BindView(R.id.user_sex_text)
    TextView user_sex_text;
    @BindView(R.id.user_birthday_text)
    TextView user_birthday_text;
    @BindView(R.id.change_user_sex_layout)
    LinearLayout change_user_sex_layout;
    @BindView(R.id.user_birthday_layout)
    LinearLayout user_birthday_layout;

    private int cameraOrAlbum=-1;
    private TimePickerView birthdayView;

    @Override
    protected UserInfoPresenter initPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.user_info_content_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.base_title_layout;
    }

    @Override
    protected String titleString() {
        return "个人信息";
    }

    @Override
    protected boolean initEventBus() {
        return true;
    }


    @Override
    public Context getcontext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        t(msg);
    }

    @Override
    public void changeHeadSuccess(String s) {
        t("修改头像成功");
        SPUtil.getInstance(this).saveStringInfo("head_portrait",s);
        EventBusBean bean=new EventBusBean();
        bean.setCode(CodeUtil.CHANGE_HEAD_SUCCESS);
        bean.setName(s);
        EventBus.getDefault().post(bean);
    }

    @Override
    public void changeSexSuccess(String s) {
        t(s);
        String sex = user_sex_text.getText().toString();
        SPUtil.getInstance(this).saveIntInfo("sex",sex.equals("保密")?0:sex.equals("男")?1:2);
    }

    @Override
    public void changeBirthdaySuccess(String s) {
        t(s);
        SPUtil.getInstance(this).saveStringInfo("birth",user_birthday_text.getText().toString());
    }

    @Override
    protected void initView() {
        super.initView();
        //适配用户信息
        setUserInfo();
    }

    @Override
    protected void setListener() {
        super.setListener();
        change_head_layout.setOnClickListener(this);
        change_user_name_layout.setOnClickListener(this);
        change_user_sex_layout.setOnClickListener(this);
        user_birthday_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_head_layout:  //修改用户头像
                new CameraAlbumDialog().show(getSupportFragmentManager(),"CameraAlbumDialog");
                break;
            case R.id.change_user_name_layout:  //修改用户昵称
                startActivity(new Intent(this, ChangeUserNameActivity.class));
                break;
            case R.id.change_user_sex_layout:  //修改用户性别
                new ChooseSexDialog().show(getSupportFragmentManager(),"ChooseSexDialog");
                break;
            case R.id.user_birthday_layout:  //选择生日
                showBirthdayDialog();
                break;
        }
    }

    /**
     * 适配用户信息
     */
    private void setUserInfo() {
        Glide.with(this).load(SPUtil.getInstance(this).getStringByKey("head_portrait"))
                .apply(RequestOptions.circleCropTransform().error(R.drawable.placehoderlogo_3x)).into(user_head_img);
        user_name_text.setText(SPUtil.getInstance(this).getStringByKey("user_name"));
        int sex = SPUtil.getInstance(this).getIntByKey("sex");
        user_sex_text.setText(sex==0?"保密":sex==1?"男":"女");
        user_birthday_text.setText(SPUtil.getInstance(this).getStringByKey("birth"));
    }

    /**
     * 接收修改昵称成功的消息
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean){
        switch (bean.getCode()){
            case CodeUtil.CHANGE_USER_NAME_SUCCESS:  //修改昵称成功
                user_name_text.setText(bean.getName());
                break;
            case CodeUtil.CHOOSE_CAMERA_ALBUM:  //选择拍照还是相册
                //0相册 1拍照
                int type = bean.getType();
                this.cameraOrAlbum=type;
                getPermission();
                break;
            case CodeUtil.CHOOSE_SEX:  //选择男女
                //0保密 1男 2女
                int sex = bean.getType();
                user_sex_text.setText(sex==0?"保密":sex==1?"男":"女");
                presenter.changeUserSex(sex);
                break;
        }
    }

    /**
     * 获取权限
     */
    private void getPermission() {
        new RTPermission.Builder()
                .permissions(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .start(this,this);
    }

    /**
     * 权限获取成功
     * @param allPermissions
     */
    @Override
    public void onAllGranted(String[] allPermissions) {
        if (cameraOrAlbum==0)
            toAlbumChooseImg();
        else if (cameraOrAlbum==1)
            toTakePhoto();
    }

    /**
     * 权限获取失败
     * @param dinedPermissions
     */
    @Override
    public void onDeined(String[] dinedPermissions) {
        t("访问权限获取失败，无法修改头像");
    }

    /**
     * 去拍照
     */
    private void toTakePhoto() {
        PictureSelector.create(UserInfoActivity.this)
                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .enableCrop(true)// 是否裁剪
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 去相册选择图片
     */
    private void toAlbumChooseImg() {
        PictureSelector.create(UserInfoActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .enableCrop(true)
                .withAspectRatio(1,1)
                .previewImage(true)// 是否可预览图片
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .compress(false)// 是否压缩
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(false)// 是否显示gif图片
                .openClickSound(false)// 是否开启点击声音
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:  //图片选择返回
                    // 图片选择
                    List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
                    if (null!=list&&list.size()!=0){
                        LocalMedia localMedia = list.get(0);
                        String path="";
                        if (localMedia.isCut()) {
                            path= localMedia.getCutPath();
                        }
                        else {
                            path= localMedia.getPath();
                        }
                        if (new File(path).exists()){
                            Glide.with(this).load(path).apply(RequestOptions.circleCropTransform().error(R.drawable.placehoderlog)).into(user_head_img);
                            presenter.changeUserHead(ImageBase64.getImageStr(path));
                        }else
                            t("文件不存在，修改头像失败");
                    }
                    break;
            }
        }
    }

    /**
     * 显示选择生日 弹窗
     */
    private void showBirthdayDialog() {
        if (null==birthdayView){
            Calendar c = Calendar.getInstance();
            Calendar startDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            startDate.set(startDate.get(Calendar.YEAR)-80, startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH));

            //时间选择器 ，自定义布局
            birthdayView = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(String date, View v) { //选中事件回调
                    user_birthday_text.setText(date);
                    presenter.changeUserBirthday(date);
                }
            })
                    .setDate(c)
                    .setRangDate(startDate, endDate)
                    .setLayoutRes(R.layout.pickerview_custom, new CustomListener() {

                        @Override
                        public void customLayout(final View v) {
                            TextView tvSubmit =v.findViewById(R.id.sure_text);
                            TextView ivCancel = v.findViewById(R.id.cancel_text);
                            tvSubmit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    birthdayView.returnData();
                                    birthdayView.dismiss();
                                }
                            });
                            ivCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    birthdayView.dismiss();
                                }
                            });
                        }

                    })
                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                    .setDividerColor(getResources().getColor(R.color.color999))
                    .setTextColorCenter(getResources().getColor(R.color.colorff5))
                    .build();
        }
        birthdayView.show();
    }
}
