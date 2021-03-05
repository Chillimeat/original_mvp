package indi.ayun.original_mvp.utils.transformation;

import java.util.Arrays;
import java.util.List;

import indi.ayun.original_mvp.mlog.MLog;

public class ListConvertUtil {

    /**
     *字符数组转List集合
     * @param array String[] array = new String[] {"zhu", "wen", "tao"};
     * @return  List
     */
    public static List<String> array2List(String[] array){
        // String数组转List集合
        List<String> mlist = Arrays.asList(array);
        // 输出List集合
        for (int i = 0; i < mlist.size(); i++) {
            MLog.d(ListConvertUtil.class.getSimpleName() + "---array2List-->" + mlist.get(i));
        }
        return mlist;
    }
}
