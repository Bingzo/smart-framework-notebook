package org.smart4j.chapter3.service;

/**
 * Created by bing on 11/7/15.
 */

import org.smart4j.chapter3.entity.Customer;
import org.smart4j.framework.orm.DataSet;
import org.smart4j.framework.tx.annotation.Service;
import org.smart4j.framework.tx.annotation.Transaction;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    public List<Customer> getCustomerList(){
        return DataSet.selectList( Customer.class );
    }

    public Customer getCustomer( long id ){
        return DataSet.select( Customer.class, "id=?", id );
    }

    @Transaction
    public boolean createCustomer( Map<String, Object> fieldMap ){
        return DataSet.insert( Customer.class, fieldMap );
    }

    @Transaction
    public boolean updateCustomer( long id, Map<String, Object> fieldMap ){
        return DataSet.update( Customer.class, fieldMap, "id=?", id );
    }

    @Transaction
    public boolean deleteCustomer( long id ){
        return DataSet.delete( Customer.class, "id=?", id );
    }
}
