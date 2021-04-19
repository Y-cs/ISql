package self.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Y-cs
 * @date 2021/4/3 0:09
 */
public class Configuration {

    private DataSource dataSource;

    private Map<String, MappedStatement> mappersMap = new HashMap<String, MappedStatement>();

    private Map<String,ClassMapper> classMapperMap=new HashMap<>();

    public Map<String, ClassMapper> getClassMapperMap() {
        return classMapperMap;
    }

    public void setClassMapperMap(Map<String, ClassMapper> classMapperMap) {
        this.classMapperMap = classMapperMap;
    }


    /*
        private String driver;
    private String url;
    private String username;
    private String password;

     */

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappersMap() {
        return mappersMap;
    }

    public void setMappersMap(Map<String, MappedStatement> mappersMap) {
        this.mappersMap = mappersMap;
    }
}
