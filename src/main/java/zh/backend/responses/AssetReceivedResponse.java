package zh.backend.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetReceivedResponse {
    private String id;
    private String assetCode;
    private String assetName;
    private BigDecimal quantity;
    private BigDecimal totalCost;
    private String locationId;
    private String locationCode;
    private String batchId;
    private String batchNumber;
    private LocalDateTime date;
}
