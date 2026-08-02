package dev.java10x.desafio.enums;

public enum OrdenacaoRelogios {
    NEWEST, PRICE_ASC, PRICE_DESC, DIAMETER_ASC, WR_DESC;

    public static OrdenacaoRelogios fromApi(String valor){
        if(valor == null || valor.isBlank()) return NEWEST;
        return switch (valor){
            case "newest" -> NEWEST;
            case "price_asc" -> PRICE_ASC;
            case "price_desc" -> PRICE_DESC;
            case "diameter_asc" -> DIAMETER_ASC;
            case "wr_desc" -> WR_DESC;
            default -> throw new IllegalArgumentException("Ordenação Inválido: " + valor);
        };
    }

    public String toApi(){
        return switch (this){
            case NEWEST -> "newest";
            case PRICE_ASC -> "price_asc";
            case PRICE_DESC -> "price_desc";
            case DIAMETER_ASC -> "diameter_asc";
            case WR_DESC -> "wr_desc";
        };
    }
}
