package ch.etmles.Backend.Lot.DTO;

public class SortOptionDTO {
    private String id;
    private String label;

    public SortOptionDTO(String id, String label) {
        this.id = id;
        this.label = label;
    }

    // Getters (et setters si besoin)
    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
