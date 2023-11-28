package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.model.Professor;
import br.com.alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository repository;

    public Professor create(Professor professor){

        return repository.save(professor);
    }

    public List<Professor> findAll(){

        return repository.findAll();
    }

    public Optional<Professor> findById(Long id){

        return repository.findById(id);
    }

    public void delete(Long id){

        repository.deleteById(id);
    }

    public void update(Long id, Professor profAtualizado) {
        Optional<Professor> alunoExistente = repository.findById(id);

        if (alunoExistente.isPresent()) {// Atualiza as informações do aluno existente com os novos dados
            Professor alunoParaAtualizar = alunoExistente.get();
            alunoParaAtualizar.setNome(profAtualizado.getNome());
            alunoParaAtualizar.setEmail(profAtualizado.getEmail());

            repository.save(alunoParaAtualizar);  // Salva o aluno atualizado no banco de dados

        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado com o ID: " + id);

        }
    }

}
