package com.alcon3sl.cms.utils;

import java.util.ArrayList;
import java.util.List;

public class JUtil {
    public static <T> List<T> refineList(List<T> list, List<T> subList) {
        List<T> newList = new ArrayList<>();

        if (list.isEmpty()) newList.addAll(subList);

        else {
            for (T current : subList) {
                if (list.contains(current))
                    newList.add(current);
            }
        }
        return newList;
    }
}
