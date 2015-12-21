package com.thinkmobiles.mysmallcommunity.utility;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.global.Constants;
import com.thinkmobiles.mysmallcommunity.models.DrawerMenuItem;
import com.thinkmobiles.mysmallcommunity.ui.custom_views.CustomBadge;

import java.util.ArrayList;

/**
 * Created by john on 29.10.15.
 */
public class DrawerMenu {


    public ArrayList<DrawerMenuItem> createDrawerNawiItem(Context context) {
        DrawerMenuItem menuItem;

        ArrayList<DrawerMenuItem> items = new ArrayList<>();
        items.add(new DrawerMenuItem(Constants.STORIES_ID, context.getResources().getDrawable(R.drawable.ic_forum_black_24dp), context.getResources().getString(R.string.stories_item)));
        items.add(new DrawerMenuItem(Constants.MESSAGES_ID, context.getResources().getDrawable(R.drawable.ic_email_black_24dp), context.getResources().getString(R.string.messages_item), 7));
        items.add(new DrawerMenuItem(Constants.GALLERY_ID, context.getResources().getDrawable(R.drawable.ic_image_black_24dp), context.getResources().getString(R.string.gallery_item)));
        items.add(new DrawerMenuItem(Constants.EVENT_ID, context.getResources().getDrawable(R.drawable.ic_event_note_black_24dp), context.getResources().getString(R.string.event_item), 12));
        items.add(new DrawerMenuItem(Constants.PEOPLE_ID, context.getResources().getDrawable(R.drawable.ic_group_black_24dp), context.getResources().getString(R.string.people_item)));
        items.add(new DrawerMenuItem(Constants.GROUP_ID, context.getResources().getDrawable(R.drawable.ic_person_add_black_24dp), context.getResources().getString(R.string.group_item)));
        items.add(new DrawerMenuItem(Constants.DIRECTORIES_ID, context.getResources().getDrawable(R.drawable.ic_assignment_black_24dp), context.getResources().getString(R.string.directories_item)));
        items.add(new DrawerMenuItem(Constants.NOTIFICATION_ID, context.getResources().getDrawable(R.drawable.ic_add_alert_black_24dp), context.getResources().getString(R.string.notification_item)));
        items.add(new DrawerMenuItem(Constants.SETTINGS_ID, context.getResources().getDrawable(R.drawable.ic_settings_black_24dp), context.getResources().getString(R.string.settings_item)));
        items.add(new DrawerMenuItem(Constants.LOGOUT_ID, context.getResources().getDrawable(R.drawable.ic_exit_to_app_black_24dp), context.getResources().getString(R.string.logout_item)));

        return items;
    }
}
