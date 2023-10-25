package mindhub.HomebankingSprings;

import mindhub.HomebankingSprings.models.*;
import mindhub.HomebankingSprings.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static mindhub.HomebankingSprings.models.TransactionType.*;

@SpringBootApplication
public class HomebankingSpringsApplication {
	@Autowired
	private PasswordEncoder passwordEnconder;
	public static void main(String[] args)
	{
		SpringApplication.run(HomebankingSpringsApplication.class, args);
	}
	@Bean
		public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository,CardRepository cardRepository){

		return args -> {
			LocalDateTime dateTime = LocalDateTime.now();
			Client melba = new Client("Melba", "Morel", "melba@mindhub.com", passwordEnconder.encode("123"));
			clientRepository.save(melba);

			Account account = new Account("VIN001", LocalDate.now(),5000.00);
			melba.addAccount(account);
			accountRepository.save(account);

			LocalDate date = LocalDate.now();
			Account account1 = new Account("VIN002",date.plusDays(1), 7500.00);
			melba.addAccount(account1);
			accountRepository.save(account1);

			Transaction Primer = new Transaction(CREDIT, "Pay Taxes" , dateTime, 455333.33);
			account1.addTransaction(Primer);
			transactionRepository.save(Primer);

			Transaction Second = new Transaction(DEBIT, "Buy cream and beer" , dateTime, -45.33);
			account1.addTransaction(Second);
			transactionRepository.save(Second);

			Transaction third = new Transaction(CREDIT, "Pay Taxis" , dateTime, 455333.1);
			account.addTransaction(third);
			transactionRepository.save(third);

			Loan loanMortgage = new Loan("Mortgage",500000.00, List.of(12,24,36,48,60));
			loanRepository.save(loanMortgage);
			Loan loanPersonal = new Loan("Personal", 10000.00, List.of( 6,12,24));
			loanRepository.save(loanPersonal);
			Loan loanAutomation = new Loan("Automation", 300000.00, List.of(6,12,24,36));
			loanRepository.save(loanAutomation);

			ClientLoan clientLoanOne = new ClientLoan(400000.00,60);
			clientLoanRepository.save(clientLoanOne);
			melba.addClientLoan(clientLoanOne);

			loanMortgage.addClientLoan(clientLoanOne);
			clientLoanRepository.save(clientLoanOne);


			ClientLoan clientLoanTwo = new ClientLoan(50000.00,12);
			clientLoanRepository.save(clientLoanTwo);
			melba.addClientLoan(clientLoanTwo);

			loanPersonal.addClientLoan(clientLoanTwo);
			clientLoanRepository.save(clientLoanTwo);


			Client client1 = new Client("jennys", "guzman", "jennys@creditbank.com", passwordEnconder.encode("1234"));
			clientRepository.save(client1);

			Account account2 = new Account("Vin003" , date, 8000.00);
			client1.addAccount(account2);
			accountRepository.save(account2);

			ClientLoan clientLoantree = new  ClientLoan (100.00, 24);
			clientLoanRepository.save(clientLoantree);
			melba.addClientLoan(clientLoantree);

			loanPersonal.addClientLoan(clientLoantree);
			clientLoanRepository.save(clientLoantree);

			ClientLoan clientLoanFoor = new ClientLoan(200.000, 36);
			clientLoanRepository.save(clientLoanFoor);
			melba.addClientLoan(clientLoanFoor);

			loanAutomation.addClientLoan(clientLoanFoor);
			clientLoanRepository.save(clientLoanFoor);

			Card card1 = new Card("Melba Morel","1785-4354-3742-4332","123",LocalDate.now(),LocalDate.now().plusYears(5), CardType.DEBIT,CardColor.GOLD);
			Card card2 = new Card("Melba Morel","8654-4377-3732-9832","654",LocalDate.now(),LocalDate.now().plusYears(5), CardType.CREDIT,CardColor.TITANIUM);
			Card card3 = new Card("Jennys Guzman","7756-4354-3562-6545","976",LocalDate.now(),LocalDate.now().plusYears(5), CardType.CREDIT,CardColor.SILVER);

			melba.addCard(card1);
			melba.addCard(card2);
			client1.addCard(card3);
			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
		};

		}

}
