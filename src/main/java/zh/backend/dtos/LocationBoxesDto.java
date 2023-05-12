package zh.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class LocationBoxesDto {

    private String boxId;
    private String assetCode;
    private String batchId;
    private BigDecimal quantity;
    private BigDecimal volume;

}
