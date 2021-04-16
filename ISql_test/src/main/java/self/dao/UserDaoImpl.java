//package self.dao;
//
//import org.dom4j.DocumentException;
//import self.io.Resources;
//import self.pojo.User;
//import self.sqlSession.SqlSession;
//import self.sqlSession.SqlSessionFactory;
//import self.sqlSession.SqlSessionFactoryBuilder;
//
//import java.beans.PropertyVetoException;
//import java.io.InputStream;
//import java.util.List;
//
///**
// * @author Y-cs
// * @date 2021/4/4 0:20
// */
//public class UserDaoImpl implements UserDao {
//    @Override
//    public List<User> findAll() throws Exception {
//        InputStream resourceAsSteam = Resources.getResourceAsSteam("config.xml");
//        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
//        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsSteam);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        List<User> list = sqlSession.selectList("self.dao.UserDao.findAll");
//        return list;
//    }
//
//    @Override
//    public User findByCondition(User user) throws Exception {
//        InputStream resourceAsSteam = Resources.getResourceAsSteam("config.xml");
//        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
//        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsSteam);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        User one = sqlSession.selectOne("self.dao.UserDao.findByCondition",user);
//        return one;
//    }
//}
