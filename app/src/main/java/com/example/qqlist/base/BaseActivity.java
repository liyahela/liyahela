package com.example.qqlist.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;


/**
 * @Author: Paper
 * time :2019/9/4  10:26
 * desc:
 */
public abstract class BaseActivity extends FragmentActivity {
    public Context myContext;
    public Activity myActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext = this;
        myActivity = this;
        setContent();
        initData();
        initListener();

    }

    protected void setContent() {

    }

    protected abstract void initData();

    protected abstract void initListener();

    /**
     * 切换：Fragment
     * 方式:hide/show
     * 注：Fragment由hide到show,不走 onCreateView 方法，所有的 view 都会保存在内存
     */
    private Fragment currentV4Fragment;
    public void changeFragment(int resView, Fragment targetFragment) {
        if (targetFragment.equals(currentV4Fragment)) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //1、若Fragment未加入过，先add这个Fragment
        if (!targetFragment.isAdded()) {
            transaction.add(resView, targetFragment, targetFragment.getClass().getName());
        }
        //2、若Fragment处于隐藏状态，show这个Fragment
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
        }
        //3、hide当前Fragment
        if (currentV4Fragment != null && currentV4Fragment.isVisible()) {
            transaction.hide(currentV4Fragment);
//            transaction.remove(currentV4Fragment);
        }
        //4、将点击的Fragment赋值给当前Fragment
        currentV4Fragment = targetFragment;
        transaction.commitAllowingStateLoss();
    }

}
