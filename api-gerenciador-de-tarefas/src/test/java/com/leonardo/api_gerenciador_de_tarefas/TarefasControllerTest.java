package com.leonardo.api_gerenciador_de_tarefas;

import com.leonardo.api_gerenciador_de_tarefas.controller.TarefasController;
import com.leonardo.api_gerenciador_de_tarefas.entity.Tarefa;
import com.leonardo.api_gerenciador_de_tarefas.entity.TarefaStatus;
import com.leonardo.api_gerenciador_de_tarefas.repository.TarefaStatusRepository;
import com.leonardo.api_gerenciador_de_tarefas.repository.TarefasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TarefasControllerTest {

    @InjectMocks
    private TarefasController tarefasController;

    @Mock
    private TarefasRepository tarefasRepository;

    @Mock
    private TarefaStatusRepository tarefaStatusRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Testes para BuscarTodasTarefas
    @Test
    public void BuscarTodasTarefas_QuandoExistemTarefas_RetornaStatusHttp200() {
        when(tarefasRepository.findAll()).thenReturn(Arrays.asList(new Tarefa()));

        ResponseEntity<?> response = tarefasController.BuscarTodasTarefas();

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void BuscarTodasTarefas_QuandoNaoExistemTarefas_RetornaStatusHttp204() {
        when(tarefasRepository.findAll()).thenReturn(Arrays.asList());

        ResponseEntity<?> response = tarefasController.BuscarTodasTarefas();

        assertEquals(204, response.getStatusCodeValue());
    }

    // Testes para BuscarTarefaPorId
    @Test
    public void BuscarTarefaPorId_QuandoATarefaExiste_RetornaStatusHttp200ETarefaBuscada() {
        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(1);
        when(tarefasRepository.findById(1)).thenReturn(Optional.of(tarefa));

        ResponseEntity<?> response = tarefasController.BuscarTarefaPorId(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Optional.of(tarefa), response.getBody());
    }

    @Test
    public void BuscarTarefaPorId_QuandoATarefaNaoExiste_RetornaStatusHttp404() {
        when(tarefasRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = tarefasController.BuscarTarefaPorId(1);

        assertEquals(404, response.getStatusCodeValue());
    }

    // Testes para CriarTarefa
    @Test
    public void CriarTarefa_QuandoDadosInformadosSaoValidos_RetornaStatusHttp201() {
        Tarefa novaTarefa = new Tarefa();
        novaTarefa.setStatus(new TarefaStatus());
        when(tarefasRepository.save(any())).thenReturn(novaTarefa);
        when(tarefaStatusRepository.findById(any())).thenReturn(Optional.of(new TarefaStatus()));

        ResponseEntity<?> response = tarefasController.CriarTarefa(novaTarefa);

        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void CriarTarefa_QuandoStatusInformadoDaTarefaNaoExiste_RetornaStatusHttp400() {
        Tarefa novaTarefa = new Tarefa();
        novaTarefa.setStatus(new TarefaStatus());
        novaTarefa.getStatus().setIdStatus(999); // ID que não existe
        when(tarefaStatusRepository.findById(any())).thenReturn(Optional.empty());

        ResponseEntity<?> response = tarefasController.CriarTarefa(novaTarefa);

        assertEquals(400, response.getStatusCodeValue());
    }

    // Testes para EditarTarefa
    @Test
    public void EditarTarefa_QuandoDadosSaoValidos_RetornaStatusHttp200EAEdicaoAcontece() {
        Tarefa tarefaExistente = new Tarefa();
        tarefaExistente.setIdTarefa(1);
        tarefaExistente.setTitulo("Título Antigo");
        when(tarefasRepository.findById(1)).thenReturn(Optional.of(tarefaExistente));
        when(tarefasRepository.save(any())).thenReturn(tarefaExistente);

        Tarefa tarefaEditada = new Tarefa();
        tarefaEditada.setTitulo("Título Novo");

        ResponseEntity<?> response = tarefasController.EditarTarefa(1, tarefaEditada);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Título Novo", tarefaExistente.getTitulo());
    }

    @Test
    public void EditarTarefa_QuandoTarefaNaoExiste_RetornaStatusHttp404() {
        Tarefa tarefaEditada = new Tarefa();
        tarefaEditada.setTitulo("Título Novo");
        when(tarefasRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = tarefasController.EditarTarefa(1, tarefaEditada);

        assertEquals(404, response.getStatusCodeValue());
    }

    // Testes para ExcluirTarefa
    @Test
    public void ExcluirTarefa_QuandoTarefaExiste_MetodoDeleteByIdEhChamadoERetornaStatusHttp200() {
        when(tarefasRepository.existsById(1)).thenReturn(true);

        ResponseEntity<?> response = tarefasController.ExcluirTarefa(1);

        assertEquals(200, response.getStatusCodeValue());
        verify(tarefasRepository, times(1)).deleteById(1);
    }

    @Test
    public void ExcluirTarefa_QuandoTarefaNaoExiste_RetornaStatusHttp404() {
        when(tarefasRepository.existsById(1)).thenReturn(false);

        ResponseEntity<?> response = tarefasController.ExcluirTarefa(1);

        assertEquals(404, response.getStatusCodeValue());
    }
}
