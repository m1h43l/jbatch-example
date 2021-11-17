package rpgnextgen.batch.data;

import java.math.BigDecimal;

public class Customer {

	public int id;
	public String company;
	public String street;
	public String city;
	public String country;
	public BigDecimal discount;
	public boolean active;

	public Customer() {
	}

	public Customer(int id, String company, String street, String city, String country, BigDecimal discount,
			boolean active) {
		super();
		this.id = id;
		this.company = company;
		this.street = street;
		this.city = city;
		this.country = country;
		this.discount = discount;
		this.active = active;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", company=" + company + ", street=" + street + ", city=" + city + ", country="
				+ country + ", discount=" + discount + ", active=" + active + "]";
	}

}
