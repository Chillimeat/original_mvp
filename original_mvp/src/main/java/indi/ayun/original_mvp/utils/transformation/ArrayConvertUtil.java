package indi.ayun.original_mvp.utils.transformation;


import java.util.List;

import indi.ayun.original_mvp.mlog.MLog;

public class ArrayConvertUtil {
    /**
     * list转string数组
     * @param list ArrayList<String> list = new ArrayList<>();
     * @return String[]
     */
    public static String[] list2Array(List<String> list){
        String[] array = list.toArray(new String[list.size()]);
        MLog.d(ArrayConvertUtil.class.getSimpleName() + "列表长度为："+list.size());
        MLog.d(ArrayConvertUtil.class.getSimpleName() + "将列表转化为数组：");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+"  ");
            MLog.d(ArrayConvertUtil.class.getSimpleName() + array[i]+"  ");
        }
        return  array;
    }
}
