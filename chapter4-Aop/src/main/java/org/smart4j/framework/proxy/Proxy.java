package org.smart4j.framework.proxy;

/**
 * Created by bing on 1/4/16.
 */
public interface Proxy {
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
