package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Account {

	private Long id;
	private String number;
	private int balance;
	private Long customerId;
	
	public Account(String number, int balance, Long customerId) {
		this.number = number;
		this.balance = balance;
		this.customerId = customerId;
	}
}
