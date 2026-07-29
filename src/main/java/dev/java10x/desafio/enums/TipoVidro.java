package dev.java10x.desafio.enums;

public enum TipoVidro {
    MINERAL, SAPPHIRE, ACRYLIC;

    public static TipoVidro fromApi(String valor){
        if(valor == null || valor.isBlank()) return null;
        return switch (valor){
            case "mineral" -> MINERAL;
            case "sapphire" -> SAPPHIRE;
            case "acrylic" -> ACRYLIC;
            default -> throw new IllegalArgumentException("Tipo do Vidro Inválido: " + valor);
        };
    }

    public String toApi(){
        return switch (this){
            case MINERAL -> "mineral";
            case SAPPHIRE -> "sapphire";
            case ACRYLIC -> "acrylic";
        };
    }
}
