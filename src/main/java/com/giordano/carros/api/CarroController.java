package com.giordano.carros.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.giordano.carros.domain.Carro;
import com.giordano.carros.domain.CarroService;
import com.giordano.carros.domain.dto.CarroDTO;

@RestController
@RequestMapping("/api/carros")
public class CarroController {

    @Autowired
    private CarroService carros;

    @GetMapping
    public List<CarroDTO> get() {
        return carros.getCarros();
    }

    @GetMapping("/{id}")
    public ResponseEntity getCarroId(@PathVariable("id") Long id) {
        Optional<CarroDTO> carro = carros.getCarroById(id);

        return carro.isPresent() ? ResponseEntity.ok(carro) : ResponseEntity.notFound().build();

        // if(carro.isPresent()){
        // return ResponseEntity.ok(carro.get());
        // } else {
        // return ResponseEntity.notFound().build();
        // }
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity getCarroId(@PathVariable("tipo") String tipo) {
        List<CarroDTO> carro = carros.getCarroByTipo(tipo);

        // Menos verboso
        return carro.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(carro);

        // Maneira simples
        // if(carro.isEmpty()){
        // return ResponseEntity.notFound().build();
        // }else{
        // return ResponseEntity.ok(carro);
        // }
    }

    @PostMapping
    public ResponseEntity salvarCarro(@RequestBody Carro carro) {
        try {
            CarroDTO c = carros.save(carro);

            // O location irá ser a URL com id do novo recurso criado que irá aparecer no
            // Header
            URI location = getUri(c.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Método para que o spring monte a URL até o novo recurso criado no POST
    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity editarCarro(@PathVariable("id") Long id, @RequestBody Carro carro) {
        carro.setId(id);

        CarroDTO c = carros.update(carro, id);

        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluirCarro(@PathVariable("id") Long id) {
        boolean ok = carros.delete(id);

        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
