package TicketMachine;

import java.sql.Time;

public class TimeTicket extends Ticket
{
    private Time timeType;

    public TimeTicket(double price, int amount, Time timeType)
    {
        super();
        if(price > 0 && amount > 0)
        {
            this.price = price;
            this.amount = amount;
            this.timeType = timeType;
            this.concession = false;
        }
        else throw new IllegalArgumentException();
    }

    public TimeTicket(double price, int amount, Time timeType, boolean concession)
    {
        super();
        if(price > 0 && amount > 0)
        {
            this.price = price;
            this.amount = amount;
            this.timeType = timeType;
            this.concession = concession;
        }
        else throw new IllegalArgumentException();
    }

    public Time getTimeType()
    {
        return timeType;
    }

    public String changeMili(long time)
    {
        return (time / 3600000) >= 24 ? (time / 3600000) + " h" : (time / 60000) + " min";
    }

    @Override
    public String toString()
    {
        return "Bilet czasowy " + changeMili(timeType.getTime()) + " - " + price + " PLN - dostępnych: " + amount  + " sztuk.";
    }

    @Override
    public boolean equals(Object object)
    {
        if(this == object)
            return true;

        if (object != null
                && object instanceof TimeTicket
                && (this.timeType.equals(((TimeTicket) object).timeType)))
            return true;

        return false;
    }
}
