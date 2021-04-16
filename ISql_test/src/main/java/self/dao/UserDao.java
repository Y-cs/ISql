package self.dao;

import self.pojo.User;

import java.util.List;

/**
 * @author Y-cs
 * @date 2021/4/4 0:19
 */
public interface UserDao {

    List<User> findAll() throws Exception;

    User findByCondition(User user) throws Exception;

    int insertUser(User user);

    int updateUser(User user);

    int deleteUser(User user);

}
