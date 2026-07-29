package dev.java10x.desafio.dto.response;

import java.util.List;

public record PaginaRelogioDto(
        List<RelogioDto> itens,
        long total
) {
}
