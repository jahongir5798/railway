package uz.jahonservice.railwayproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.jahonservice.railwayproject.dto.user.LoginDto;
import uz.jahonservice.railwayproject.dto.user.UserDto;
import uz.jahonservice.railwayproject.exception.RequestValidationException;
import uz.jahonservice.railwayproject.response.JwtResponse;
import uz.jahonservice.railwayproject.response.StandardResponse;
import uz.jahonservice.railwayproject.service.AuthService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("ap1/v1/auth")
public class AuthController {


    private final AuthService authService;

    @PostMapping("/sign-up")
    public StandardResponse<JwtResponse> signUp(
            @RequestBody UserDto userDto,
            BindingResult bindingResult
    ) throws RequestValidationException {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        return authService.signUp(userDto);
    }

    @PostMapping("/sign-in")
    public StandardResponse<JwtResponse> login(
            @RequestBody LoginDto loginDto
    ) {
        return authService.login(loginDto);
    }

}
