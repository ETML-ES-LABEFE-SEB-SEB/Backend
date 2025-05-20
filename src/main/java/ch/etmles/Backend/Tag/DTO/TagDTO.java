package ch.etmles.Backend.Tag.DTO;

import ch.etmles.Backend.Tag.Tag;

public class TagDTO {

    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public TagDTO toDto(Tag tag)
    {
        TagDTO dto = new TagDTO();
        dto.setLabel(tag.getLabel());
        return dto;
    }
}
