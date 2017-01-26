package com.j7arsen.dribbleviewer.main;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.j7arsen.dribbleviewer.R;
import com.j7arsen.dribbleviewer.dataclasses.Shot;

import java.util.ArrayList;

/**
 * Created by j7ars on 25.01.2017.
 */

public class ShotAdapter extends RecyclerView.Adapter<ShotAdapter.ViewHolder> {

    private ArrayList<Shot> mShotList;

    public ShotAdapter(ArrayList<Shot> data) {
        mShotList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_shot_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Shot data = mShotList.get(position);
        initView(holder, data);
    }

    @Override
    public int getItemCount() {
        return mShotList == null ? 0 : mShotList.size();
    }

    private void initView(ViewHolder holder, Shot data) {
        holder.tvTitle.setText(data.getTitle());
        if (data.getDescription() != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                holder.tvDescription.setText(Html.fromHtml(data.getDescription(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                holder.tvDescription.setText(Html.fromHtml(data.getDescription()));
            }
        } else {
            holder.tvDescription.setVisibility(View.GONE);
        }

        displayImage(holder.sdvImage, data);

    }

    private void displayImage(SimpleDraweeView view, Shot data) {
        String url = data.getImages().getImageUrl();
        if (url != null) {
            Uri uri = Uri.parse(url);
            view.setImageURI(uri);
            view.setAspectRatio((float) data.getWidth() / data.getHeight());
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llContainer;
        TextView tvTitle;
        SimpleDraweeView sdvImage;
        TextView tvDescription;


        public ViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }


        private void initViews(View view) {
            llContainer = (LinearLayout) view.findViewById(R.id.ll_row_dribble_container);
            tvTitle = (TextView) view.findViewById(R.id.tv_shot_title);
            tvDescription = (TextView) view.findViewById(R.id.tv_shot_description);
            sdvImage = (SimpleDraweeView) view.findViewById(R.id.sdv_shot_image);


        }
    }
}
