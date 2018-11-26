/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.dao;

import com.optimal.dbmodel.Departments;
import com.optimal.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author shady
 */
public class DepartmentsDAO {
    
    
    public void save(Departments departments) {

        Session se = HibernateUtil.getSessionFactory().openSession();
        Transaction trans = se.beginTransaction();
        try {
            se.save(departments);
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
    public void executedDepartment(String HQL) {

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

    public void deletdDepartment(int id) {

        String hql = "delete Departments d where d.id ='" + id + "'";
        executedDepartment(hql);

    }

    public void updateDepartment(int id, String newDepartmentTitel, String newDepartmentDescrption) {

        String hql = "update Departments d set d.title ='" + newDepartmentTitel + "' , d.descrption ='" + newDepartmentDescrption + "'where d.id ='" + id + "'";
        executedDepartment(hql);

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

    public List getAllDepartments() {
        String hql = "from Departments d";
        return list(hql);
    }

    //search by department Titel
    public List getDepartmentByTitel(String titel) {

        String hql = "from Departments d  where  d.title  ='" + titel + "'";

        return list(hql);
    }

    //get data of selected row by id of datarow
    public List getDepartmentById(int id) {

        String hql = "from Departments d where  d.id  ='" + id + "'";

        return list(hql);
    }

    // return object 
    public Departments getIdByDepartmentTitel(String DepartmentTitel) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Object uniqueResult = null;
        try {

            session.beginTransaction();
            uniqueResult = session.createQuery("from Departments d   where  d.title   ='" + DepartmentTitel + "'").uniqueResult();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return (Departments) uniqueResult;
    }

    
}
