import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;
import self.dao.UserDao;
import self.io.Resources;
import self.pojo.User;
import self.sqlSession.SqlSession;
import self.sqlSession.SqlSessionFactory;
import self.sqlSession.SqlSessionFactoryBuilder;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author Y-cs
 * @date 2021/4/4 0:29
 */
public class ISqlProxyTest {

    UserDao userMapper = null;

    @Before
    public void before() throws PropertyVetoException, DocumentException {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        userMapper = sqlSession.getMapper(UserDao.class);
    }

    @Test
    public void test1() throws Exception {
        System.out.println("------------->selectOne");
        User user = new User();
        user.setId(1);
        user.setUsername("lucy");
        System.out.println(userMapper.findByCondition(user));
        System.out.println("------------->selectList");
        System.out.println(userMapper.findAll());
    }

    @Test
    public void insert() {
        User user = new User();
        user.setUsername("aaaa");
        user.setPassword("pass");
        userMapper.insertUser(user);
    }

    @Test
    public void update() {
        User user = new User();
        user.setId(3);
        user.setUsername("aaaa");
        user.setPassword("update");
        int i = userMapper.updateUser(user);
        System.out.println(i);
    }

    @Test
    public void delete() {
        User user = new User();
        user.setId(3);
        int i = userMapper.deleteUser(user);
        System.out.println(i);
    }


}
