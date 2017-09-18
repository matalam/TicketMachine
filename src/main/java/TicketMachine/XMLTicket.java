package TicketMachine;

import java.util.concurrent.TimeUnit;

public class XMLTicket
{
    private String type;
    private double price;
    private int amount;
    private long time;
    private String kind;
    private String label;

    public XMLTicket(String type, String price, String unit, String amount, String time, String kind)
    {
        this.type = type;
        this.price = Double.parseDouble(price);
        this.amount = Integer.parseInt(amount);

        if(type.equals(TicketType.TIME.toString()))
        {
            this.time = unit.equals(XMLabels.MINUTE.toString()) ? TimeUnit.MINUTES.toMillis(Integer.parseInt(time)) : TimeUnit.HOURS.toMillis(Integer.parseInt(time));
            this.label = type + "-" + time;
        }
        else
        {
            this.kind = kind;
            this.label = type + "-" + kind;
        }
    }

    public String getType() { return type; }

    public double getPrice() { return price; }

    public int getAmount() { return amount; }

    public long getTime() { return time; }

    public String getKind() { return kind; }

    public String getLabel() { return label; }
}
