package TicketMachine;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Time;

public class TicketFactory
{
    private XMLTicket[] xmlTickets;

    public TicketFactory() throws IOException, SAXException, ParserConfigurationException
    {
        XMLoader loader = new XMLoader();
        xmlTickets = loader.getXmlTickets();
    }

    public XMLTicket[] getXmlTickets() { return xmlTickets; }

    public Ticket getTicket(String ticketType, int amount, boolean concession)
    {
        XMLTicket tempTicket = findTicket(ticketType);

        if(ticketType == null)
            return null;

        if(tempTicket.getType().equals(TicketType.TIME.toString()))
            return new TimeTicket(tempTicket.getPrice(), amount, new Time(tempTicket.getTime()), concession);

        if(tempTicket.getType().equals(TicketType.SINGLE.toString()))
            return new SingleTicket(tempTicket.getPrice(), amount, tempTicket.getKind(), concession);

        return null;
    }

    public Ticket getTicket(String ticketType)
    {
        XMLTicket tempTicket = findTicket(ticketType);

        if(ticketType == null)
            return null;

        if(tempTicket.getType().equals(TicketType.TIME.toString()))
            return new TimeTicket(tempTicket.getPrice(), tempTicket.getAmount(), new Time(tempTicket.getTime()));

        if(tempTicket.getType().equals(TicketType.SINGLE.toString()))
            return new SingleTicket(tempTicket.getPrice(), tempTicket.getAmount(), tempTicket.getKind());

        return null;
    }

    private XMLTicket findTicket(String ticketType)
    {
        for(XMLTicket elem : xmlTickets)
            if(elem.getLabel().equals(ticketType))
                return elem;

        return null;
    }
}
