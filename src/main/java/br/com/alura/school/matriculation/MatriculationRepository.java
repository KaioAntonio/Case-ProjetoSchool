package br.com.alura.school.matriculation;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


interface MatriculationRepository extends JpaRepository<Matriculation, Long> {
    Optional<Matriculation> findAllByCourseCode(String code);

}
