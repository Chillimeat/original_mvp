package indi.ayun.mingwork_all.retrofit2.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassTypeReflect {
    public ClassTypeReflect() {
    }

    private static Type getGenericType(int index, Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (!(superclass instanceof ParameterizedType)) {
            return subclass.getGenericSuperclass();
        } else {
            Type[] params = ((ParameterizedType)superclass).getActualTypeArguments();
            if (index < params.length && index >= 0) {
                return (Type)(!(params[index] instanceof Class) ? Object.class : params[index]);
            } else {
                throw new RuntimeException("Index outof bounds");
            }
        }
    }

    public static Type getModelClazz(Class<?> subclass) {
        return getGenericType(0, subclass);
    }
}
