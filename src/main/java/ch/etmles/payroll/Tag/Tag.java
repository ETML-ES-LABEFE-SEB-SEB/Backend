package ch.etmles.payroll.Tag;

import ch.etmles.payroll.Lot.Lot;
import ch.etmles.payroll.LotCategory.LotCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Tag {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String label;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private List<Lot> lots;

    public Tag() {}

    public Tag(String label) {
        this.label = label;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof Tag tag))
            return false;
        return Objects.equals(this.id, tag.id)
                && Objects.equals(this.label, tag.label);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.label);
    }

    @Override
    public String toString(){
        return "Tag{" + "id=" + this.getId()
                + ",label='" + this.getLabel() + '\'' + '}';
    }
}
