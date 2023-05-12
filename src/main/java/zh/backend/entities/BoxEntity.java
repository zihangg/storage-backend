package zh.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "boxes")
@AllArgsConstructor
@NoArgsConstructor
public class BoxEntity extends BaseEntity {
    @Column(unique = true)
    private String boxId;
    private String batchNumber;
    private String assetCode;
    private BigDecimal quantity;
    private BigDecimal length;
    private BigDecimal height;
    private BigDecimal width;
    private Integer boxNumber;
    private String locationCode;
    private BigDecimal volume;

    public BoxEntity(
            String batchNumber,
            String assetCode,
            BigDecimal quantity,
            BigDecimal length,
            BigDecimal height,
            BigDecimal width,
            Integer boxNumber,
            String locationCode
    ) {
        this.batchNumber = batchNumber;
        this.assetCode = assetCode;
        this.quantity = quantity;
        this.length = length;
        this.height = height;
        this.width = width;
        this.boxNumber = boxNumber;
        this.locationCode = locationCode;
        this.volume = calculateVolume(width, height, length);
        this.boxId = generateBoxId(batchNumber, boxNumber);
    }

    private BigDecimal calculateVolume(BigDecimal width,BigDecimal height,BigDecimal length) {
        return width.multiply(height).multiply(length);
    }

    private String generateBoxId(String batchNumber, Integer boxNumber) {
        return batchNumber + "-" + boxNumber.toString();
    }

}
