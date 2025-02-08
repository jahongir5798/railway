package uz.jahonservice.railwayproject.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.jahonservice.railwayproject.entity.wagon.WagonEntity;
import uz.jahonservice.railwayproject.exception.DataHasAlreadyExistException;
import uz.jahonservice.railwayproject.repository.WagonRepository;

@Component
@RequiredArgsConstructor
public class CheckHasWagon {

    private final WagonRepository wagonRepository;

    public void checkHasWagon(String number) {
        WagonEntity wagon = wagonRepository.findWagonEntityByNumber(number);
        if (wagon != null) {
            throw new DataHasAlreadyExistException("Wagon has already added!");
        }
    }

}
