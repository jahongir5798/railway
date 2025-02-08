package uz.jahonservice.railwayproject.response;

import lombok.*;
import uz.jahonservice.railwayproject.dto.user.UserForFront;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class JwtResponse {

     private String accessToken;

    private String refreshToken;

    private UserForFront user;

}
