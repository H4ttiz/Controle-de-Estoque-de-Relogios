package dev.java10x.desafio.enums;

public enum TipoMovimento {
    QUARTZ, AUTOMATIC, MANUAL;

    public static TipoMovimento fromApi(String valor){
        if(valor == null || valor.isBlank()) return null;
        return switch (valor){
            case "quartz" -> QUARTZ;
            case "automatic" -> AUTOMATIC;
            case "manual" -> MANUAL;
            default -> throw new IllegalArgumentException("Tipo de Movimento Inválido: " + valor);
        };
    }

    public String toApi(){
        return switch (this){
            case QUARTZ -> "quartz";
            case AUTOMATIC -> "automatic";
            case MANUAL -> "manual";
        };
    }

}
