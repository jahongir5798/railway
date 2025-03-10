package uz.jahonservice.railwayproject.entity.wagon;

import jakarta.persistence.*;
import lombok.*;
import uz.jahonservice.railwayproject.entity.BaseModel;
import uz.jahonservice.railwayproject.entity.order.OrderEntity;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "wagons")
public class WagonEntity extends BaseModel {

    @Column(nullable = false,unique = true)
    private String number;

    @Column(nullable = false)
    private String capacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WagonType type;

    private Double price;

    @Column(nullable = false)
    private String description;

    private UUID updatedBy;

    @OneToMany(mappedBy = "id",cascade = CascadeType.ALL)
    private List<OrderEntity> orders;

}
