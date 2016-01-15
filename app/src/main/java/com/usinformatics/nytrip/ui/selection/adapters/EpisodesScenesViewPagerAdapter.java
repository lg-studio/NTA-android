package com.usinformatics.nytrip.ui.selection.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.models.SceneModel;
import com.usinformatics.nytrip.storages.StorageFactory;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarActions;
import com.usinformatics.nytrip.ui.selection.TasksSelectionActivity;
import com.usinformatics.nytrip.ui.selection.models.ScenesWithEpisodeDescriptionModel;

import common.adapters.NoFragmentViewPagerAdapter;

/**
 * Created by D1m11n on 15.06.2015.
 */

public class EpisodesScenesViewPagerAdapter extends NoFragmentViewPagerAdapter<SceneModel> implements View.OnClickListener {


    private TasksSelectionActivity mActivity;
    private ScenesWithEpisodeDescriptionModel mModel;

    public EpisodesScenesViewPagerAdapter(TasksSelectionActivity context, ScenesWithEpisodeDescriptionModel model) {
        super(context, model.mScenesList);
        mModel = model;
        mActivity = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = generateViewFor(position);
        container.addView(view);
        return view;
    }


    @Override
    public final View generateViewFor(int position) {
        LinearLayout layout = (LinearLayout) getInflater().inflate(R.layout.item_scene_in_pager, null, false);
        TextView text = (TextView) layout.findViewById(R.id.tv_frg_item_scene_title);
        text.setText(getItem(position).name);
        layout.setTag(position);
        layout.setOnClickListener(this);
        return layout;
    }

    @Override
    public void onClick(View v) {
        SceneModel m = mModel.mScenesList.get((Integer) v.getTag());
        StorageFactory.getUserStorage(mActivity).setCurrentEpisode(mModel.episode);
        StorageFactory.getUserStorage(mActivity).setCurrentScene(m);
        //mActivity.actionToolbarCallback(ToolbarActions.SCENES_WTH_TASKS);
        mActivity.displayScenesTasks();
    }
}


