package com.example.forumhub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    
    private final TopicoService topicoService;
    
    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    @GetMapping
    public ResponseEntity<Object> listarTopicos() {
        return ResponseEntity.ok(topicoService.listarTodos());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Topico> criarTopico(@RequestBody TopicoDTO topicoDTO) {
        Topico novoTopico = topicoService.criar(topicoDTO);
        return ResponseEntity.ok(novoTopico);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarTopico(@PathVariable Long id) {
        return ResponseEntity.ok(topicoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Topico> atualizarTopico(@PathVariable Long id, @RequestBody TopicoDTO topicoDTO) {
        Topico atualizado = topicoService.atualizar(id, topicoDTO);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarTopico(@PathVariable Long id) {
        topicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    public TopicoService getTopicoService() {
        return topicoService;
    }
}
