package self.io;

import com.mchange.io.impl.EndsWithFilenameFilter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Y-cs
 * @date 2021/4/3 0:05
 */
public class Resources {


    public static InputStream getResourceAsSteam(String path) {
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }

    public static List<InputStream> getSteamByFilePath(String path) {
        File fileD = new File(path);
        if (fileD.isDirectory()) {
            File[] files = fileD.listFiles((dir, fileName) -> fileName.endsWith(".xml"));
            List<InputStream> inputStreams = Collections.emptyList();
            if (files != null) {
                inputStreams = new ArrayList<>(files.length);
                for (File file : files) {
                    InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(file.getPath());
                    inputStreams.add(resourceAsStream);
                }
            }
            return inputStreams;
        } else {
            throw new RuntimeException(path + " is not a directory");
        }
    }


}
