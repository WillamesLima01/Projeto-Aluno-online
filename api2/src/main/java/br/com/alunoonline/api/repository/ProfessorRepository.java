package br.com.alunoonline.api.repository;

import br.com.alunoonline.api.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    @Modifying
    @Query("UPDATE Professor a SET a.email = :email, a.nome = :nome WHERE a.id = :id")
    void update(@Param("id") Long id, @Param("email") String email, @Param("nome") String nome);

}
