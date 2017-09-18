package TicketMachine;

public abstract class Ticket
{
    protected double price;
    protected int amount;
    protected boolean concession;

    public void setPrice(double price)
    {
        if(price > 0)
            this.price = price;
        else throw new IllegalArgumentException();
    }
    public void setAmount(int amount)
    {
        if(amount >= 0)
            this.amount = amount;
        else throw new IllegalArgumentException();
    }

    public void setConcession(boolean concession)
    {
        this.concession = concession;
    }

    public double getPrice() { return price; }
    public int getAmount() { return amount; }
    public boolean getConcession() { return concession; }

    public abstract String toString();
}
