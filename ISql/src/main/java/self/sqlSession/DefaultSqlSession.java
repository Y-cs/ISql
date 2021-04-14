package self.sqlSession;

import self.pojo.Configuration;
import self.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

/**
 * @author Y-cs
 * @date 2021/4/3 22:51
 */
public class DefaultSqlSession implements SqlSession {


    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {

        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappersMap().get(statementId);
        if (mappedStatement != null) {
            return simpleExecutor.query(configuration, mappedStatement, params);
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
    public <T> T getMapper(Class<?> mapperClass) {

        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(),
                new Class[]{mapperClass},
                //当前代理对象的应用,当前被调用方法的应用,传递的参数
                (proxy, method, args) -> {
                    String statementKey = getStatementKey(method);

                    Type genericReturnType = method.getGenericReturnType();

                    if (genericReturnType instanceof ParameterizedType) {
                        return selectList(statementKey, args);
                    }

                    return selectOne(statementKey, args);
                }
        );

        return (T) proxyInstance;
    }


    private String getStatementKey(Method method) {
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        return className + "." + methodName;
    }

}
