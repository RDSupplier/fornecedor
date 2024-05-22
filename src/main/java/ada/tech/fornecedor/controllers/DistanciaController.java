package ada.tech.fornecedor.controllers;


import ada.tech.fornecedor.external.IDistanciaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fornecedor")
public class DistanciaController {

    @Autowired
    IDistanciaApi distanciaApi;

    @GetMapping("/distancia")
    public String getDistance(String origins, String destinations, String key) {
        return distanciaApi.getDistance(origins, destinations, key);
    }

}