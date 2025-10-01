package com.example.crud_aluno_curso.controller;

import com.example.crud_aluno_curso.model.Curso;
import com.example.crud_aluno_curso.repository.CursoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curso")
public class cursoController { // classe com letra maiúscula por convenção

    private final CursoRepository cursoRepository;

    public cursoController(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @GetMapping
    public List<Curso> listar() { // retorna lista de entidades Curso
        return cursoRepository.findAll();
    }

    @PostMapping
    public Curso salvar(@RequestBody Curso curso) { // recebe e salva entidade Curso
        return cursoRepository.save(curso);
    }
}