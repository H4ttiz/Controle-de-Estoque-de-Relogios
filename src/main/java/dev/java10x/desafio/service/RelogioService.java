package dev.java10x.desafio.service;

import dev.java10x.desafio.dto.request.AtualizarRelogio;
import dev.java10x.desafio.dto.request.CriarRelogioDto;
import dev.java10x.desafio.dto.response.PaginaRelogioDto;
import dev.java10x.desafio.dto.response.RelogioDto;
import dev.java10x.desafio.entity.Relogio;
import dev.java10x.desafio.enums.MaterialCaixa;
import dev.java10x.desafio.enums.OrdenacaoRelogios;
import dev.java10x.desafio.enums.TipoMovimento;
import dev.java10x.desafio.enums.TipoVidro;
import dev.java10x.desafio.exception.NaoEncontradoException;
import dev.java10x.desafio.mapper.RelogioMapper;
import dev.java10x.desafio.repository.RelogioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static dev.java10x.desafio.repository.Specification.RelogioSpecs.*;

@Service
@RequiredArgsConstructor
public class RelogioService {

    private final RelogioRepository repository;
    private final RelogioMapper mapper;

    private void mapRelogio(
            Relogio relogio,
            String marca,
            String modelo,
            String referencia,
            String tipoMovimento,
            String tipoVidro,
            String materialCaixa,
            Integer resistenciaAguaM,
            Integer diametroMm,
            Integer lugToLugMm,
            Integer espessuraMm,
            Integer larguraLugMm,
            Long precoEmCentavos,
            String urlImagem
    ) {
        relogio.setMarca(marca);
        relogio.setModelo(modelo);
        relogio.setReferencia(referencia);
        relogio.setTipoMovimento(TipoMovimento.fromApi(tipoMovimento));
        relogio.setTipoVidro(TipoVidro.fromApi(tipoVidro));
        relogio.setMaterialCaixa(MaterialCaixa.fromApi(materialCaixa));
        relogio.setResistenciaAguaM(resistenciaAguaM);
        relogio.setDiametroMm(diametroMm);
        relogio.setLugToLugMm(lugToLugMm);
        relogio.setEspessuraMm(espessuraMm);
        relogio.setLarguraLugMm(larguraLugMm);
        relogio.setPrecoEmCentavos(precoEmCentavos);
        relogio.setUrlImagem(urlImagem);
    }

    public PaginaRelogioDto listar (
            int pagina,
            int porPagina,
            String busca,
            String marca,
            String tipoMovimento,
            String materialCaixa,
            String tipoVidro,
            Integer resistenciaMin,
            Integer resistenciaMax,
            Long precoMin,
            Long precoMax,
            Integer diametroMin,
            Integer diametroMax,
            String ordenar
    ){
        int paginaSegura = Math.max(1, pagina);
        int porPaginaSegura = Math.max(60, Math.max(1, porPagina));

        TipoMovimento movimento = TipoMovimento.fromApi(tipoMovimento);
        MaterialCaixa material = MaterialCaixa.fromApi(materialCaixa);
        TipoVidro vidro = TipoVidro.fromApi(tipoVidro);

        OrdenacaoRelogios ordenacao = OrdenacaoRelogios.fromApi(ordenar);

        Sort sort = switch (ordenacao){
            case NEWEST -> Sort.by(Sort.Direction.DESC, "criadoEm");
            case PRICE_ASC -> Sort.by(Sort.Direction.ASC, "precoEmCentavos");
            case PRICE_DESC -> Sort.by(Sort.Direction.DESC, "precoEmCentavos");
            case DIAMETER_ASC -> Sort.by(Sort.Direction.ASC, "diametroMm");
            case WR_DESC -> Sort.by(Sort.Direction.DESC, "resistenciaAguaM");
        };

        Pageable pageable = PageRequest.of(paginaSegura - 1, porPaginaSegura, sort );

        Specification<Relogio> spec = Specification.where(busca(busca))
                .and(marcaIgual(marca))
                .and(tipoMovimentoIgual(movimento))
                .and(tipoVidroIgual(vidro))
                .and(materialCaixaIgual(material))
                .and(resistenciaAguaEntre(resistenciaMin,resistenciaMax))
                .and(precoEntre(precoMin,precoMax))
                .and(diametroEntre(diametroMin,diametroMax));

        Page<Relogio> resultado = repository.findAll(spec,pageable);

        return new PaginaRelogioDto(
                resultado.getContent().stream().map(mapper::toDto).toList(),
                resultado.getTotalElements()
        );
    }

    public RelogioDto buscarPorId(UUID id){
        Relogio relogio = repository.findById(id).orElseThrow(() -> new NaoEncontradoException("Relogio não Encontrado!"));
        return mapper.toDto(relogio);
    }

    public RelogioDto criar(CriarRelogioDto request) {
        Relogio relogio = Relogio.builder()
                .id(UUID.randomUUID())
                .criadoEm(Instant.now())
                .build();

        mapRelogio(
                relogio,
                request.marca(),
                request.modelo(),
                request.referencia(),
                request.tipoMovimento(),
                request.tipoVidro(),
                request.materialCaixa(),
                request.resistenciaAguaM(),
                request.diametroMm(),
                request.lugToLugMm(),
                request.espessuraMm(),
                request.larguraLugMm(),
                request.precoEmCentavos(),
                request.urlImagem()
        );

        Relogio salvo = repository.save(relogio);

        return mapper.toDto(salvo);
    }

    public RelogioDto atualizar(UUID id, AtualizarRelogio request) {
        Relogio relogio = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Relógio não encontrado!"));

        mapRelogio(
                relogio,
                request.marca(),
                request.modelo(),
                request.referencia(),
                request.tipoMovimento(),
                request.tipoVidro(),
                request.materialCaixa(),
                request.resistenciaAguaM(),
                request.diametroMm(),
                request.lugToLugMm(),
                request.espessuraMm(),
                request.larguraLugMm(),
                request.precoEmCentavos(),
                request.urlImagem()
        );

        Relogio atualizado = repository.save(relogio);

        return mapper.toDto(atualizado);
    }

    public void remover(UUID id){
        if (!repository.existsById(id)){
            throw new NaoEncontradoException("Relógio não encontrado!");
        }
        repository.deleteById(id);
    }
}
