package TicketMachine;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLoader
{
    private final String XML_PATH = "src\\main\\java\\TicketMachine\\prices.xml";
    private double concessionValue;
    private XMLTicket[] xmlTickets;
    private Coin[] xmlCoins;

    public XMLoader() throws ParserConfigurationException, IOException, SAXException
    {
        File inputFile = new File(XML_PATH);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputFile);
        doc.getDocumentElement().normalize();

        NodeList listOfNodes = doc.getElementsByTagName(XMLabels.TICKET.toString());
        xmlTickets = new XMLTicket[listOfNodes.getLength()];

        for(int i=0; i < listOfNodes.getLength(); ++i)
        {
            Node current = listOfNodes.item(i);

            if(current.getNodeType() == Node.ELEMENT_NODE)
            {
                Element elem = (Element) current;
                String currType = elem.getElementsByTagName(XMLabels.TYPE.toString()).item(0).getTextContent();

                xmlTickets[i] = new XMLTicket(
                        currType,
                        elem.getElementsByTagName(XMLabels.PRICE.toString()).item(0).getTextContent(),
                        currType.equals(TicketType.TIME.toString()) ? elem.getElementsByTagName(XMLabels.UNIT.toString()).item(0).getTextContent() : null,
                        elem.getElementsByTagName(XMLabels.AMOUNT.toString()).item(0).getTextContent(),
                        currType.equals(TicketType.TIME.toString()) ? elem.getElementsByTagName(XMLabels.TIME.toString()).item(0).getTextContent() : null,
                        currType.equals(TicketType.SINGLE.toString()) ? elem.getElementsByTagName(XMLabels.KIND.toString()).item(0).getTextContent() : null
                );
            }
        }

        listOfNodes = doc.getElementsByTagName(XMLabels.COIN.toString());
        xmlCoins = new Coin[listOfNodes.getLength()];

        for(int i=0; i < listOfNodes.getLength(); ++i)
        {
            Node current = listOfNodes.item(i);

            if(current.getNodeType() == Node.ELEMENT_NODE)
            {
                Element elem = (Element) current;

                xmlCoins[i] = new Coin(
                        Double.parseDouble(elem.getElementsByTagName(XMLabels.VALUE.toString()).item(0).getTextContent()),
                        Integer.parseInt(elem.getElementsByTagName(XMLabels.AMOUNT.toString()).item(0).getTextContent())
                );
            }
        }

        listOfNodes = doc.getElementsByTagName(XMLabels.CONCESSION.toString());
        Element current = (Element) listOfNodes.item(0);
        concessionValue = Double.parseDouble(current.getElementsByTagName(XMLabels.VALUE.toString()).item(0).getTextContent());
    }

    public double getConcessionValue()
    {
        return concessionValue;
    }

    public XMLTicket[] getXmlTickets()
    {
        return xmlTickets;
    }

    public Coin[] getXmlCoins()
    {
        return xmlCoins;
    }
}
