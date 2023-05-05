package zh.backend.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAssetDto {
    @NotEmpty(message = "Code cannot be null or empty")
    private String assetCode;
    @NotEmpty(message = "Name cannot be null or empty")
    private String name;
    @NotNull(message = "Type cannot be null")
    private Integer type;
    @NotNull(message = "Cost cannot be null")
    @DecimalMin("0")
    private BigDecimal cost;
}
