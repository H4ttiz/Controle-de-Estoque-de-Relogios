package dev.java10x.desafio.enums;

public enum MaterialCaixa {
    STEEL, TITANIUM, RESIN, BRONZE, CERAMIC;

    public static MaterialCaixa fromApi(String valor){
        if(valor == null || valor.isBlank()) return null;
        return switch (valor){
            case "steel" -> STEEL;
            case "titanium" -> TITANIUM;
            case "resin" -> RESIN;
            case "bronze" -> BRONZE;
            case "ceramic" -> CERAMIC;
            default -> throw new IllegalArgumentException("Material Inválido: " + valor);
        };
    }

    public String toApi(){
        return switch (this){
            case STEEL -> "steel";
            case TITANIUM -> "titanium";
            case RESIN -> "resin";
            case BRONZE -> "bronze";
            case CERAMIC -> "ceramic";
        };
    }
}
