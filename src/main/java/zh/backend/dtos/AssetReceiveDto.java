package zh.backend.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AssetReceiveDto {
    @NotEmpty
    private String orderNumber;
    @NotEmpty
    private String assetCode;
    @NotEmpty
    private String locationCode;
    @NotEmpty
    private String expiry;
    @NotNull
    private List<AssetBoxDto> boxes;

}
