package br.com.esig.task.controller;

import br.com.esig.task.domain.Pessoa;
import br.com.esig.task.domain.Tarefa;
import br.com.esig.task.dto.NovaTarefaDTO;
import br.com.esig.task.repository.PessoaRepository;
import br.com.esig.task.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Tarefas", description = "Operações para gerenciar tarefas e responsáveis")
public class TarefaController {

    @Autowired
    private final TarefaService tarefaService;
    @Autowired
    private final PessoaRepository pessoaRepository;

    public TarefaController(TarefaService tarefaService, PessoaRepository pessoaRepository) {
        this.tarefaService = tarefaService;
        this.pessoaRepository = pessoaRepository;
    }
    @Operation(summary = "Criar tarefas")
    @PostMapping("/tarefas")
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody NovaTarefaDTO dto) {
        Tarefa tarefaCriada = tarefaService.criarTarefa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaCriada);
    }

    @Operation(summary = "Listar tarefas")
    @GetMapping("/tarefas")
    public ResponseEntity<List<Tarefa>> listarTarefas() {
        List<Tarefa> tarefas = tarefaService.listarTarefas();
        return ResponseEntity.ok(tarefas);
    }

    @Operation(summary = "Atualizar tarefas")
    @PutMapping("/tarefas/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(
            @Parameter(description = "ID da tarefa a ser atualizada") @PathVariable Long id,
            @RequestBody NovaTarefaDTO dto) {
        Tarefa tarefaAtualizada = tarefaService.atualizarTarefa(id, dto);
        return ResponseEntity.ok(tarefaAtualizada);
    }

    @Operation(summary = "Excluir tarefas")
    @DeleteMapping("/tarefas/{id}")
    public ResponseEntity<Void> excluirTarefa(
            @Parameter(description = "ID da tarefa a ser excluída") @PathVariable Long id) {
        tarefaService.excluirTarefa(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar pessoas")
    @GetMapping("/pessoas")
    public ResponseEntity<List<Pessoa>> listarPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return ResponseEntity.ok(pessoas);
    }
}
