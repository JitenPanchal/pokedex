package com.truelayer.pokemon.services.models.pokemon;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class PokemonInformation {

    private String name;
    private Habitat habitat;
    private boolean isLegendary;
    public List<FlavorTextEntry> flavorTextEntries;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    public boolean isLegendary() {
        return isLegendary;
    }

    @JsonSetter("is_legendary")
    public void setLegendary(boolean legendary) {
        isLegendary = legendary;
    }

    public List<FlavorTextEntry> getFlavorTextEntries() {
        return flavorTextEntries;
    }

    @JsonSetter("flavor_text_entries")
    public void setFlavorTextEntries(List<FlavorTextEntry> flavorTextEntries) {
        this.flavorTextEntries = flavorTextEntries;
    }


    public static class FlavorTextEntry {
        private String flavorText;
        private Language language;
        private Version version;

        public String getFlavorText() {
            return flavorText;
        }

        @JsonSetter("flavor_text")
        public void setFlavorText(String flavorText) {
            this.flavorText = flavorText;
        }

        public Language getLanguage() {
            return language;
        }

        public void setLanguage(Language language) {
            this.language = language;
        }

        public Version getVersion() {
            return version;
        }

        public void setVersion(Version version) {
            this.version = version;
        }
    }

    public static class Habitat {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Language {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Version {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}