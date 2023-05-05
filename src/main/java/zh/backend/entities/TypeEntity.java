package zh.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "types")
@EqualsAndHashCode(callSuper = true)
public class TypeEntity extends BaseEntity{
    @Column(name = "assetType")
    private String assetType;
}
