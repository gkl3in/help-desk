package br.com.gklein.orderserviceapi;

import br.com.gklein.orderserviceapi.entities.Order;
import br.com.gklein.orderserviceapi.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class OrderServiceApiApplication implements CommandLineRunner {

	private final OrderRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repository.save(Order.builder()
				.requesterId("1")
				.customerId("2")
				.title("Order 1")
				.description("Order 1 description")
				.build());
	}
}
