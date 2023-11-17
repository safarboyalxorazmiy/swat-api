package uz.logist.master.component.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterComponentRequestSumbitDTO {
    private Long componentId;
    private Long masterId;
    private Double quantity;

    @Override
    public String toString() {
        return "MasterComponentRequestSumbitDTO{" +
            "componentId=" + componentId +
            ", masterId=" + masterId +
            ", quantity=" + quantity +
            '}';
    }
}