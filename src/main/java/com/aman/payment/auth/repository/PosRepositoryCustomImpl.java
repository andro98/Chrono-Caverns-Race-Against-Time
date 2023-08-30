package com.aman.payment.auth.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.User;

public class PosRepositoryCustomImpl implements PosRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    @Override
    public List<Pos> getAllPos(long id)
    {

        List<Pos>res=new ArrayList<Pos>();
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT mo FROM Pos mo join fetch mo.users u where u.id=:u");//where mo.id not in(select pos_id from user_pos)

        TypedQuery<Pos> query = em.createQuery(jpql.toString(),Pos.class);
        query.setParameter("u",id);
        res=query.getResultList();
    return res;
    }
	@Override
	public List<User> getAllUsers(long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
