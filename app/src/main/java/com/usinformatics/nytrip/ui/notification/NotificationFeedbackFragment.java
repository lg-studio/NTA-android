package com.usinformatics.nytrip.ui.notification;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.usinformatics.nytrip.R;

import java.util.ArrayList;

/**
 * Created by admin on 7/27/15.
 */
public class NotificationFeedbackFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frg_notification_feedback, container, false);

        initFeedbackList(view);

        return view;

    }

    private void initFeedbackList(View view) {
        ListView feedbackList = (ListView) view.findViewById(R.id.feedback_list);
        ArrayList<FeedbackModel> list = new ArrayList<>();
        FeedbackModel fakeData = new FeedbackModel();
        fakeData.setTaskPath("Scene 1 Task 3");
        fakeData.setFinishedTime("yesterday");
        fakeData.setTaskName("NY trip");
        fakeData.setScore(20);
        list.add(fakeData);
        list.add(fakeData);
        list.add(fakeData);
        FeedbackAdapter adapter = new FeedbackAdapter(getActivity(), list);
        feedbackList.setAdapter(adapter);
    }
}
