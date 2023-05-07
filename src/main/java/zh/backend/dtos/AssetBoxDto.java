package zh.backend.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssetBoxDto {
    @NotEmpty
    private BigDecimal quantity;
    @NotEmpty
    private BigDecimal length;
    @NotEmpty
    private BigDecimal height;
    @NotEmpty
    private BigDecimal width;
    @NotEmpty
    private Integer boxNumber;
    private String locationCode;
    private BigDecimal volume;
    public BigDecimal getVolume() {
        return width.multiply(height).multiply(length);
    }

}
