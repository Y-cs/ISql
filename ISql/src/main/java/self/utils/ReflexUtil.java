package self.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Y-cs
 * @date 2021/4/16 17:34
 */
public class ReflexUtil {


    public static Class<?> getClassType(String classType) throws ClassNotFoundException {
        if (classType != null) {
            return Class.forName(classType);
        }
        return null;
    }

    public static Object getInstance(Class<?> resultClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?>[] constructors = resultClass.getConstructors();
        return constructors[0].newInstance();
    }

}
