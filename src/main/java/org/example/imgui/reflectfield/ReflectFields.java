package org.example.imgui.reflectfield;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectFields {

    public static class FieldInfo{
        public String name;
        public Class<?> type;
        public Object value;
        public Field field;

        public  FieldInfo(String name, Class<?> type, Field field, Object value) {
            this.name = name;
            this.type = type;
            this.value = value;
            this.field = field;
        }

        @Override
        public String toString() {
            return name + " : "+type.getSimpleName() +" : "+value;
        }
    }

    public static List<FieldInfo> getFields(Object obj){
        List<FieldInfo> fields = new ArrayList<>();

        Class<?> clazz = obj.getClass();

        for(Field f : clazz.getDeclaredFields()) {
            boolean isPublic = java.lang.reflect.Modifier.isPublic(f.getModifiers());
            boolean isPrivate = java.lang.reflect.Modifier.isPrivate(f.getModifiers());

            boolean hasSerialized = f.isAnnotationPresent(Serialized.class);
            boolean hasHidePublic = f.isAnnotationPresent(HidePublic.class);

            if (isPublic && hasHidePublic)
                continue;
            if (isPrivate && !hasSerialized)
                continue;

            f.setAccessible(true);

            try{
                Object value = f.get(obj);
                fields.add(new FieldInfo(f.getName(), f.getType(), f, value));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        return  fields;

    }

    public static void setValue(Object obj, FieldInfo info, Object newValue) throws Exception {
        info.field.setAccessible(true);
        info.field.set(obj, newValue);
        info.value = newValue;
    }
}
