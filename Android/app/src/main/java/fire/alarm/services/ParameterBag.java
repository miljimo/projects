package fire.alarm.services;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Isreal on 25/09/2017.
 */

public class ParameterBag
{
    private final char  PARAMETER_MAKER     ='?';
    private final char  PAREMETER_SEPERATOR ='&';
    private ArrayList<ValuePair> _paramters;
    private  ValuePair           _service;

    private int _currentPos  ;
    ParameterBag(final String service, final String name)
    {
        _paramters      =   new ArrayList<ValuePair>();
        _service        =   new ValuePair(service, name);
        _currentPos      =   -1;
    }


    /*
      @brief
       The function i used to replace the value at the given key if exists
     */
    private void replace(String key, String value)
    {
        Iterator<ValuePair> iterator = this._paramters.iterator();

        int currentPos =0;
        while(iterator.hasNext())
        {
            currentPos++;
            final ValuePair pair  = iterator.next();
            if(pair.key().equalsIgnoreCase(key) == true)
            {
                this.replace(currentPos, key,  value);
                break;
            }
        }
    }
    public void add(final String key, final String  value)
    {
        if(this.exists(key) == true)
        {
            this.replace(this._currentPos, key, value);
        }
        else
        {
            this._paramters.add(new ValuePair(key,value));
        }
    }

    private void replace(int pos, String key, String value)
    {
        if(!key.isEmpty())
        {
            if ((this._paramters.size() > pos) && (pos >= 0))
            {
                this._paramters.add(pos, new ValuePair(key, value));
            }
        }
    }

    private  boolean exists(@NotNull String key)
    {
        boolean isFound  = false;
        Iterator<ValuePair> iterator = this._paramters.iterator();
        int currentPos  = 0; // used for replace optimization.
        while(iterator.hasNext())
        {
            currentPos++;
            final ValuePair pair  = iterator.next();
            if(pair.key().equalsIgnoreCase(key) == true)
            {
                _currentPos = currentPos;
                isFound     = true;
                break;
            }
        }
        return isFound;
    }

  public void clear()
  {
      _paramters.clear();

  }

  public  final String toUrlString()
    {
        StringBuilder builder        = new StringBuilder();
        String args                  = PARAMETER_MAKER + _service.toString();
        builder.append(args);
        Iterator<ValuePair> iterator = this._paramters.iterator();

        while(iterator.hasNext())
        {
            ValuePair vPair  = iterator.next();
            builder.append(PAREMETER_SEPERATOR + vPair.toString());
        }
        return builder.toString();
    };
}
