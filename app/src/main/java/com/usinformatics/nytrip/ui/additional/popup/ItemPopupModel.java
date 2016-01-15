package com.usinformatics.nytrip.ui.additional.popup;

/**
 * Created by D1m11n on 16.06.2015.
 */
class ItemPopupModel {

    public int idMenuIcon;

    public String menuTitle;

    public String menuSubtitle;

    public String additionalImage;


    public ItemPopupModel(){}

    public ItemPopupModel(int idMenuIcon, String menuTitle, String menuSubtitle, String additionalImage) {
        this.idMenuIcon = idMenuIcon;
        this.menuTitle = menuTitle;
        this.menuSubtitle = menuSubtitle;
        this.additionalImage = additionalImage;
    }


}
