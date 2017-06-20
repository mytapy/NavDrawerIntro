package com.example.mytapy.navdrawerintro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddressBookActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        getSupportActionBar().setTitle("Address Book");

        setNavDrawer(new MainNavDrawer(this));
    }
}
