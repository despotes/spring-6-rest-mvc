package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    List<Beer> findAllByBeerNameIsLikeIgnoreCase(String beerName);
}
