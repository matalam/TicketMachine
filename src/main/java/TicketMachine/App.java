package TicketMachine;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class App
{
    private Machine ticketMachine;

    public App() throws IOException, SAXException, ParserConfigurationException
    {
        this.ticketMachine = new Machine();
    }

    private void displayTickets()
    {
        System.out.println("Dostępne bilety w automacie: ");
        Ticket[] availableTickets = ticketMachine.getTickets();

        for(int i=0; i < availableTickets.length; ++i)
            if(availableTickets[i].getAmount() > 0)
                System.out.println((i + 1) + ". " + availableTickets[i].toString());
    }

    private void displayCoins()
    {
        Coin[] availableCoins = ticketMachine.getCoins();

        for(int i=0; i < availableCoins.length; ++i)
            if(availableCoins[i].getAmount() > 0)
                System.out.println((i + 1) + ". " + availableCoins[i].toString());
    }

    private void buyTickets(ArrayList<Ticket> orderTickets, int option, int amount, boolean concession) throws ParserConfigurationException, SAXException, IOException
    {
        Ticket[] availableTickets = ticketMachine.getTickets();

        if(option > 0 && option <= availableTickets.length && amount > 0)
        {
            if(availableTickets[option - 1].getAmount() >= amount)
            {
                TicketFactory factory = new TicketFactory();
                orderTickets.add(factory.getTicket(factory.getXmlTickets()[option - 1].getLabel(), amount, concession));
            }
            else
                System.out.println("Zbyt mała liczba biletów w automacie.");
        }
        else
            System.out.println("Podano nieprawidłowy numer biletu.");
    }

    private ArrayList<Ticket> createOrder() throws IOException, SAXException, ParserConfigurationException
    {
        ArrayList<Ticket> order = new ArrayList<Ticket>();
        Scanner scan = new Scanner(System.in);
        boolean end = false;
        displayTickets();

        while(!end)
        {
            System.out.println("Proszę podać numer biletu: ");
            int option = scan.nextInt();

            if(option > 0 && option <= ticketMachine.getTickets().length)
            {
                System.out.println("Proszę podać ilość biletów: ");
                int amount = scan.nextInt();
                System.out.println("Czy bilet ma być ulgowy? \ntak / nie");
                boolean concession = scan.next().equals("tak");

                buyTickets(order, option, amount, concession);

                System.out.println("w - Koniec zamówienia \nDowolny klawisz - Kontynuuj zamawówienie");
                end = scan.next().equals("w");
            }
            else
                System.out.println("Podano zły numer biletu");
        }

        return order;
    }

    private ArrayList<Coin> payOrder(double sumToPay)
    {
        ArrayList<Coin> charge = new ArrayList<Coin>();
        Scanner scan = new Scanner(System.in);
        boolean end = false;
        double sumPayed = 0.0;
        System.out.println("Opłacanie zamówienia - do zapłaty: " + roundSum(sumToPay) + " PLN");
        System.out.println("0. Anulowanie opłaty");
        displayCoins();

        while(!end && sumToPay > sumPayed)
        {
            System.out.println("Proszę podać numer monety");
            int option = scan.nextInt();

            if(option > 0 && option <= ticketMachine.getCoins().length)
            {
                System.out.println("Proszę podać ilość monet");
                int amount = scan.nextInt();
                sumPayed += ticketMachine.getCoins()[option - 1].getValue() * amount;
                Coin newCoin = new Coin(ticketMachine.getCoins()[option - 1].getValue(), amount);

                if(charge.contains(newCoin))
                    charge.get(charge.indexOf(newCoin)).setAmount(charge.get(charge.indexOf(newCoin)).getAmount() + amount);
                else
                    charge.add(newCoin);

                System.out.println("Do tej pory zapłacono: " + sumPayed + " PLN");
            }
            else if(option == 0)
            {
                end = true;
                System.out.println("Zamówienie zostało anulowane");
                charge.clear();
            }
            else
                System.out.println("Podano zły numer monety");

            if(sumPayed >= sumToPay)
                end = true;
        }

        return charge;
    }

    private double chargeOrder(ArrayList<Ticket> order)
    {
        double sum = 0.0;

        for(Ticket elem : order)
            sum += elem.getPrice()
                    * elem.getAmount()
                    * (elem.getConcession() ? 1 - ticketMachine.getConcessionValue() : 1);

        return sum;
    }

    private double chargeCustomer(ArrayList<Coin> charge)
    {
        double sum = 0.0;

        for(Coin elem : charge)
            sum += elem.getValue() * elem.getAmount();

        return sum;
    }

    private double roundSum(double sum)
    {
        return (double) Math.round(sum * ticketMachine.getRATE()) / ticketMachine.getRATE();
    }

    private void start() throws ParserConfigurationException, SAXException, IOException, DeficitOfCoinsException, DeficitOfTicketsException
    {
        Scanner scan = new Scanner(System.in);

        ArrayList<Ticket> order = createOrder();
        double sumOrder = chargeOrder(order);
        ArrayList<Coin> customerCoins = payOrder(sumOrder);
        double sumCustomer = chargeCustomer(customerCoins);

        if(sumOrder <= sumCustomer)
        {
            System.out.println("Czy potwierdzasz zakup biletów? \ntak / nie");
            if(scan.next().equals("tak"))
            {
                double changeCustomer = roundSum(sumCustomer - sumOrder);
                ArrayList<Coin> changeCoins;

                if(changeCustomer > 0)
                {
                    ticketMachine.supplyCoins(customerCoins);
                    changeCoins = ticketMachine.findChangeCoins(changeCustomer);

                    if(changeCoins.isEmpty())
                    {
                        System.out.println("Automat nie może wydać reszty, brak monet");
                        ticketMachine.checkResourceCoins(changeCoins);
                    }
                    else
                    {
                        System.out.println("Wydawanie reszty oraz drukowanie biletów");
                        System.out.println("Reszta: " +  changeCoins);
                        ticketMachine.checkResourceCoins(changeCoins);
                        ticketMachine.sellTickets(order);
                        printTickets(order);
                    }
                }
            }
            else
                System.out.println("Zakup biletów został anulowany, monety zwrócone");
        }
        else
            System.out.println("Brak całej kwoty, monety zwrócone");
    }

    private void printTickets(ArrayList<Ticket> order)
    {
        System.out.println("Bilety:");

        for(Ticket elem : order)
        {
            double price = (elem.getConcession() ? roundSum(elem.getPrice() * (1 - ticketMachine.getConcessionValue())) : elem.getPrice());

            if (elem instanceof SingleTicket)
                System.out.println("Bilet jednorazowy " + (((SingleTicket) elem).getType().equals("SPECIAL") ? "normalny" : "specjalny"  ) + " - " + price + " PLN - " + elem.getAmount() + " sztuk.");
            if (elem instanceof TimeTicket)
                System.out.println("Bilet czasowy " + ((TimeTicket) elem).changeMili(((TimeTicket) elem).getTimeType().getTime()) + " - " + price  + " PLN - " + elem.getAmount()  + " sztuk.");
        }
    }

    public static void main( String[] args ) throws ParserConfigurationException, SAXException, IOException, DeficitOfTicketsException, DeficitOfCoinsException
    {
        App app = new App();
        app.start();
    }
}
