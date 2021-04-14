package self.dao;

import org.dom4j.DocumentException;
import self.pojo.User;

import java.beans.PropertyVetoException;
import java.util.List;

/**
 * @author Y-cs
 * @date 2021/4/4 0:19
 */
public interface UserDao {

    List<User> findAll() throws Exception;

    User findByCondition(User user) throws Exception;




}
