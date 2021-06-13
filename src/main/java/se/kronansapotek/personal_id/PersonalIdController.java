package se.kronansapotek.personal_id;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/personal-ids/")
public class PersonalIdController {

    private final PersonalIdService personalIdService;
    private final PersonalIdRepository personalIdRepository;

    @GetMapping(value = "/{id}/valid")
    public PersonalId validate(@PathVariable("id") String id) {
        return personalIdRepository.findById(id).orElseGet(() -> {
            boolean valid = personalIdService.isValid(id);
            return personalIdRepository.save(new PersonalId(id, valid));
        });
    }
}
