package ch.etmles.Backend.LotCategory.DTO;

import ch.etmles.Backend.LotCategory.Category;

import java.util.UUID;

public class SummaryCategoryDTO {

    private UUID id;
    private String name;
    private String parentId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public static SummaryCategoryDTO toDTO(Category category) {
        SummaryCategoryDTO dto = new SummaryCategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setParentId(category.getParent() == null ? "" : category.getParent().getId().toString());
        return dto;
    }
}
