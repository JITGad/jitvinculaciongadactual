
package com.jitgad.bjitgad.Utilities;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jorge
 */
public class ReflectToClass {
    
    public static final int KAMELYCASESTRUCTURE = 0;
    public static final int DATABASEESTRUCTURE = 1;

    /**
     * Put the result of ResultSet into java object
     *
     * @param <T>
     * @param rs ResultSet
     * @param obj java Class of class
     * @param estructure KAMELYCASESTRUCTURE|DATABASEESTRUCTURE
     * @return
     */
    public static <T> ArrayList<T> putResult(ResultSet rs, Class<T> obj, int estructure) throws Exception {
        try {
            ArrayList<T> arrayList = new ArrayList<>();
            ResultSetMetaData metaData = rs.getMetaData();
            //List<Field> fields = getAllFields(obj);
            /**
             * Get total columns
             */
            int count = metaData.getColumnCount();
            while (rs.next()) {
                /**
                 * Create an object instance
                 */
                T newInstance = obj.newInstance();
                for (int i = 1; i <= count; i++) {
                    /**
                     * Assign a value to an attribute of an object
                     */
                    String name = metaData.getColumnName(i).toLowerCase();
                    if (estructure == KAMELYCASESTRUCTURE) {
                        name = toJavaField(name);// Change column name format to java Naming format
                    }
                    String substring = name.substring(0, 1);// title case
                    String replace = name.replaceFirst(substring, substring.toUpperCase());
                    Class<?> type = null;
                    try {
                        type = obj.getDeclaredField(name).getType();// Get field type
                        //type = fields.stream().filter(field -> field.getName().equals(name)).findAny().orElse(null).getType();
                        
                    } catch (NoSuchFieldException | SecurityException e) { // Class When the field is not defined by the object,skip
                        continue;
                    }
                    
                    Method method = obj.getMethod("set" + replace, type);
                    /**
                     * Determine the type of data read
                     */
                    if (type.isAssignableFrom(String.class)) {
                        method.invoke(newInstance, rs.getString(i));
                    } else if (type.isAssignableFrom(byte.class) || type.isAssignableFrom(Byte.class)) {
                        method.invoke(newInstance, rs.getByte(i));// byte The data type is an 8-bit signed integer represented by a binary complement
                    } else if (type.isAssignableFrom(short.class) || type.isAssignableFrom(Short.class)) {
                        method.invoke(newInstance, rs.getShort(i));// short The data type is a 16 bit signed integer represented by a binary complement
                    } else if (type.isAssignableFrom(int.class) || type.isAssignableFrom(Integer.class)) {
                        method.invoke(newInstance, rs.getInt(i));// int The data type is a 32-bit signed integer represented by a binary complement
                    } else if (type.isAssignableFrom(long.class) || type.isAssignableFrom(Long.class)) {
                        method.invoke(newInstance, rs.getLong(i));// long The data type is a 64 bit signed integer represented by a binary complement
                    } else if (type.isAssignableFrom(float.class) || type.isAssignableFrom(Float.class)) {
                        method.invoke(newInstance, rs.getFloat(i));// float Data type is single precision, 32-bit, compliant IEEE 754 Standard floating point number
                    } else if (type.isAssignableFrom(double.class) || type.isAssignableFrom(Double.class)) {
                        method.invoke(newInstance, rs.getDouble(i));// double Data type is double, 64 bit, compliant IEEE 754 Standard floating point number
                    } else if (type.isAssignableFrom(BigDecimal.class)) {
                        method.invoke(newInstance, rs.getBigDecimal(i));
                    } else if (type.isAssignableFrom(boolean.class) || type.isAssignableFrom(Boolean.class)) {
                        method.invoke(newInstance, rs.getBoolean(i));// boolean Data type represents one bit of information
                    } else if (type.isAssignableFrom(Date.class)) {
                        method.invoke(newInstance, rs.getDate(i));
                    }
                }
                arrayList.add(newInstance);
            }
            return arrayList;

        } catch (InstantiationException | IllegalAccessException | SQLException | SecurityException | NoSuchMethodException | IllegalArgumentException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            System.out.println("Reflect error: " + e);
            throw e;
        }
    }

    /**
     * Database naming format to java Naming format
     *
     * @param str Database field name
     * @return java Field name
     */
    public static String toJavaField(String str) {

        String[] split = str.split("_");
        StringBuilder builder = new StringBuilder();
        builder.append(split[0]);// Concatenate first character

        // If the array has more than one word
        if (split.length > 1) {
            for (int i = 1; i < split.length; i++) {
                // Remove underscores and capitalize initial
                String string = split[i];
                String substring = string.substring(0, 1);
                split[i] = string.replaceFirst(substring, substring.toUpperCase());
                builder.append(split[i]);
            }
        }

        return builder.toString();
    }
    
    public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }
}
