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

    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement,Object... params) throws SQLException, ClassNotFoundException, Exception;

}
