package uz.jahonservice.railwayproject.dto.order;

import lombok.*;
import uz.jahonservice.railwayproject.entity.user.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderForFront {

    private UUID id;

    private UUID wagonId;

    private LocalDateTime startTime;

    private String fromWhere;

    private String toWhere;

    private LocalDateTime endTime;

    private UserEntity user;

}
