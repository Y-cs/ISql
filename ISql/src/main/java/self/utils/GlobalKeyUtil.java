package self.utils;

import java.lang.reflect.Method;

/**
 * @author Y-cs
 * @date 2021/4/3 0:37
 */
public class GlobalKeyUtil {


    public static String getStatementKey(String namespace, String id) {
        return namespace + "." + id;
    }

    public static String getStatementKey(Method method) {
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        return className + "." + methodName;
    }

}
