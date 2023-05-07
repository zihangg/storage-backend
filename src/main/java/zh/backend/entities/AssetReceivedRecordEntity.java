package zh.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "assetReceivedRecord")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetReceivedRecordEntity extends BaseEntity {
    private String assetCode;
    private String assetName;
    private BigDecimal quantity;
    private BigDecimal costPerAsset;
    private BigDecimal totalCost;
    private String locationCode;
    private String batchNumber;
    private LocalDateTime date;
    private Integer totalBoxes;
}
