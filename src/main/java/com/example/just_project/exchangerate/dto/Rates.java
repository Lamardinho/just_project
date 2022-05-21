package com.example.just_project.exchangerate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Rates {

    @JsonProperty("RUB")
    private Double rub;

    @JsonProperty("USD")
    private Double usd;

    @JsonProperty("EUR")
    private Double eur;


    @JsonProperty("AUD")
    private Double aud;

    @JsonProperty("AZN")
    private Double azn;

    @JsonProperty("GBP")
    private Double gbp;

    @JsonProperty("AMD")
    private Double amd;

    @JsonProperty("BYN")
    private Double byn;

    @JsonProperty("BGN")
    private Double bgn;

    @JsonProperty("BRL")
    private Double brl;

    @JsonProperty("HUF")
    private Double huf;

    @JsonProperty("HKD")
    private Double hkd;

    @JsonProperty("DKK")
    private Double dkk;

    @JsonProperty("INR")
    private Double inr;

    @JsonProperty("KZT")
    private Double kzt;

    @JsonProperty("CAD")
    private Double cad;

    @JsonProperty("KGS")
    private Double kgs;

    @JsonProperty("CNY")
    private Double cny;

    @JsonProperty("MDL")
    private Double mdl;

    @JsonProperty("NOK")
    private Double nok;

    @JsonProperty("PLN")
    private Double pln;

    @JsonProperty("RON")
    private Double ron;

    @JsonProperty("XDR")
    private Double xdr;

    @JsonProperty("SGD")
    private Double sgd;

    @JsonProperty("TJS")
    private Double tjs;

    @JsonProperty("TRY")
    private Double tryValue;

    @JsonProperty("TMT")
    private Double tmt;

    @JsonProperty("UZS")
    private Double uzs;

    @JsonProperty("UAH")
    private Double uah;

    @JsonProperty("CZK")
    private Double czk;

    @JsonProperty("SEK")
    private Double sek;

    @JsonProperty("CHF")
    private Double chf;

    @JsonProperty("ZAR")
    private Double zar;

    @JsonProperty("KRW")
    private Double krw;

    @JsonProperty("JPY")
    private Double jpy;
}
