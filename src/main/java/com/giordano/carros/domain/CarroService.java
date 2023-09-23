package com.giordano.carros.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    public List<Carro> getCarros(){
        return carroRepository.findAll();
    }

    public Optional<Carro> getCarroById(Long id){
        return carroRepository.findById(id);
    }

    public List<Carro> getCarroByTipo(String tipo){
        return carroRepository.findByTipo(tipo);
    }

    public Carro save(Carro carro){
        return carroRepository.save(carro);
    }

    public Carro update(Carro carro, Long id){
        Assert.notNull(id, "Não foi possivel atualizar o registro!");

        Optional<Carro> optional = carroRepository.findById(id);
        if(optional.isPresent()){
            Carro db = optional.get();

            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());

            carroRepository.save(db);

            return db;
        }else{
            throw new RuntimeException("Não foi possivel atualizar o registro!");
        }
    }

    public void delete(Long id){
        carroRepository.deleteById(id);
    }
}
