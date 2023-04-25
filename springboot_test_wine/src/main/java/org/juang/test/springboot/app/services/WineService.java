package org.juang.test.springboot.app.services;

import org.juang.test.springboot.app.models.Wine;
import org.juang.test.springboot.app.response.WineResponse;
import org.juang.test.springboot.app.response.WineResponseRest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WineService {

    public ResponseEntity<WineResponseRest> findWines();
    public ResponseEntity<WineResponseRest> findById(Long id);
    public ResponseEntity<WineResponseRest> save(Wine wine);
    public ResponseEntity<WineResponseRest> actualizar(Wine wine,Long id);
    public ResponseEntity<WineResponseRest> deleteById(Long id);






}
