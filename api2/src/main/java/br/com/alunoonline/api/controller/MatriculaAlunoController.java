package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.dtos.PatchNotasRequest;
import br.com.alunoonline.api.exception.*;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.service.MatriculaAlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matricula-aluno")
public class MatriculaAlunoController {

    @Autowired
    MatriculaAlunoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestBody MatriculaAluno matriculaAluno){
        try {
            service.create(matriculaAluno);
            return ResponseEntity.status(HttpStatus.CREATED).body("Matrícula realizada com sucesso.");
        }catch (MatriculaException e){
            return ResponseEntity.status(HttpStatus.CREATED).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

    @PatchMapping("/notas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> patchNotas(@PathVariable Long id, @RequestBody PatchNotasRequest patchNotasRequest){

        //service.patchNotas(id, patchNotasRequest);

        try {
            service.patchNotas(id, patchNotasRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Nota(s) adicionada(as) com sucesso.");
        } catch (ValidarNota1Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }

    }

    @PatchMapping("/patchStatusParaTrancado/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> patchStatusParaTrancado(@PathVariable Long id) {

        try {
            service.patchStatusParaTrancado(id);
            return ResponseEntity.status(HttpStatus.OK).body("Matrícula trancada com sucesso.");
        } catch (MatriculaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (MatriculaDuplicadaException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

}
