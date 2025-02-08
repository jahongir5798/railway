package uz.jahonservice.railwayproject.dto.order;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDto {

    private String startTime;

    private String endTime;

    private String wagonId;

    private String fromWhere;

    private String toWhere;

}
