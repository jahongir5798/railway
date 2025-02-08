package uz.jahonservice.railwayproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.jahonservice.railwayproject.dto.user.LoginDto;
import uz.jahonservice.railwayproject.dto.user.UserDto;
import uz.jahonservice.railwayproject.dto.user.UserForFront;
import uz.jahonservice.railwayproject.entity.user.Gender;
import uz.jahonservice.railwayproject.entity.user.UserEntity;
import uz.jahonservice.railwayproject.entity.user.UserRole;
import uz.jahonservice.railwayproject.exception.AuthenticationFailedException;
import uz.jahonservice.railwayproject.exception.DataNotFoundException;
import uz.jahonservice.railwayproject.exception.NotAcceptableException;
import uz.jahonservice.railwayproject.repository.UserRepository;
import uz.jahonservice.railwayproject.response.JwtResponse;
import uz.jahonservice.railwayproject.response.StandardResponse;
import uz.jahonservice.railwayproject.response.Status;
import uz.jahonservice.railwayproject.service.AuthService;
import uz.jahonservice.railwayproject.service.auth.JwtService;
import uz.jahonservice.railwayproject.service.validation.CheckEmailAndNumber;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CheckEmailAndNumber checkEmailAndNumber;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    @Override
    public StandardResponse<JwtResponse> signUp(UserDto userDto) {

        checkEmailAndNumber.checkUserEmailAndPhoneNumber(userDto.getEmail(),userDto.getNumber());
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setRole(UserRole.User);
        userEntity.setFullName(userDto.getFullName());
        userEntity.setEmail(userDto.getEmail());

        try{
        userEntity.setGender(Gender.valueOf(userDto.getGender()));
        }catch (Exception e){
            throw new NotAcceptableException("Invalid gender type!");
        }

        userEntity.setNumber(userDto.getNumber());
        userEntity=userRepository.save(userEntity);
        userEntity.setCreatedBy(userEntity.getId());
        userRepository.save(userEntity);

        String accessToken = jwtService.generateAccessToken(userEntity);
        String refreshToken = jwtService.generateRefreshToken(userEntity);
        UserForFront user = modelMapper.map(userEntity, UserForFront.class);
        JwtResponse jwtResponse = JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(user)
                .build();
        return StandardResponse.<JwtResponse>builder()
                .status(Status.SUCCESS)
                .message("Successfully signed Up")
                .data(jwtResponse)
                .build();
    }

    @Override
    public StandardResponse<JwtResponse> login(LoginDto loginDto) {
        UserEntity userEntity = userRepository.findUserEntityByEmail(loginDto.getEmail());
        if (userEntity == null){
            throw new DataNotFoundException("User not found!");
        }
        if (passwordEncoder.matches(loginDto.getPassword(), userEntity.getPassword())){
            String accessToken= jwtService.generateAccessToken(userEntity);
            String refreshToken= jwtService.generateRefreshToken(userEntity);
            UserForFront user = modelMapper.map(userEntity, UserForFront.class);
            JwtResponse jwtResponse= JwtResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .user(user)
                    .build();
            return StandardResponse.<JwtResponse>builder()
                    .status(Status.SUCCESS)
                    .message("Sign in successfully!")
                    .data(jwtResponse)
                    .build();
        }
        else{
            throw new AuthenticationFailedException("Something error during signed in!");
        }
    }
}
