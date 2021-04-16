package self.sqlSession;

import self.pojo.Configuration;
import self.pojo.MappedStatement;
import self.pojo.MapperTypeEnum;
import self.utils.GlobalKeyUtil;

import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Y-cs
 * @date 2021/4/3 22:51
 */
public class DefaultSqlSession implements SqlSession {


    private Configuration configuration;

    private Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = new SimpleExecutor();
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(),
                new Class[]{mapperClass},
                //当前代理对象的应用,当前被调用方法的应用,传递的参数
                (proxy, method, args) -> {
                    String statementKey = GlobalKeyUtil.getStatementKey(method);

                    MappedStatement mappedStatement = configuration.getMappersMap().get(statementKey);

                    if (mappedStatement != null) {
                        MapperTypeEnum mapperType = mappedStatement.getMapperType();
                        switch (mapperType) {
                            case SELECT:
                                Type genericReturnType = method.getGenericReturnType();
                                if (genericReturnType instanceof ParameterizedType) {
                                    return selectList(statementKey, args);
                                }
                                return selectOne(statementKey, args);
                            case DELETE:
                                return delete(statementKey, args);
                            case INSERT:
                                return insert(statementKey, args);
                            case UPDATE:
                                return update(statementKey, args);
                            default:
                                return null;
                        }
                    } else {
                        throw new RuntimeException("statement not found");
                    }
                }
        );

        return (T) proxyInstance;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        MappedStatement mappedStatement = configuration.getMappersMap().get(statementId);
        if (mappedStatement != null) {
            return this.executor.query(configuration, mappedStatement, params);
        } else {
            throw new RuntimeException("statement is not found");
        }
    }

    @Override
    public <E> E selectOne(String statementId, Object... params) throws Exception {

        List<Object> objects = selectList(statementId, params);
        if (objects.size() == 1) {
            return (E) objects.get(0);
        } else {
            throw new RuntimeException("result too much");
        }
    }

    @Override
    public int update(String statementId, Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        MappedStatement mappedStatement = configuration.getMappersMap().get(statementId);
        if (mappedStatement != null) {
            return this.executor.update(configuration, mappedStatement, params);
        } else {
            throw new RuntimeException("update sql error:" + mappedStatement);
        }
    }

    @Override
    public int insert(String statementId, Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        MappedStatement mappedStatement = configuration.getMappersMap().get(statementId);
        if (mappedStatement != null) {
            return this.executor.insert(configuration, mappedStatement, params);
        } else {
            throw new RuntimeException("insert sql error:" + mappedStatement);
        }
    }

    @Override
    public int delete(String statementId, Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        MappedStatement mappedStatement = configuration.getMappersMap().get(statementId);
        if (mappedStatement != null) {
            return this.executor.delete(configuration, mappedStatement, params);
        } else {
            throw new RuntimeException("delete sql error:" + mappedStatement);
        }
    }


}
