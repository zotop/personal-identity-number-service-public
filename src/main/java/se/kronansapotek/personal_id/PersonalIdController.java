package se.kronansapotek.personal_id;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/personal-ids/")
public class PersonalIdController {

    private final PersonalIdService personalIdService;
    private final PersonalIdRepository personalIdRepository;

    @GetMapping(value = "/{id}/valid")
    public PersonalId valid(@PathVariable("id") @Pattern(regexp = "(\\d{6}|\\d{8})(\\+|-?)(\\d{4})") String id) {
        return personalIdRepository.findById(id).orElseGet(() -> {
            boolean valid = personalIdService.isValid(id);
            return personalIdRepository.save(new PersonalId(id, valid));
        });
    }
}
