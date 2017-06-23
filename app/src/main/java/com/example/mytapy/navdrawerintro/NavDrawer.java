package com.example.mytapy.navdrawerintro;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class NavDrawer {
    ActionBarDrawerToggle mToogle;
    private ArrayList<NavDrawerItem> items;
    private NavDrawerItem selectedItem;

    protected BaseActivity baseActivity;
    protected DrawerLayout drawerLayout;
    protected ViewGroup navDrawerViewGroup;

    public NavDrawer(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
        items = new ArrayList<>();
        drawerLayout = (DrawerLayout) baseActivity.findViewById(R.id.drawer_layout);
        navDrawerViewGroup = (ViewGroup) baseActivity.findViewById(R.id.nav_drawer_view);

        if (drawerLayout == null || navDrawerViewGroup == null) {
            throw new RuntimeException("To use this class you must have views with id_s " +
                    "of drawer_layout and nav_drawer");
        }

        Toolbar toolbar = baseActivity.getToolbar();
        mToogle = new ActionBarDrawerToggle(baseActivity, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();

    }

    public void addItem(NavDrawerItem item) {
        items.add(item);
        item.navDrawer = this;
    }

    public boolean isOpen() {
        return drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    public void setOpen(boolean isOpen) {
        if (isOpen)
            drawerLayout.openDrawer(GravityCompat.START);
        else
            drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void setSelectedItem(NavDrawerItem item) {
        if (selectedItem != null)
            selectedItem.setSelected(false);
        else {
            selectedItem = item;
            selectedItem.setSelected(true);
        }
    }

    public void create() {
        LayoutInflater inflater = baseActivity.getLayoutInflater();
        for (NavDrawerItem item : items) {
            item.inflate(inflater, navDrawerViewGroup);
        }

    }


    public static abstract class NavDrawerItem {
        protected NavDrawer navDrawer;

        public abstract void inflate(LayoutInflater inflater, ViewGroup container);

        public abstract void setSelected(boolean isSelected);
    }

    public static class BasicNavDrawerItem extends NavDrawerItem implements View.OnClickListener {

        private String titleText;
        private String badgeText;
        private int iconDrawable;
        private int containerId;

        private ImageView icon;
        private TextView tvTitleText, badgeTextView;
        private View view;
        protected int defaultTextColor;

        public BasicNavDrawerItem(String titleText, String badgeText, int iconDrawable, int containerId) {
            this.titleText = titleText;
            this.badgeText = badgeText;
            this.iconDrawable = iconDrawable;
            this.containerId = containerId;
        }

        @Override
        public void inflate(LayoutInflater inflater, ViewGroup navDrawerView) {
            ViewGroup container = navDrawerView.findViewById(containerId);
            if (container == null) {
                throw new RuntimeException("Nav drawer item " + titleText +
                        " could not be attached to the view group. View not found");
            }
            view = inflater.inflate(R.layout.nav_drawer_each_list_item, container, false);
            container.addView(view);
            view.setOnClickListener(this);

            tvTitleText = (TextView) view.findViewById(R.id.list_item_nav_drawer_title_text_view);
            badgeTextView = (TextView) view.findViewById(R.id.list_item_nav_drawer_badge_text_view);
            icon = (ImageView) view.findViewById(R.id.list_item_nav_drawer_icon_view);
            defaultTextColor = tvTitleText.getCurrentTextColor();

            icon.setImageResource(iconDrawable);
            tvTitleText.setText(titleText);


            if (badgeText != null) {
                badgeTextView.setText(badgeText);
            } else {
                badgeTextView.setVisibility(View.GONE);
            }

        }

        @Override
        public void setSelected(boolean isSelected) {
            if (isSelected) {
                view.setBackgroundResource(R.drawable.list_item_nav_drawer_selected_item_background);
                tvTitleText.setTextColor(ContextCompat.getColor(navDrawer.baseActivity,
                        R.color.list_item_nav_drawer_selected_item_text_color));

            } else {
                view.setBackground(null);
                tvTitleText.setTextColor(defaultTextColor);
            }

        }


        public void setTitleText(String titleText) {
            this.titleText = titleText;
            if (view != null) {
                tvTitleText.setText(titleText);
            }
        }

        public void setBadgeText(String badgeText) {
            this.badgeText = badgeText;
            if (view != null) {
                if (badgeText != null) {
                    badgeTextView.setVisibility(View.VISIBLE);
                } else {
                    badgeTextView.setVisibility(View.GONE);
                }
            }
        }

        public void setIconDrawable(int iconDrawable) {
            this.iconDrawable = iconDrawable;
            if (view != null) {
                icon.setImageResource(iconDrawable);
            }
        }

        @Override
        public void onClick(View view) {
            navDrawer.setSelectedItem(this);
        }
    }

    public static class ActivityNavDrawerItem extends BasicNavDrawerItem {

        private final Class targetActivity;

        public ActivityNavDrawerItem(Class targetActivity, String titleText,
                                     String badgeText, int iconDrawable, int containerId) {
            super(titleText, badgeText, iconDrawable, containerId);
            this.targetActivity = targetActivity;
        }

        @Override
        public void inflate(LayoutInflater inflater, ViewGroup navDrawerView) {
            super.inflate(inflater, navDrawerView);

            if (this.navDrawer.baseActivity.getClass() == targetActivity) {
                this.navDrawer.setSelectedItem(this);
            }
        }

        @Override
        public void onClick(View view) {
            navDrawer.setOpen(false);
            if (navDrawer.baseActivity.getClass() == targetActivity) {
                return;
            }

            super.onClick(view);

            navDrawer.baseActivity.fadeOut(new BaseActivity.FadeOutListener() {
                @Override
                public void onFadeOutEnd() {
                    navDrawer.baseActivity.startActivity(new Intent(navDrawer.baseActivity,
                            targetActivity));
                    navDrawer.baseActivity.finish();
                }
            });
        }
    }
}
