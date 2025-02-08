package uz.jahonservice.railwayproject.dto.order;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ChangeOrderTime {

    private String startTime;

    private String endTime;

}
