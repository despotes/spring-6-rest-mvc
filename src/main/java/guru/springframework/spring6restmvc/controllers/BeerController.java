package guru.springframework.spring6restmvc.controllers;


import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {
    public static final String  BEER_PATH = "/api/v1/beer";
    public static final String  BEER_PATH_PATH_ID = BEER_PATH +  "/{beerId}";

    private final BeerService beerService;

    @PatchMapping(BEER_PATH_PATH_ID)
    public ResponseEntity updateBeerPatchById(@PathVariable UUID beerId, @RequestBody BeerDTO beer) {
        if (beerService.patchBeerById(beerId, beer).isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId) {

        log.debug("Deleting Beer with id: {}", beerId);
        if (!beerService.deleteById(beerId)){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_PATH_PATH_ID)
    public ResponseEntity<BeerDTO> updateById(@PathVariable("beerId") UUID beerId, @Validated @RequestBody BeerDTO beer) {

       if (beerService.updateBeerById(beerId, beer).isEmpty()){
           throw new NotFoundException();
       }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(BEER_PATH)
    public ResponseEntity<BeerDTO> handleePost(@Validated @RequestBody BeerDTO beer) {

        BeerDTO savedBeer = beerService.saveBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_PATH + "/" + savedBeer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(BEER_PATH)
    public Page<BeerDTO> listBeers(
            @RequestParam(required = false) String beerName,
            @RequestParam(required = false) BeerStyle beerStyle,
            @RequestParam(required = false) Boolean showInventory,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize
    ) {
        return beerService.listBeers(beerName, beerStyle, showInventory, pageNumber, pageSize);
    }

    @GetMapping(BEER_PATH_PATH_ID)
    public BeerDTO getBearById(@PathVariable("beerId") UUID beerId){
        log.debug("Get Beer by Id - id: {}", beerId.toString());
        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }
}
