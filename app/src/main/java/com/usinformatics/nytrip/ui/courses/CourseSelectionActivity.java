package com.usinformatics.nytrip.ui.courses;

import android.os.Bundle;
import android.widget.ListView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.storages.StorageFactory;
import com.usinformatics.nytrip.ui.BaseActivity;
import com.usinformatics.nytrip.ui.additional.popup.ItemRawPopup;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarActions;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarEngine;

/**
 * Created by admin on 7/9/15.
 */
public class CourseSelectionActivity extends BaseActivity {


    private static final String SEMESTER_SELECTION = "Cource selection";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_semester_selection);
        initToolbarEngine(true);
        mToolbarEngine.setToolbarTitle("Semester selection");
        initSelectionCourseList();
    }


    private void initSelectionCourseList() {

        ListView semesterList = (ListView) findViewById(R.id.select_semester_list);
        CourseListAdapter adapter = new CourseListAdapter(this, StorageFactory.getEduStorage(this).getCourses());
        semesterList.setAdapter(adapter);
        semesterList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public String getTag() {
        return SEMESTER_SELECTION;
    }

    @Override
    public ItemRawPopup getPopupItemOfCurrentActivity() {
        return null;
    }

    @Override
    protected ToolbarEngine getInstanceOfToolbar() {
        return null;
    }

    @Override
    protected int getIdResourcesOfToolbar() {
        return R.id.select_semester_toolbar;
    }

    @Override
    public void actionToolbarCallback(ToolbarActions currentItem) {
        if(currentItem==ToolbarActions.BACK){
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        if(dismissPopupIsNeeded())
            return;
        super.onBackPressed();
    }
}
