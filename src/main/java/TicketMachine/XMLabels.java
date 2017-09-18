package TicketMachine;

public enum XMLabels
{
    TYPE("type"),
    TIME("time"),
    UNIT("unit"),
    PRICE("price"),
    AMOUNT("amount"),
    KIND("kind"),
    VALUE("value"),
    TICKET("ticket"),
    CONCESSION("concession"),
    MINUTE("minute"),
    COIN("coin");

    private final String text;

    private XMLabels(final String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return text;
    }
}
