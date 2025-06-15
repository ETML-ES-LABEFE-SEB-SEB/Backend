package ch.etmles.Backend.LotCategory.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryHierarchyDTO {

    private UUID id;
    private String name;

    private final List<CategoryHierarchyDTO> children = new ArrayList<>();

    public CategoryHierarchyDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addChild(CategoryHierarchyDTO child) {
        this.children.add(child);
    }

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

    public List<CategoryHierarchyDTO> getChildren() {
        return children;
    }
}
