package test;

import POJO.User;
import POJO.UserDaoImp;
import org.junit.Test;

/**
 *
 */
public class BaseDaoTest {
    @Test
    public void test() {
        UserDaoImp userDaoImp = new UserDaoImp();
        User user = userDaoImp.queryByAge(35);
        System.out.println(user.toString());
    }
}
