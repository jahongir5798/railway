package uz.jahonservice.railwayproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.jahonservice.railwayproject.dto.user.AdminDto;
import uz.jahonservice.railwayproject.dto.user.UserDto;
import uz.jahonservice.railwayproject.dto.user.UserForFront;
import uz.jahonservice.railwayproject.entity.order.OrderEntity;
import uz.jahonservice.railwayproject.entity.user.Gender;
import uz.jahonservice.railwayproject.entity.user.UserEntity;
import uz.jahonservice.railwayproject.entity.user.UserRole;
import uz.jahonservice.railwayproject.exception.DataNotFoundException;
import uz.jahonservice.railwayproject.exception.NotAcceptableException;
import uz.jahonservice.railwayproject.repository.OrderRepository;
import uz.jahonservice.railwayproject.repository.UserRepository;
import uz.jahonservice.railwayproject.response.StandardResponse;
import uz.jahonservice.railwayproject.response.Status;
import uz.jahonservice.railwayproject.service.UserService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public StandardResponse<String> delete(UUID id, Principal principal) {
        UserEntity user = userRepository.findUserEntityByEmail(principal.getName());
        List<OrderEntity> orderEntities = orderRepository.findOrderEntityByCreatedBy(id);
        for (OrderEntity order : orderEntities) {
            if (order.getEndTime().isAfter(LocalDateTime.now())) {
                throw new NotAcceptableException("Can not delete this user. Because user has active order!");
            }
        }
        UserEntity userEntity = userRepository.findUserEntityById(id);
        if (userEntity == null) {
            throw new DataNotFoundException("User not found!");
        }
        userEntity.setDeleted(true);
        userEntity.setDeletedBy(user.getId());
        userEntity.setDeletedTime(LocalDateTime.now());
        userRepository.save(userEntity);
        return StandardResponse.<String>builder()
                .data("User deleted successfully!")
                .status(Status.SUCCESS)
                .message("DELETED")
                .build();
    }

    @Override
    public StandardResponse<UserForFront> getByEmail(String email) {
        UserEntity userEntity = userRepository.findUserEntityByEmail(email);
        if (userEntity == null) {
            throw new DataNotFoundException("User not found!");
        }
        UserForFront user = modelMapper.map(userEntity, UserForFront.class);
        return StandardResponse.<UserForFront>builder()
                .status(Status.SUCCESS)
                .data(user)
                .message("This is user")
                .build();
    }

    @Override
    public StandardResponse<UserForFront> getById(UUID id) {
        UserEntity userEntity = userRepository.findUserEntityById(id);
        if (userEntity == null) {
            throw new DataNotFoundException("User not found!");
        }
        UserForFront user = modelMapper.map(userEntity, UserForFront.class);
        return StandardResponse.<UserForFront>builder()
                .status(Status.SUCCESS)
                .data(user)
                .message("This is user")
                .build();
    }

    @Override
    public List<UserEntity> getAll() {
        List<UserEntity> userEntities = userRepository.getAll();
        if (userEntities == null) {
            throw new DataNotFoundException("Users not found!");
        }
        return userEntities;
    }

    @Override
    public StandardResponse<UserForFront> getByNumber(String number) {
        Optional<UserEntity> userEntity = userRepository.findUserEntityByNumber(number);
        if (userEntity.isEmpty()) {
            throw new DataNotFoundException("User not found!");
        }
        UserForFront user = modelMapper.map(userEntity, UserForFront.class);
        return StandardResponse.<UserForFront>builder()
                .status(Status.SUCCESS)
                .data(user)
                .message("This is user!")
                .build();
    }

    @Override
    public StandardResponse<UserForFront> addAdmin(AdminDto adminDto, Principal principal) {
        UserEntity userEntity=  userRepository.findUserEntityByEmail(adminDto.getEmail());
        UserEntity user = userRepository.findUserEntityByEmail(principal.getName());
        if (userEntity==null){
            throw new DataNotFoundException("User not found same this email!");
        }
        userEntity.setRole(UserRole.ADMIN);
        userEntity.setChangeRoleBy(user.getId());
        UserEntity save = userRepository.save(userEntity);
        UserForFront userForFront = modelMapper.map(save, UserForFront.class);
        return StandardResponse.<UserForFront>builder()
                .status(Status.SUCCESS)
                .data(userForFront)
                .message("Admin added!")
                .build();
    }

    @Override
    public StandardResponse<UserForFront> update(UserDto userDto, Principal principal, UUID id) {
        UserEntity user = userRepository.findUserEntityByEmail(principal.getName());
        UserEntity userEntity = userRepository.findUserEntityById(id);
        if (userEntity==null){
            throw new DataNotFoundException("User not found!");
        }
        userEntity.setUpdatedTime(LocalDateTime.now());
        userEntity.setFullName(userDto.getFullName());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        try{
        userEntity.setGender(Gender.valueOf(userDto.getGender()));
        }catch (Exception e){
            throw new NotAcceptableException("Invalid gender type!");
        }
        userEntity.setUpdatedBy(user.getId());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setNumber(userDto.getNumber());
        UserEntity save = userRepository.save(userEntity);
        UserForFront userForFront = modelMapper.map(save, UserForFront.class);
        return StandardResponse.<UserForFront>builder()
                .status(Status.SUCCESS)
                .data(userForFront)
                .message("Profile updated!")
                .build();
    }

    @Override
    public List<OrderEntity> getMyOrders(UUID id) {
        List<OrderEntity> orderEntities = orderRepository.findOrderEntityByCreatedBy(id);
        if (orderEntities.isEmpty()){
            throw new DataNotFoundException("Orders not found!");
        }
        return orderEntities;
    }
}
