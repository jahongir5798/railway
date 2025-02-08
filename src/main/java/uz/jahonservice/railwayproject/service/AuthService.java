package uz.jahonservice.railwayproject.service;

import uz.jahonservice.railwayproject.dto.user.LoginDto;
import uz.jahonservice.railwayproject.dto.user.UserDto;
import uz.jahonservice.railwayproject.response.JwtResponse;
import uz.jahonservice.railwayproject.response.StandardResponse;

public interface AuthService {
    StandardResponse<JwtResponse> signUp(UserDto userDto);

    StandardResponse<JwtResponse> login(LoginDto loginDto);
}
