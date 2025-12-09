package br.com.esig.task.service;

import br.com.esig.task.domain.Pessoa;
import br.com.esig.task.domain.Prioridade;
import br.com.esig.task.domain.Situacao;
import br.com.esig.task.domain.Tarefa;
import br.com.esig.task.dto.NovaTarefaDTO;
import br.com.esig.task.repository.PessoaRepository;
import br.com.esig.task.repository.TarefaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final PessoaRepository pessoaRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public TarefaService(TarefaRepository tarefaRepository, PessoaRepository pessoaRepository) {
        this.tarefaRepository = tarefaRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public Tarefa criarTarefa(NovaTarefaDTO dto) {
        Tarefa tarefa = new Tarefa();

        tarefa.setTitulo(dto.getTitulo());
        tarefa.setDescricao(dto.getDescricao());

        Pessoa responsavel = pessoaRepository
                .findById(dto.getResponsavelId())
                .orElseThrow(() -> new IllegalArgumentException("Responsável não encontrado"));
        tarefa.setResponsavel(responsavel);

        Prioridade prioridade = Prioridade.valueOf(dto.getPrioridade());
        tarefa.setPrioridade(prioridade);

        tarefa.setSituacao(Situacao.EM_ANDAMENTO);

        if (!dto.getDeadline().isEmpty()) {
            LocalDate deadline = LocalDate.parse(dto.getDeadline(), formatter);
            tarefa.setDeadline(deadline);
        }
        return tarefaRepository.save(tarefa);
    }

    @Transactional(readOnly = true)
    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    @Transactional
    public void excluirTarefa(Long id) {
        if (!tarefaRepository.existsById(id)) {
            throw new IllegalArgumentException("Tarefa não encontrada");
        }
        tarefaRepository.deleteById(id);
    }

    @Transactional
    public Tarefa atualizarTarefa(Long id, NovaTarefaDTO dto) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));

        tarefa.setTitulo(dto.getTitulo());
        tarefa.setDescricao(dto.getDescricao());

        Pessoa responsavel = pessoaRepository
                .findById(dto.getResponsavelId())
                .orElseThrow(() -> new IllegalArgumentException("Responsável não encontrado"));
        tarefa.setResponsavel(responsavel);

        Prioridade prioridade = Prioridade.valueOf(dto.getPrioridade());
        tarefa.setPrioridade(prioridade);

        if (dto.getDeadline() != null && !dto.getDeadline().isEmpty()) {
            LocalDate deadline = LocalDate.parse(dto.getDeadline(), formatter);
            tarefa.setDeadline(deadline);
        } else {
            tarefa.setDeadline(null);
        }

        return tarefaRepository.save(tarefa);
    }
}
