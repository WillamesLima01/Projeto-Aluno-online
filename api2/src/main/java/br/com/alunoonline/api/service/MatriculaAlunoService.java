package br.com.alunoonline.api.service;

import br.com.alunoonline.api.dtos.PatchNotasRequest;
import br.com.alunoonline.api.enums.StatusMatricula;
import br.com.alunoonline.api.exception.*;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatriculaAlunoService {

    @Autowired
    MatriculaAlunoRepository repository;

    //Média para aprovação em constante.
    private static final Double MEDIA_PARA_APROVACAO = 7.0;

    public void create(MatriculaAluno matriculaAluno) {
        if (isAlunoJaMatriculado(matriculaAluno.getAluno().getId(), matriculaAluno.getDisciplina().getId())) {
            throw new MatriculaException("Aluno matriculado nessa disciplina.");
        } else {
            matriculaAluno.setStatus(String.valueOf(StatusMatricula.MATRICULADO));
            repository.save(matriculaAluno);
        }
    }

    public void patchNotas(Long id, PatchNotasRequest patchNotasRequest) {
        Optional<MatriculaAluno> matriculaAluno = repository.findById(id);

        //fazer a validação para ver se a matricula existe
        if (matriculaAluno.isPresent()) {
            MatriculaAluno matriculaUpdated = matriculaAluno.get();

            if(validarNotas(patchNotasRequest.getNota1(), patchNotasRequest.getNota2()) == "notas"){
                matriculaUpdated.setNota1(patchNotasRequest.getNota1());
                matriculaUpdated.setNota2(patchNotasRequest.getNota2());
                Double media = media(patchNotasRequest.getNota1(), patchNotasRequest.getNota2());
                matriculaUpdated.setStatus(calcularStatus(media));
            } else if(validarNotas(patchNotasRequest.getNota1(), patchNotasRequest.getNota2()) != "nota1"){
                if(matriculaUpdated.getNota2() != null) {
                    matriculaUpdated.setNota1(patchNotasRequest.getNota1());
                    Double media = media(matriculaUpdated.getNota1(), matriculaUpdated.getNota2());
                    matriculaUpdated.setStatus(calcularStatus(media));
                } else {
                    matriculaUpdated.setNota1(patchNotasRequest.getNota1());
                }
            } else{
                if(matriculaUpdated.getNota1() != null) {
                    matriculaUpdated.setNota2(patchNotasRequest.getNota2());
                    Double media = media(matriculaUpdated.getNota1(), matriculaUpdated.getNota2());
                    matriculaUpdated.setStatus(calcularStatus(media));
                } else {
                    matriculaUpdated.setNota2(patchNotasRequest.getNota2());
                }
            }

            repository.save(matriculaUpdated);

        } else {
            throw new MatriculaNotFoundException("Matrícula não encontrada no banco de dados");
        }
    }

    //quero que verifique o valor do status em método por fora.
    private String calcularStatus(Double media){

        if(media < MEDIA_PARA_APROVACAO){
            return String.valueOf(StatusMatricula.REPROVADO);
        } else {
            return String.valueOf(StatusMatricula.APROVADO);
        }
    }

    public void patchStatusParaTrancado(Long id){
       Optional<MatriculaAluno> matriculaAluno = repository.findById(id);

        if (matriculaAluno.isPresent()) {

            MatriculaAluno matriculaAlunoToLuck = matriculaAluno.get();

            if(validarMatriculaDuplicada(matriculaAlunoToLuck.getStatus())){
                throw new MatriculaDuplicadaException("Matrícula consta como trancada no sistema");
            }

            matriculaAlunoToLuck.setStatus(validarNotasTrancar(matriculaAlunoToLuck.getNota1(), matriculaAlunoToLuck.getNota2()));

            if (matriculaAlunoToLuck.getStatus().equals("TRANCADA")) {
                repository.save(matriculaAlunoToLuck);
            }

        } else {
            throw new MatriculaNotFoundException("Matrícula não encontrada no banco de dados.");
        }
    }

    //Validar matrícula
    private boolean isAlunoJaMatriculado(Long alunoId, Long disciplinaId) {
        List<MatriculaAluno> matriculas = repository.findByAlunoIdAndDisciplinaId(alunoId, disciplinaId);
        return !matriculas.isEmpty();
    }

    private String validarNotas(Double nota1, Double nota2) {
        if (nota1 == null && nota2 == null) {
            throw new ValidarNota1Exception("Notas nulas.");
        }

        if (nota1 != null && (nota1 < 0 || nota1 > 10)) {
            throw new ValidarNota1Exception("Nota1 inválida. Certifique-se de que a nota1 está dentro do intervalo de 0 a 10.");
        }

        if (nota2 != null && (nota2 < 0 || nota2 > 10)) {
            throw new ValidarNota1Exception("Nota2 inválida. Certifique-se de que a nota2 está dentro do intervalo de 0 a 10.");
        }

        if (nota1 == null) {
            return "nota1";
        }

        if (nota2 == null) {
            return "nota2";
        }

        return "notas";
    }


    //quero a regra de calcular a média fora daqui
    private Double media(Double nota1, Double nota2){

        return (nota1 + nota2)/2;
    }

    private String validarNotasTrancar(Double nota1, Double nota2){

        if (nota1 == null && nota2 == null) {
            return String.valueOf(StatusMatricula.TRANCADA);
        } else {
            throw new TrancarMatriculaException("Não é permitido trancar matricula quando se tem uma das notas no sistema!");
        }
    }

    private Boolean validarMatriculaDuplicada(String status){

        if(status.equals("TRANCADA")){
            return true;
        }else{
            return false;
        }
    }

    //Utilizando o toString
  // private String calcularStatus(Double media) {
    //    return (media < MEDIA_PARA_APROVACAO) ? StatusMatricula.REPROVADO.toString() : StatusMatricula.APROVADO.toString();


    //Utilizando Enum como String
   //private String calcularStatus(Double media) {
   //    return (media < MEDIA_PARA_APROVACAO) ? StatusMatricula.REPROVADO.name() : StatusMatricula.APROVADO.name();
  //}

    //Utilizando ternário sem toString
   // private String calcularStatus(Double media) {
    //    return (media < MEDIA_PARA_APROVACAO) ? "REPROVADO" : "APROVADO";
    //}


}


