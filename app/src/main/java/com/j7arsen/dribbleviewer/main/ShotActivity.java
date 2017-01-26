package com.j7arsen.dribbleviewer.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.j7arsen.dribbleviewer.R;
import com.j7arsen.dribbleviewer.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by j7ars on 24.01.2017.
 */

public class ShotActivity extends BaseActivity {

    @BindView(R.id.navigation_bar)
    Toolbar mToolbar;

    private Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_layout);
        mUnbinder = ButterKnife.bind(this);
        initToolbar();

        openFragment();
    }


    private void initToolbar() {
        setSupportActionBar(mToolbar);
        setTitle(this.getResources().getString(R.string.label_shot_list_activity_title));
    }


    protected void openFragment() {
        getFragmentManager().beginTransaction().replace(R.id.container, ShotFragment.newInstance()).commit();
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
    }
}
