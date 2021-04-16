package self.sqlSession;

import self.pojo.Configuration;
import self.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Y-cs
 * @date 2021/4/3 23:05
 */
public interface Executor {

    /**
     * query
     * @param configuration
     * @param mappedStatement
     * @param params
     * @param <E>
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws Exception
     */
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, Exception;

    /**
     * update
     * @param configuration
     * @param mappedStatement
     * @param params
     * @return
     */
    int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException;

    /**
     * insert
     * @param configuration
     * @param mappedStatement
     * @param params
     * @return
     */
    int insert(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException;

    /**
     * delete
     * @param configuration
     * @param mappedStatement
     * @param params
     * @return
     */
    int delete(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException;
}
