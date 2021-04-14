package self.sqlSession;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Y-cs
 * @date 2021/4/3 22:51
 */
public interface SqlSession {

    //查询所有
    <E> List<E> selectList(String statementId, Object... params) throws Exception;

    //查询单个
    <T> T selectOne(String statementId, Object... params) throws Exception;

    <T> T getMapper(Class<?> mapperClass);

}
