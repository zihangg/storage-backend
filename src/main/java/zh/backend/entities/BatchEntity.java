package zh.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import zh.backend.dtos.AssetBoxDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "batches")
@AllArgsConstructor
@NoArgsConstructor
public class BatchEntity extends BaseEntity {
    @Column(unique = true)
    private String batchNumber;
    private String assetCode;
    private BigDecimal initialQuantity;
    private BigDecimal currentQuantity;
    private LocalDate expiry;
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> boxIds;
}
