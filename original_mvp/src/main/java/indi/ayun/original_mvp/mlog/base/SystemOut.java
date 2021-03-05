package indi.ayun.original_mvp.mlog.base;

public class SystemOut {

    public static void printOut(String tag, String msg, String headString){
        System.out.print("tag=>"+tag+"; headString=>"+headString+";  [msg:"+msg+"]");
    }
}
