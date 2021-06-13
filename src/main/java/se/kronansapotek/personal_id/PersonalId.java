package se.kronansapotek.personal_id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PersonalId {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "valid")
    private Boolean valid;

}
