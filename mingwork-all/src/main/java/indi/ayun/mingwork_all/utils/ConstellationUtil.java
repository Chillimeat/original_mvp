package indi.ayun.mingwork_all.utils;

public class ConstellationUtil {

    public static String getConstellation(String mothe,String day){
        String M =mothe;
        String D =day;
        String r="20";
        String y="21";
        String u="22";
        String u1="23";
        String u2="24";

        int i =D.compareTo(r);
        int er=D.compareTo(y);
        int san=D.compareTo(u);
        int si=D.compareTo(u1);
        int wu=D.compareTo(u2);

        String s="";
        if (M.equals("01") ){
            if (er>=0){
                s="水瓶座";
            }
            else {
                s="魔蝎座";
            }
        }
        if(M.equals("02")){
            if (i >= 0){
                s="双鱼座";
            }
            else {
                s="水瓶座";
            }
        }
        if(M.equals("03")){
            if (er>=0){
                s="白羊座";
            }
            else {
                s="双鱼座";
            }
        }
        if(M.equals("04")){
            if (er>=0){
                s="金牛座";
            }
            else {
                s="白羊座";
            }
        }
        if(M.equals("05")){
            if (san>=0){
                s="双子座";
            }
            else {
                s="金牛座";
            }
        }
        if(M.equals("06")){
            if (san>=0){
                s="巨蟹座";
            }
            else {
                s="双子座";
            }
        }
        if(M.equals("07")){
            if (si>=0){
                s="狮子座";
            }
            else {
                s="巨蟹座";
            }
        }
        if(M.equals("08")){
            if (wu>=0){
                s="处女座";
            }
            else {
                s="狮子座";
            }
        }
        if(M.equals("09")){
            if (wu>=0){
                s="天秤座";
            }
            else {
                s="处女座";
            }
        }
        if(M.equals("10")){
            if (wu>=0){
                s="天蝎座";
            }
            else {
                s="天秤座";
            }
        }
        if(M.equals("11")){
            if (si>=0){
                s="射手座";
            }
            else {
                s="天蝎座";
            }
        }
        if(M.equals("12")){
            if (si>=0){
                s="魔蝎座";
            }
            else {
                s="射手座";
            }
        }
        return s;
    }
}
