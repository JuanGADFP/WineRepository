package org.juang.test.springboot.app.services;

import org.hibernate.cache.spi.AbstractCacheTransactionSynchronization;
import org.juang.test.springboot.app.models.Wine;
import org.juang.test.springboot.app.repositories.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.juang.test.springboot.app.response.WineResponseRest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class WineServiceImpl implements WineService {

    private WineRepository wineRepository;

    public WineServiceImpl(WineRepository wineRepository) {
        this.wineRepository = wineRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<WineResponseRest> findWines() {

        WineResponseRest response = new WineResponseRest();

        try{
            List<Wine> wine = (List<Wine>) wineRepository.findAll();

            response.getWineResponse().setWine(wine);

            response.setMetadata("Response Status Ok", "200", "Successfully Response");
        }catch (Exception e){
            response.setMetadata("Response Status INTERNAL_SERVER_ERROR","500" , "INTERNAL_SERVER_ERROR");
            e.getStackTrace();
            return new ResponseEntity<WineResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<WineResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<WineResponseRest> findById(Long id) {

        WineResponseRest response = new WineResponseRest();

        List<Wine> list = new ArrayList<>();

        try {
            Optional<Wine> wines = wineRepository.findById(id);

            if (wines.isPresent()){
                list.add(wines.get());
                response.getWineResponse().setWine(list);
            }else {
                response.setMetadata("Response Status NOT_FOUND","404" , "Could not consult wine id");
                return new ResponseEntity<WineResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            response.setMetadata("Response Status INTERNAL_SERVER_ERROR","500" , "INTERNAL_SERVER_ERROR");
            return new ResponseEntity<WineResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Response Status Ok", "200", "Successfully Response");
        return new ResponseEntity<WineResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<WineResponseRest> save(Wine wine) {

        WineResponseRest response = new WineResponseRest();

        List<Wine> list = new ArrayList<>();

        try{
            Wine wineGuardado = wineRepository.save(wine);

            if (wineGuardado != null){
                list.add(wineGuardado);
                response.getWineResponse().setWine(list);
            }else {
                response.setMetadata("Response Status BAD_REQUEST","400" , "Could not save wine in database");
                return new ResponseEntity<WineResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            response.setMetadata("Response Status INTERNAL_SERVER_ERROR","500" , "INTERNAL_SERVER_ERROR");
            return new ResponseEntity<WineResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Response Status Ok", "200", "Successfully Response");
        return new ResponseEntity<WineResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<WineResponseRest> actualizar(Wine wine, Long id) {
        WineResponseRest response = new WineResponseRest();
        List<Wine> list = new ArrayList<>();

        try{

            Optional<Wine> wineBuscado = wineRepository.findById(id);

            if (wineBuscado.isPresent()){

                wineBuscado.get().setName(wine.getName());
                wineBuscado.get().setWinery(wine.getWinery());

                Wine wineActualizar = wineRepository.save(wineBuscado.get());

                if (wineActualizar != null){

                    response.setMetadata("Response Status Ok", "200", "Successfully Response");
                    list.add(wineActualizar);
                    response.getWineResponse().setWine(list);

                }else {

                    response.setMetadata("Response Status BAD_REQUEST","400" , "Could not update wine in database");
                    return new ResponseEntity<WineResponseRest>(response, HttpStatus.BAD_REQUEST);

                }
            }else{

                response.setMetadata("Response Status NOT_FOUND","404" , "Could not update wine id");
                return new ResponseEntity<WineResponseRest>(response,HttpStatus.NOT_FOUND);

            }


        }catch (Exception e){
            e.getStackTrace();
            response.setMetadata("Response Status INTERNAL_SERVER_ERROR","500" , "INTERNAL_SERVER_ERROR");
            return new ResponseEntity<WineResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<WineResponseRest>(response,HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<WineResponseRest> deleteById(Long id) {

        WineResponseRest response = new WineResponseRest();
        List<Wine> list = new ArrayList<>();

        try{

            Optional<Wine> wineBuscado = wineRepository.findById(id);

            if (wineBuscado.isPresent()){
                list.add(wineBuscado.get());
                response.getWineResponse().setWine(list);
                wineRepository.deleteById(id);
                response.setMetadata("Response Status Ok", "200", "Successfully Response");
            }else {
                response.setMetadata("Response Status NOT_FOUND","404" , "Could not delete wine id");
                return new ResponseEntity<WineResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){

            e.getStackTrace();
            response.setMetadata("Response Status INTERNAL_SERVER_ERROR","500" , "INTERNAL_SERVER_ERROR");

            return new ResponseEntity<WineResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<WineResponseRest>(response, HttpStatus.OK);
    }



}
