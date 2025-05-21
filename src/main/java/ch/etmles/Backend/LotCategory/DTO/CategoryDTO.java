package ch.etmles.Backend.LotCategory.DTO;

import ch.etmles.Backend.LotCategory.Category;

import java.util.UUID;

public class CategoryDTO {

    private UUID id;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public static CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setParent(category.getParent() == null ? "" : category.getParent().getName());
        return dto;
    }
}


