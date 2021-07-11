package com.truelayer.pokemon.models;

public class PokemonInformationDto {

    private String name;
    private String habitat;
    private boolean isLegendary;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public boolean isLegendary() {
        return isLegendary;
    }

    public void setLegendary(boolean legendary) {
        isLegendary = legendary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}