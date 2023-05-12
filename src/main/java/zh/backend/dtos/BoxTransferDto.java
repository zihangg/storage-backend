package zh.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoxTransferDto {
    private String boxId;
    private String fromLocationCode;
    private String toLocationCode;

    /*
     * Quantity not added due to just moving entire box.
     * Quantity only utilized on asset usage or box repack.
     * All other scenarios should be a complete move of box.
     */

}
