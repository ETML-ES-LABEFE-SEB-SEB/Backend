package ch.etmles.Backend.Lot.DTO;

import ch.etmles.Backend.Lot.Lot;
import ch.etmles.Backend.LotCategory.Category;
import ch.etmles.Backend.Tag.Tag;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LotDTO {
    private UUID id;
    private String name;
    private String description;
    private String pictureUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal startPrice;
    private BigDecimal currentPrice;
    private Category category;
    private String status;
    private List<String> tagNames;

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<Tag> tagNames) {
        this.tagNames = new ArrayList<>();
        for (Tag tag : tagNames) {
            this.tagNames.add(tag.getLabel());
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public static LotDTO toDto(Lot lot) {
        LotDTO dto = new LotDTO();
        dto.setId(lot.getId());
        dto.setName(lot.getName());
        dto.setDescription(lot.getDescription());
        dto.setPictureUrl(lot.getPictureUrl());
        dto.setStartDate(lot.getStartDate());
        dto.setEndDate(lot.getEndDate());
        dto.setStartPrice(lot.getStartPrice());
        dto.setCurrentPrice(lot.getCurrentPrice());
        dto.setCategory(lot.getCategory() != null ? lot.getCategory() : null);
        dto.setStatus(lot.getStatus() != null ? lot.getStatus().name() : null);
        dto.setTagNames(lot.getTags());
        return dto;
    }
}
