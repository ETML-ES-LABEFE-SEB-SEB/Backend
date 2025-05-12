package ch.etmles.payroll.LotCategory;

public class CategoryDTO {
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

    public CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setName(category.getName());
        dto.setParent(category.getParent() == null ? "" : category.getParent().getName());
        return dto;
    }
}


