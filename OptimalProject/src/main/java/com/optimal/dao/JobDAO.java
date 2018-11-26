/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.dao;

import com.optimal.hibernate.HibernateUtil;
import com.optimal.dbmodel.Job;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author shady
 */
public class JobDAO {

    public void save(Job job) {

        Session se = HibernateUtil.getSessionFactory().openSession();
        Transaction trans = se.beginTransaction();
        try {
            se.save(job);
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
    public void executedJob(String HQL) {

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

    public void deletdJob(int id) {

        String hql = "delete Job j where j.id ='" + id + "'";
        executedJob(hql);

    }

    public void updateJob(int id, String newJobTitel, String newJobDescrption) {

        String hql = "update Job j set j.jopTitle ='" + newJobTitel + "' , j.description ='" + newJobDescrption + "'where j.id ='" + id + "'";
        executedJob(hql);

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

    public List getAllJobs() {
        String hql = "from Job j";
        return list(hql);
    }

    //search by jobTitel
    public List getjobByJobTitel(String titel) {

        String hql = "from Job j  where  j.jopTitle  ='" + titel + "'";

        return list(hql);
    }

    //get data of selected row by id of datarow
    public List getJobByJobId(int id) {

        String hql = "from Job j  where  j.id  ='" + id + "'";

        return list(hql);
    }

    public Job getIdByjobTitel(String jobTitel) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Object uniqueResult = null;
        try {

            session.beginTransaction();
            uniqueResult = session.createQuery("from Job j  where  j.jopTitle  ='" + jobTitel + "'").uniqueResult();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return (Job) uniqueResult;
    }

}
