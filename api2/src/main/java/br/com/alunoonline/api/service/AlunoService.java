package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository repository;

    public Aluno create(Aluno aluno){

        return repository.save(aluno);
    }

    public List<Aluno> findAll(){

        return repository.findAll();
    }

    public Optional<Aluno> findById (Long id){

        return repository.findById(id);
    }

    public void delete(Long id){

        repository.deleteById(id);
    }

    public void atualizar(Long id, Aluno alunoAtualizado) {
        Optional<Aluno> alunoExistente = repository.findById(id);

        if (alunoExistente.isPresent()) {
            // Atualiza as informações do aluno existente com os novos dados
            Aluno alunoParaAtualizar = alunoExistente.get();
            alunoParaAtualizar.setNome(alunoAtualizado.getNome());
            alunoParaAtualizar.setEmail(alunoAtualizado.getEmail());
            alunoParaAtualizar.setCurso(alunoAtualizado.getCurso());

            // Salva o aluno atualizado no banco de dados
            repository.save(alunoParaAtualizar);
        } else {
            // Trate o caso em que o aluno com o ID fornecido não existe
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado no banco de dados");

        }
    }

}
