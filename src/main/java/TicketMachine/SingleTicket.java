package TicketMachine;

public class SingleTicket extends Ticket
{
    private String type;

    public SingleTicket(double price, int amount, String type)
    {
        super();
        if(price > 0 && amount > 0)
        {
            this.price = price;
            this.amount = amount;
            this.type = type;
            this.concession = false;
        }
        else throw new IllegalArgumentException();
    }

    public SingleTicket(double price, int amount, String type, boolean concession)
    {
        super();
        if(price > 0 && amount > 0)
        {
            this.price = price;
            this.amount = amount;
            this.type = type;
            this.concession = concession;
        }
        else throw new IllegalArgumentException();
    }

    public String getType() { return type; }

    @Override
    public String toString()
    {
        return "Bilet jednorazowy " + (type.equals("SPECIAL") ? "normalny" : "specjalny"  ) + " - " + price + " PLN - dostępnych: " + amount + " sztuk.";
    }

    @Override
    public boolean equals(Object object)
    {
        if(this == object)
            return true;

        if (object != null
                && object instanceof SingleTicket
                && (this.type.equals(((SingleTicket) object).type)))
            return true;

        return false;
    }
}
