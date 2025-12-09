package br.com.esig.task.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NovaTarefaDTO {
    private String titulo;
    private String descricao;
    private Long responsavelId;
    private String prioridade; // BAIXA, MEDIA, ALTA
    private String deadline;
}
