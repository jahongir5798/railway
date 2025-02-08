package uz.jahonservice.railwayproject.service;

import uz.jahonservice.railwayproject.dto.order.ChangeOrderTime;
import uz.jahonservice.railwayproject.dto.order.OrderDto;
import uz.jahonservice.railwayproject.dto.order.OrderForFront;
import uz.jahonservice.railwayproject.entity.order.OrderEntity;
import uz.jahonservice.railwayproject.response.StandardResponse;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    StandardResponse<OrderForFront> save(OrderDto orderDto, Principal principal);

    StandardResponse<OrderForFront> cancelOrder(UUID id, Principal principal);

    StandardResponse<OrderForFront> delete(UUID id, Principal principal);

    List<OrderEntity> getCanceledOrders();

    List<OrderEntity> getAll();

    StandardResponse<OrderForFront> changeOrderTime(UUID id, ChangeOrderTime change, Principal principal);
}
