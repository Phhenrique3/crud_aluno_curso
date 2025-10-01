package com.example.crud_aluno_curso.repository;

import com.example.crud_aluno_curso.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {

}