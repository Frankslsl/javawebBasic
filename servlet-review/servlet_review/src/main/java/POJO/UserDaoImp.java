package POJO;

/**
 *
 */
public class UserDaoImp extends BaseDao implements UserDao{

    @Override
    public User queryByAge(Integer age) {
        String sql = "select `user_id` as `userId`, `first_name` as `firstName` from user where age = ?";
        User user = (User) queryForOne(User.class, sql, age);
        return user;
    }
}
