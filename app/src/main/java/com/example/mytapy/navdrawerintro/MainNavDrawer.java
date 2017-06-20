package com.example.mytapy.navdrawerintro;


import android.view.View;
import android.widget.Toast;

public class MainNavDrawer extends NavDrawer {

    public MainNavDrawer(final BaseActivity baseActivity) {
        super(baseActivity);

        addItem(new ActivityNavDrawerItem(InboxActivity.class, "Inbox", null,
                R.drawable.ic_email_black_24dp, R.id.include_main_nav_drawer_top_items));

        addItem(new ActivityNavDrawerItem(AddressBookActivity.class, "Address Book", null,
                R.drawable.ic_contact_mail_black_24dp, R.id.include_main_nav_drawer_top_items));

        addItem(new BasicNavDrawerItem("Logout", null, R.drawable.ic_backspace_black_24dp,
                R.id.include_main_nav_drawer_bottom_item) {
            @Override
            public void onClick(View view) {
                super.onClick(view);
                Toast.makeText(baseActivity, "Logout Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
