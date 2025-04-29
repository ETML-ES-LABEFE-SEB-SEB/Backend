package ch.etmles.payroll.LotCategory;

import ch.etmles.payroll.Lot.Lot;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class LotCategory {
    private @Id
    @GeneratedValue Long id;

    private String name;
    @OneToOne
    private LotCategory parent;

    public LotCategory() { }

    public LotCategory(String name, LotCategory parent) {
        this.setName(name);
        this.setParent(parent);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LotCategory getParent() {
        return parent;
    }

    public void setParent(LotCategory parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof LotCategory lot))
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
        return "Lot{" + "id=" + this.getId()
                + ",name='" + this.getName() + '\'' +
                ",parent='" + this.getParent() + '\'' + '}';
    }
}
