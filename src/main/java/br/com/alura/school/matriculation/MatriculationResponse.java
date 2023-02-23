package br.com.alura.school.matriculation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class MatriculationResponse {

    @JsonProperty
    private final String code;

    @JsonProperty
    private final String username;

    @JsonProperty
    private final LocalDateTime date;


    public MatriculationResponse(String code, String username, LocalDateTime date) {
        this.code = code;
        this.username = username;
        this.date = date;
    }
}
