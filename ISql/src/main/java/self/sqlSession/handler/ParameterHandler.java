package self.sqlSession.handler;

import self.config.BoundSql;
import self.pojo.MappedStatement;
import self.sqlSession.hook.SqlSessionHook;
import self.utils.ParameterMapping;
import self.utils.ReflexUtil;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * @author Y-cs
 * @date 2021/4/16 17:31
 */
public class ParameterHandler implements Handler, SqlSessionHook {

    public void handler(PreparedStatement preparedStatement, BoundSql boundSql, MappedStatement mappedStatement, Object... params) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, SQLException {
        //获取参数类型
        String parameterType = mappedStatement.getParameterType();
        Class<?> parameterClass = ReflexUtil.getClassType(parameterType);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        //设置参数
        //todo 设置参数的时候 没有完美考虑params 解决这个问题需要从解析开始 支持getMapper解析
        HashMap<String, Object> parameters = new HashMap<String, Object>(parameterMappings.size(), 0.1F);
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String content = parameterMapping.getContent();
            Field declaredField = parameterClass.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object field = declaredField.get(params[0]);
            parameters.put(content, field);
            preparedStatement.setObject(i + 1, field);
        }
        SqlSessionHook.super.executeSqlHook(boundSql,mappedStatement, parameters);
    }

}
