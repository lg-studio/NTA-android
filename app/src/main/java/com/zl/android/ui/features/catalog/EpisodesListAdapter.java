package com.zl.android.ui.features.catalog;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.SceneModel;
import com.usinformatics.nytrip.ui.selection.TasksSelectionActivity;
import com.usinformatics.nytrip.ui.selection.adapters.EpisodesScenesViewPagerAdapter;
import com.usinformatics.nytrip.ui.selection.models.ScenesWithEpisodeDescriptionModel;
import com.zl.android.domain.features.catalog.model.Episode;
import com.zl.android.domain.features.catalog.model.Scene;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EpisodesListAdapter extends BaseExpandableListAdapter {

    private List<Episode> mEpisodes;

    @Override
    public int getGroupCount() {
        return mEpisodes == null ? 0 : mEpisodes.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Scene[] scenes = mEpisodes.get(groupPosition).scenes;
        return scenes == null ? 0 : 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mEpisodes.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mEpisodes.get(groupPosition).scenes;
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
        Episode episode = (Episode) getGroup(groupPosition);
        EpisodeHolder holder = EpisodeHolder.getViewHolder(convertView, parent);
        // todo load icon
        return holder.bind(episode);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Episode episode = (Episode) getGroup(groupPosition);
        SceneHolder holder = SceneHolder.getViewHolder(convertView, parent);
        return holder.bind(episode.scenes, episode);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void setEpisodes(List<Episode> episodes) {
        mEpisodes = episodes;
        notifyDataSetChanged();
    }

    // region Inner classes

    static abstract class Holder<T> {
        T item;
        View view;

        void create(ViewGroup parent, int layoutId) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(layoutId, parent, false);
            view.setTag(this);
            ButterKnife.bind(this, view);
        }
    }

    static class EpisodeHolder extends Holder<Episode> {

        @Bind(R.id.tv_item_grouplist) TextView title;
        @Bind(R.id.tv_item_groupsublist) TextView subTitle;
        @Bind(R.id.iv_item_grouplist) ImageView icon;

        public View bind(Episode episode) {
            item = episode;
            title.setText(episode.name);
            subTitle.setText(episode.desc);
            // todo load image
            return view;
        }

        public static EpisodeHolder getViewHolder(View convertView, ViewGroup parent) {
            EpisodeHolder holder = convertView == null ? null : (EpisodeHolder) convertView.getTag();
            if (holder == null) {
                holder = new EpisodeHolder();
                holder.create(parent, R.layout.item_grouplist_scenes);
            }
            return holder;
        }
    }

    static class SceneHolder extends Holder<Scene[]> {

        @Bind(R.id.vp_item_list_scene) ViewPager viewPager;

        View bind(Scene[] scenes, Episode episode) {
            item = scenes;
            EpisodesScenesViewPagerAdapter adapter =
                    new EpisodesScenesViewPagerAdapter((TasksSelectionActivity) view.getContext(),
                    new ScenesWithEpisodeDescriptionModel(toScenes(scenes), toEpisode(episode)));
            viewPager.setAdapter(adapter);
            return view;
        }

        public static SceneHolder getViewHolder(View convertView, ViewGroup parent) {
            SceneHolder holder = convertView == null ? null : (SceneHolder) convertView.getTag();
            if (holder == null) {
                holder = new SceneHolder();
                holder.create(parent, R.layout.item_childlist_scenes);
            }
            return holder;
        }

    }

    // endregion


    // temporary methods for jumping back into the old model api

    private static SceneModel[] toScenes(Scene[] scenes) {
        if (scenes == null) return null;

        SceneModel[] res = new SceneModel[scenes.length];
        for (int i = 0; i < res.length; i++) {
            Scene scene = scenes[i];
            SceneModel model = res[i] = new SceneModel();
            model.sceneID = scene.sceneId;
            model.name = scene.name;
            model.desc = scene.desc;
            model.taskIDs = scene.taskIds;
        }
        return res;
    }

    private static EpisodeModel toEpisode(Episode episode) {
        EpisodeModel res = new EpisodeModel();
        res.episodeID = episode.episodeId;
        res.name = episode.name;
        res.desc = episode.desc;
        res.scenes = toScenes(episode.scenes);
        return res;
    }

}
