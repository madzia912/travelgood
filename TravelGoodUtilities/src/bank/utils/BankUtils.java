package bank.utils;

import bank.unsecure.ws.AccountType;
import bank.unsecure.ws.BankPortType;
import bank.unsecure.ws.BankService;
import bank.unsecure.ws.CreditCardFaultMessage;
import bank.unsecure.ws.CreditCardInfoType;
import bank.unsecure.ws.ExpirationDateType;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class BankUtils {
    
    private static final int GROUP = 1;
    
    private BankUtils() {
        
    }
    
    public static boolean chargeCreditCard(int amount, String name, int expMonth, int expYear, String creditCardNumber, String accountName, String accountNumber) throws CreditCardFaultMessage {
        CreditCardInfoType creditCardInfo = new CreditCardInfoType();
        creditCardInfo.setName(name);
        creditCardInfo.setNumber(creditCardNumber);
        ExpirationDateType expirationDate = new ExpirationDateType();
        expirationDate.setMonth(expMonth);
        expirationDate.setYear(expYear);
        creditCardInfo.setExpirationDate(expirationDate);
        
        AccountType account = new AccountType();
        account.setName(accountName);
        account.setNumber(accountNumber);
        
        BankService service = new BankService();
        BankPortType port = service.getBankPort();
        return port.chargeCreditCard(GROUP, creditCardInfo, amount, account);
    }
    
    
    
    public static boolean refundCreditCard(int amount, String name, int expMonth, int expYear, String creditCardNumber, String accountName, String accountNumber) throws CreditCardFaultMessage {
        CreditCardInfoType creditCardInfo = new CreditCardInfoType();
        creditCardInfo.setName(name);
        creditCardInfo.setNumber(creditCardNumber);
        ExpirationDateType expirationDate = new ExpirationDateType();
        expirationDate.setMonth(expMonth);
        expirationDate.setYear(expYear);
        creditCardInfo.setExpirationDate(expirationDate);
        
        AccountType account = new AccountType();
        account.setName(accountName);
        account.setNumber(accountNumber);
        
        BankService service = new BankService();
        BankPortType port = service.getBankPort();
        return port.refundCreditCard(GROUP, creditCardInfo, amount, account);
    }

    public static boolean validateCreditCard(int amount, String name, int expMonth, int expYear, String creditCardNumber) throws CreditCardFaultMessage {
        CreditCardInfoType creditCardInfo = new CreditCardInfoType();
        creditCardInfo.setName(name);
        creditCardInfo.setNumber(creditCardNumber);
        ExpirationDateType expirationDate = new ExpirationDateType();
        expirationDate.setMonth(expMonth);
        expirationDate.setYear(expYear);
        creditCardInfo.setExpirationDate(expirationDate);
        
        BankService service = new BankService();
        BankPortType port = service.getBankPort();
        return port.validateCreditCard(GROUP, creditCardInfo, amount);
    }
    
}
