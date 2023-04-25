package org.juang.test.springboot.app.controller;


import org.juang.test.springboot.app.models.Wine;
import org.juang.test.springboot.app.response.WineResponseRest;
import org.juang.test.springboot.app.services.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1")
public class WineController {

    @Autowired
    private WineService wineService;

    @GetMapping("/wines/")
   public ResponseEntity<WineResponseRest> getWines(){
       ResponseEntity<WineResponseRest> response = wineService.findWines();
       return response;
   }

   @GetMapping("/wines/{id}")
    public ResponseEntity<WineResponseRest> consultarPorId(@PathVariable Long id){
        ResponseEntity<WineResponseRest> response = wineService.findById(id);
        return response;

   }

    @PostMapping("/wines/")
    public ResponseEntity<WineResponseRest> crear(@RequestBody Wine request){
        ResponseEntity<WineResponseRest> response = wineService.save(request);
        return response;
    }

    @PutMapping("/wines/{id}")
    public ResponseEntity<WineResponseRest> actualizar(@RequestBody Wine wine, @PathVariable Long id){
        ResponseEntity<WineResponseRest> response = wineService.actualizar(wine,id);
        return response;
    }


    @DeleteMapping("/wines/{id}")
    public ResponseEntity<WineResponseRest> eliminar(@PathVariable Long id){
        ResponseEntity<WineResponseRest> response = wineService.deleteById(id);
        return response;
    }



}
