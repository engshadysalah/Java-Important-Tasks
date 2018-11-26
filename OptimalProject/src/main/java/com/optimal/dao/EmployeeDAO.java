/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.dao;

import com.optimal.dbmodel.Employee;
import com.optimal.hibernate.HibernateUtil;
 import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author shady
 */


public class EmployeeDAO {

    public void Save(Employee emp) {

        Session se = HibernateUtil.getSessionFactory().openSession();
        Transaction trans = se.beginTransaction();
        try {

            se.save(emp);
            se.getTransaction().commit();

        } catch (RuntimeException ex) {

            if (trans != null) {

                trans.rollback();
            }
            ex.printStackTrace();
        } finally {
            se.flush();
            se.close();

        }
    }

    // general to delet or update
    public void executedEmployee(String HQL) {

        Transaction trans = null;

        Session se = HibernateUtil.getSessionFactory().openSession();

        try {

            trans = se.beginTransaction();

            se.createQuery(HQL).executeUpdate();

            se.getTransaction().commit();

        } catch (RuntimeException ex) {

            if (trans != null) {

                trans.rollback();
            }
            ex.printStackTrace();
        } finally {
            se.flush();
            se.close();

        }
    }

    public void deletdEmployee(int id) {

        String hql = "delete Employee e where e.id ='" + id + "'";
        executedEmployee(hql);

    }

    public void updateEmployee(int id, String newfirstNamel, String newaddress, String newphone , String newBirthDate, int jobId , int departmentId) {

        String hql = "update Employee e set e.firstName ='" + newfirstNamel + "' , e.address ='" + newaddress + "' , e.phone ='" + newphone + "' , e.birthDate ='" + newBirthDate + "' , e.job ='" + jobId + "' , e.departments ='" + departmentId +"' where e.id ='" + id + "'";
        executedEmployee(hql);

    }

    // genral
    public List list(String HQL) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            session.beginTransaction();
            List l = session.createQuery(HQL).list();
            session.getTransaction().commit();
            return l;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public List getEmployeeByphone(String phone) {

        String hql = "from Employee e join fetch e.job join fetch e.departments where  e.phone  ='" + phone + "'";

        return list(hql);
    }

    //get data of selected row by id of datarow
    
    public List getEmployeeById(int id) {

        //join 3 tables
        String hql = "from Employee e join fetch e.job  join fetch e.departments where  e.id  ='" + id + "'";

        return list(hql);
    }

    public List getAllEmployees() {
        
        // join 3 tables (, e.departments)
        String hql = "from Employee e join fetch e.job join fetch e.departments";

        return list(hql);
    }

  

}
