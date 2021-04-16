//package self.test;
//
//import org.dom4j.DocumentException;
//import self.dao.UserDao;
//import self.dao.UserDaoImpl;
//import self.io.Resources;
//import self.pojo.User;
//import self.sqlSession.SqlSession;
//import self.sqlSession.SqlSessionFactory;
//import self.sqlSession.SqlSessionFactoryBuilder;
//
//import java.beans.PropertyVetoException;
//import java.io.InputStream;
//import java.sql.SQLException;
//import java.util.List;
//
///**
// * @author Y-cs
// * @date 2021/4/3 22:58
// */
//public class ISqlTest {
//
//    public static void main(String[] args) throws Exception {
//
//        UserDao userDao=new UserDaoImpl();
//
//        System.out.println("------------->selectOne");
//        User user = new User();
//        user.setId(1);
//        user.setUsername("lucy");
//        System.out.println(userDao.findByCondition(user));
//        System.out.println("------------->selectList");
//        System.out.println(userDao.findAll());
//
//
//
//
//
//    }
//
//
//}
