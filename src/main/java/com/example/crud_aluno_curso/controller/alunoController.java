package com.example.crud_aluno_curso.controller;

import com.example.crud_aluno_curso.model.Aluno;
import com.example.crud_aluno_curso.repository.AlunoRepository;
import com.example.crud_aluno_curso.repository.CursoRepository;
import com.example.crud_aluno_curso.model.Curso;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aluno")
public class alunoController {  // <- PascalCase pro nome da classe


    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;

    public alunoController(AlunoRepository alunoRepository, CursoRepository cursoRepository) {
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
    }

    @GetMapping
    public List<Aluno> listar() {
        return alunoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> getById(@PathVariable Long id) {
        return alunoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Aluno> criar(@RequestBody Aluno aluno) {
        // Verifica se o curso existe antes de associar
        if (aluno.getCurso() != null) {
            Long cursoId = aluno.getCurso().getId();
            Curso curso = cursoRepository.findById(cursoId).orElse(null);
            if (curso == null) {
                return ResponseEntity.badRequest().body(null); // Curso não encontrado
            }
            aluno.setCurso(curso);
        }

        Aluno novoAluno = alunoRepository.save(aluno);
        return ResponseEntity.ok(novoAluno); // Retorna o aluno salvo
    }
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody Aluno alunoAtualizado) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    aluno.setNome(alunoAtualizado.getNome());
                    aluno.setEmail(alunoAtualizado.getEmail());
                    aluno.setDataNadcimento(alunoAtualizado.getDataNadcimento());

                    // Atualiza curso se informado
                    if (alunoAtualizado.getCurso() != null) {
                        Curso curso = cursoRepository.findById(alunoAtualizado.getCurso().getId()).orElse(null);
                        if (curso != null) {
                            aluno.setCurso(curso);
                        }
                    }

                    alunoRepository.save(aluno);
                    return ResponseEntity.ok(aluno);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    alunoRepository.delete(aluno);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}