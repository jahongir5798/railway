package uz.jahonservice.railwayproject.dto.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserDto {

    private String fullName;

    private String email;

    private String password;

    private String number;

    private String gender;

}
