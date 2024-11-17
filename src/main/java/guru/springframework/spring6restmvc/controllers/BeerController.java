package guru.springframework.spring6restmvc.controllers;


import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {
    public static final String  BEER_PATH = "/api/v1/beer";
    public static final String  BEER_PATH_PATH_ID = BEER_PATH +  "/{beerId}";

    private final BeerService beerService;

    @PatchMapping(BEER_PATH_PATH_ID)
    ResponseEntity updateBeerPatchrById(@PathVariable UUID beerId, @RequestBody Beer beer) {
        beerService.patchBeerById(beerId, beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId) {

        log.debug("Deleting Beer with id: {}", beerId);
        beerService.deteteById(beerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_PATH_PATH_ID)
    public ResponseEntity<Beer> updateById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {

        beerService.updateBeerById(beerId, beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(BEER_PATH)
    public ResponseEntity<Beer> handleePost(@RequestBody Beer beer) {

        Beer savedBeer = beerService.saveBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/beer" + savedBeer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(BEER_PATH)
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

    @GetMapping(BEER_PATH_PATH_ID)
    public Beer getBearById(@PathVariable("beerId") UUID beerId){
        log.debug("Get Beer by Id - id: {}", beerId.toString());
        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }
}
