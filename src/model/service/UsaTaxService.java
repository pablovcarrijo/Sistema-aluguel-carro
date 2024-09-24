package model.service;

public class UsaTaxService implements TaxService{

	public UsaTaxService() {
		
	}
	
	public Double tax(Double amount) {
		return  10.0;
	}
	
}
