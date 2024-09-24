package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.service.BrazilTaxService;
import model.service.RentalService;
import model.service.UsaTaxService;

public class App {

	public static void main(String[] args) {

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		Scanner sc = new Scanner(System.in).useLocale(Locale.US);
		
		try {
			System.out.println("Entre com os dados do aluguel: ");
			System.out.print("Modelo do carro: ");
			String model = sc.nextLine();
			
			System.out.print("Retirada (dd/MM/yyyy HH:mm): ");
			LocalDateTime start = LocalDateTime.parse(sc.nextLine(), fmt);
			
			System.out.print("Entrega (dd/MM/yyyy HH:mm): ");
			LocalDateTime finish = LocalDateTime.parse(sc.nextLine(), fmt);
			
			CarRental carRental = new CarRental(new Vehicle(model), start, finish);
			
			System.out.print("Price per hour: ");
			Double pricePerHour = sc.nextDouble();
			
			System.out.print("Price per day: ");
			Double pricePerDay = sc.nextDouble();
			
			System.out.print("Brazil service or USA service (b/u): ");
			char ch = sc.next().toLowerCase().charAt(0);
			sc.nextLine();
			
			RentalService service = null;
			
			if(ch == 'u') {
				service = new RentalService(pricePerDay, pricePerHour, new UsaTaxService());
			}
			else if(ch == 'b') {
				service = new RentalService(pricePerDay, pricePerHour, new BrazilTaxService());
			}
			service.processInvoice(carRental);
			System.out.println("INVOICE: ");
			System.out.println("Pagamento básico: " + String.format("%.2f", carRental.getInvoice().getBasicPayment()));
			System.out.println("Impost: " + String.format("%.2f", carRental.getInvoice().getTax()));
			System.out.println("Pagamento total: " + String.format("%.2f", carRental.getInvoice().getTotalPayment()));
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}
