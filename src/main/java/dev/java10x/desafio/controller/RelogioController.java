package dev.java10x.desafio.controller;

import dev.java10x.desafio.dto.request.AtualizarRelogio;
import dev.java10x.desafio.dto.request.CriarRelogioDto;
import dev.java10x.desafio.dto.response.PaginaRelogioDto;
import dev.java10x.desafio.dto.response.RelogioDto;
import dev.java10x.desafio.service.RelogioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/relogios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RelogioController {

    private final RelogioService service;


    @GetMapping
    public ResponseEntity<PaginaRelogioDto> listar(
            @RequestParam(defaultValue = "1") int pagina,
            @RequestParam(defaultValue = "12") int porPagina,
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String tipoMovimento,
            @RequestParam(required = false) String materialCaixa,
            @RequestParam(required = false) String tipoVidro,
            @RequestParam(required = false) Integer recistenciaMin,
            @RequestParam(required = false) Integer resistenciaMax,
            @RequestParam(required = false) Long precoMin,
            @RequestParam(required = false) Long precoMax,
            @RequestParam(required = false) Integer diametroMin,
            @RequestParam(required = false) Integer diametroMax,
            @RequestParam(required = false) String ordenar
    ){
        return ResponseEntity.ok().body(service.listar(
                pagina,
                porPagina,
                busca,
                marca,
                tipoMovimento,
                materialCaixa,
                tipoVidro,
                recistenciaMin,
                resistenciaMax,
                precoMin,
                precoMax,
                diametroMin,
                diametroMax,
                ordenar
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelogioDto> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<RelogioDto> criar(@Valid @RequestBody CriarRelogioDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RelogioDto> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody AtualizarRelogio dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
