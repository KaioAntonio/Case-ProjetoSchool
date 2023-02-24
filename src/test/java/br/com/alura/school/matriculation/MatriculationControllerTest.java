package br.com.alura.school.course;

import br.com.alura.school.matriculation.Matriculation;
import br.com.alura.school.matriculation.MatriculationRepository;
import br.com.alura.school.matriculation.NewMatriculationRequest;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.array;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class MatriculationControllerTest {
    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MatriculationRepository matriculationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void should_retrieve_matriculationReport() throws Exception{
        matriculationRepository.save(new Matriculation("java 1", "alex", LocalDateTime.now()));
        userRepository.save(new User("alex", "alex@alura.com.br"));

        mockMvc.perform(get("/courses/enroll/report")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email", is("alex@alura.com.br")))
                .andExpect(jsonPath("$[0].quantity", is(1)));


    }

    @Test
    void should_retrive_newMatriculation() throws Exception{
        NewMatriculationRequest newMatriculationRequest = new NewMatriculationRequest(null, "alex", null);
        userRepository.save(new User("alex", "alex@alura.com.br"));
        courseRepository.save(new Course("java-2", "Java OO", "Java and O..."));

        mockMvc.perform(post("/courses/java-2/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newMatriculationRequest)))
                .andExpect(status().isCreated());

    }

    @Test
    void should_retrive_newMatriculation_with_bad_request() throws Exception{
        matriculationRepository.save(new Matriculation("java-2", "alex", LocalDateTime.now()));
        NewMatriculationRequest newMatriculationRequest = new NewMatriculationRequest(null, "alex", null);
        userRepository.save(new User("alex", "alex@alura.com.br"));
        courseRepository.save(new Course("java-2", "Java OO", "Java and O..."));

        mockMvc.perform(post("/courses/java-2/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newMatriculationRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_retrieve_matriculationReport_with_no_content() throws Exception{
        userRepository.save(new User("alex", "alex@alura.com.br"));

        mockMvc.perform(get("/courses/enroll/report")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}