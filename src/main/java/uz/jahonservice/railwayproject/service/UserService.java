package uz.jahonservice.railwayproject.service;

import uz.jahonservice.railwayproject.dto.user.AdminDto;
import uz.jahonservice.railwayproject.dto.user.UserDto;
import uz.jahonservice.railwayproject.dto.user.UserForFront;
import uz.jahonservice.railwayproject.entity.order.OrderEntity;
import uz.jahonservice.railwayproject.entity.user.UserEntity;
import uz.jahonservice.railwayproject.response.StandardResponse;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface UserService {
    StandardResponse<String> delete(UUID id, Principal principal);

    StandardResponse<UserForFront> getByEmail(String email);

    StandardResponse<UserForFront> getById(UUID id);

    List<UserEntity> getAll();

    StandardResponse<UserForFront> getByNumber(String number);

    StandardResponse<UserForFront> addAdmin(AdminDto adminDto, Principal principal);

    StandardResponse<UserForFront> update(UserDto userDto, Principal principal, UUID id);

    List<OrderEntity> getMyOrders(UUID id);
}
