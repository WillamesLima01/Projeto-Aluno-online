package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Disciplina;
import br.com.alunoonline.api.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaRepository repository;

    public Disciplina create(Disciplina disciplina){

        return repository.save(disciplina);
    }

    public List<Disciplina> findAll(){

        return repository.findAll();
    }

    public Optional<Disciplina> findById(Long id){

        return repository.findById(id);
    }

    public void delete (Long id){

        repository.deleteById(id);
    }

    public void update(Long id, Disciplina discAtualizada){

        Optional<Disciplina> discExistente = repository.findById(id);

        if(discExistente.isPresent()){
            Disciplina discAtual = discExistente.get();
            discAtual.setNome(discAtualizada.getNome());
            discAtual.setProfessor(discAtualizada.getProfessor());

            repository.save(discAtual);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado com o id: " + id);
        }
    }

    public List<Disciplina> findByProfessorId(Long professorId){

        return repository.findByProfessorId(professorId);
    }

}
