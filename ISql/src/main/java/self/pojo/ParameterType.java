package self.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Y-cs
 * @date 2021/4/17 17:01
 */
public class ParameterType{

    private String name;
    private Class cls;
    private int index;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getCls() {
        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
