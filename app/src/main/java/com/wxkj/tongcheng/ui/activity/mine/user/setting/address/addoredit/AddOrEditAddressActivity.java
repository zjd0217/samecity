package com.wxkj.tongcheng.ui.activity.mine.user.setting.address.addoredit;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.luck.picture.lib.rxbus2.RxBus;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.bean.ReceivingAddressBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.mine.user.setting.address.ReceivingAddressPresenter;
import com.wxkj.tongcheng.ui.activity.mine.user.setting.address.ReceivingAddressView;
import com.wxkj.tongcheng.util.CodeUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Liu haijun
 * @create 2018/10/20 0020
 * @Describe 添加和修改收货地址
 */
public class AddOrEditAddressActivity extends MvpBaseActivity<ReceivingAddressPresenter>
        implements ReceivingAddressView, OnRetryListener {


    @BindView(R.id.add_or_edit_title_text)
    TextView addOrEditTitleText;
    @BindView(R.id.add_or_edit_title_save)
    TextView addOrEditTitleSave;
    @BindView(R.id.add_or_edit_name)
    EditText addOrEditName;
    @BindView(R.id.add_or_edit_phone)
    EditText addOrEditPhone;
    @BindView(R.id.add_or_edit_address_province)
    TextView addOrEditAddressProvince;
    @BindView(R.id.add_or_edit_address_detail)
    EditText addOrEditAddressDetail;
    @BindView(R.id.add_or_edit_remark)
    EditText addOrEditRemark;
    @BindView(R.id.add_or_edit_default)
    CheckBox addOrEditDefault;
    /** 添加这个实体为null，修改不为null */
    private ReceivingAddressBean bean;
    /** 区一级id */
    private int region_id = -1;


    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_add_or_edit_address)
                .emptyDataView(R.layout.address_empty_data_layout)
                .errorView(R.layout.error_layout)
                .loadingView(R.layout.loading_layout)
                .netWorkErrorView(R.layout.network_error_layout)
                .onRetryListener(this)
                .build();
        statusLayoutManager.showContent();
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
    protected ReceivingAddressPresenter initPresenter() {
        return new ReceivingAddressPresenter();
    }

    @Override
    protected void initData() {
        super.initData();
        bean = (ReceivingAddressBean) getIntent().getSerializableExtra("bean");
        if (bean == null) {
            //添加收货地址
            addOrEditTitleSave.setVisibility(View.GONE);
            addOrEditTitleText.setText("新增收货地址");
            bean = new ReceivingAddressBean();
        } else {
            //修改收货地址
            addOrEditTitleSave.setVisibility(View.VISIBLE);
            addOrEditTitleText.setText("编辑收货地址");
            addOrEditName.setText(bean.getContact_man());
            addOrEditPhone.setText(bean.getContact_number());
            addOrEditAddressProvince.setText(bean.getRegion_path());
            addOrEditAddressDetail.setText(bean.getAddr_detail());
            addOrEditRemark.setText(bean.getMemo());
            addOrEditDefault.setChecked(bean.getDefault_fg() == 1);
            region_id = bean.getRegion_id();
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        addOrEditDefault.setOnCheckedChangeListener((buttonView, isChecked) -> {
            bean.setDefault_fg(isChecked ? 1 : 0);
        });
    }

    @OnClick({R.id.add_or_edit_title_img, R.id.add_or_edit_address_icon, R.id.add_or_edit_save,
            R.id.add_or_edit_title_save})
    public void click(View view) {
        switch (view.getId()) {
            //返回
            case R.id.add_or_edit_title_img:
                finish();

                break;
            //收货地址
            case R.id.add_or_edit_address_icon:


                break;
            //保存
            case R.id.add_or_edit_save:
            case R.id.add_or_edit_title_save:
                if (checkedParameter()) {
                    presenter.saveOrUpdateAddress(bean);
                }
                break;

        }

    }

    /**
     * 检查传递的参数
     *
     * @return
     */
    private boolean checkedParameter() {
        String string = addOrEditName.getText().toString();
        //检查收货人
        if (TextUtils.isEmpty(string)) {
            t("" + addOrEditName.getHint());
            return false;
        }
        //检查收货人电话
        bean.setContact_man(string);
        String phone = addOrEditPhone.getText().toString();
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            t("请输入正确的电话（11位）");
            return false;
        }
        bean.setContact_number(phone);
        //检查收货地址
        String addressDetail = addOrEditAddressDetail.getText().toString();
        if (TextUtils.isEmpty(addressDetail)) {
            t("请选择正确的收货地址");
            return false;
        }
        bean.setRegion_id(region_id);
        bean.setAddr_detail(addressDetail);
        String remark = addOrEditRemark.getText().toString();
        if (remark == null) {
            remark = "";
        }
        bean.setMemo(remark);
        return true;
    }

    @Override
    public Context getcontext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(false, msg);
    }

    @Override
    public void getAddressList(List<ReceivingAddressBean> list) {
        //没有使用
    }

    @Override
    public void success() {
        //保存（修改成功）
        t(addOrEditTitleText.getText() + "成功");
        EventBusBean eventBusBean = new EventBusBean();
        eventBusBean.setCode(CodeUtil.REFRESH_ADDRESS_LIST);
        EventBus.getDefault().post(eventBusBean);
        finish();
    }

    @Override
    public void onRetry() {
        statusLayoutManager.showContent();
        presenter.saveOrUpdateAddress(bean);
    }


}
