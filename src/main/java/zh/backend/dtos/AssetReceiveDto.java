package zh.backend.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssetReceiveDto {
    private String orderNumber;
    private String assetCode;
    private BigDecimal quantity;
    private String locationCode;
    private String expiry;

}
