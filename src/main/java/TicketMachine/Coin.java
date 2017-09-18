package TicketMachine;

public class Coin
{
    private double value;
    private int amount;

    public Coin(double value, int amount)
    {
        if(value > 0 && amount >= 0)
        {
            this.value = value;
            this.amount = amount;
        }
        else throw new IllegalArgumentException();
    }

    public double getValue() { return value; }

    public int getAmount() { return amount; }

    public void setValue(double value)
    {
        if(value > 0)
            this.value = value;
        else
            throw new IllegalArgumentException();
    }

    public void setAmount(int amount)
    {
        if(amount >= 0)
            this.amount = amount;
        else
            throw new IllegalArgumentException();
    }

    @Override
    public String toString()
    {
        return value + " PLN - ilość: " + amount;
    }

    @Override
    public boolean equals(Object object)
    {
        if(this == object)
            return true;

        if(object != null
                && object instanceof Coin
                && (this.value == ((Coin) object).value))
            return true;

        return false;
    }
}
