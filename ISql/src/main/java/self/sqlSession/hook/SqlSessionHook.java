package self.sqlSession.hook;

import self.config.BoundSql;
import self.pojo.MappedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

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
     * 执行中
     *
     * @param BoundSql
     * @param parameters
     */
    default void executeSqlHook(BoundSql BoundSql, HashMap<String, Object> parameters) {
        System.out.println("=====> " + BoundSql.getSql() + "\n" +
                "=====> Parameter:" + parameters);
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
