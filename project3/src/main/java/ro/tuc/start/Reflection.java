package ro.tuc.start;

import java.lang.reflect.Field;

/**
 * obtine valorile campurilor obiectului dat ca si argument si le returneaza in cel de-al doilea parametru
 */

public class Reflection {

    public static void retrieveProperties(Object object, Object[] objects) {

        int count = 0;

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);
                //System.out.println(field.getName() + "=" + value);
                objects[count] = value;
                count++;

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    public static Object[] getTableHeaders(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Object[] headers = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            headers[i] = fields[i].getName();
        }
        return headers;
    }
}
