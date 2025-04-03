package com.example.consumingwebservice.wsdl;

public class CountryWithToString extends Country {

    @Override
    public String toString() {
        return "Country{name='" + getName() + "', " +
                "population=" + getPopulation() + ", " +
                "capital='" + getCapital() + "', " +
                "currency=" + getCurrency() + "}";
    }
}
