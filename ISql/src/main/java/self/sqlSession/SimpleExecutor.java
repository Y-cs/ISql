package self.sqlSession;

import self.config.BoundSql;
import self.pojo.Configuration;
import self.pojo.MappedStatement;
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
import java.util.List;

/**
 * @author Y-cs
 * @date 2021/4/3 23:06
 */
public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {

        Connection connection = configuration.getDataSource().getConnection();

        String sql = mappedStatement.getSql();
        String parameterType = mappedStatement.getParameterType();
        Class<?> parameterClass = getClassType(parameterType);
        BoundSql boundSql = getBoundSql(sql);

        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String content = parameterMapping.getContent();

            Field declaredField = parameterClass.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object field = declaredField.get(params[0]);

            preparedStatement.setObject(i + 1, field);
        }

        ResultSet resultSet = preparedStatement.executeQuery();

        String resultType = mappedStatement.getResultType();
        Class<?> resultClassType = getClassType(resultType);

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
    }

    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if (parameterType != null) {
            return Class.forName(parameterType);
        }
        return null;
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
