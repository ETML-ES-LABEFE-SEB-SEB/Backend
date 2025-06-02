package ch.etmles.Backend.Lot.DTO;

import ch.etmles.Backend.LotCategory.DTO.CategoryDTO;

import java.util.List;

public class LotSearchDTO {

    private List<LotDTO> lots;
    private CategoryDTO category;

    public List<LotDTO> getLots() {
        return lots;
    }

    public void setLots(List<LotDTO> lots) {
        this.lots = lots;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public static LotSearchDTO toLotSearchDTO(List<LotDTO> lots, CategoryDTO category) {
        LotSearchDTO lotSearchDTO = new LotSearchDTO();
        lotSearchDTO.setLots(lots);
        lotSearchDTO.setCategory(category);
        return lotSearchDTO;
    }
}
