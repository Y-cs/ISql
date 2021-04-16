package self.pojo;

/**
 * @author Y-cs
 * @date 2021/4/3 0:07
 */
public class MappedStatement {

    private String id;
    private String resultType;
    private String parameterType;
    private String sql;
    private MapperTypeEnum mapperType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
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

    @Override
    public String toString() {
        return "MappedStatement{" +
                "id='" + id + '\'' +
                ", resultType='" + resultType + '\'' +
                ", parameterType='" + parameterType + '\'' +
                ", sql='" + sql + '\'' +
                ", mapperType=" + mapperType +
                '}';
    }
}
