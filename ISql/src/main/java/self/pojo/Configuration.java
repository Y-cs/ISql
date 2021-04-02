package self.pojo;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author Y-cs
 * @date 2021/4/3 0:09
 */
public class Configuration {

    private DataSource dataSource;

    private Map<String, MappedStatement> mappersMap;


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
