package br.com.alura.school.matriculation;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MatriculationRepository extends JpaRepository<Matriculation, Long> {
    Optional<Matriculation> findAllByCourseCode(String code);
    Optional<Matriculation> findByUsername(String username);

    Optional<Matriculation> findAllByUsernameAndCourseCode(String username, String courseCode);

    List<Matriculation> findAllByUsername(String username);

}
