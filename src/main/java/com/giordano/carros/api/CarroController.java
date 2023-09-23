package com.giordano.carros.api;

import com.giordano.carros.domain.Carro;
import com.giordano.carros.domain.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carros")
public class CarroController {

    @Autowired
    private CarroService carros;

    @GetMapping
    public List<Carro> get(){
        return carros.getCarros();
    }

    @GetMapping("/{id}")
    public Optional<Carro> getCarroId(@PathVariable("id") Long id){
        return carros.getCarroById(id);
    }

    @GetMapping("/tipo/{tipo}")
    public List<Carro> getCarroId(@PathVariable("tipo") String tipo){
        return carros.getCarroByTipo(tipo);
    }

    @PostMapping
    public void salvarCarro(@RequestBody Carro carro){
        carros.save(carro);
    }

    @PutMapping("/{id}")
    public void editarCarro(@PathVariable("id") Long id, @RequestBody Carro carro){
        carros.update(carro, id);
    }

    @DeleteMapping("/{id}")
    public void excluirCarro(@PathVariable("id") Long id){
        carros.delete(id);
    }
}
