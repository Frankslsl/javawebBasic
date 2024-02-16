package POJO;

import com.mysql.cj.jdbc.JdbcConnection;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import util.JDBCutileWithPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public abstract class BaseDao {
    private QueryRunner queryRunner = new QueryRunner();

    public int update(String sql, Object ...args){
        Connection conn = JDBCutileWithPool.getConnection2();
        try {
            int result = queryRunner.update(conn, sql, args);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCutileWithPool.close(conn);
        }

    }

    public <T> Object queryForOne(Class<T> type, String sql, Object ...args){
        Connection connection2 = JDBCutileWithPool.getConnection2();
        try {
            T query = queryRunner.query(connection2, sql, new BeanHandler<>(type), args);
            return query;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCutileWithPool.close(connection2);
        }
    }

    public <T> List<T> queryForList(Class<T> type, String sql, Object ... args){
        Connection conn = JDBCutileWithPool.getConnection2();
        try {
           return queryRunner.query(conn, sql, new BeanListHandler<T>(type));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCutileWithPool.close(conn);
        }
    }

    public Object queryForSingleValue(String sql, Object ... args){
        Connection connection2 = JDBCutileWithPool.getConnection2();
        try {
            Object query = queryRunner.query(connection2, sql, new ScalarHandler());
            return query;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCutileWithPool.close(connection2);
        }

    }
}
