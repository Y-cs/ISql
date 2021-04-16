package self.sqlSession.hook;

import self.config.BoundSql;
import self.pojo.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Y-cs
 * @date 2021/4/16 14:33
 */
public interface SqlSessionHook {

    /**
     * 开始的Hook
     *
     * @param connection
     * @param mappedStatement
     * @param params
     */
    default void beginExecuteHook(Connection connection, MappedStatement mappedStatement, Object... params) {
        System.out.println("==========Start==========");
    }

    /**
     * 发送
     * @param BoundSql
     * @param mappedStatement
     * @param parameters
     */
    default void executeSqlHook(BoundSql BoundSql, MappedStatement mappedStatement, Map<String, Object> parameters) {
        System.out.println("=====> " + BoundSql.getSql() + "\n" +
                "=====> Parameter:" + parameters);
    }

    /**
     * 返回
     * @param resultSet
     * @param mappedStatement
     * @param resultList
     * @throws SQLException
     */
    default void resultSqlHook(ResultSet resultSet, MappedStatement mappedStatement, List<Object> resultList) throws SQLException {
        System.out.println("<===== result size:" + resultSet.getRow() + "\n" +
                "<===== Result:" + resultList);
    }

    /**
     * 结束的Hook
     *
     * @param connection
     * @param mappedStatement
     * @param params
     */
    default void afterExecuteHook(Connection connection, MappedStatement mappedStatement, Object... params) {
        System.out.println("===========End===========");
    }

}
