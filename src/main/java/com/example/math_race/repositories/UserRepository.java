package com.example.math_race.repositories;

import com.example.math_race.entities.UserEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UserRepository extends BaseRepository {

    @Autowired
    public UserRepository(SessionFactory sf) {
        super(sf);
    }

    public UserEntity findByEmail(String email) {
        String hql = "FROM UserEntity where email = :email";

        return getCurrentSession()
                .createQuery(hql, UserEntity.class)
                .setParameter("email", email)
                .uniqueResult();
    }
}
