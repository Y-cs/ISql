package self.sqlSession.handler;

import self.pojo.MappedStatement;
import self.sqlSession.Executor;
import self.sqlSession.hook.SqlSessionHook;
import self.utils.ReflexUtil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Y-cs
 * @date 2021/4/16 17:31
 */
public class ResultHandler implements Handler, SqlSessionHook {

    public <T> T handlerResultSet(ResultSet resultSet, MappedStatement mappedStatement) throws ClassNotFoundException, SQLException, InvocationTargetException, IllegalAccessException, InstantiationException, IntrospectionException {
        //获取结果参数
        String resultType = mappedStatement.getResultType();
        Class<?> resultClassType = ReflexUtil.getClassType(resultType);
        //结果集封装
        List<Object> resultList = new ArrayList<Object>();
        while (resultSet.next()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            Object result = ReflexUtil.getInstance(resultClassType);
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
        SqlSessionHook.super.resultSqlHook(resultSet,mappedStatement,resultList);
        return (T) resultList;
    }

}
