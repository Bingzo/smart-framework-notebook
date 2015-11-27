package org.smart4j.test.helper;

/**
 * Created by bing on 11/7/15.
 */

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.PropsUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger( DatabaseHelper.class );
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();
    private static final BasicDataSource DATA_SOURCE;

    static {
        String driver = ConfigHelper.getJdbcDriver();
        String url = ConfigHelper.getJdbcUrl();
        String username = ConfigHelper.getJdbcUsername();
        String password = ConfigHelper.getJdbcPassword();

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName( driver );
        DATA_SOURCE.setUrl( url );
        DATA_SOURCE.setUsername( username );
        DATA_SOURCE.setPassword( password );
    }

    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params ) {
        List<T> entityList;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query( conn, sql, new BeanListHandler<T>(entityClass), params );
        } catch ( SQLException e ){
            LOGGER.error( "query entity list failure", e );
            throw new RuntimeException( e );
        }
        return entityList;
    }

    public static <T> T queryEntity( Class<T> entityClass, String sql, Object... params ) {
        T entity;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query( conn, sql, new BeanHandler<T>(entityClass), params );
        } catch ( SQLException e ){
            LOGGER.error( "query entity failure", e );
            throw new RuntimeException( e );
        }
        return entity;
    }

    public static <T> boolean insertEntity( Class<T> entityClass, Map<String, Object> fieldMap ) {
        if ( CollectionUtil.isEmpty( fieldMap ) ) {
            LOGGER.error( "can not insert entity: fieldMap is empty" );
            return false;
        }

        String sql = "INSERT INTO " + getTableName( entityClass );
        StringBuilder columns = new StringBuilder( "(" );
        StringBuilder values = new StringBuilder( "(" );
        for ( String fieldName : fieldMap.keySet() ) {
            columns.append( fieldName ).append( ", " );
            values.append( "?, " );
        }

        columns.replace( columns.lastIndexOf( ", " ), columns.length(), ")" );
        values.replace( values.lastIndexOf( ", " ), values.length(), ")" );
        sql += columns + " VALUES " + values;

        Object[] params = fieldMap.values().toArray();
        return executeUpdate( sql, params ) == 1;
    }

    public static <T> boolean updateEntity( Class<T> entityClass, long id, Map<String, Object> fieldMap ){
        if ( CollectionUtil.isEmpty( fieldMap ) ) {
            LOGGER.error( "can not update entity: fieldMap is empty" );
            return false;
        }

        String sql = "UPDATE " + getTableName( entityClass ) + " SET ";
        StringBuilder columns = new StringBuilder();
        for ( String fieldName : fieldMap.keySet() ){
            columns.append( fieldName ).append( "=?, " );
        }
        sql += columns.substring( 0, columns.lastIndexOf( ", " ) ) + " WHERE id=?";

        List<Object> paramList = new ArrayList<Object>();
        paramList.addAll( fieldMap.values() );
        paramList.add( id );
        Object[] params = paramList.toArray();

        return executeUpdate( sql, params ) == 1;
    }

    public static <T> boolean deleteEntity( Class<T> entityClass, long id ){
        String sql = "DELETE FROM " + getTableName( entityClass ) + " WHERE id=?";
        return executeUpdate( sql, id ) == 1;
    }

    public static int executeUpdate( String sql, Object... params ) {
        int rows = 0;
        try {
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update( conn, sql, params );
        } catch ( SQLException e ){
            LOGGER.error( "excute update failure", e );
            throw new RuntimeException( e );
        }
        return rows;
    }

    public static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if( conn == null ) {
            try {
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("get connection failure", e);
                throw new RuntimeException( e );
            } finally {
                CONNECTION_HOLDER.set( conn );
            }
        }
        return conn;
    }

//    public static void closeConnection(){
//        Connection conn = CONNECTION_HOLDER.get();
//        if ( conn != null ){
//            try {
//                conn.close();
//            } catch ( SQLException e ){
//                LOGGER.error( "close connection failure", e );
//                throw new RuntimeException( e );
//            } finally {
//                CONNECTION_HOLDER.remove();
//            }
//        }
//    }

    public static void executeSqlFile( String filePath ){
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream( filePath );
        BufferedReader reader = new BufferedReader( new InputStreamReader( is ) );
        try {
            String sql;
            while ( ( sql = reader.readLine() ) != null ){
                executeUpdate( sql );
            }
        } catch ( Exception e ){
            LOGGER.error( "execute sql file failure", e );
            throw  new RuntimeException( e );
        }
    }

    private static String getTableName( Class<?> entityClass ){
        return entityClass.getSimpleName().toLowerCase();
    }

}
