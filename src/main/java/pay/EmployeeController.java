package pay;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
class EmployeeController {

	private final EmployeeRepository employeeRepository;
	private final EmployeeModelAssembler assembler;
	
	public EmployeeController(EmployeeRepository repository,EmployeeModelAssembler assembler) {
//		super();
		// TODO Auto-generated constructor stub
		this.employeeRepository=repository;
		this.assembler=assembler;
		
	}

//	Simple Method	
	
	/*
	 * @GetMapping("/employees") List<Employee> all(){ return
	 * employeeRepository.findAll(); }
	 */
	
	
// Working Method without Assembler
	
	/*
	 * @GetMapping("/employees") CollectionModel<EntityModel<Employee>> all(){
	 * List<EntityModel<Employee>> employees=employeeRepository.findAll() .stream()
	 * .map(employee -> EntityModel.of(employee,
	 * linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(
	 * ) , linkTo(methodOn(EmployeeController.class).all()).withRel("employees") )
	 * 
	 * ). collect(Collectors.toList());
	 * 
	 * return CollectionModel.of( employees,
	 * linkTo(methodOn(EmployeeController.class).all()) .withSelfRel() );
	 * 
	 * }
	 */
	
	@GetMapping("/employees")
	CollectionModel<EntityModel<Employee>> all(){
		
		List<EntityModel<Employee>> employees=employeeRepository.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
	return CollectionModel.of(employees,
				linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
	
			
	}
	
//	Simple Method
	
//	  @GetMapping("/employees/{id}") Employee one(@PathVariable Long id) { return
//	  employeeRepository.findById(id). orElseThrow(()-> new
//	  EmployeeNotFoundException(id));
//	  
//	  }

	//Working Method without modelAssembler 
	
	
	/*
	 * @GetMapping("/employees/{id}") EntityModel<Employee> one(@PathVariable Long
	 * id){ Employee employee =employeeRepository.findById(id) .orElseThrow(() ->new
	 * EmployeeNotFoundException(id));
	 * 
	 * return EntityModel.of(employee, //
	 * linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
	 * linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
	 * 
	 * 
	 * }
	 */
//	
	@GetMapping("/employees/{id}")
	EntityModel<Employee> one(@PathVariable Long id){
		
		Employee employee= employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException(id));		
		return assembler.toModel(employee);
		
	}
	
	/*
	 * @PostMapping("/employees") 
	 * Employee newEmployee(@RequestBody Employee newEmployee) {
	 *  return employeeRepository.save(newEmployee); 
	 *  }
	 */
	@PostMapping("/employees")
	ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee){
		EntityModel<Employee> entityModel= assembler.toModel(employeeRepository.save(newEmployee));
		
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
		
	}
	
	
	/*
	 * @PutMapping("/employees/{id}") Employee replaceEmplyee(@RequestBody Employee
	 * newEmployee,@PathVariable Long id) { return employeeRepository.findById(id).
	 * map(employee -> { employee.setName(newEmployee.getName());
	 * employee.setRole(newEmployee.getRole()); return
	 * employeeRepository.save(employee); }) .orElseGet( () -> {
	 * newEmployee.setId(id); return employeeRepository.save(newEmployee); });
	 * 
	 * }
	 */
	
	@PutMapping("/employees/{id}")
	ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee,@PathVariable Long id){
		Employee updatedEmployee=employeeRepository.findById(id).
				map(employee ->{
				employee.setFirstName(newEmployee.getFirstName());
				employee.setLastName(newEmployee.getLastName());
				return employeeRepository.save(employee);
				}).
				orElseGet(()->{
					newEmployee.setId(id);
					return employeeRepository.save(newEmployee);
				});
		EntityModel<Employee> entityModel=assembler.toModel(updatedEmployee);
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
				.toUri())
				.body(entityModel);
		
	}
	
	
	/*
	 * @DeleteMapping("/employees/{id}") void deleteEmployee(@PathVariable Long id)
	 * { employeeRepository.deleteById(id); }
	 */
	@DeleteMapping("/employees/{id}")
	ResponseEntity<?> deleteEmployee(@PathVariable Long id){
		employeeRepository.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
}
