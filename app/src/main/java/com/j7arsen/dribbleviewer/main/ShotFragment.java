package com.j7arsen.dribbleviewer.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.j7arsen.dribbleviewer.R;
import com.j7arsen.dribbleviewer.app.DribbleViewerApp;
import com.j7arsen.dribbleviewer.base.BaseActivity;
import com.j7arsen.dribbleviewer.base.BaseFragment;
import com.j7arsen.dribbleviewer.dataclasses.Shot;
import com.j7arsen.dribbleviewer.db.DBHelper;
import com.j7arsen.dribbleviewer.error.ErrorHandler;
import com.j7arsen.dribbleviewer.managers.DBManager;
import com.j7arsen.dribbleviewer.managers.RequestManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by j7ars on 24.01.2017.
 */

public class ShotFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String SAVE_PAGE = "ShotListFragment.SAVE_PAGE";
    private static final String SAVE_SHOT_LIST = "ShotListFragment.SAVE_SHOT_LIST";

    @BindView(R.id.srl_shot_list)
    SwipeRefreshLayout mSrlShotUpdate;
    @BindView(R.id.rl_shot_content)
    RelativeLayout mRlShotContent;
    @BindView(R.id.rv_short_list)
    RecyclerView mRvShotList;
    @BindView(R.id.ll_shot_list_empty)
    LinearLayout mLLEmptyShotList;

    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.pb_load)
    ProgressBar pbLoad;
    @BindView(R.id.ll_progress_error)
    LinearLayout llError;
    @BindView(R.id.tv_progress_error)
    TextView tvError;

    private Activity mActivity;

    private Subscription mSubscription = Subscriptions.empty();

    private Unbinder mUnbinder;

    private ShotAdapter mShotAdapter;
    private ArrayList<Shot> mShotList;

    private int mPage = 1;

    public static ShotFragment newInstance() {
        ShotFragment shotFragment = new ShotFragment();
        return shotFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mActivity = (Activity) context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof BaseActivity) {
            mActivity = activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shot_layout, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            mPage = savedInstanceState.getInt(SAVE_PAGE);
            mShotList = savedInstanceState.getParcelableArrayList(SAVE_SHOT_LIST);
        }
        initView();
        initAdapters();
        setListeners();
        initData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVE_PAGE, mPage);
        outState.putParcelableArrayList(SAVE_SHOT_LIST, mShotList);
        super.onSaveInstanceState(outState);
    }

    private void initView() {
        mSrlShotUpdate.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
    }

    private void initAdapters() {
        if (mShotList == null) {
            mShotList = new ArrayList<>();
        }
        mShotAdapter = new ShotAdapter(mShotList);
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);

        mRvShotList.setLayoutManager(manager);
        mRvShotList.setAdapter(mShotAdapter);
    }

    private void setListeners() {
        mSrlShotUpdate.setOnRefreshListener(this);
    }

    private void initData() {
        if (DBHelper.getInstance(DribbleViewerApp.mInstance).getShotsCount() > 0) {
            startLoading();
            mSubscription = DBManager.getInstance()
                    .getShotFromDB().
                            subscribe(this::fillList, this::onErrorLoadFromDB);
        } else {
            loadShotsRequest(false);
        }
    }

    private void loadShotsRequest(boolean isRefresh){
        if(!isRefresh){
            startLoading();
        }
        mSubscription = RequestManager.getInstance().getShotResponse(mPage).subscribe(this::fillList, this::onErrorLoadFromApi);
    }

    private void fillList(List<Shot> shotListResponse) {
        unsubscribe(mSubscription);
        completeLoading();
        if (shotListResponse != null) {
            clearList();
            mShotList.addAll(shotListResponse);
            mShotAdapter.notifyDataSetChanged();
            checkIsEmptyList(shotListResponse);
        }
    }

    private void checkIsEmptyList(List<Shot> bindingCards) {
        if (bindingCards == null || bindingCards.isEmpty()) {
            mRvShotList.setVisibility(View.GONE);
            mLLEmptyShotList.setVisibility(View.VISIBLE);
        } else {
            mRvShotList.setVisibility(View.VISIBLE);
            mLLEmptyShotList.setVisibility(View.GONE);
        }
    }

    private void onErrorLoadFromDB(Throwable throwable){
        unsubscribe(mSubscription);
        completeLoading();
        reloadList(false);
    }

    private void onErrorLoadFromApi(Throwable throwable){
        unsubscribe(mSubscription);
        errorLoading(throwable);
    }


    private void startLoading() {
        if (isAdded()) {
            mRlShotContent.setVisibility(View.GONE);
            rlProgress.setVisibility(View.VISIBLE);
            pbLoad.setVisibility(View.VISIBLE);
            llError.setVisibility(View.GONE);
        }
    }

    private void completeLoading() {
        if (isAdded()) {
            mRlShotContent.setVisibility(View.VISIBLE);
            rlProgress.setVisibility(View.GONE);
            llError.setVisibility(View.GONE);
            pbLoad.setVisibility(View.GONE);
            goneSrl();
        }
    }

    private void errorLoading(Throwable throwable) {
        if (isAdded()) {
            mRlShotContent.setVisibility(View.GONE);
            rlProgress.setVisibility(View.VISIBLE);
            llError.setVisibility(View.VISIBLE);
            pbLoad.setVisibility(View.GONE);
            ErrorHandler errorHandler = new ErrorHandler(throwable);
            tvError.setText(errorHandler.getMessage());
            goneSrl();
        }
    }

    private void goneSrl() {
        mSrlShotUpdate.post(new Runnable() {
            @Override
            public void run() {
                if (isAdded()) {
                    mSrlShotUpdate.setRefreshing(false);
                }
            }
        });
    }

    private void clearList() {
        mShotList.clear();
        mShotAdapter.notifyDataSetChanged();
    }

    private void reloadList(boolean isRefresh){
        mPage = 1;
        loadShotsRequest(isRefresh);
    }

    @OnClick(R.id.ll_progress_error)
    void reload() {
        reloadList(false);
    }

    @Override
    public void onRefresh() {
        reloadList(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribeAll();
        mSrlShotUpdate.removeAllViews();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
