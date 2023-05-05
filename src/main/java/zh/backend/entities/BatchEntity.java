package zh.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "batches")
@AllArgsConstructor
@NoArgsConstructor
public class BatchEntity extends BaseEntity {
    private String batchNumber;
    private String assetCode;
    private BigDecimal initialQuantity;
    private BigDecimal currentQuantity;
    private LocalDate expiry;
}
