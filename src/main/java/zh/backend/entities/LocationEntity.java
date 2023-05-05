package zh.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "locations")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class LocationEntity extends BaseEntity{

    // location code e.g. "A-11-C"
    @Column(name = "code", unique = true)
    @NonNull
    private String code;


    // has 3 dimensions: length, height and width, which can be used to calculate total volume of the space.
    // all in centimeters.
    @Column(name = "height")
    @NonNull
    private BigDecimal height;

    @Column(name = "length")
    @NonNull
    private BigDecimal length;

    @Column(name = "width")
    @NonNull
    private BigDecimal width;

    @Column(name = "totalVolume")
    @NonNull
    private BigDecimal totalVolume;

    // for when volume is being used during storage
    @Column(name = "availableVolume")
    private BigDecimal availableVolume;

    //TODO: location should also have a mapping of {assetCode: quantity}.
}
