package TicketMachine;

public enum TicketType
{
    TIME("TIME"),
    SINGLE("SINGLE");

    private final String text;

    private TicketType(final String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return text;
    }
}
