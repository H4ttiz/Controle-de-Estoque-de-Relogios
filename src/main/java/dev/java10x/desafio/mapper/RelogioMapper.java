package dev.java10x.desafio.mapper;

import dev.java10x.desafio.dto.response.RelogioDto;
import dev.java10x.desafio.entity.Relogio;
import dev.java10x.desafio.enums.MaterialCaixa;
import dev.java10x.desafio.enums.TipoMovimento;
import dev.java10x.desafio.enums.TipoVidro;
import org.springframework.stereotype.Component;

@Component
public class RelogioMapper {

    public RelogioDto toDto(Relogio relogio) {
        if (relogio == null) {
            return null;
        }

        return RelogioDto.builder()
                .id(relogio.getId())
                .marca(relogio.getMarca())
                .modelo(relogio.getModelo())
                .referencia(relogio.getReferencia())
                .tipoMovimento(relogio.getTipoMovimento().toApi())
                .materialCaixa(relogio.getMaterialCaixa().toApi())
                .tipoVidro(relogio.getTipoVidro().toApi())
                .resistenciaAguaM(relogio.getResistenciaAguaM())
                .diametroMm(relogio.getDiametroMm())
                .lugToLugMm(relogio.getLugToLugMm())
                .espessuraMm(relogio.getEspessuraMm())
                .larguraLugMm(relogio.getLarguraLugMm())
                .precoEmCentavos(relogio.getPrecoEmCentavos())
                .urlImagem(relogio.getUrlImagem())
                .etiquetaResistenciaAgua(etiquetaResistenciaAgua(relogio.getResistenciaAguaM()))
                .pontuacaoColecionador(pontuacaoColecionador(relogio))
                .build();
    }

    private String etiquetaResistenciaAgua(int resistenciaM) {
        if (resistenciaM < 50) {
            return "respingos";
        }

        if (resistenciaM < 100) {
            return "uso_diario";
        }

        if (resistenciaM < 200) {
            return "natacao";
        }

        return "mergulho";
    }

    private int pontuacaoColecionador(Relogio relogio) {
        int pontos = 0;

        if (relogio.getTipoVidro() == TipoVidro.SAPPHIRE) {
            pontos += 25;
        }

        if (relogio.getResistenciaAguaM() >= 100) {
            pontos += 15;
        }

        if (relogio.getResistenciaAguaM() >= 200) {
            pontos += 10;
        }

        if (relogio.getTipoMovimento() == TipoMovimento.AUTOMATIC) {
            pontos += 20;
        }

        if (relogio.getMaterialCaixa() == MaterialCaixa.STEEL) {
            pontos += 10;
        } else if (relogio.getMaterialCaixa() == MaterialCaixa.TITANIUM) {
            pontos += 12;
        }

        if (relogio.getDiametroMm() >= 38 && relogio.getDiametroMm() <= 42) {
            pontos += 8;
        }

        return pontos;
    }
}