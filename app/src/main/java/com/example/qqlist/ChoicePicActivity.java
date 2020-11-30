package com.example.qqlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qqlist.base.BaseActivity;
import com.example.qqlist.base.BaseRecyclerAdapter;
import com.example.qqlist.base.MyRVViewHolder;
import com.example.qqlist.bean.EventBus_Tag;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChoicePicActivity extends BaseActivity {


    @BindView(R.id.lv)
    RecyclerView lv;

    GridAdapter adapter;
    List<Integer> list = new ArrayList<>();




    @Override
    protected void setContent() {
        super.setContent();
        setContentView(R.layout.activity_choice_pic);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {

        list.add(R.mipmap.a1);
        list.add(R.mipmap.a2);
        list.add(R.mipmap.a3);
        list.add(R.mipmap.a4);
        list.add(R.mipmap.a5);
        list.add(R.mipmap.a6);
        list.add(R.mipmap.a7);
        list.add(R.mipmap.a8);
        list.add(R.mipmap.a9);
        list.add(R.mipmap.a1);
        list.add(R.mipmap.a2);
        list.add(R.mipmap.a3);
        list.add(R.mipmap.a4);
        list.add(R.mipmap.a5);
        list.add(R.mipmap.a6);
        list.add(R.mipmap.a7);
        list.add(R.mipmap.a8);
        list.add(R.mipmap.a9);
        @SuppressLint("WrongConstant")
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        if (null == gridLayoutManager)
            return;
        lv.setLayoutManager(gridLayoutManager);

//        @SuppressLint("WrongConstant")
//        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
//        if (null == manager)
//            return;
//        lv.setLayoutManager(manager);

        adapter = new GridAdapter(this, (ArrayList) list, R.layout.item_gridview);
        lv.setAdapter(  adapter);


        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                EventBus.getDefault().post(new EventBus_Tag(2,list.get(position)));
                finish();
            }
        });
    }

    @Override
    protected void initListener() {

    }





    @RequiresApi(api = Build.VERSION_CODES.N)
    class GridAdapter extends BaseRecyclerAdapter<Integer> {


        private int selPosi;

        public void setSelPosi(int selPosi) {
            this.selPosi = selPosi;
        }

        public GridAdapter(Context context, List<Integer> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void setView(MyRVViewHolder holder, final Integer bean, int position) {
            if (null == holder || null == bean)
                return;
            //init view
            ImageView imgv_item = holder.getView(R.id.imgv_item);
            imgv_item.setImageResource(bean);
        }
    }

}
