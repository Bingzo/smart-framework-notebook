package org.smart4j.chapter1.controller;

import org.smart4j.chapter1.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bing on 11/9/15.
 */
@WebServlet("/customer_edit")
public class CustomerCreateServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp )
        throws ServletException, IOException {
        String id = req.getParameter( "id" );
        req.getRequestDispatcher( "/WEB-INF/view/customer_create.jsp" ).forward( req, resp );
    }
}
