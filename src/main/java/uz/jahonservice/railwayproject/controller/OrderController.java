package uz.jahonservice.railwayproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.jahonservice.railwayproject.dto.order.ChangeOrderTime;
import uz.jahonservice.railwayproject.dto.order.OrderDto;
import uz.jahonservice.railwayproject.dto.order.OrderForFront;
import uz.jahonservice.railwayproject.entity.order.OrderEntity;
import uz.jahonservice.railwayproject.response.StandardResponse;
import uz.jahonservice.railwayproject.service.OrderService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/save")
    public StandardResponse<OrderForFront> save(
            @RequestBody OrderDto orderDto,
            Principal principal
            ){
        return orderService.save(orderDto, principal);
    }

    @PutMapping("/cancel")
    public StandardResponse<OrderForFront> cancel(
            @RequestParam UUID id,
            Principal principal
    ){
        return orderService.cancelOrder(id, principal);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<OrderForFront> delete(
            @RequestParam UUID id,
            Principal principal
    ){
        return orderService.delete(id, principal);
    }

    @GetMapping("/get-canceled-orders")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderEntity> getCanceledOrders(
    ){
        return orderService.getCanceledOrders();
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderEntity> getAll(){
        return orderService.getAll();
    }

    @PutMapping("/change-order-time")
    public StandardResponse<OrderForFront> changeOrderTime(
            @RequestBody ChangeOrderTime change,
            @RequestParam UUID id,
            Principal principal
            ){
       return orderService.changeOrderTime(id, change,principal);
    }

}
