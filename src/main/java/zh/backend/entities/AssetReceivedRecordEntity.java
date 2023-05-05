package zh.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "assetReceivedRecord")
@AllArgsConstructor
@NoArgsConstructor
public class AssetReceivedRecordEntity extends BaseEntity {
    private String assetCode;
    private String assetName;
    private BigDecimal quantity;
    private BigDecimal costPerAsset;
    private BigDecimal totalCost;
    private String locationId;
    private String locationCode;
    private String batchId;
    private String batchNumber;
    private LocalDateTime date;
}
