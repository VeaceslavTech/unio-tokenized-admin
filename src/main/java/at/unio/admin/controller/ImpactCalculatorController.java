package at.unio.admin.controller;

import at.unio.admin.data.service.ImpactCalculationData;
import at.unio.admin.data.service.ImpactCalculationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/impact-calculation")
public class ImpactCalculatorController {
    @Autowired
    private ImpactCalculationDataService service;

    @PostMapping("/save")
    public ResponseEntity<ImpactCalculationData> saveImpactCalculationData(@RequestBody ImpactCalculationData data) {
        try {
            System.out.println("Received data: {}"+ data);
            ImpactCalculationData savedData = service.saveImpactCalculationData(data);
            return new ResponseEntity<>(savedData, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
