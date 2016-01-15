package com.usinformatics.nytrip.ui.notification;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.ui.excercises.ChatAdapter;
import com.usinformatics.nytrip.ui.excercises.items.ChatItemList;
import com.usinformatics.nytrip.ui.excercises.items.StudentAnswerItemList;

import java.util.ArrayList;

/**
 * Created by admin on 7/27/15.
 */
public class NotificationMessageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frg_notification_message, container, false);
        initAdapter(view);
        return view;
    }

    private void initAdapter(View view) {
        ListView chatList = (ListView) view.findViewById(R.id.notification_message_list);
        ArrayList<ChatItemList> differentView = new ArrayList<>();
        TeacherMessageListItem teacherMessage = new TeacherMessageListItem(getActivity());
        VoiceMessageListItem voiceMessage = new VoiceMessageListItem(getActivity());
        StudentAnswerItemList studentAnswer = new StudentAnswerItemList(getActivity());
        differentView.add(teacherMessage);
        differentView.add(voiceMessage);
        differentView.add(studentAnswer);
        ChatAdapter adapter = new ChatAdapter(getActivity(), differentView);
    }
}
