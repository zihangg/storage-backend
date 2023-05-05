package zh.backend.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateLocationDto {
    @NotEmpty(message = "Name cannot be null or empty")
    private String code;
    private BigDecimal width;
    private BigDecimal height;
    private BigDecimal length;
}
