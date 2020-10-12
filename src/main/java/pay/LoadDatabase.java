package pay;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
	private static final Logger log=
			LoggerFactory.getLogger(LoadDatabase.class);
	
	@Bean
	CommandLineRunner initDatabase(EmployeeRepository repository ,OrderRepository orderRepository) {
		return args -> {
			log.info("Non Rest Preloaded Database" + repository.save(new Employee("Harsh","Bhardwaj","Manager")));
			log.info("2nd Entry"+repository.save(new Employee("Vishal","Singh","CEO")));
			
			orderRepository.save(new Order("Motrolo One Action",Status.COMPLETED));
			orderRepository.save(new Order("Asus Gaming Laptop",Status.IN_PROGRESS));
			
			orderRepository.findAll().forEach(order -> {
				log.info("Preloaded "+order);
			});
			
		};
		
	}
}
