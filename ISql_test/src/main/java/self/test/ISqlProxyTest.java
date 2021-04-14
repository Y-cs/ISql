package self.test;

import self.dao.UserDao;
import self.io.Resources;
import self.pojo.User;
import self.sqlSession.SqlSession;
import self.sqlSession.SqlSessionFactory;
import self.sqlSession.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @author Y-cs
 * @date 2021/4/4 0:29
 */
public class ISqlProxyTest {

    public static void main(String[] args) throws Exception {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserDao userMapper = sqlSession.getMapper(UserDao.class);

        System.out.println("------------->selectOne");
        User user = new User();
        user.setId(1);
        user.setUsername("lucy");
        System.out.println(userMapper.findByCondition(user));
        System.out.println("------------->selectList");
        System.out.println(userMapper.findAll());


    }


}
