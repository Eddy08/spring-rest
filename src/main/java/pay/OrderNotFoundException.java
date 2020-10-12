package pay;

public class OrderNotFoundException extends RuntimeException{
	public OrderNotFoundException(Long id) {
		super("Order not Found");
	}
}
