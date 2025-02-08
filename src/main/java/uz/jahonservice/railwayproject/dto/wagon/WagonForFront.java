package uz.jahonservice.railwayproject.dto.wagon;

import lombok.*;
import uz.jahonservice.railwayproject.entity.wagon.WagonType;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class WagonForFront {

    private UUID id;

    private WagonType type;

    private String number;

    private Double price;

    private String description;

}
