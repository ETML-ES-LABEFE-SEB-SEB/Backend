package ch.etmles.Backend.Lot.DTO;

import ch.etmles.Backend.Lot.Lot;

import java.util.ArrayList;
import java.util.List;

public class FollowsLotsDTO {
    private List<LotDTO> bidsOnLots = new ArrayList<>();
    private List<LotDTO> mineLots = new ArrayList<>();
    private List<LotDTO> boughtLots = new ArrayList<>();
    private List<LotDTO> soldAndUnsold = new ArrayList<>();

    public List<LotDTO> getSoldAndUnsold() {
        return soldAndUnsold;
    }

    public void setSoldAndUnsold(List<LotDTO> soldAndUnsold) {
        this.soldAndUnsold = soldAndUnsold;
    }

    public List<LotDTO> getBoughtLots() {
        return boughtLots;
    }

    public void setBoughtLots(List<LotDTO> boughtLots) {
        this.boughtLots = boughtLots;
    }

    public List<LotDTO> getMineLots() {
        return mineLots;
    }

    public void setMineLots(List<LotDTO> mineLots) {
        this.mineLots = mineLots;
    }

    public List<LotDTO> getBidsOnLots() {
        return bidsOnLots;
    }

    public void setBidsOnLots(List<LotDTO> bidsOnLots) {
        this.bidsOnLots = bidsOnLots;
    }
}
