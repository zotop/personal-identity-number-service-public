package se.kronansapotek.personalidentitynumberservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
public class DataRepository {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    void persist(String in, boolean res) {
        this.entityManager
                .createNativeQuery("INSERT INTO data VALUES(?1, ?2)")
                .setParameter(1, in)
                .setParameter(2, res)
                .executeUpdate();
    }


}
