package com.mint.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Locale;

import org.springframework.util.Assert;

public class MyBeanUtils {
    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" +
                    fieldName + "] on target [" + object + "]");
        }

        makeAccessible(field);

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("never happend exception!", e);
        }
        return result;
    }

    public static void setFieldValue(Object object, String fieldName, Object value) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" +
                    fieldName + "] on target [" + object + "]");
        }

        makeAccessible(field);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("never happend exception!", e);
        }
    }

    protected static Field getDeclaredField(Object object, String fieldName) {
        Assert.notNull(object);
        return getDeclaredField(object.getClass(), fieldName);
    }

    protected static Field getDeclaredField(Class clazz, String fieldName) {
        Assert.notNull(clazz);
        Assert.hasText(fieldName);
        for (Class superClass = clazz; superClass != Object.class; ) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException localNoSuchFieldException) {
                superClass = superClass
                        .getSuperclass();
            }

        }

        return null;
    }

    protected static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers())) ||
                (!Modifier.isPublic(field.getDeclaringClass().getModifiers())))
            field.setAccessible(true);
    }

    public static Object getSimpleProperty(Object bean, String propName)
            throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return bean.getClass().getMethod(getReadMethod(propName), new Class[0]).invoke(bean, new Object[0]);
    }

    private static String getReadMethod(String name) {
        return "get" + name.substring(0, 1).toUpperCase(Locale.ENGLISH) +
                name.substring(1);
    }
}

