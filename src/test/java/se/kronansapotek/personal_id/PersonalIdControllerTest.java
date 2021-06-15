package se.kronansapotek.personal_id;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonalIdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonalIdRepository personalIdRepository;

    @ParameterizedTest
    @MethodSource("validPersonalIds")
    void should_detect_valid_personal_ids(String personalId) throws Exception {
        mockMvc.perform(get("/api/personal-ids/{id}/valid", personalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", equalTo(2)))
                .andExpect(jsonPath("$.id", equalTo(personalId)))
                .andExpect(jsonPath("$.valid", equalTo(true)));
    }

    @ParameterizedTest
    @MethodSource("invalidPersonalIds")
    void should_detect_invalid_personal_ids(String invalidPersonalId) throws Exception {
        mockMvc.perform(get("/api/personal-ids/{id}/valid", invalidPersonalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", equalTo(2)))
                .andExpect(jsonPath("$.id", equalTo(invalidPersonalId)))
                .andExpect(jsonPath("$.valid", equalTo(false)));
    }

    @ParameterizedTest
    @MethodSource("wronglyFormattedPersonalIds")
    void should_return_bad_request_for_wrongly_formatted_personal_ids(String id) throws Exception {
        mockMvc.perform(get("/api/personal-ids/{id}/valid", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("valid.id: must match \"(\\d{6}|\\d{8})(\\+|-?)(\\d{4})\""));
    }

    @Test
    void should_persist_validation_attempt() throws Exception {
        String id = "890115-5344";
        mockMvc.perform(get("/api/personal-ids/{id}/valid", id))
                .andExpect(status().isOk());

        personalIdRepository.findById(id).ifPresentOrElse(personalId -> {
            assertThat(personalId.getId(), equalTo(id));
            assertThat(personalId.getValid(), equalTo(true));
        }, () -> Assertions.fail("Did not store validation attempt for personal id: " + id));
    }

    private static Stream<Arguments> validPersonalIds() {
        return Stream.of(
                Arguments.of("811228-9874"),
                Arguments.of("8112289874"),
                Arguments.of("19811228-9874"),
                Arguments.of("198112289874"),
                Arguments.of("140613+8808"),
                Arguments.of("19140613+8808")
        );
    }

    private static Stream<Arguments> invalidPersonalIds() {
        return Stream.of(
                Arguments.of("811228-9873"),
                Arguments.of("8112289873"),
                Arguments.of("140613+8807"),
                Arguments.of("19140613+8807")
        );
    }

    private static Stream<Arguments> wronglyFormattedPersonalIds() {
        return Stream.of(
                Arguments.of(" "),
                Arguments.of("112289874"),
                Arguments.of("198112280-9874"),
                Arguments.of("19811228--9874"),
                Arguments.of("140613++8808"),
                Arguments.of("YYMMDDXXXX")
        );
    }
}
