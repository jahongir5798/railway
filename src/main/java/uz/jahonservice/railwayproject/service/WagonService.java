package uz.jahonservice.railwayproject.service;

import uz.jahonservice.railwayproject.dto.wagon.WagonDto;
import uz.jahonservice.railwayproject.dto.wagon.WagonForFront;
import uz.jahonservice.railwayproject.entity.wagon.WagonEntity;
import uz.jahonservice.railwayproject.response.StandardResponse;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface WagonService {
    StandardResponse<WagonForFront> save(WagonDto wagonDto, Principal principal);

    StandardResponse<String> delete(UUID id, Principal principal);

    List<WagonEntity> getByType(String type);

     List<WagonEntity> getAll();

    StandardResponse<WagonForFront> updateWagon(WagonDto wagonDto, Principal principal);

}
