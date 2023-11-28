package br.com.alunoonline.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchNotasRequest {

    private Double nota1;
    private Double nota2;

}

