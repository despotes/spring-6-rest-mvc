package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<BeerDTO> listBeers();

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveBeer(BeerDTO beer);

    void updateBeerById(UUID beerId, BeerDTO beer);

    void deteteById(UUID beerId);

    void patchBeerById(UUID beerId, BeerDTO beer);
}
