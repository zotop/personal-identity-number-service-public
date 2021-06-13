package se.kronansapotek.personal_id;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalIdRepository extends JpaRepository<PersonalId, String> {
}

