package uz.jahonservice.railwayproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.jahonservice.railwayproject.dto.wagon.WagonDto;
import uz.jahonservice.railwayproject.dto.wagon.WagonForFront;
import uz.jahonservice.railwayproject.entity.order.OrderEntity;
import uz.jahonservice.railwayproject.entity.user.UserEntity;
import uz.jahonservice.railwayproject.entity.wagon.WagonEntity;
import uz.jahonservice.railwayproject.entity.wagon.WagonType;
import uz.jahonservice.railwayproject.exception.DataNotFoundException;
import uz.jahonservice.railwayproject.exception.NotAcceptableException;
import uz.jahonservice.railwayproject.repository.OrderRepository;
import uz.jahonservice.railwayproject.repository.UserRepository;
import uz.jahonservice.railwayproject.repository.WagonRepository;
import uz.jahonservice.railwayproject.response.StandardResponse;
import uz.jahonservice.railwayproject.response.Status;
import uz.jahonservice.railwayproject.service.WagonService;
import uz.jahonservice.railwayproject.service.validation.CheckHasWagon;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WagonServiceImpl implements WagonService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CheckHasWagon checkHasWagon;
    private final WagonRepository wagonRepository;
    private final OrderRepository orderRepository;

    @Override
    public StandardResponse<WagonForFront> save(WagonDto wagonDto, Principal principal) {
        UserEntity userEntity = userRepository.findUserEntityByEmail(principal.getName());
        checkHasWagon.checkHasWagon(wagonDto.getNumber());
        WagonEntity wagon = modelMapper.map(wagonDto, WagonEntity.class);
        wagon.setCapacity(wagon.getCapacity());
        try {
            wagon.setType(WagonType.valueOf(wagonDto.getType()));
        } catch (Exception e) {
            throw new NotAcceptableException("Invalid wagon type!");
        }
        wagon.setNumber(wagonDto.getNumber());
        wagon.setCreatedBy(userEntity.getId());
        wagon.setDescription(wagonDto.getDescription());
        wagon.setPrice(wagon.getType().getAmount());
        wagon.setCapacity(wagonDto.getCapacity());
        WagonEntity wagonEntity = wagonRepository.save(wagon);
        WagonForFront wagonForFront = modelMapper.map(wagonEntity, WagonForFront.class);
        return StandardResponse.<WagonForFront>builder()
                .status(Status.SUCCESS)
                .message("Wagon added!")
                .data(wagonForFront)
                .build();
    }

    @Override
    public StandardResponse<String> delete(UUID id, Principal principal) {
        UserEntity user = userRepository.findUserEntityByEmail(principal.getName());
        WagonEntity wagon = wagonRepository.findWagonEntityById(id);
        if (wagon == null) {
            throw new DataNotFoundException("Wagon not found!");
        }
        List<OrderEntity> orderEntities = orderRepository.findOrderEntityByWagonId(wagon.getId());
        if (orderEntities != null) {
            for (OrderEntity order : orderEntities) {
                if (order.getStartTime().isAfter(LocalDateTime.now()) || order.getStartTime().isEqual(LocalDateTime.now())
                        || order.getEndTime().isAfter(LocalDateTime.now())) {
                    throw new NotAcceptableException("You can not delete wagon. Because it has order!");
                }
            }
        }
        wagon.setDeleted(true);
        wagon.setDeletedBy(user.getId());
        wagon.setDeletedTime(LocalDateTime.now());
        wagonRepository.save(wagon);
        return StandardResponse.<String>builder()
                .status(Status.SUCCESS)
                .message("Wagon deleted!")
                .data("DELETED")
                .build();
    }

    @Override
    public List<WagonEntity> getByType(String type) {
        List<WagonEntity> wagon = wagonRepository.findWagonEntityByType(type);
        if (wagon == null) {
            throw new DataNotFoundException("Wagon not found same this type!");
        }
        return wagon;
    }

    @Override
    public List<WagonEntity> getAll() {
        List<WagonEntity> wagonEntities = wagonRepository.getAll();
        if (wagonEntities == null) {
            throw new DataNotFoundException("Wagons not found!");
        }
        return wagonEntities;
    }

    @Override
    public StandardResponse<WagonForFront> updateWagon(WagonDto wagonDto, Principal principal) {
        WagonEntity wagon = wagonRepository.findWagonEntityByNumber(wagonDto.getNumber());
        UserEntity user = userRepository.findUserEntityByEmail(principal.getName());
        if (wagon == null) {
            throw new DataNotFoundException("Wagon not found!");
        }
        wagon.setUpdatedTime(LocalDateTime.now());
        wagon.setUpdatedBy(user.getId());
        wagon.setCapacity(wagonDto.getCapacity());
        try {
            wagon.setType(WagonType.valueOf(wagonDto.getType()));
        } catch (Exception e) {
            throw new NotAcceptableException("Invalid wagon type!");
        }
        wagon.setNumber(wagonDto.getNumber());
        wagon.setDescription(wagonDto.getDescription());
        wagon.setPrice(wagon.getType().getAmount());
        WagonForFront wagonForFront = modelMapper.map(wagon, WagonForFront.class);
        return StandardResponse.<WagonForFront>builder()
                .status(Status.SUCCESS)
                .message("Wagon updated!")
                .data(wagonForFront)
                .build();
    }
}
