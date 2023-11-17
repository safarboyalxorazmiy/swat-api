package uz.logist.master.component.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterComponentInfoDTO {
    private Long id,
            componentId,
            masterId;
    private String componentCode, componentName, componentSpecs;
    private Double quantity;
}