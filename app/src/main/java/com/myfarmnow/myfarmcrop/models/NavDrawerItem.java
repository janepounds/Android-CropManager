package com.myfarmnow.myfarmcrop.models;

import java.util.ArrayList;

/**
 * Created by Krishna on 9/2/2016.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private String id;
    private int icon;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int position;


    public ArrayList<NavDrawerItemchild> getList() {
        return list;
    }

    public void setList(ArrayList<NavDrawerItemchild> list) {
        this.list = list;
    }
    public void addChildItem(NavDrawerItemchild item){
        list.add(item);
    }

    private ArrayList<NavDrawerItemchild> list = new ArrayList<NavDrawerItemchild>();


    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title, String id, int icon) {
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