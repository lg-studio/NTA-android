package com.usinformatics.nytrip.ui.courses;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.models.CourseModel;
import com.usinformatics.nytrip.storages.StorageFactory;
import com.usinformatics.nytrip.ui.BaseActivity;
import com.usinformatics.nytrip.ui.additional.dialogs.DialogFactory;
import com.usinformatics.nytrip.ui.additional.popup.ItemRawPopup;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarActions;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarEngine;
import com.usinformatics.nytrip.ui.selection.TasksSelectionActivity;

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
        mToolbarEngine.setToolbarTitle("Course selection");
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
        return ItemRawPopup.COURSE_SELECTION;
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

    public void setNewCourse(final CourseModel course){
        DialogFactory.showSimpleTwoButtonsDialog(CourseSelectionActivity.this,
                "New course", "Dou you want change current course to \"" + course.getName() + "\"",
                new DialogFactory.OnOkClickListener() {
                    @Override
                    public void wasOkClicked(DialogInterface dialog, boolean isOk) {
                        if(!isOk) return;
                        StorageFactory.getUserStorage(CourseSelectionActivity.this).setCurrentCourseId(course.id);
                        openTaskSelectionActivity();
                    }
                });
    }

    private void openTaskSelectionActivity() {
        Intent intent = new Intent(CourseSelectionActivity.this, TasksSelectionActivity.class);
        CourseSelectionActivity.this.startActivity(intent);
        CourseSelectionActivity.this.finish();
    }
}
