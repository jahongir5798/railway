package uz.jahonservice.railwayproject.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.jahonservice.railwayproject.entity.user.UserEntity;
import uz.jahonservice.railwayproject.exception.UserBadRequestException;
import uz.jahonservice.railwayproject.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class CheckEmailAndNumber {

    private final UserRepository userRepository;

    public void checkUserEmailAndPhoneNumber(String email, String phoneNumber) {
        UserEntity userEntity = userRepository.findUserEntityByEmail(email);
        if (userEntity!=null){
            throw new UserBadRequestException("Email has already exist!");
        }
        if (userRepository.findUserEntityByNumber(phoneNumber).isPresent()){
            throw new UserBadRequestException("Number has already exist!");
        }
    }

}
