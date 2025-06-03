package ch.etmles.Backend.Lot;

public enum SortOptions {
    PRICE_DESC("Prix décroissant"),
    PRICE_ASC("Prix croissant"),
    POSTED_AT("Date de publication"),
    FINISH_AT("Date de fin"),
    NAME_DESC("Nom Z à A"),
    NAME_ASC("Nom A à Z");

    private final String label;

    SortOptions(String s) {
        this.label = s;
    }

    public String getLabel() {
        return label;
    }
}
