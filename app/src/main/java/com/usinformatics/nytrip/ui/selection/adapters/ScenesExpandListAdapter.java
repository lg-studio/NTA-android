package com.usinformatics.nytrip.ui.selection.adapters;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.SceneModel;
import com.usinformatics.nytrip.ui.selection.TasksSelectionActivity;
import com.usinformatics.nytrip.ui.selection.models.EpisodesScenesListModel;
import com.usinformatics.nytrip.ui.selection.models.ScenesWithEpisodeDescriptionModel;

import common.picasso.transformations.CircleTransform;
import common.utis.ListsUtils;

/**
 * Created by D1m11n on 15.06.2015.
 */
public class ScenesExpandListAdapter extends BaseExpandableListAdapter {


    private static final String TAG = ScenesExpandListAdapter.class.getSimpleName();
    private EpisodesScenesListModel mExpModel;
    private Activity mActivity;
    private LayoutInflater inflater;

    public ScenesExpandListAdapter(Activity context, EpisodesScenesListModel model) {
        super();
        mActivity = context;
        mExpModel = model;
        inflater = mActivity.getLayoutInflater();
    }

    @Override
    public int getGroupCount() {
        return ListsUtils.isEmpty(mExpModel.getGroupItems())?0:mExpModel.getGroupItems().length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        SceneModel[] models=mExpModel.getChildItems().get(getGroup(groupPosition));
        return ListsUtils.isEmpty(models)?0:1;
    }

    @Override
    public EpisodeModel getGroup(int groupPosition) {
        return mExpModel.getGroupItems()[groupPosition];
    }

    @Override
    public SceneModel [] getChild(int groupPosition, int childPosition) {
        return mExpModel.getChildItems().get(getGroup(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_grouplist_scenes, null, false);
            viewHolder = new GroupHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_item_grouplist);
             viewHolder.icon = (ImageView) convertView.findViewById(R.id.iv_item_grouplist);
            viewHolder.subTitle=(TextView)convertView.findViewById(R.id.tv_item_groupsublist);
            viewHolder.dividerBottom=convertView.findViewById(R.id.divider_bottom_item_grouplist);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupHolder) convertView.getTag();
        }
        if (isExpanded/*&&getChildrenCount(groupPosition)>0*/){
            viewHolder.dividerBottom.setVisibility(View.GONE);
        }else{
            viewHolder.dividerBottom.setVisibility(View.VISIBLE);
        }
        EpisodeModel mm= getGroup(groupPosition);
        viewHolder.title.setText(String.valueOf(mm.name));
        //viewHolder.subTitle.setText(mm.subTitle);
        viewHolder.subTitle.setText("Episode");
        Log.e(TAG, "EPISODE IMAGE +  " + mm.getImageUrl());
        Picasso.with(mActivity).load(mm.getImageUrl()).error(R.mipmap.ic_fake_taxi).placeholder(R.mipmap.ic_fake_taxi).transform(new CircleTransform()).into(viewHolder.icon);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_childlist_scenes, null, false);
            viewHolder = new ChildHolder();
            viewHolder.viewPager = (ViewPager) convertView.findViewById(R.id.vp_item_list_scene);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildHolder) convertView.getTag();
        }
        ViewPager viewPager = viewHolder.viewPager;
        EpisodesScenesViewPagerAdapter adapter = new EpisodesScenesViewPagerAdapter((TasksSelectionActivity) mActivity,
                new ScenesWithEpisodeDescriptionModel(getChild(groupPosition,childPosition), getGroup(groupPosition)));
        viewPager.setAdapter(adapter);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    class ChildHolder {
        ViewPager viewPager;
    }

    class GroupHolder {
        TextView title;
        TextView subTitle;
        ImageView icon;
        View dividerBottom;
    }


}
