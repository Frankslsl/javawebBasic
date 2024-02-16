package com.accountServlet.util;

/**
 *
 */
public class Utile {
    public static boolean strIsNull(String str){
        return str == null || ("").equals(str);
    }
    public static  boolean strNotNull(String str){
        return !strIsNull(str);
    }
}
