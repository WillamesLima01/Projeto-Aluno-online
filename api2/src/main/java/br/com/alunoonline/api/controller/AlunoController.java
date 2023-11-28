package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

   @Autowired
   AlunoService service;

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public ResponseEntity<Aluno> create(@RequestBody Aluno aluno){
      Aluno alunoCreated = service.create(aluno);

      return ResponseEntity.status(201).body(alunoCreated);
   }

   @GetMapping("/all")
   @ResponseStatus(HttpStatus.OK)
   public List<Aluno> findall(){

      return service.findAll();
   }

   @GetMapping("/{id}")
   @ResponseStatus(HttpStatus.OK)
   public Optional<Aluno> finById(@PathVariable Long id){

      return service.findById(id);
   }

   @DeleteMapping("/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void Delete(@PathVariable Long id){

      service.delete(id);
   }

   @PutMapping("/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void atualizar(@PathVariable Long id, @RequestBody Aluno alunoAtualizado) throws ChangeSetPersister.NotFoundException {
      service.atualizar(id, alunoAtualizado);
   }

}
