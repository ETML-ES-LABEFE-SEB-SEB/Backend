package ch.etmles.payroll.Lot;

import ch.etmles.payroll.Bid.Bid;
import ch.etmles.payroll.LotCategory.Category;
import ch.etmles.payroll.Member.Member;
import ch.etmles.payroll.Tag.Tag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @JoinColumn(name="category_Id")
    private Category category;

    private LotStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "lot_tag",
        joinColumns = @JoinColumn(name = "lot_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @ManyToOne
    @JsonIgnore
    private Member owner;

    @OneToMany(mappedBy = "bidUpLot", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Bid> bids = new ArrayList<>();

    public Lot(){}

    public Lot(String name, String description, String pictureUrl, BigDecimal startPrice, String startDate, String endDate, Category category, LotStatus status, List<Tag> tags, Member owner) {
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
        this.setOwner(owner);
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
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
