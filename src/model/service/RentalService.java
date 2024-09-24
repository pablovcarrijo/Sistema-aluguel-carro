package model.service;

import java.time.temporal.ChronoUnit;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

	private Double pricePerDay;
	private Double pricePerHour;
	private TaxService taxService;
	
	public RentalService(Double pricePerDay, Double pricePerHour, TaxService taxService) {
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public TaxService getTaxService() {
		return taxService;
	}

	public void setTaxService(TaxService taxService) {
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		long minutes = ChronoUnit.MINUTES.between(carRental.getStart(), carRental.getFinish());
		double hours = Math.ceil((double)minutes/60.0);
		double payment = 0;
		if(hours <= 12) {
			payment = hours * getPricePerHour();
		}
		else if(hours > 12) {
			payment = Math.ceil(hours/24) * getPricePerDay();
		}
		
		double tax = taxService.tax(payment);
		
		carRental.setInvoice(new Invoice(payment, tax));
		
	}
	
}
