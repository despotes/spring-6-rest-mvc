package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    List<Beer> findAllByBeerNameIsLikeIgnoreCase(String beerName);

    List<Beer> findAllByBeerStyle(BeerStyle beerStyle);

    List<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(String beerName, @NotNull BeerStyle beerStyle);
}
