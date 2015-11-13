package com.leidos.dataparser.io.formatting;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Interface for implementing an output data formatter. Includes default methods for calling the appropriated format
 * methods for Object and Field types. Also capable of formatting Collection objects and Map objects that contain either
 * primitive type data or further nested ParsedData objects.
 */
public interface Formatter {
    /**
     * Request the formatter to encode a String in it's contents.
     * @param name The value of the field corresponding to the String.
     * @param s The actual string value to be encoded.
     */
    void formatString(String name, String s);

    /**
     * Request the formatter to encode an int in it's contents.
     * @param name The value of the field corresponding to the int.
     * @param i The actual int value to be encoded.
     */
    void formatInt(String name, int i);

    /**
     * Request the formatter to encode a double in it's contents.
     * @param name The value of the field corresponding to the double.
     * @param d The actual double value to be encoded.
     */
    void formatDouble(String name, double d);

    /**
     * Request the formatter to encode a long in it's contents.
     * @param name The value of the field corresponding to the long.
     * @param l The actual long value to be encoded.
     */
    void formatLong(String name, long l);

    /**
     * Request the formatter to encode a byte in it's contents.
     * @param name The value of the field corresponding to the byte.
     * @param b The actual byte value to be encoded.
     */
    void formatByte(String name, byte b);

    /**
     * Request the formatter to encode a boolean in it's contents.
     * @param name The value of the field corresponding to the boolean.
     * @param b The actual boolean value to be encoded.
     */
    void formatBoolean(String name, boolean b);

    /**
     * Request the formatter to encode a byte in it's contents.
     * @param name The value of the field corresponding to the short.
     * @param s The actual short value to be encoded.
     */
    void formatShort(String name, short s);

    /**
     * Request the formatter to encode a char in it's contents.
     * @param name The value of the field corresponding to the char.
     * @param c The actual char value to be encoded.
     */
    void formatChar(String name, char c);

    /**
     * Request the formatter to encode a float in it's contents.
     * @param name The value of the field corresponding to the float.
     * @param f The actual float value to be encoded.
     */
    void formatFloat(String name, float f);

    /**
     * Request the formatter to a nested ParsedData value in it's contents.
     * This is accomplished by recursively formatting the nested data until
     * only primitive types of data are encountered.
     *
     * @param name The value of the field corresponding to the double
     * @param p The actual ParsedData object containing data to nest
     */
    void formatOutputData(String name, OutputData p);

    /**
     * Retrieve the current contents of the Formatter implementation.
     * @return The string representation of the Formatter's internal buffer.
     *         May throw an exception if the buffer isn't in a legal state.
     */
    String getFormattedData();

    /**
     * Dispatch a format call for the given type of the field on the object.
     * @param f The field to be formatted.
     * @param p The object the field is associated with.
     */
    default void dispatch(Field f, OutputData p) {
        try {
            if (f.get(p) == null) {
                return;
            }
            f.setAccessible(true);
            Output annotation = f.getAnnotation(Output.class);
            if (String.class.isAssignableFrom(f.getType())) {
                if (annotation != null && !annotation.value().equals("")) {
                    formatString(annotation.value(), (String) f.get(p));
                } else {
                    formatString(f.getName(), (String) f.get(p));
                }
            } else if (OutputData.class.isAssignableFrom(f.get(p).getClass())) {
                if (annotation != null && !annotation.value().equals("")) {
                    formatOutputData(annotation.value(), (OutputData) f.get(p));
                } else {
                    formatOutputData(f.getName(), (OutputData) f.get(p));
                }
            } else if (int.class.isAssignableFrom(f.get(p).getClass()) || Integer.class.isAssignableFrom(f.get(p).getClass())) {
                if (annotation != null && !annotation.value().equals("")) {
                    formatInt(annotation.value(), f.getInt(p));
                } else {
                    formatInt(f.getName(), f.getInt(p));
                }
            } else if (double.class.isAssignableFrom(f.get(p).getClass()) || Double.class.isAssignableFrom(f.get(p).getClass())) {
                if (annotation != null && !annotation.value().equals("")) {
                    formatDouble(annotation.value(), f.getDouble(p));
                } else {
                    formatDouble(f.getName(), f.getDouble(p));
                }
            } else if (long.class.isAssignableFrom(f.get(p).getClass()) || Long.class.isAssignableFrom(f.get(p).getClass())) {
                if (annotation != null && !annotation.value().equals("")) {
                    formatLong(annotation.value(), f.getLong(p));
                } else {
                    formatLong(f.getName(), f.getLong(p));
                }
            } else if (short.class.isAssignableFrom(f.get(p).getClass()) || Short.class.isAssignableFrom(f.get(p).getClass())) {
                if (annotation != null && !annotation.value().equals("")) {
                    formatShort(annotation.value(), f.getShort(p));
                } else {
                    formatShort(f.getName(), f.getShort(p));
                }
            } else if (byte.class.isAssignableFrom(f.get(p).getClass()) || Byte.class.isAssignableFrom(f.get(p).getClass())) {
                if (annotation != null && !annotation.value().equals("")) {
                    formatByte(annotation.value(), f.getByte(p));
                } else {
                    formatByte(f.getName(), f.getByte(p));
                }
            } else if (char.class.isAssignableFrom(f.get(p).getClass()) || Character.class.isAssignableFrom(f.get(p).getClass())) {
                if (annotation != null && !annotation.value().equals("")) {
                    formatChar(annotation.value(), f.getChar(p));
                } else {
                    formatChar(f.getName(), f.getChar(p));
                }
            } else if (boolean.class.isAssignableFrom(f.get(p).getClass()) || Boolean.class.isAssignableFrom(f.get(p).getClass())) {
                if (annotation != null && !annotation.value().equals("")) {
                    formatBoolean(annotation.value(), f.getBoolean(p));
                } else {
                    formatBoolean(f.getName(), f.getBoolean(p));
                }
            } else if (float.class.isAssignableFrom(f.get(p).getClass()) ||Float.class.isAssignableFrom(f.get(p).getClass())) {
                if (annotation != null && !annotation.value().equals("")) {
                    formatFloat(annotation.value(), f.getFloat(p));
                } else {
                    formatFloat(f.getName(), f.getFloat(p));
                }
            } else if (Collection.class.isAssignableFrom(f.get(p).getClass())) {
                if (annotation != null && !annotation.value().equals("")) {
                    int i = 0;
                    for (Object o : (Collection) f.get(p)) {
                        dispatch(annotation.value() + "[" + i + "]", o);
                        i++;
                    }
                } else {
                    int i = 0;
                    for (Object o : (Collection) f.get(p)) {
                        dispatch(f.getName() + "[" + i + "]", o);
                        i++;
                    }
                }
            } else if (Map.class.isAssignableFrom(f.get(p).getClass())) {
                if (annotation != null && !annotation.value().equals("")) {
                    for (Object o1 : ((Map) f.get(p)).entrySet()) {
                        Map.Entry entry = (Map.Entry) o1;
                        dispatch(annotation.value() + "[\"" + entry.getKey().toString() + "\"]", entry.getValue());
                    }
                } else {
                    for (Object o1 : ((Map) f.get(p)).entrySet()) {
                        Map.Entry entry = (Map.Entry) o1;
                        dispatch(f.getName() + "[\"" + entry.getKey().toString() + "\"]", entry.getValue());
                    }
                }
            }  else if (f.get(p).getClass().isArray()) {
                Object o = f.get(p);
                String name = (annotation != null && !annotation.value().equals("") ? annotation.value() : f.getName());
                if (o instanceof int[]) {
                    int[] casted = (int[]) o;
                    for (int i = 0; i < casted.length; i++) {
                        dispatch(name + "[" + i + "]", casted[i]);
                    }
                } else if (o instanceof double[]) {
                    double[] casted = (double[]) o;
                    for (int i = 0; i < casted.length; i++) {
                        dispatch(name + "[" + i + "]", casted[i]);
                    }
                } else if (o instanceof float[]) {
                    float[] casted = (float[]) o;
                    for (int i = 0; i < casted.length; i++) {
                        dispatch(name + "[" + i + "]", casted[i]);
                    }
                } else if (o instanceof long[]) {
                    long[] casted = (long[]) o;
                    for (int i = 0; i < casted.length; i++) {
                        dispatch(name + "[" + i + "]", casted[i]);
                    }
                } else if (o instanceof short[]) {
                    short[] casted = (short[]) o;
                    for (int i = 0; i < casted.length; i++) {
                        dispatch(name + "[" + i + "]", casted[i]);
                    }
                } else if (o instanceof byte[]) {
                    byte[] casted = (byte[]) o;
                    for (int i = 0; i < casted.length; i++) {
                        dispatch(name + "[" + i + "]", casted[i]);
                    }
                } else if (o instanceof char[]) {
                    char[] casted = (char[]) o;
                    for (int i = 0; i < casted.length; i++) {
                        dispatch(name + "[" + i + "]", casted[i]);
                    }
                } else if (o instanceof boolean[]) {
                    boolean[] casted = (boolean[]) o;
                    for (int i = 0; i < casted.length; i++) {
                        dispatch(name + "[" + i + "]", casted[i]);
                    }
                } else if (o instanceof String[]) {
                    String[] casted = (String[]) o;
                    for (int i = 0; i < casted.length; i++) {
                        dispatch(name + "[" + i + "]", casted[i]);
                    }
                } else if (o instanceof OutputData[]) {
                    OutputData[] casted = (OutputData[]) o;
                    for (int i = 0; i < casted.length; i++) {
                        dispatch(name + "[" + i + "]", casted[i]);
                    }
                }
            }
        } catch (IllegalAccessException iae) {
            // Ignore it
        }
    }

    /**
     * Overloaded dispatch method for use with raw objects instead of Fields.
     * @param name A String name associated with the object
     * @param o The object to be formatted
     */
    default void dispatch(String name, Object o) {
        if (o == null) {
            return;
        }

        if (String.class.isAssignableFrom(o.getClass())) {
            formatString(name, (String) o);
        } else if (OutputData.class.isAssignableFrom(o.getClass())) {
            formatOutputData(name, (OutputData) o);
        } else if (int.class.isAssignableFrom(o.getClass()) || Integer.class.isAssignableFrom(o.getClass())) {
            formatInt(name, (int) o);
        } else if (double.class.isAssignableFrom(o.getClass()) || Double.class.isAssignableFrom(o.getClass())) {
            formatDouble(name, (double) o);
        } else if (long.class.isAssignableFrom(o.getClass())  || Long.class.isAssignableFrom(o.getClass())) {
            formatLong(name, (long) o);
        } else if (short.class.isAssignableFrom(o.getClass())  || Short.class.isAssignableFrom(o.getClass())) {
            formatShort(name, (short) o);
        } else if (byte.class.isAssignableFrom(o.getClass())  || Byte.class.isAssignableFrom(o.getClass())) {
            formatByte(name, (byte) o);
        } else if (char.class.isAssignableFrom(o.getClass()) || Character.class.isAssignableFrom(o.getClass())) {
            formatChar(name, (char) o);
        } else if (boolean.class.isAssignableFrom(o.getClass()) || Boolean.class.isAssignableFrom(o.getClass())) {
            formatBoolean(name, (boolean) o);
        } else if (float.class.isAssignableFrom(o.getClass()) || Float.class.isAssignableFrom(o.getClass())) {
            formatFloat(name, (float) o);
        } else if (Collection.class.isAssignableFrom(o.getClass())) {
            int i = 1;
            for (Object o1 : (Collection) o) {
                dispatch(name + "[" + i + "]", o1);
                i++;
            }
        } else if (Map.class.isAssignableFrom(o.getClass())) {
            /**
             * Implicit contract:
             *
             * The toString method of any keys used in an @ParserOutput tagged map must be useful. If this is not the
             * case the default Java Object#toString() will be used, which is unlikely to be helpful.
             */

            for (Object o1 : ((Map) o).entrySet()) {
                Map.Entry entry = (Map.Entry) o1;
                dispatch(name + "[\"" + entry.getKey().toString() + "\"]", entry.getValue());
            }
        } else if (o.getClass().isArray()) {
            if (o instanceof int[]) {
                int[] casted = (int[]) o;
                for (int i = 0; i < casted.length; i++) {
                    dispatch(name + "[" + i + "]", casted[i]);
                }
            } else if (o instanceof double[]) {
                double[] casted = (double[]) o;
                for (int i = 0; i < casted.length; i++) {
                    dispatch(name + "[" + i + "]", casted[i]);
                }
            } else if (o instanceof float[]) {
                float[] casted = (float[]) o;
                for (int i = 0; i < casted.length; i++) {
                    dispatch(name + "[" + i + "]", casted[i]);
                }
            } else if (o instanceof long[]) {
                long[] casted = (long[]) o;
                for (int i = 0; i < casted.length; i++) {
                    dispatch(name + "[" + i + "]", casted[i]);
                }
            } else if (o instanceof short[]) {
                short[] casted = (short[]) o;
                for (int i = 0; i < casted.length; i++) {
                    dispatch(name + "[" + i + "]", casted[i]);
                }
            } else if (o instanceof byte[]) {
                byte[] casted = (byte[]) o;
                for (int i = 0; i < casted.length; i++) {
                    dispatch(name + "[" + i + "]", casted[i]);
                }
            } else if (o instanceof char[]) {
                char[] casted = (char[]) o;
                for (int i = 0; i < casted.length; i++) {
                    dispatch(name + "[" + i + "]", casted[i]);
                }
            } else if (o instanceof boolean[]) {
                boolean[] casted = (boolean[]) o;
                for (int i = 0; i < casted.length; i++) {
                    dispatch(name + "[" + i + "]", casted[i]);
                }
            } else if (o instanceof String[]) {
                String[] casted = (String[]) o;
                for (int i = 0; i < casted.length; i++) {
                    dispatch(name + "[" + i + "]", casted[i]);
                }
            } else if (o instanceof OutputData[]) {
                OutputData[] casted = (OutputData[]) o;
                for (int i = 0; i < casted.length; i++) {
                    dispatch(name + "[" + i + "]", casted[i]);
                }
            }
        }
    }
}
