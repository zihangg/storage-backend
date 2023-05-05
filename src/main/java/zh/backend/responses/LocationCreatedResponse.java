package zh.backend.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LocationCreatedResponse {
    private String id;
    private String code;
    private BigDecimal volume;
    private LocalDateTime createdTime;
}
