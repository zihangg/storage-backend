package zh.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 * V1 Asset Entity, needs to be reconfigured once Types and Locations data types are created.
 */
@Data
@Entity
@Table(name = "assets")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AssetEntity extends BaseEntity {

    @Column(name = "assetCode", unique = true)
    private String assetCode;

    @Column(name = "name")
    private String name;

    @Column(name = "assetType")
    private Integer assetType;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "cost")
    private BigDecimal cost;
}
