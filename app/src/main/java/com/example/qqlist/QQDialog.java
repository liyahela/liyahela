package com.example.qqlist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qqlist.base.BaseDialog;
import com.example.qqlist.bean.EventBus_Tag;
import com.example.qqlist.bean.QQBean;
import com.example.qqlist.util.DateUtil;
import com.example.qqlist.util.ScreenUtil;
import com.example.qqlist.util.StrUtil;
import com.example.qqlist.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;


/**
 * @Author: Paper
 * time :2019/10/4 16:42
 * desc:
 */
public class QQDialog extends BaseDialog {

    TextView tv_commit, tv_cannal, tv_title;
    EditText et_phone, et_pwd;
    int myTag;//标记
    String content;
    int stype = 0;//0添加，1修改
    QQBean bean;
    public QQDialog(Activity activity, int stype) {
        super(activity);
        this.stype = stype;
    }

    public QQDialog(Activity activity, int stype, QQBean bean) {
        super(activity);
        this.stype = stype;
        this.bean = bean;
    }
    @Override
    public void initDialogEvent(Window window) {
        window.setContentView(R.layout.dialog_qq);
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //init view
        et_phone = window.findViewById(R.id.et_phone);
        et_pwd = window.findViewById(R.id.et_pwd);
        tv_commit = window.findViewById(R.id.tv_commit);
        tv_cannal = window.findViewById(R.id.tv_cannal);
        tv_title = window.findViewById(R.id.tv_title);
        //set view
        if (stype == 0) {
            tv_title.setText("添加");
        } else {
            tv_title.setText("修改");
        }
        //取消
        tv_cannal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //确定
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempPhone = et_phone.getText().toString().trim();
                String tempPwd = et_pwd.getText().toString().trim();
                if (StrUtil.isEmpty(tempPhone) || StrUtil.isEmpty(tempPwd)) {
                    ToastUtil.showToast(activity, "名称不能为空");
                    return;
                }
                if (StrUtil.isEmpty(tempPwd) || StrUtil.isEmpty(tempPwd)) {
                    ToastUtil.showToast(activity, "内容不能为空");
                    return;
                }

                if (stype == 0) {//add
                    QQBean tempBean = new QQBean();
                    tempBean.setName(tempPhone);
                    tempBean.setContents(tempPwd);
                    tempBean.setTimes(DateUtil.getTodayData_3());
                    tempBean.save();
                    if (tempBean.isSaved()) {
                        ToastUtil.showToast(activity, "添加成功");
                    }
                } else {//updata
                    ContentValues values = new ContentValues();
                    values.put("name", tempPhone);
                    values.put("contents", tempPwd);
                    DataSupport.updateAll(QQBean.class, values, "times = ?", bean.getTimes());
                    ToastUtil.showToast(activity, "修改成功");
                }
                dialog.dismiss();
                EventBus.getDefault().post(new EventBus_Tag(1));

            }
        });

    }


    @Override
    public void showDialog() {
        dialog = new AlertDialog.Builder(activity).create();
        //点击外部区域取消dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(null);
        dialog.show();

        Window window = dialog.getWindow();
        window.setLayout((int) (ScreenUtil.getScreenWidth(activity) * 0.8), (int) (ScreenUtil.getScreenHeight(activity) * 0.5));
        window.setGravity(Gravity.CENTER);
        //解决棱角问题
        window.setBackgroundDrawable(new BitmapDrawable());
        initDialogEvent(window);
    }
}

