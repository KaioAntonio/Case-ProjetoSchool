package br.com.alura.school.matriculation;


import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.matriculation.exceptions.ExceptionHandler;
import br.com.alura.school.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;


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
        if(!matriculationRepository.findAllByCourseCode(code).isPresent()){
            matriculationRepository.save(
                    new NewMatriculationRequest(code, newMatriculationRequest.getUsername(), LocalDateTime.now()).toEntity());
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
