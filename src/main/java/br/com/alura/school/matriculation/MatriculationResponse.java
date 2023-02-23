package br.com.alura.school.matriculation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class MatriculationResponse {

    @JsonProperty
    private String email;

    @JsonProperty
    private Integer quantity;


    public MatriculationResponse(String email, Integer quantity) {
        this.email = email;
        this.quantity = quantity;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
