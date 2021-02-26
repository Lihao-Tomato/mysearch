package org.example.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LiHao
 * Date: 2021-01-29
 * Time: 20:11
 */
public class ListUtil {
    //list1 - list2
    public static <E> List<E> differenceSet(List<E> list1, List<E> list2) {
        List<E> resultList = new ArrayList<>();
        for (E item : list1){
            if(!list2.contains(item)){
                resultList.add(item);
            }
        }

        return resultList;
    }
}
