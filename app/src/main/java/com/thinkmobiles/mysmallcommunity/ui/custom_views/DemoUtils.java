package com.thinkmobiles.mysmallcommunity.ui.custom_views;

import com.thinkmobiles.mysmallcommunity.models.DemoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dreamfire on 07.12.15.
 */
final class DemoUtils {
    int currentOffset;

    DemoUtils() {
    }

    public List<DemoItem> moarItems(int qty) {
        List<DemoItem> items = new ArrayList<>();

        for (int i = 0; i < qty; i++) {
            int colSpan ;//= Math.random() < 0.2f ? 2 : 1;
            int rowSpan ;

            // Swap the next 2 lines to have items with variable
            // column/row span.
            // int rowSpan = Math.random() < 0.2f ? 2 : 1;
            if(i==0) {
                colSpan = 4;
                rowSpan = 2;
            } else if(qty == 7 && i>2) {
                colSpan=1;
                rowSpan = 1;
            } else if(qty == 2 && i != 0) {
                colSpan = 4;
                rowSpan = 2;
            } else if(qty == 6 && i>2){
                colSpan = 2;
                rowSpan = 1;
            } else {
                colSpan = 2;
                rowSpan = 1;
            }
            DemoItem item = new DemoItem(colSpan, rowSpan, currentOffset + i);
            items.add(item);
        }

        currentOffset += qty;

        return items;
    }
}
