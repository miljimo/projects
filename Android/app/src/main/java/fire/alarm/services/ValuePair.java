package fire.alarm.services;

/**
 * Created by Isreal on 25/09/2017.
 */
public  class ValuePair
{
    private String key;
    private String value;

    public ValuePair(String  key, String value)
    {
        this.key    = key;
        this.value  = value;
    }

    public String getString()
    {
        return this.value;
    }

    public int   getInteger()
    {
        return Integer.parseInt(this.value);
    }
    public float getFloat()
    {
        return Float.parseFloat(this.value);
    }
    public boolean getBoolean()
    {
        return Boolean.parseBoolean(this.value);
    }
    public String key()
    {
        return this.key;
    }

    public final String toString()
    {
        return  this.key+"="+this.value;
    }

}
