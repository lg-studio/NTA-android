package com.usinformatics.nytrip.ui.selection.adapters;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.models.TaskModel;
import com.usinformatics.nytrip.preferences.PrefsUser;
import com.usinformatics.nytrip.ui.selection.TasksSelectionActivity;

import java.util.ArrayList;

import common.adapters.NoFragmentViewPagerAdapter;

/**
 * Created by D1m11n on 15.06.2015.
 */


public class TaskListPagerAdapter extends NoFragmentViewPagerAdapter<TaskModel> {

    private Context context;
    private String pathToTask;
    private TasksSelectionActivity mActivity;

    public TaskListPagerAdapter(TasksSelectionActivity activity, ArrayList<TaskModel> pagerItems, String pathToTask) {
        super(activity, pagerItems);
        this.mActivity =activity;
        this.pathToTask = pathToTask;
    }

    @Override
    public final View generateViewFor(final int position) {
        View layout = getInflater().inflate(R.layout.item_task, null, false);
        TextView title = (TextView) layout.findViewById(R.id.tv_task_title);
        title.setText(getItem(position).name);
        TextView description = (TextView) layout.findViewById(R.id.tv_task_info);
        description.setText(getItem(position).name);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startExercise(position);
            }
        });
        return layout;
    }

    private void startExercise(int pos) {
        generatePathForTask(pos);
        mActivity.openTaskWindow(getItem(pos));
    }

    private void generatePathForTask(int position) {
        String tasksPath = Environment.getExternalStorageDirectory()
                + "/NYTrip" + "/"
                + pathToTask + "/" + position;
        PrefsUser.getInstance(context).setCurrentTaskPath(tasksPath);
    }
}