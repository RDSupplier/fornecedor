package ada.tech.fornecedor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FornecedorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FornecedorApplication.class, args);
	}

}
