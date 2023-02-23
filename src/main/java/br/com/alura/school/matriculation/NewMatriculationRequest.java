package br.com.alura.school.matriculation;

import br.com.alura.school.support.validation.Unique;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class NewMatriculationRequest {

//    @Unique(entity = MatriculationController.class, field = "code")
    @Size(max=10)
    @JsonProperty
    private final String code;

//    @Unique(entity = MatriculationController.class, field = "username")
    @Size(max=20)
    @NotBlank
    @JsonProperty
    private final String username;

//    @Unique(entity = MatriculationController.class, field = "date")
    @JsonProperty
    private final LocalDateTime date;

    public NewMatriculationRequest(String code, String username, LocalDateTime date) {
        this.code = code;
        this.username = username;
        this.date = date;

    }

    public String getCode() {
        return code;
    }

    public String getUsername() {
        return username;
    }

    Matriculation toEntity(){
        return new Matriculation(code, username, date);
    }
}
