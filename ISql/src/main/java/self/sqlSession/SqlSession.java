package self.sqlSession;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Y-cs
 * @date 2021/4/3 22:51
 */
public interface SqlSession {

    /**
     * 查询所有
     *
     * @param statementId
     * @param params
     * @param <E>
     * @return
     * @throws Exception
     */
    <E> List<E> selectList(String statementId, Object... params) throws Exception;

    /**
     * 查询单个
     *
     * @param statementId
     * @param params
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T selectOne(String statementId, Object... params) throws Exception;

    /**
     * 获取代理对象
     *
     * @param mapperClass
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<?> mapperClass);

    /**
     * 更新
     * @param statementId
     * @param params
     * @return
     */
    int update(String statementId,Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException;

    /**
     * 新增
     * @param statementId
     * @param params
     * @return
     */
    int insert(String statementId,Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException;

    /**
     * 删除
     * @param statementId
     * @param params
     * @return
     */
    int delete(String statementId,Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException;

}
