package com.ibm.jpascanner.testapp.jee7.simple.webapp;

import java.io.IOException;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

@WebServlet(name="TestServlet", urlPatterns = {"/TestServlet"})
public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = 3680926979467486790L;

    @PersistenceContext(unitName="Test-Web-PU")
    private EntityManager em;
    
    private UserTransaction tx;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        
        long time = System.currentTimeMillis();
        try {
            tx.begin();
            TestEntity entity = new TestEntity();
            entity.setId(time);
            entity.setIntData(42);
            entity.setStrData("In the land of Mordor, where the shadows dwell.");
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
