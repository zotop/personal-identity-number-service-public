package se.kronansapotek.personalidentitynumberservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class PersonalIdentityNumberController {

    @Autowired
    public DataRepository repository;

    @Transactional
    @GetMapping(value = "/val/{in}")
    public Object validate(@PathVariable("in") String in) {
        boolean validated = new Random().nextInt() % 2 == 0;
        repository.persist(in, validated);
        return validated;
    }
}
