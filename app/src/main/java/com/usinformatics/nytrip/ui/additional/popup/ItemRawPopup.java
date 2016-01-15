package com.usinformatics.nytrip.ui.additional.popup;

/**
 * Created by D1m11n on 22.06.2015.
 */
public enum ItemRawPopup {

    PROFILE(new ItemPopupModel(0, "Profile", "", null)),
    NOTIFICATIONS(new ItemPopupModel(0, "Notifications", "8", null)),
    USER_ACTIVITY(new ItemPopupModel(0, "User Activity", "", null)),
    DICTIONARY(new ItemPopupModel(0, "Dictionary", "", null)),
    SETTINGS(new ItemPopupModel(0, "Settings", "", null)),
    SEMESTER_SELECTION(new ItemPopupModel(0, "Semester Selection", "", null)),
    LOGOUT(new ItemPopupModel(0, "Logout", "", null));

    private ItemPopupModel mModel;

    private ItemRawPopup(ItemPopupModel model) {
        mModel = model;
    }

    public ItemPopupModel getModel() {
        return mModel;
    }
}
