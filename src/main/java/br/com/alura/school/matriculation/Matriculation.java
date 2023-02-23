package br.com.alura.school.matriculation;

import br.com.alura.school.course.Course;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;


@Entity
public class Matriculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max=10)
    @NotBlank
    @Column(nullable = false)
    private String courseCode;

    @Size(max=20)
    @NotBlank
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDateTime date;

    public Matriculation(String courseCode, String username, LocalDateTime date) {
        this.courseCode = courseCode;
        this.username = username;
        this.date = date;
    }

    public Matriculation() {

    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


}
