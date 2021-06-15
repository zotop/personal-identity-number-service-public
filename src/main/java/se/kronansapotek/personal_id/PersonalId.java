package se.kronansapotek.personal_id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PersonalId {

    @Id
    @Column(name = "id")
    @Pattern(regexp = "(\\d{6}|\\d{8})(\\+|-?)(\\d{4})")
    private String id;

    @Column(name = "valid")
    private Boolean valid;

}
