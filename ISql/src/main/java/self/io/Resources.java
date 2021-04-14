package self.io;

import java.io.InputStream;

/**
 * @author Y-cs
 * @date 2021/4/3 0:05
 */
public class Resources {


    public static InputStream getResourceAsSteam(String path) {
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }


}
