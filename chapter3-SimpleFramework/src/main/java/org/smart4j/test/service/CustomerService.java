package org.smart4j.test.service;

/**
 * Created by bing on 11/7/15.
 */

import org.smart4j.framework.annotation.Service;
import org.smart4j.test.helper.DatabaseHelper;
import org.smart4j.test.model.Customer;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    public List<Customer> getCustomerList(){
        String sql = "SELECT * FROM customer";
        return DatabaseHelper.queryEntityList( Customer.class, sql );
    }

    public Customer getCustomer( long id ){
        String sql = "SELECT * FROM customer WHERE id=?";
        return DatabaseHelper.queryEntity( Customer.class, sql, id );
    }

    public boolean createCustomer( Map<String, Object> fieldMap ){
        return DatabaseHelper.insertEntity( Customer.class, fieldMap );
    }

    public boolean updateCustomer( long id, Map<String, Object> fieldMap ){
        return DatabaseHelper.updateEntity( Customer.class, id, fieldMap );
    }

    public boolean deleteCustomer( long id ){
        return DatabaseHelper.deleteEntity( Customer.class, id );
    }

}
