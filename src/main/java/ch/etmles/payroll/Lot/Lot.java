package ch.etmles.payroll.Lot;

import ch.etmles.payroll.LotCategory.LotCategory;
import ch.etmles.payroll.Tag.Tag;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Lot {

    private @Id
    @GeneratedValue UUID id;
    private String name;
    private String description;
    private String pictureUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal startPrice;
    private BigDecimal currentPrice;

    @ManyToOne
    @JoinColumn(name="lotCategoryId")
    private LotCategory category;

    private LotStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "lot_tag",
        joinColumns = @JoinColumn(name = "lot_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    public Lot(){}

    public Lot(String name, String description, String pictureUrl, BigDecimal startPrice, String startDate, String endDate, LotCategory category, LotStatus status, List<Tag> tags) {
        this.setName(name);
        this.setDescription(description);
        this.setPictureUrl(pictureUrl);
        this.setStartPrice(startPrice);
        this.setCurrentPrice(startPrice);
        this.setStartDate(LocalDateTime.parse(startDate));
        this.setEndDate(LocalDateTime.parse(endDate));
        this.setCategory(category);
        this.setStatus(status);
        this.setTags(tags);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public LotCategory getCategory() {
        return category;
    }

    public void setCategory(LotCategory category) {
        this.category = category;
    }

    public LotStatus getStatus() {
        return status;
    }

    public void setStatus(LotStatus status) {
        this.status = status;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof Lot lot))
            return false;
        return Objects.equals(this.id, lot.id)
                && Objects.equals(this.name, lot.name)
                && Objects.equals(this.description, lot.description)
                && Objects.equals(this.currentPrice, lot.currentPrice)
                && Objects.equals(this.startDate, lot.startDate)
                && Objects.equals(this.endDate, lot.endDate)
                && Objects.equals(this.startPrice, lot.startPrice)
                && Objects.equals(this.tags, lot.tags);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.name, this.description, this.currentPrice);
    }

    @Override
    public String toString(){
        return "Lot{" + "id=" + this.getId()
                + ",name='" + this.getName() + '\'' +
                ",description='" + this.getDescription() + '\'' +
                ",price=" + this.getCurrentPrice() + "CHF " + '}';
    }
}
