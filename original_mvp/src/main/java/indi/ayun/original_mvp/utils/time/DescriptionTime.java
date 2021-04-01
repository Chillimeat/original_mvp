package indi.ayun.original_mvp.utils.time;


import java.util.Date;

public class DescriptionTime {
    /**
     * FIXME:没测试，没完成
     * @param startDate
     * @param endDate
     * @return
     */
    private static long strTimeSubtraction(Date startDate,Date endDate){
        //得到两个日期对象的总毫秒数
        long firstDateMilliSeconds = startDate.getTime();
        long secondDateMilliSeconds = endDate.getTime();
        //得到两者之差
        long firstMinusSecond = secondDateMilliSeconds - firstDateMilliSeconds;
        //得到秒
        //long totalSeconds = (firstMinusSecond / 1000);
        return firstMinusSecond;
    }

    /**
     * 毫秒改成时分秒
     * @param ms
     * @return
     */
    public static String msToHMS(String ms){
        int s= Integer.parseInt(ms);
        int hI=0;String hS="00";
        if (s>1000*60*60) {
            hI = s/(1000 * 60 * 60);
            s=s-hI*1000*60*60;
            hS=hI+"";
        }
        int mI=0;String mS="00";
        if (s>1000*60){
            mI = s/(1000 * 60 );
            s=s-mI*1000*60;
            mS=mI+"";
        }
        int sI=0;String sS="00";
        if (s>1000){
            sI = s/(1000);
            s=s-sI*1000;
            sS=sI+"";
        }
        return hS+":"+mS+":"+sS;
    }
}
