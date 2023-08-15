package br.unitins.topicos1.api.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class BeanUtil {

    public static void copyNonNullProperties(Object source, Object target) {
        try {
            for (PropertyDescriptor targetPd : Introspector.getBeanInfo(target.getClass()).getPropertyDescriptors()) {
                Method writeMethod = targetPd.getWriteMethod();
                if (writeMethod != null) {
                    PropertyDescriptor sourcePd = new PropertyDescriptor(targetPd.getName(), source.getClass());
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            writeMethod.getParameterTypes()[0].isAssignableFrom(readMethod.getReturnType())) {
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        if (value != null) {
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        }
                    }
                }
            }
        } catch (Throwable ex) {
            throw new RuntimeException(
                    "Could not copy non-null properties", ex);
        }
    }
}
