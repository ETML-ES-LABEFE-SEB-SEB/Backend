package ch.etmles.payroll.LotCategory;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Category {
    private @Id
    @GeneratedValue
    UUID id;

    @Column(unique=true)
    private String name;

    @OneToOne
    @JoinColumn(name="lot_Id")
    private Category parent;

    public Category() { }

    public Category(String name, Category parent) {
        this.setName(name);
        this.setParent(parent);
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof Category lot))
            return false;
        return Objects.equals(this.id, lot.id)
                && Objects.equals(this.name, lot.name)
                && Objects.equals(this.parent, lot.parent);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.name, this.parent);
    }

    @Override
    public String toString(){
        return "Category{" + "id=" + this.getId()
                + ",name='" + this.getName() + '\'' +
                ",parent='" + this.getParent() + '\'' + '}';
    }
}
