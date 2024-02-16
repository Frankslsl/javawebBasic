package test;

import org.junit.Test;
import util.JDBCutileWithPool;

import java.sql.Connection;

/**
 *
 */

public class JdbcConnTest {
    @Test
    public void connTest(){
        Connection connection2 = JDBCutileWithPool.getConnection2();
        System.out.println(connection2.toString());
        JDBCutileWithPool.close(connection2);
    }
}
