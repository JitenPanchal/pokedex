package com.truelayer.pokemon.services.models.translator;

public class TranslatedInformation {

    private Success success;
    private Contents contents;

    public TranslatedInformation() {
    }

    public TranslatedInformation(Success success, Contents contents) {
        this.success = success;
        this.contents = contents;
    }

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }

    public static class Success {
        private int total;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public static class Contents {
        private String translated;
        private String text;
        private String translation;

        public String getTranslated() {
            return translated;
        }

        public String getText() {
            return text;
        }

        public String getTranslation() {
            return translation;
        }

        public void setTranslated(String translated) {
            this.translated = translated;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setTranslation(String translation) {
            this.translation = translation;
        }
    }
}
