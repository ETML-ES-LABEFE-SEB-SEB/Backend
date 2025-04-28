package ch.etmles.payroll.Lot;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LotController {

    private final LotRepository repository;

    public LotController(LotRepository repository) {
        this.repository = repository;
    }

    /* curl sample :
    curl -i localhost:8080/lots
    */
    @GetMapping("/lots")
    List<Lot> all() { return repository.findAll(); }

    /* curl sample :
    curl -i localhost:8080/lots/1
    */
    @GetMapping("/lots/{id}")
    Lot one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new LotNotFoundException(id));
    }
}
