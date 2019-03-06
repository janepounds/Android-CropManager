package com.myfarmnow.myfarmcrop.models;

/**
 * Created by Krishna on 9/2/2016.
 */
public class NavDrawerItemchild {
    private boolean showNotify;
    private String title;
    private String id;
    private int icon;
    public NavDrawerItemchild()
    {

    }
    public NavDrawerItemchild(boolean showNotify, String title, String id, int icon) {
        this.showNotify = showNotify;
        this.title = title;
        this.id = id;
        this.icon = icon;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}