package com.giordano.carros.api;

import java.net.URI;
import java.util.List;

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

import com.giordano.carros.domain.dto.CarroDTO;
import com.giordano.carros.domain.entity.Carro;
import com.giordano.carros.domain.service.CarroService;

@RestController
@RequestMapping("/api/v1/carros")
public class CarroController {

    @Autowired
    private CarroService carros;

    @GetMapping
    public List<CarroDTO> get() {
        return carros.getCarros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarroDTO> getCarroId(@PathVariable("id") Long id) {
        CarroDTO carro = carros.getCarroById(id);

        return ResponseEntity.ok(carro);

        // if(carro.isPresent()){
        // return ResponseEntity.ok(carro.get());
        // } else {
        // return ResponseEntity.notFound().build();
        // }
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<CarroDTO>> getCarroId(@PathVariable("tipo") String tipo) {
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
    public ResponseEntity<CarroDTO> salvarCarro(@RequestBody Carro carro) {

        CarroDTO c = carros.save(carro);

        // O location irá ser a URL com id do novo recurso criado que irá aparecer no
        // Header
        URI location = getUri(c.getId());
        return ResponseEntity.created(location).build();
    }

    // Método para que o spring monte a URL até o novo recurso criado no POST
    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarroDTO> editarCarro(@PathVariable("id") Long id, @RequestBody Carro carro) {
        carro.setId(id);

        CarroDTO c = carros.update(carro, id);

        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCarro(@PathVariable("id") Long id) {
        carros.delete(id);

        return ResponseEntity.ok().build();
    }
}
