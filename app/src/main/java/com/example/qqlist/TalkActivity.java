package com.example.qqlist;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.example.qqlist.base.BaseActivity;
import com.example.qqlist.base.CommonAdapter;
import com.example.qqlist.base.MyApplication;
import com.example.qqlist.base.ViewHolder;
import com.example.qqlist.bean.EventBus_Tag;
import com.example.qqlist.bean.TalkBean;
import com.example.qqlist.util.DateUtil;
import com.example.qqlist.util.StrUtil;
import com.example.qqlist.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TalkActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.listview)
    ListView listview;
 

    @BindView(R.id.tv_quest)
    TextView tvQuest;
    @BindView(R.id.tv_time)
    TextView tv_time;

    @BindView(R.id.edt)
    EditText edt;
    @BindView(R.id.tv_bt)
    TextView tv_bt;


    String title;
    String mTime;

    TalkAdapter adapter;
    List<TalkBean> list = new ArrayList<>();



    public static void start(Context context,   String title, String mTime) {
        Intent starter = new Intent(context, TalkActivity.class);

        starter.putExtra("title", title);
        starter.putExtra("mTime", mTime);
        context.startActivity(starter);
    }

    @Override
    protected void setContent() {
        super.setContent();
        setContentView(R.layout.activity_talk);
        ButterKnife.bind(this);
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().register(this);
        tvTitle.setText("讨论详情");
    }


    @Override
    protected void initData() {
        title = getIntent().getStringExtra("title");
        mTime = getIntent().getStringExtra("mTime");
        List<TalkBean> list_all = DataSupport.findAll(TalkBean.class);

        tvQuest.setText(title);
//        tv_time.setText(quesBean.getCreate_user() + "发布于：" + quesBean.getCreate_time());


        if (list_all.size() > 0) {
            for (int i = 0; i < list_all.size(); i++) {
                if ( list_all.get(i).getQues_id().equals(mTime)) {
                    list.add(list_all.get(i));
                }
            }
        }

        Collections.reverse(list); // 倒序排列

        adapter = new TalkAdapter(this, (ArrayList) list, R.layout.item_list_talk);
        listview.setAdapter(adapter);


    }

    @Override
    protected void initListener() {
        findViewById(R.id.imgv_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.tv_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StrUtil.isEmpty(edt.getText().toString())) {
                    ToastUtil.showToast(TalkActivity.this, "请输入内容");
                    return;
                }

                TalkBean bean = new TalkBean();
                bean.setCreate_time(DateUtil.getTodayData_3());
                bean.setTalk_id(System.currentTimeMillis() + "");
                bean.setUser(MyApplication.curUser);
                bean.setUser_id(MyApplication.curUser);
                bean.setContent(edt.getText().toString());
                bean.setQues_id(mTime);
                bean.save();

                list.add(0, bean);
                adapter.notifyDataSetChanged();

                edt.setText("");
                EventBus.getDefault().post(new EventBus_Tag(2));
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBus_Tag event) {
        switch (event.getTag()) {
            case 2:
                List<TalkBean>   list_all = DataSupport.findAll(TalkBean.class);
                list.clear();

                    for (int i = 0; i < list_all.size(); i++) {
                        if ( list_all.get(i).getQues_id().equals(mTime)) {
                            list.add(list_all.get(i));
                        }
                    }

                Collections.reverse(list); // 倒序排列
                adapter.notifyDataSetChanged();
                break;
        }
    }

//    @OnClick({R.id.imgv_return, R.id.tv_bt})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.imgv_return:
//
//                break;
//
//            case R.id.tv_bt:
//
//                if (StrUtil.isEmpty(edt.getText().toString())) {
//                    ToastUtil.showToast(this, "请输入内容");
//                    return;
//                }
//
//                TalkBean bean = new TalkBean();
//                bean.setCreate_time(DateUtil.getTodayData_3());
//                bean.setTalk_id(System.currentTimeMillis() + "");
//                bean.setUser(MyApplication.curUser);
//                bean.setUser_id(MyApplication.curUser);
//                bean.setContent(edt.getText().toString());
//                bean.setQues_id(mTime);
//                bean.save();
//
//                list.add(0, bean);
//                adapter.notifyDataSetChanged();
//
//                edt.setText("");
//                EventBus.getDefault().post(new EventBus_Tag(2));
//                break;
//        }
//    }



    class TalkAdapter extends CommonAdapter {

        public TalkAdapter(Context context, ArrayList datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void setView(ViewHolder holder, Object o, int position) {
            TalkBean bean = (TalkBean) o;
            TextView tv_name = holder.getView(R.id.tv_username);
            TextView tv_time = holder.getView(R.id.tv_time);
            TextView tv_content = holder.getView(R.id.tv_content);
            TextView tv_liuyan = holder.getView(R.id.tv_liuyan);


            tv_name.setText(bean.getUser());
            tv_time.setText(bean.getCreate_time());
            tv_content.setText(bean.getContent());
            tv_liuyan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                EventBus.getDefault().post(new EventMessage(EventMessage.Click, position));
                }
            });

        }
    }
}
