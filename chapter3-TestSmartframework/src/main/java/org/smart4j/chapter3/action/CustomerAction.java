package org.smart4j.chapter3.action;

import org.smart4j.chapter3.entity.Customer;
import org.smart4j.chapter3.service.CustomerService;
import org.smart4j.framework.ioc.annotation.Inject;
import org.smart4j.framework.mvc.DataContext;
import org.smart4j.framework.mvc.annotation.Action;
import org.smart4j.framework.mvc.annotation.Request;
import org.smart4j.framework.mvc.bean.View;

import java.util.List;

/**
 * Created by bing on 11/10/15.
 */

@Action
public class CustomerAction {

    @Inject
    private CustomerService customerService;

    @Request.Get( "/customer" )
    public View index() {
        List<Customer> customerList = customerService.getCustomerList();
        DataContext.Request.put( "customerList", customerList );
        return new View( "customer.jsp" );
    }

    @Request.Get( "/customer_edit/{id}")
    public View edit( long id ){
        Customer customer = customerService.getCustomer( id );
        DataContext.Request.put( "customer", customer );
        return new View( "customer_edit.jsp" );
    }
}
