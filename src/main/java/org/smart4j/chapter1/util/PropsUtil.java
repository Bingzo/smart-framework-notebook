package org.smart4j.chapter1.util;

/**
 * Created by bing on 11/7/15.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger( PropsUtil.class );

    public static Properties loadProps( String fileName ) {
        Properties props = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream( fileName );
            if( is == null ){
                throw new FileNotFoundException( fileName + "file is not found" );
            }
            props = new Properties();
            props.load( is );
        } catch ( IOException e ){
            LOGGER.error( "load properties file failure", e );
        } finally {
            if( is != null ){
                try {
                    is.close();
                } catch ( IOException e ){
                    LOGGER.error( "close input stream failure", e );
                }
            }
        }
        return props;
    }

}
