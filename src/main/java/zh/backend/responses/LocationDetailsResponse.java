package zh.backend.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import zh.backend.dtos.LocationBoxesDto;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class LocationDetailsResponse {

    private String locationId;
    private String locationCode;
    private BigDecimal totalVolume;
    private BigDecimal availableVolume;
    private List<LocationBoxesDto> boxes;

}
