package com.example.mytapy.navdrawerintro;


import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    protected NavDrawer navDrawer;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    public void fadeOut(final FadeOutListener listner){
        View rootView = findViewById(android.R.id.content);
        rootView.animate()
                .alpha(0)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {}
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        listner.onFadeOutEnd();
                    }
                    @Override
                    public void onAnimationCancel(Animator animator) {}
                    @Override
                    public void onAnimationRepeat(Animator animator) {}
                }).setDuration(300).start();
    }

    protected void setNavDrawer(NavDrawer drawer) {
        this.navDrawer = drawer;
        this.navDrawer.create();

        overridePendingTransition(0, 0);
        View rootView = findViewById(android.R.id.content);
        rootView.animate().alpha(1).setDuration(400).start();
    }

    public Toolbar getToolbar() {
        return toolbar;
    }


    public interface FadeOutListener{
        void onFadeOutEnd();
    }












    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ON CREATE", "onCreate Called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ON START", "onStart Called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ON RESUME", "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ON PAUSE", "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ON STOP", "onStop Called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("ON RESTART", "onRestart Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ON DESTROY", "onDestroy");
    }
}
