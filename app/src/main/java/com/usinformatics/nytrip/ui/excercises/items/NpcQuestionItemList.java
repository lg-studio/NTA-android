package com.usinformatics.nytrip.ui.excercises.items;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.models.QuestionModel;
import com.usinformatics.nytrip.ui.excercises.ExcerciseActivity;
import com.usinformatics.nytrip.ui.excercises.LinkedMap;
import com.usinformatics.nytrip.ui.excercises.PlayQuestionClickAction;
import com.usinformatics.nytrip.ui.excercises.notifiers.QuestionNotifier;

import java.util.Map;

/**
 * Created by admin on 7/8/15.
 */
public class NpcQuestionItemList implements ChatItemList, QuestionNotifier {

    private QuestionViewHolder holder;
    private ExcerciseActivity mActivity;
    private LinkedMap<QuestionModel, PlayQuestionClickAction> mQuestions = new LinkedMap<>();
    private LayoutInflater mInflater;

    public NpcQuestionItemList(ExcerciseActivity activity) {
        mActivity = activity;
    }

    @Override
    public int getViewType() {
        return ChatItemType.NPC_QUESTION.ordinal();
    }

    @Override
    public int getCount() {
        return mQuestions.size();
    }

    @Override
    public View getView(LayoutInflater inflater, int position, View convertView) {
        if (convertView == null) {
            holder = new QuestionViewHolder();
            convertView = createView(inflater);
            convertView.setTag(holder);
        } else {
            holder = (QuestionViewHolder) convertView.getTag();
        }
        Map.Entry entry = mQuestions.getEntry(position);
        QuestionModel q = (QuestionModel) entry.getKey();
        if (entry.getValue() == null)
            entry.setValue(new PlayQuestionClickAction(mActivity, q));
        holder.massage.setText(q.text);
        holder.playStopBtn.setTag(holder.progressWheel);
        ((PlayQuestionClickAction) entry.getValue()).updateHolderViewsState(holder);
        //holder.playStopBtn.setTag(holder);
        holder.playStopBtn.setOnClickListener((View.OnClickListener) entry.getValue());
        return convertView;
    }

//    private void initProgressWheel(QuestionViewHolder questionViewHolder,QuestionModel questionModel) {
//       if(questionViewHolder.progressWheel.getVisibility()==View.INVISIBLE)
//           return;
//
//    }

    private View createView(LayoutInflater inflater) {
        if (inflater == null)
            mInflater = mActivity.getLayoutInflater();
        else {
            mInflater = inflater;
        }
        View view = mInflater.inflate(R.layout.item_npc_massage, null, false);
        holder.massage = (TextView) view.findViewById(R.id.npc_massage);
        holder.playStopBtn = (ImageButton) view.findViewById(R.id.npc_play_pause_btn);
        holder.progressWheel = (ProgressWheel) view.findViewById(R.id.npc_progress_wheel);
        return view;
    }

    @Override
    public void notify(QuestionModel question, int position) {
        mQuestions.put(question, null);
    }

    public class QuestionViewHolder {
        public TextView massage;
        public ImageButton playStopBtn;
        public ProgressWheel progressWheel;
    }


}
