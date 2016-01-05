package org.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Transaction;
import org.smart4j.framework.helper.DatabaseHelper;

import java.lang.reflect.Method;

/**
 * Created by bing on 1/5/16.
 */
public class TransactionProxy implements Proxy{

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);

    private static final ThreadLocal<Boolean> FlAG_HOLDER = new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue(){
            return false;
        }
    };

    public Object doProxy(ProxyChain proxyChain) throws Throwable{
        Object result = null;
        boolean flag = FlAG_HOLDER.get();
        Method method = proxyChain.getTargetMethod();
        if(!flag && method.isAnnotationPresent(Transaction.class)){
            FlAG_HOLDER.set(true);
            try{
                DatabaseHelper.beginTransaction();
                LOGGER.debug("begin transaction");
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
                LOGGER.debug("commit transaction");
            } catch (Exception e){
                DatabaseHelper.rollbackTransaction();
                LOGGER.debug("rollback transaction");
            } finally {
                FlAG_HOLDER.remove();
            }
        } else {
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
