package zh.backend.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AssetCreatedResponse {

    private String id;
    private String assetCode;
    private String name;
    private LocalDateTime createdTime;
}
