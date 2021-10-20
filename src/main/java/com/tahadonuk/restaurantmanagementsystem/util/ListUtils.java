package com.tahadonuk.restaurantmanagementsystem.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListUtils {
    public static <T> List<T> getListFromOptionals(List<Optional<T>> optionals) {

        List<T> listItems = new ArrayList<>();

        for(Optional<T> orderOpt : optionals) {
            listItems.add(orderOpt.get());
        }

        if(listItems.isEmpty()) return null;

        return listItems;
    }
}
