package com.example.crud_aluno_curso.repository;

import com.example.crud_aluno_curso.model.Aluno;  // <-- aqui a entidade certa
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}