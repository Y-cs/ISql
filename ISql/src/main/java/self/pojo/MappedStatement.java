package self.pojo;

/**
 * @author Y-cs
 * @date 2021/4/3 0:07
 */
public class MappedStatement {

    private String id;
    private String globalId;
    private String resultType;
    private ParameterType[] parameterTypes;
    private String sql;
    private MapperTypeEnum mapperType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public ParameterType[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(ParameterType[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public MapperTypeEnum getMapperType() {
        return mapperType;
    }

    public void setMapperType(MapperTypeEnum mapperType) {
        this.mapperType = mapperType;
    }

    public String getParameterType() {
        return null;
    }
}
