package com.accountServlet.daoUpdate;


import com.accountServlet.util.JDBCutileWithPool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 封装了针对于数据表的通用的操作
 * DAO: data access object
 * The utility tool for JDBC about insert, delete, update
 * Using reflect to get the generic from super class
 */
public abstract class BaseDao<T> {
    /**
     * @param conn -- need a Connection instance as a parameter in order to create a transaction by using same connection
     * @param sql
     * @param args
     * @return -- how many lines have been affected
     */

    private Class<T> clazz = null;

    /**
     * 给clazz赋值,值就是子类AccountDaoImp继承父类BaseDao<Account>的泛型Account
     * 因为子类不用,泛型不同,所以将赋值语句写在父类中,子类自动继承即可
     * 这里就是利用反射来得到父类的泛型.
     * 可以显式赋值(不推荐),代码块赋值,或者构造器赋值
     */ {//造对象的时候,子类会调用该语句,届时语句中的this,就是只得执行的子类(多态)
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;//已知type是泛型,所以强转
        Type[] actualTypeArguments = paramType.getActualTypeArguments();//获得父类泛型的list
        clazz = (Class<T>) actualTypeArguments[0];

    }

    public int updateWithoutConn(Connection conn, String sql, Object... args) throws SQLException {
        PreparedStatement ps = null;

//            create preparedstatement
        ps = conn.prepareStatement(sql);
//            set parameters
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
        return (ps.executeUpdate());

    }

    public T queryInstanceWithoutConn(Connection conn, String sql, Object... args) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
        rs = ps.executeQuery();
//            rsmd is the resultSet's source data, using rsmd to get the colum in total
        ResultSetMetaData rsmd = rs.getMetaData();
//            the class who relate to this table
        int columnCount = rsmd.getColumnCount();
        if (rs.next()) {
            T obj = clazz.getDeclaredConstructor().newInstance();
            for (int i = 0; i < columnCount; i++) {
//                    using column index to get the data
                Object columnValue = rs.getObject(i + 1);
//                    getting the name of the colum.
//                    If the name in class is different to the one in table, give the name a label in sql
//                    using the rsmd.getColumnlabel()
//                    String columnName = rsmd.getColumnName(i + 1);
                String columnlabel = rsmd.getColumnLabel(i + 1);
//                    using reflect to assign the value to the property
                Field field = obj.getClass().getDeclaredField(columnlabel);
                field.setAccessible(true);
                field.set(obj, columnValue);
            }
            return obj;
        }
        return null;
    }

    /**
     * return List
     */
    public List<T> queryList(Connection conn, String sql, Object... args) throws SQLException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<>();

        ps = conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
        rs = ps.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        while (rs.next()) {
            T t = clazz.getDeclaredConstructor().newInstance();
            for (int i = 0; i < columnCount; i++) {
                String columnLabel = rsmd.getColumnLabel(i + 1);
                Object columnValue = rs.getObject(i + 1);
                Field field = clazz.getDeclaredField(columnLabel);
                field.setAccessible(true);
                field.set(t, columnValue);
            }
            list.add(t);
        }
        return list;

    }

    /**
     * 用于查询特殊的聚合函数的通用方法
     *
     * @param conn -- connection
     * @param sql  -- sql
     * @param args -- values for placeholder
     * @param <E>  -- the data type which will be returned
     * @return 返回一行一列
     */
    public <E> E getValue(Connection conn, String sql, Object... args) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
        rs = ps.executeQuery();
        if (rs.next()) {
            return (E) rs.getObject(1);
        }
        return null;
    }
}
