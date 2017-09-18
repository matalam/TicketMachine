package TicketMachine;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;

public class AppTest
{
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void testFindChangeCoinsOne() throws IOException, SAXException, ParserConfigurationException
    {
        Machine test = new Machine();
        ArrayList<Coin> testList = new ArrayList<Coin>();
        assertEquals(testList,test.findChangeCoins(0));
        testList.add(new Coin(1, 1));
        assertEquals(testList, test.findChangeCoins(1));
    }

    @Test
    public void testFindChangeCoinsTwo() throws IOException, SAXException, ParserConfigurationException
    {
        Machine test = new Machine();
        ArrayList<Coin> testList = new ArrayList<Coin>();
        testList.add(new Coin(2, 1));
        assertEquals(testList, test.findChangeCoins(2));
    }

    @Test
    public void testFindChangeCoinsThree() throws IOException, SAXException, ParserConfigurationException
    {
        Machine test = new Machine();
        ArrayList<Coin> testList = new ArrayList<Coin>();
        testList.add(new Coin(1, 1));
        testList.add(new Coin(2, 1));
        assertEquals(testList, test.findChangeCoins(3));
    }

    @Test
    public void testFindChangeCoinsFive() throws IOException, SAXException, ParserConfigurationException
    {
        Machine test = new Machine();
        ArrayList<Coin> testList = new ArrayList<Coin>();
        testList.add(new Coin(5, 1));
        assertEquals(testList, test.findChangeCoins(5));
    }

    @Test
    public void testFindChangeCoinsFiveDotSeven() throws IOException, SAXException, ParserConfigurationException
    {
        Machine test = new Machine();
        ArrayList<Coin> testList = new ArrayList<Coin>();
        testList.add(new Coin(0.2, 1));
        testList.add(new Coin(0.5, 1));
        testList.add(new Coin(5, 1));
        assertEquals(testList, test.findChangeCoins(5.7));
    }

    @Test
    public void testFindChangeCoinsSevenDotNine() throws IOException, SAXException, ParserConfigurationException
    {
        Machine test = new Machine();
        ArrayList<Coin> testList = new ArrayList<Coin>();
        testList.add(new Coin(0.2, 2));
        testList.add(new Coin(0.5, 1));
        testList.add(new Coin(2, 1));
        testList.add(new Coin(5, 1));
        assertEquals(testList, test.findChangeCoins(7.9));
    }

    @Test
    public void testFindChangeCoinsTwoDotEleven() throws IOException, SAXException, ParserConfigurationException
    {
        Machine test = new Machine();
        ArrayList<Coin> testList = new ArrayList<Coin>();
        assertEquals(testList, test.findChangeCoins(2.11));
    }

    @Test
    public void testFindChangeCoinsSevenDotSixteen() throws IOException, SAXException, ParserConfigurationException
    {
        Machine test = new Machine();
        ArrayList<Coin> testList = new ArrayList<Coin>();
        assertEquals(testList, test.findChangeCoins(7.17));
    }

    @Test
    public void testFindChangeCoinsMinus() throws IOException, SAXException, ParserConfigurationException
    {
        thrown.expect(IllegalArgumentException.class);
        Machine test = new Machine();
        test.findChangeCoins(-1);
    }

    @Test
    public void testCheckResourceCoins() throws IOException, SAXException, ParserConfigurationException, DeficitOfCoinsException
    {
        Machine test = new Machine();
        ArrayList<Coin> testList = new ArrayList<Coin>();
        Coin[] testArray = copyArray(test.getCoins());
        test.checkResourceCoins(testList);
        assertArrayEquals(test.getCoins(), testArray);
    }

    @Test
    public void testCheckResourceCoinsOne() throws IOException, SAXException, ParserConfigurationException, DeficitOfCoinsException
    {
        Machine test = new Machine();
        ArrayList<Coin> testList = new ArrayList<Coin>();
        Coin[] testArray = copyArray(test.getCoins());
        Coin temp = new Coin(1, 1);
        testList.add(temp);
        test.checkResourceCoins(testList);
        assertTrue(test.getCoins()[findIndexCoin(testArray, temp)].getAmount() < testArray[findIndexCoin(testArray, temp)].getAmount());
    }

    @Test
    public void testCheckResourceCoinsFive() throws IOException, SAXException, ParserConfigurationException, DeficitOfCoinsException
    {
        Machine test = new Machine();
        ArrayList<Coin> testList = new ArrayList<Coin>();
        Coin[] testArray = copyArray(test.getCoins());
        Coin temp = new Coin(5, 4);
        testList.add(temp);
        test.checkResourceCoins(testList);
        assertTrue(test.getCoins()[findIndexCoin(testArray, temp)].getAmount() < testArray[findIndexCoin(testArray, temp)].getAmount());
    }

    @Test
    public void testCheckResourceOverResource() throws IOException, SAXException, ParserConfigurationException, DeficitOfCoinsException
    {
        thrown.expect(DeficitOfCoinsException.class);
        Machine test = new Machine();
        ArrayList<Coin> testList = new ArrayList<Coin>();
        testList.add(new Coin(1, 15));
        test.checkResourceCoins(testList);
    }

    @Test
    public void testCheckResourceBadCoin() throws IOException, SAXException, ParserConfigurationException, DeficitOfCoinsException
    {
        thrown.expect(IllegalArgumentException.class);
        Machine test = new Machine();
        ArrayList<Coin> testList = new ArrayList<Coin>();
        testList.add(new Coin(-10, 15));
        test.checkResourceCoins(testList);
    }

    @Test
    public void testSupplyCoinsOne() throws IOException, SAXException, ParserConfigurationException, DeficitOfCoinsException
    {
        Machine test = new Machine();
        ArrayList<Coin> testList = new ArrayList<Coin>();
        Coin[] testArray = copyArray(test.getCoins());
        Coin temp = new Coin(1, 10);
        testList.add(temp);
        test.supplyCoins(testList);
        assertTrue(test.getCoins()[findIndexCoin(testArray, temp)].getAmount() > testArray[findIndexCoin(testArray, temp)].getAmount());
    }

    @Test
    public void testSupplyCoinsZeroDotOne() throws IOException, SAXException, ParserConfigurationException, DeficitOfCoinsException
    {
        Machine test = new Machine();
        ArrayList<Coin> testList = new ArrayList<Coin>();
        Coin[] testArray = copyArray(test.getCoins());
        Coin temp = new Coin(0.1, 30);
        testList.add(temp);
        test.supplyCoins(testList);
        assertTrue(test.getCoins()[findIndexCoin(testArray, temp)].getAmount() > testArray[findIndexCoin(testArray, temp)].getAmount());
    }

    @Test
    public void testSupplyCoinsOneZeroAmount() throws IOException, SAXException, ParserConfigurationException, DeficitOfCoinsException
    {
        Machine test = new Machine();
        ArrayList<Coin> testList = new ArrayList<Coin>();
        testList.add(new Coin(1, 0));
        test.supplyCoins(testList);
    }

    @Test
    public void testSupplyCoinsOneMinusAmount() throws IOException, SAXException, ParserConfigurationException, DeficitOfCoinsException
    {
        thrown.expect(IllegalArgumentException.class);
        Machine test = new Machine();
        ArrayList<Coin> testList = new ArrayList<Coin>();
        testList.add(new Coin(1, -1));
        test.supplyCoins(testList);
    }

    @Test
    public void testSellTickets() throws IOException, SAXException, ParserConfigurationException, DeficitOfTicketsException
    {
        Machine test = new Machine();
        ArrayList<Ticket> testList = new ArrayList<Ticket>();
        Ticket[] testArray = copyArray(test.getTickets());
        test.sellTickets(testList);
        assertArrayEquals(test.getTickets(), testArray);
    }

    @Test
    public void testSellTicketsSingleSpecial() throws IOException, SAXException, ParserConfigurationException, DeficitOfTicketsException
    {
        Machine test = new Machine();
        ArrayList<Ticket> testList = new ArrayList<Ticket>();
        Ticket[] testArray = copyArray(test.getTickets());
        Ticket temp = new SingleTicket(3.2, 4, "SPECIAL");
        testList.add(temp);
        test.sellTickets(testList);
        assertTrue(test.getTickets()[findIndexTicket(testArray, temp)].getAmount() < testArray[findIndexTicket(testArray, temp)].getAmount());
    }

    @Test
    public void testSellTicketsTime() throws IOException, SAXException, ParserConfigurationException, DeficitOfTicketsException
    {
        Machine test = new Machine();
        ArrayList<Ticket> testList = new ArrayList<Ticket>();
        Ticket[] testArray = copyArray(test.getTickets());
        Ticket temp = new TimeTicket(4.5, 4, new Time(3600000));
        testList.add(temp);
        test.sellTickets(testList);
        assertTrue(test.getTickets()[findIndexTicket(testArray, temp)].getAmount() < testArray[findIndexTicket(testArray, temp)].getAmount());
    }

    @Test
    public void testSellTicketsOverResource() throws IOException, SAXException, ParserConfigurationException, DeficitOfTicketsException
    {
        thrown.expect(DeficitOfTicketsException.class);
        Machine test = new Machine();
        ArrayList<Ticket> testList = new ArrayList<Ticket>();
        testList.add(new TimeTicket(4.5, 15, new Time(3600000)));
        test.sellTickets(testList);
    }

    @Test
    public void testSellTicketsMinusPrice() throws IOException, SAXException, ParserConfigurationException, DeficitOfTicketsException
    {
        thrown.expect(IllegalArgumentException.class);
        Machine test = new Machine();
        ArrayList<Ticket> testList = new ArrayList<Ticket>();
        testList.add(new TimeTicket(-3, 2, new Time(3600000)));
        test.sellTickets(testList);
    }

    @Test
    public void testSellTicketsMinusAmount() throws IOException, SAXException, ParserConfigurationException, DeficitOfTicketsException
    {
        thrown.expect(IllegalArgumentException.class);
        Machine test = new Machine();
        ArrayList<Ticket> testList = new ArrayList<Ticket>();
        testList.add(new TimeTicket(4.5, -10, new Time(3600000)));
        test.sellTickets(testList);
    }

    private static Coin[] copyArray(Coin[] toCopy)
    {
        Coin [] newArray = new Coin[toCopy.length];

        for(int i=0; i < toCopy.length; ++i)
            newArray[i] = new Coin(toCopy[i].getValue(),toCopy[i].getAmount());

        return newArray;
    }

    private static Ticket[] copyArray(Ticket[] toCopy)
    {
        Ticket[] newArray = new Ticket[toCopy.length];

        for(int i=0; i < toCopy.length; ++i)
        {
            if (toCopy[i] instanceof SingleTicket)
                newArray[i] = new SingleTicket(toCopy[i].getPrice(), toCopy[i].getAmount(), ((SingleTicket)toCopy[i]).getType(), toCopy[i].getConcession());
            if (toCopy[i] instanceof TimeTicket)
                newArray[i] = new TimeTicket(toCopy[i].getPrice(), toCopy[i].getAmount(), ((TimeTicket)toCopy[i]).getTimeType(), toCopy[i].getConcession());
        }

        return newArray;
    }

    private static int findIndexCoin(Coin[] array, Coin find)
    {
        int index = -1;

        for(int i=0; index < 0 && i < array.length; ++i)
            if(array[i].equals(find))
                index = i;

        return index;
    }

    private static int findIndexTicket(Ticket[] array, Ticket find)
    {
        int index = -1;

        for(int i=0; index < 0 && i < array.length; ++i)
            if(array[i].equals(find))
                index = i;

        return index;
    }
}