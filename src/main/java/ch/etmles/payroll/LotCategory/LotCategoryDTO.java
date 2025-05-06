package ch.etmles.payroll.LotCategory;

public class LotCategoryDTO {
    private String name;
    private String parent;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LotCategoryDTO toDTO(LotCategory lotCategory) {
        LotCategoryDTO dto = new LotCategoryDTO();
        dto.setName(lotCategory.getName());
        dto.setParent(lotCategory.getParent() == null ? "" : lotCategory.getParent().getName());
        return dto;
    }
}


