package uz.jahonservice.railwayproject.dto.wagon;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class WagonDto {

    private String number;

    private String type;

    private String description;

    private String capacity;

}
