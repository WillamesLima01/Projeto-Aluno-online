package br.com.alunoonline.api.repository;

import br.com.alunoonline.api.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Modifying
    @Query("UPDATE Aluno a SET a.nome = :nome, a.email = :email, a.curso = :curso WHERE a.id = :id")
    void update(@Param("id") Long id, @Param("nome") String nome, @Param("email") String email, @Param("curso") String curso);
}

