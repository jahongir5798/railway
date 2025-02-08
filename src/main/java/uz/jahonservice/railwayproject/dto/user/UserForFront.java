package uz.jahonservice.railwayproject.dto.user;

import lombok.*;
import uz.jahonservice.railwayproject.entity.user.Gender;
import uz.jahonservice.railwayproject.entity.user.UserRole;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserForFront {

    private UUID id;

    private String fullName;

    private String email;

    private String number;

    private UserRole role;

    private Gender gender;
}
