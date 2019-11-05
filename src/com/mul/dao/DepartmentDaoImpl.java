package com.mul.dao;

import com.mul.entity.ProgramStudi;
import com.mul.util.DaoService;
import com.mul.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoImpl implements DaoService<ProgramStudi> {

    @Override
    public List<ProgramStudi> showAll() {
        List<ProgramStudi> departments = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        Criteria criteria = session.createCriteria(ProgramStudi.class);
        departments.addAll(criteria.list());

//        Session session = HibernateUtil.getSession();
//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery<ProgramStudi> query = builder.createQuery(ProgramStudi.class);
//        Root<ProgramStudi> root = query.from(ProgramStudi.class);
//        query.select(root);
//        Query<ProgramStudi> q = session.createQuery(query);
//        departments.addAll(q.list());

        return departments;
    }

    @Override
    public int addData(ProgramStudi object) {
        int result = 0;
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(object);
            transaction.commit();
            result = 1;
        } catch (HibernateException exception) {
            transaction.rollback();
        }
        session.close();
        return result;
    }

    @Override
    public int deleteData(ProgramStudi object) {
        int result = 0;
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(object);
            transaction.commit();
            result = 1;
        } catch (HibernateException exception) {
            transaction.rollback();
        }
        session.close();
        return result;    }

    @Override
    public int updateData(ProgramStudi object) {
        int result = 0;
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(object);
            transaction.commit();
            result = 1;
        } catch (HibernateException exception) {
            transaction.rollback();
        }
        session.close();
        return result;
    }
}
