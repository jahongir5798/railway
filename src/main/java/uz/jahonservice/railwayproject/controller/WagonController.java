package uz.jahonservice.railwayproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.jahonservice.railwayproject.dto.wagon.WagonDto;
import uz.jahonservice.railwayproject.dto.wagon.WagonForFront;
import uz.jahonservice.railwayproject.entity.wagon.WagonEntity;
import uz.jahonservice.railwayproject.response.StandardResponse;
import uz.jahonservice.railwayproject.service.WagonService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wagon")
public class WagonController {

    private final WagonService wagonService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<WagonForFront> save(
            @RequestBody WagonDto wagonDto,
            Principal principal
            ){
        return wagonService.save(wagonDto, principal);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<String> delete(
            @RequestParam UUID id,
            Principal principal
    ){
        return wagonService.delete(id, principal);
    }

    @GetMapping("/get-by-type")
    public List<WagonEntity> getByType(
            @RequestParam String type
    ){
        return wagonService.getByType(type);
    }

    @GetMapping("/get-all")
    public List<WagonEntity> getAll(){
        return wagonService.getAll();
    }

    @PutMapping("/update-wagon")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<WagonForFront> updateWagon(
            @RequestBody WagonDto wagonDto,
            Principal principal
    ){
        return wagonService.updateWagon(wagonDto, principal);
    }

}
