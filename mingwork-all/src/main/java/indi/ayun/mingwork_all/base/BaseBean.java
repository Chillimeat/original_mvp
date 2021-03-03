package indi.ayun.mingwork_all.base;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import indi.ayun.mingwork_all.mlog.MLog;

public abstract class BaseBean {
    protected abstract Class onClass();

    public boolean notNull() {
        try {
            Class c = onClass();
            Object o = c.newInstance();//Object可转换为具体类
            if (c == null) return false;
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String output() {
        StringBuilder s = new StringBuilder(onClass() + ":\n");
        try {
            System.out.println("该类的名称:" + onClass().getName());
//            Class c = Class.forName(onClassName());//要包名+类名
            Class c = onClass();
            Object o = c.newInstance();//Object可转换为具体类
            Field[] fields = c.getDeclaredFields();//拿到数据成员
            Method[] methods = c.getMethods();//拿到函数成员
            //System.out.println(fields.length);System.out.println(methods.length);
            for (Field field : fields) {
                //System.out.println("该类的变量有:" + f.getName());
                for (Method method : methods) {
                    //System.out.println("该类的方法有:" + methods[j].getName());
                    MLog.d("javabean反射输出:" + method.getName() + "=>" + field.getName().substring(1, field.getName().length()));
                    if (method.getName().contains(field.getName().substring(1, field.getName().length()))
                            && !method.getName().contains("set")) {
                        MLog.d("javabean反射输出:" + method.getName() + "进场");

                        s.append(method.getName()).append("=>").append(method.invoke(c.newInstance())).append(";\n");
                    }
                }
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return s.toString();
    }

}