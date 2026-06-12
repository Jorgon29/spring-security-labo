package com.server.app.entities.enums;

public enum Moneda {
    USD("Dólar estadounidense"),
    EUR("Euro"),
    JPY("Yen japonés"),
    GBP("Libra esterlina"),
    AUD("Dólar australiano"),
    CAD("Dólar canadiense"),
    CHF("Franco suizo"),
    CNY("Yuan chino"),
    HKD("Dólar de Hong Kong"),
    NZD("Dólar de Nueva Zelanda"),
    SEK("Corona sueca"),
    KRW("Won surcoreano"),
    SGD("Dólar de Singapur"),
    NOK("Corona noruega"),
    MXN("Peso mexicano"),
    INR("Rupia india"),
    BRL("Real brasileño"),
    ZAR("Rand sudafricano"),
    RUB("Rublo ruso"),
    TRY("Lira turca");

    private final String descripcion;

    Moneda(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
