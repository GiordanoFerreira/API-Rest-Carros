package com.giordano.carros.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.giordano.carros.domain.dto.CarroDTO;
import com.giordano.carros.domain.entity.Carro;
import com.giordano.carros.domain.repository.CarroRepository;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    public List<CarroDTO> getCarros() {
        // Utilizando Lambdas
        return carroRepository.findAll().stream().map(CarroDTO::new).collect(Collectors.toList());

        // Maneira Simples
        // List<Carro> carros = carroRepository.findAll();
        // List<CarroDTO> list = new ArrayList<>();
        // for(Carro carro : carros){
        // list.add(new CarroDTO(carro));
        // }

        // return list;
    }

    public CarroDTO getCarroById(Long id) {
        Optional<Carro> carro = carroRepository.findById(id);

        return carro.map(CarroDTO::new).orElseThrow();
    }

    public List<CarroDTO> getCarroByTipo(String tipo) {
        return carroRepository.findByTipo(tipo).stream().map(CarroDTO::new).collect(Collectors.toList());
    }

    public CarroDTO save(Carro carro) {
        Assert.notNull(carro.getId(), "Não foi possível criar o registro");

        return new CarroDTO(carroRepository.save(carro));
    }

    public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id, "Não foi possivel atualizar o registro!");

        Optional<Carro> optional = carroRepository.findById(id);
        if (optional.isPresent()) {
            Carro db = optional.get();

            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());

            carroRepository.save(db);

            return new CarroDTO(db);
        } else {
            throw new RuntimeException("Não foi possivel atualizar o registro!");
        }
    }

    public void delete(Long id) {
        carroRepository.deleteById(id);
    }
}
