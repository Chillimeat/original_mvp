package indi.ayun.original_mvp.utils;

import indi.ayun.original_mvp.utils.verification.IsNothing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ToStringUtil {
    public static String output(Character[] value){
        String s="Character[]长度为"+value.length+"; \n";
        if (!IsNothing.onAnything(value)){
            return s;
        }

        for (int i=0;i<value.length;i++){
            s=s+i+"=>"+value[i]+"; \n";
        }
        return s;
    }
    public static String output(Character[][] value){
        if (!IsNothing.onAnything(value)){
            return "Character[][]元素总个数为0";
        }
        String s="Character[][]元素总个数为"+value.length*value[0].length+"; \n";
        for (int i=0;i<value.length;i++){
            s=s+"第"+i+"行";
            for (int j=0;j<value[i].length;j++){
                s=s+"第"+j+"列=>"+value[i][j]+"; \n";
            }
            s=s+"\n";
        }
        return s;
    }
    public static String output(byte[] value){
        String s="byte[]长度为"+value.length+"; \n";
        if (!IsNothing.onAnything(value)){
            return s;
        }
        for (int i=0;i<value.length;i++){
            s=s+i+"=>"+value[i]+"; \n";
        }
        return s;
    }
    public static String output(byte[][] value){
        if (!IsNothing.onAnything(value)){
            return "byte[][]元素总个数为0";
        }
        String s="byte[][]元素总个数为"+value.length*value[0].length+"; \n";
        for (int i=0;i<value.length;i++){
            s=s+"第"+i+"行";
            for (int j=0;j<value[i].length;j++){
                s=s+"第"+j+"列=>"+value[i][j]+"; \n";
            }
            s=s+"\n";
        }
        return s;
    }
    public static String output(Short[] value){
        String s="Short[]长度为"+value.length+"; \n";
        if (!IsNothing.onAnything(value)){
            return s;
        }
        for (int i=0;i<value.length;i++){
            s=s+i+"=>"+value[i]+"; \n";
        }
        return s;
    }
    public static String output(Short[][] value){
        if (!IsNothing.onAnything(value)){
            return "Short[][]元素总个数为0";
        }
        String s="Short[][]元素总个数为"+value.length*value[0].length+"; \n";
        for (int i=0;i<value.length;i++){
            s=s+"第"+i+"行";
            for (int j=0;j<value[i].length;j++){
                s=s+"第"+j+"列=>"+value[i][j]+"; \n";
            }
            s=s+"\n";
        }
        return s;
    }
    public static String output(Integer[] value){
        String s="Integer[]长度为"+value.length+"; \n";
        if (!IsNothing.onAnything(value)){
            return s;
        }
        for (int i=0;i<value.length;i++){
            s=s+i+"=>"+value[i]+"; \n";
        }
        return s;
    }
    public static String output(Integer[][] value){
        if (!IsNothing.onAnything(value)){
            return "Integer[][]元素总个数为0";
        }
        String s="Integer[][]元素总个数为"+value.length*value[0].length+"; \n";
        for (int i=0;i<value.length;i++){
            s=s+"第"+i+"行";
            for (int j=0;j<value[i].length;j++){
                s=s+"第"+j+"列=>"+value[i][j]+"; \n";
            }
            s=s+"\n";
        }
        return s;
    }
    public static String output(Long[] value){
        String s="Long[]长度为"+value.length+"; \n";
        if (!IsNothing.onAnything(value)){
            return s;
        }
        for (int i=0;i<value.length;i++){
            s=s+i+"=>"+value[i]+"; \n";
        }
        return s;
    }
    public static String output(Long[][] value){
        if (!IsNothing.onAnything(value)){
            return "Long[][]元素总个数为0";
        }
        String s="Long[][]元素总个数为"+value.length*value[0].length+"; \n";
        for (int i=0;i<value.length;i++){
            s=s+"第"+i+"行";
            for (int j=0;j<value[i].length;j++){
                s=s+"第"+j+"列=>"+value[i][j]+"; \n";
            }
            s=s+"\n";
        }
        return s;
    }
    public static String output(Float[] value){
        String s="Float[]长度为"+value.length+"; \n";
        if (!IsNothing.onAnything(value)){
            return s;
        }
        for (int i=0;i<value.length;i++){
            s=s+i+"=>"+value[i]+"; \n";
        }
        return s;
    }
    public static String output(Float[][] value){
        if (!IsNothing.onAnything(value)){
            return "Float[][]元素总个数为0";
        }
        String s="Float[][]元素总个数为"+value.length*value[0].length+"; \n";
        for (int i=0;i<value.length;i++){
            s=s+"第"+i+"行";
            for (int j=0;j<value[i].length;j++){
                s=s+"第"+j+"列=>"+value[i][j]+"; \n";
            }
            s=s+"\n";
        }
        return s;
    }
    public static String output(Double[] value){
        String s="Double[]长度为"+value.length+"; \n";
        if (!IsNothing.onAnything(value)){
            return s;
        }
        for (int i=0;i<value.length;i++){
            s=s+i+"=>"+value[i]+"; \n";
        }
        return s;
    }
    public static String output(Double[][] value){
        if (!IsNothing.onAnything(value)){
            return "Double[][]元素总个数为0";
        }
        String s="Double[][]元素总个数为"+value.length*value[0].length+"; \n";
        for (int i=0;i<value.length;i++){
            s=s+"第"+i+"行";
            for (int j=0;j<value[i].length;j++){
                s=s+"第"+j+"列=>"+value[i][j]+"; \n";
            }
            s=s+"\n";
        }
        return s;
    }
    public static String output(ArrayList<Object> value){
        String s="ArrayList长度为"+value.size()+"; \n";
        if (!IsNothing.onAnything(value)){
            return s;
        }
        for (int i=0;i<value.size();i++){
            s=s+i+"=>"+value.get(i)+"; \n";
        }
        return s;
    }
    public static String output(List<Object> value){
        String s="List长度为"+value.size()+"; \n";
        if (!IsNothing.onAnything(value)){
            return s;
        }
        for (int i=0;i<value.size();i++){
            s=s+i+"=>"+value.get(i)+"; \n";
        }
        return s;
    }
    public static String output(Map<Object,Object> value){
        String s="Map长度为"+value.size()+"; \n";
        if (!IsNothing.onAnything(value)){
            return s;
        }
        for(Map.Entry<Object,Object> entry:value.entrySet()){
            s=s+entry.getKey()+"=>"+entry.getValue()+"; \n";

        }
        return s;
    }
    public static String output(Set<Object> value){
        String s="Set长度为"+value.size()+"; \n";
        if (!IsNothing.onAnything(value)){
            return s;
        }
        Object[] s1 = new String[value.size()];
        Object[] s2 = value.toArray(s1);

        for (int i = 0; i < s2.length; i++) {
            Object s3 = s2[i];
            s=s+i+"=>"+s3+"; \n";
        }
        return s;
    }
    public static String output(String[] value){
        String s="String[]长度为"+value.length+"; \n";
        if (!IsNothing.onAnything(value)){
            return s;
        }
        for (int i=0;i<value.length;i++){
            s=s+i+"=>"+value[i]+"; \n";
        }
        return s;
    }
    public static String output(String[][] value){
        if (!IsNothing.onAnything(value)){
            return "String[][]元素总个数为0";
        }
        String s="String[][]元素总个数为"+value.length*value[0].length+"; \n";
        for (int i=0;i<value.length;i++){
            s=s+"第"+i+"行";
            for (int j=0;j<value[i].length;j++){
                s=s+"第"+j+"列=>"+value[i][j]+"; \n";
            }
            s=s+"\n";
        }
        return s;
    }
}
