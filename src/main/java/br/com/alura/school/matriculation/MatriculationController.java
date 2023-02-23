package br.com.alura.school.matriculation;


import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.matriculation.exceptions.ExceptionHandler;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;


@RestController
public class MatriculationController {
    private final MatriculationRepository matriculationRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    MatriculationController(MatriculationRepository matriculationRepository, CourseRepository courseRepository, UserRepository userRepository){
        this.matriculationRepository = matriculationRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/courses/{courseCode}/enroll")
    ResponseEntity<?> newMatriculation(@PathVariable("courseCode") String code,
            @RequestBody @Valid NewMatriculationRequest newMatriculationRequest) throws ExceptionHandler {

        courseRepository.findByCode(code)
                .orElseThrow(() -> new ExceptionHandler("Course Code not found!"));
        userRepository.findByUsername(newMatriculationRequest.getUsername())
                .orElseThrow(() -> new ExceptionHandler("Username not found!"));
        if(matriculationRepository.findAllByUsernameAndCourseCode(newMatriculationRequest.getUsername(), code).isEmpty()) {
            matriculationRepository.save(
                    new NewMatriculationRequest(code, newMatriculationRequest.getUsername(), LocalDateTime.now()).toEntity());
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/courses/enroll/report")
    ResponseEntity<List<MatriculationResponse>> matriculationReport(){

        List<MatriculationResponse> matriculationResponses = new ArrayList<>();
        List<Matriculation> matriculations = matriculationRepository.findAll();
        if(matriculations.toArray().length > 0) {
            for (Matriculation matriculation : matriculations) {
                User user = userRepository.findByUsername(matriculation.getUsername()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("User %s not found", matriculation.getUsername())));
                if (matriculationRepository.findAllByUsername(user.getUsername()).toArray().length > 0) {
                    List<Matriculation> matriculationList = matriculationRepository.findAllByUsername(user.getUsername());
                    MatriculationResponse matriculationResponse = new MatriculationResponse(user.getEmail(), matriculationList.toArray().length);
                    if (matriculationResponses.stream()
                            .filter(obj -> obj.getEmail() == matriculationResponse.getEmail()).findAny().isPresent() == false) {
                        matriculationResponses.add(matriculationResponse);


                    }
                }
            }
            return new ResponseEntity<>(matriculationResponses, OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
