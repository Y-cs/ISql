package self.sqlSession;

import self.config.BoundSql;
import self.pojo.Configuration;
import self.pojo.MappedStatement;
import self.sqlSession.hook.SqlSessionHook;
import self.utils.GenericTokenParser;
import self.utils.ParameterMapping;
import self.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Y-cs
 * @date 2021/4/3 23:06
 */
public class SimpleExecutor implements Executor, SqlSessionHook {

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        Connection connection = null;
        try {
            //获取链接
            connection = configuration.getDataSource().getConnection();
            beginExecuteHook(connection, mappedStatement, params);
            //获取sql
            String sql = mappedStatement.getSql();
            //获取参数类型
            String parameterType = mappedStatement.getParameterType();
            Class<?> parameterClass = getClassType(parameterType);
            //预处理
            PreparedStatement preparedStatement = executeSql(connection, sql, parameterClass, params);
            //执行
            ResultSet resultSet = preparedStatement.executeQuery();
            //获取结果参数
            String resultType = mappedStatement.getResultType();
            Class<?> resultClassType = getClassType(resultType);
            //结果集封装
            List<Object> resultList = new ArrayList<Object>();
            while (resultSet.next()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                Object result = getInstance(resultClassType);
                for (int i = 1; i < metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(columnName);
                    /**
                     * 内省(IntroSpector)是Java 语言对 Bean 类属性、事件的一种缺省处理方法。　
                     * JavaBean是一种特殊的类，主要用于传递数据信息，这种类中的方法主要用于访问私有的字段，且方法名符合某种命名规则。
                     * 如果在两个模块之间传递信息，可以将信息封装进JavaBean中，这种对象称为“值对象”(Value Object)，
                     * 或“VO”。方法比较少。这些信息储存在类的私有变量中，通过set()、get()获得。内省机制是通过反射来实现的，
                     * BeanInfo用来暴露一个bean的属性、方法和事件，以后我们就可以操纵该JavaBean的属性。
                     */
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultClassType);
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    writeMethod.invoke(result, value);

                }
                resultList.add(result);
            }
            return (List<E>) resultList;
        } finally {
            afterExecuteHook(connection, mappedStatement, params);
        }
    }

    @Override
    public int insert(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        return this.update(configuration, mappedStatement, params);
    }

    @Override
    public int delete(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        return this.update(configuration, mappedStatement, params);
    }

    @Override
    public int update(Configuration configuration,MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Connection connection = null;
        try {
            //获取链接
            connection = configuration.getDataSource().getConnection();
            beginExecuteHook(connection, mappedStatement, params);
            //获取sql
            String sql = mappedStatement.getSql();
            //获取参数类型
            String parameterType = mappedStatement.getParameterType();
            Class<?> parameterClass = getClassType(parameterType);
            //预处理
            PreparedStatement preparedStatement = executeSql(connection, sql, parameterClass, params);
            //执行
            //返回影响行数
            return preparedStatement.executeUpdate();
        } finally {
            afterExecuteHook(connection, mappedStatement, params);
        }
    }

    private PreparedStatement executeSql(Connection connection, String sql, Class<?> parameterClass, Object... param) throws SQLException, NoSuchFieldException, IllegalAccessException {
        BoundSql boundSql = getBoundSql(sql);
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        //设置参数
        //todo 设置参数的时候 没有完美考虑params
        HashMap<String, Object> parameters = new HashMap<>();
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String content = parameterMapping.getContent();
            Field declaredField = parameterClass.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object field = declaredField.get(param[0]);
            parameters.put(content,field);
            preparedStatement.setObject(i + 1, field);
        }
        executeSqlHook(boundSql,parameters);
        return preparedStatement;
    }

    private Class<?> getClassType(String classType) throws ClassNotFoundException {
        if (classType != null) {
            return Class.forName(classType);
        }else{
            throw new RuntimeException("classType is not found : "+classType);
        }
    }

    private Object getInstance(Class<?> resultClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?>[] constructors = resultClass.getConstructors();
        return constructors[0].newInstance();
    }

    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //翻译后的Sql
        String parseSql = genericTokenParser.parse(sql);
        //解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        return new BoundSql(parseSql, parameterMappings);
    }

}
