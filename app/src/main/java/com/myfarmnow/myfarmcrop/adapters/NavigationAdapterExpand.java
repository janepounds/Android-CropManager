package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.NavDrawerItem;
import com.myfarmnow.myfarmcrop.models.NavDrawerItemchild;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;



public class NavigationAdapterExpand extends BaseExpandableListAdapter {

    private Context activity;
    private ArrayList<NavDrawerItem> deptList1 = null;
    private LayoutInflater inflater;
    public static int selectedindex = -1;
    private int lastExpandedPosition = -1;

    public NavigationAdapterExpand(Context c, ArrayList<NavDrawerItem> menu, int index) {
        super();
        this.activity = c;
        this.selectedindex = index;
        this.deptList1 = menu;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        ArrayList<NavDrawerItemchild> productList = deptList1.get(groupPosition).getList();
        return productList.get(childPosititon);
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<NavDrawerItemchild> productList = deptList1.get(groupPosition).getList();
        return productList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return deptList1.get(groupPosition);

    }

    @Override
    public int getGroupCount() {
        return this.deptList1.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    int pos;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


            NavDrawerItemchild detailInfo = (NavDrawerItemchild) getChild(groupPosition, childPosition);

            convertView = null;
            View vi = convertView;
            final Holder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.drawer_list_item_child, null);
                holder = new Holder();
                holder.tv_categ = (TextView) convertView.findViewById(R.id.tv_cat);
                holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                holder.relativedrawer = (RelativeLayout) convertView.findViewById(R.id.relativedrawer);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

 
            try {

                holder.tv_categ.setText(detailInfo.getTitle().toString());
                holder.iv_icon.setImageDrawable(activity.getResources().getDrawable(detailInfo.getIcon()));
                // holder.iv_icon.setVisibility(View.INVISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;

    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public static Holder holder;

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            NavDrawerItem headerInfo = (NavDrawerItem) getGroup(groupPosition);

            View vi = convertView;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.drawer_list_item, null);
                holder = new Holder();
                holder.tv_categ = (TextView) convertView.findViewById(R.id.tv_cat);
                holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                holder.iv_leftarrow = (ImageView) convertView.findViewById(R.id.iv_leftarrow);
                holder.iv_downarrow = (ImageView) convertView.findViewById(R.id.iv_downarrow);
                holder.ivGroupIndicator = (ImageView) convertView.findViewById(R.id.ivGroupIndicator);
                holder.relativedrawer = (RelativeLayout) convertView.findViewById(R.id.relativedrawer);
                convertView.setTag(holder);


            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.tv_categ.setText(headerInfo.getTitle());


            return convertView;
    }


    public static class Holder {
        TextView tv_categ;
        public static ImageView iv_icon, iv_leftarrow, iv_downarrow, ivGroupIndicator;
        RelativeLayout relativedrawer;
    }




}
