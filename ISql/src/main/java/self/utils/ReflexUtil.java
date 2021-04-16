package self.utils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
