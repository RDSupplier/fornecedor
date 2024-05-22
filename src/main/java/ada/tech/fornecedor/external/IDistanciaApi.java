package ada.tech.fornecedor.external;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(name = "distance", url =
        "https://api.distancematrix.ai/maps/api")
public interface IDistanciaApi {
    @GetMapping("/distancematrix/json")
    String getDistance(@RequestParam("origins") String origins, @RequestParam("destinations") String destinations,
                       @RequestParam("key") String key);
}