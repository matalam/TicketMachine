package TicketMachine;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Machine
{
    private final int RATE = 100;

    private double concessionValue;
    private TicketFactory factoryTick;
    private XMLoader loader;
    private Coin[] coins;
    private Ticket[] tickets;

    public Machine() throws ParserConfigurationException, SAXException, IOException
    {
        factoryTick = new TicketFactory();
        loader = new XMLoader();

        concessionValue = loader.getConcessionValue();
        tickets = new Ticket[loader.getXmlTickets().length];
        coins = loader.getXmlCoins();

        for(int i=0; i < tickets.length; ++i)
        {
            String label = loader.getXmlTickets()[i].getLabel();
            tickets[i] = factoryTick.getTicket(label);
        }
    }

    public Coin[] getCoins() { return coins; }

    public Ticket[] getTickets() { return tickets; }

    public double getConcessionValue() { return concessionValue; }

    public int getRATE() { return RATE; }

    private int[] findFirstUsedCoin(double amount)
    {
        if(amount < 0)
            throw new IllegalArgumentException();

        amount *= RATE;
        int[] valuesCoins = new int[coins.length];
        int[] minNumbCoins = new int[(int) amount + 1];
        int[] usedCoin = new int[(int) amount + 1];

        for(int i=0; i < coins.length; ++i)
            valuesCoins[i] = (int) (coins[i].getValue() * RATE);

        for(int currAmount=1; currAmount <= (int) amount; ++currAmount)
        {
            minNumbCoins[currAmount] = Integer.MAX_VALUE;

            for(int currCoin=0; currCoin < coins.length; ++currCoin)
            {
                if(currAmount >= valuesCoins[currCoin]
                    && (Integer.MAX_VALUE > minNumbCoins[currAmount - valuesCoins[currCoin]]))
                {
                    if(minNumbCoins[currAmount] > 1 + minNumbCoins[currAmount - valuesCoins[currCoin]])
                    {
                        minNumbCoins[currAmount] = minNumbCoins[currAmount - valuesCoins[currCoin]] + 1;
                        usedCoin[currAmount] = valuesCoins[currCoin];
                    }
                }
            }
        }

        return usedCoin;
    }

    public ArrayList<Coin> findChangeCoins(double amount)
    {
        if(amount < 0)
            throw new IllegalArgumentException();

        ArrayList<Coin> changeCoins = new ArrayList<Coin>();
        int[] usedCoin = findFirstUsedCoin(amount);

        if(usedCoin[usedCoin.length - 1] > 0)
            for(int i = usedCoin.length - 1; i > 0; i -= usedCoin[i])
            {
                Coin findCoin = new Coin((double) usedCoin[i] / RATE, 1);

                if(changeCoins.contains(findCoin))
                {
                    Coin temp = changeCoins.get(changeCoins.indexOf(findCoin));
                    temp.setAmount(temp.getAmount() + 1);
                }
                else
                    changeCoins.add(findCoin);
            }

        return changeCoins;
    }

    public void checkResourceCoins(ArrayList<Coin> changeCoins) throws DeficitOfCoinsException
    {
        if(changeCoins != null)
            for(int i=0; i < coins.length; ++i)
            {
                if(changeCoins.contains(coins[i]))
                {
                    int amountCoins = changeCoins.get(changeCoins.indexOf(coins[i])).getAmount();
                    if(coins[i].getAmount() >= amountCoins)
                        coins[i].setAmount(coins[i].getAmount() - amountCoins);
                    else
                        throw new DeficitOfCoinsException();
                }
            }
    }

    public void sellTickets(ArrayList<Ticket> neededTick) throws DeficitOfTicketsException
    {
        if(neededTick != null)
            for(int i=0; i < tickets.length; ++i)
            {
                if(neededTick.contains(tickets[i]))
                {
                    int amountTickets = neededTick.get(neededTick.indexOf(tickets[i])).getAmount();
                    if(tickets[i].getAmount() >= amountTickets)
                        tickets[i].setAmount(tickets[i].getAmount() - amountTickets);
                    else
                        throw new DeficitOfTicketsException();
                }
            }
    }

    public void supplyCoins(ArrayList<Coin> newCoins)
    {
        if(newCoins != null)
            for(int i=0; i < coins.length; ++i)
                if(newCoins.contains(coins[i]))
                    coins[i].setAmount(coins[i].getAmount() + newCoins.get(newCoins.indexOf(coins[i])).getAmount());
    }
}
