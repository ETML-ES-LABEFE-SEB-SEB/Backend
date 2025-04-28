package ch.etmles.payroll.Lot;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Lot {

    private @Id
    @GeneratedValue Long id;
    private String name;
    private String description;
    private BigDecimal price;

    public Lot(){}

    public Lot(String name, String description, BigDecimal price) {
        this.setName(name);
        this.setDescription(description);
        this.setPrice(price);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
                && Objects.equals(this.price, lot.price);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.name, this.description, this.price);
    }

    @Override
    public String toString(){
        return "Lot{" + "id=" + this.getId()
                + ",name='" + this.getName() + '\'' +
                ",description='" + this.getDescription() + '\'' +
                ",price=" + this.getPrice() + "CHF " + '}';
    }
}
