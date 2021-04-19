package self.pojo;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Y-cs
 * @date 2021/4/17 18:15
 */
public class ClassMapper {

    private Class cls;
    private Map<String, Method> methodMap;

    public Class getCls() {
        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }

    public Map<String, Method> getMethodMap() {
        return methodMap;
    }

    public void setMethodMap(Map<String, Method> methodMap) {
        this.methodMap = methodMap;
    }
}
