/**
 * Created by Isreal on 08/07/2017.
 */
package fire.alarm.services;

import android.os.AsyncTask;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class BaseService
{
    private final char  PARAMETER_MAKER     ='?';
    private final char  PAREMETER_SEPERATOR ='&';
    private final Request asyncRequest;
    private ArrayList<ValuePair>  paramters;
    private int currentPos;
    public enum  RequestType
    {
        POST,
        GET,
        PUT
    }
    private final ValuePair   serviceUrl;
    private final RequestType requestType;
    private       Callback    callback;

    public interface Callback
    {
        void onSuccess(final JSONObject object);
        void onError   (final JSONObject error);
        void onProgress(final String error);
        void onInitialize();

    }
   public  static class ValuePair
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

        public int getInteger()
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


     public BaseService(@NotNull final String name, RequestType type)
     {
        this.serviceUrl  = new ValuePair("service", name);
        paramters= new ArrayList<ValuePair>();
        requestType = type;
         asyncRequest = new Request(this);
     }
     public BaseService(@NotNull final String name)
     {
         this(name , RequestType.GET);
     }

     public String name()
     {
         return this.serviceUrl.getString();
     }

    /**
     * The method add the request paremeter to the request content.
     * @param name
     * @param value
     */
     public  void addParameter(String name , String value)
     {
        if(this.exists(name) == true)
        {
            this.replace(this.currentPos, name, value);
        }
        else
        {
          this.paramters.add(new ValuePair(name,value));
        }
     }

     private  boolean exists(@NotNull  String key)
     {
         boolean isFound  = false;
         Iterator<ValuePair> iterator = this.paramters.iterator();
         currentPos=0; // used for replace optimization.
         while(iterator.hasNext())
         {
             currentPos++;
             final ValuePair pair  = iterator.next();
             if(pair.key().equalsIgnoreCase(key) == true)
             {
                isFound  = true;
                 break;
             }
         }
      return isFound;
     }

    private void replace(int pos, String key, String value)
    {
        if(!key.isEmpty())
        {
            if ((this.paramters.size() > pos) && (pos >= 0))
            {
                this.paramters.add(pos, new ValuePair(key, value));
            }
        }
    }

    private   String type()
    {
        String requestStr ="";
        switch(this.requestType)
        {
          case POST:
          {
              requestStr ="POST";
          }break;
          case PUT:
          {
              requestStr ="PUT";
          }break;
          default:
              requestStr ="GET";
           break;
        }
        return requestStr;
    }
    /*
      @brief
       The function i used to replace the value at the given key if exists
     */
    private void replace(String key, String value)
    {
        Iterator<ValuePair> iterator = this.paramters.iterator();

        this.currentPos = -1;
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

    /**
     * The function or method return the url data request string
     * @return String
     */
    protected final String data()
    {
        StringBuilder builder = new StringBuilder();
        String args =PARAMETER_MAKER+this.serviceUrl.toString();
        builder.append(args);

        Iterator<ValuePair> iterator = this.paramters.iterator();
        while(iterator.hasNext())
        {
            ValuePair vPair  = iterator.next();
            builder.append(PAREMETER_SEPERATOR+vPair.toString());
        }
        return builder.toString();
    };

    /**
     * The class that talks the outside world
     */
     protected  static class Request extends AsyncTask<String, String, JSONObject>
    {
         private BaseService service;
         public Request(@NotNull BaseService baseClass)
         {
           this.service = baseClass;
         }
        /**
         * The function run the request in the background
         * Therefore no ui blocking.
         * @param params
         * @return
         */
        @Override
        protected JSONObject doInBackground(String... params)
        {

            final int count  =  params.length;
            JSONObject  jsonObject  = null;
            if(count == 1)
            {


                HttpURLConnection connection=null;
                URL url = null;
                StringBuilder response = new StringBuilder();
                try
                {
                    String targetURL = params[0].toString()+ this.service.data();
                    Log.e(this.service.name(), targetURL);
                    //open the connection to the URL
                    url = new URL(targetURL);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod(this.service.type());
                    connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                    connection.setUseCaches (false);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    //create a data outstream object
                    DataOutputStream outStream = new DataOutputStream(connection.getOutputStream());
                    outStream.flush();
                    outStream.close();

                    //now get the reponse from the service
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(inputStream));

                    String line;

                    //read the response text line after lines
                    while((line = bufferedReader.readLine()) != null)
                    {
                        response.append(line);

                    }
                    bufferedReader.close();
                    Log.e("Response dump " , response.toString());
                    final String responseData  =  response.toString();
                    jsonObject =  this.parseJsonContext(responseData);

                }
                catch(Exception err)
                {
                    this.publishProgress(err.getMessage());
                    this.cancel(true);
                    Log.e("@BaseService", err.getMessage());
                }
            }
            return jsonObject;
        }


        private JSONObject parseJsonContext(final String context)
        {
            Log.d("@BaseService", "Parsing result to json context");
            JSONObject jsonObject = null;
            try
            {
              jsonObject  = new JSONObject(context);
              Log.d("@Parse", "Success");
            }
            catch(JSONException err)
            {
              this.publishProgress(err.getMessage());
              this.cancel(true);
              Log.d("@ParseError",err.getMessage());
            }
            return jsonObject;
        }

        /**
         * The method is used to talk back to the ui on what is going on.
         * @param values
         */
        @Override
        protected void onProgressUpdate(String... values)
        {
            super.onProgressUpdate(values);
            if(this.service.callback != null)
            {
                String updateStr = (values.length >= 1)?values[0]:"";
                this.service.callback.onProgress(updateStr);
            }
        }

        /**
         * The function is called when the services as finished
         * and required view update
         *
         * @param object
         */
        @Override
        protected void onPostExecute(JSONObject object)
        {
            if(this.service.callback != null)
            {
               Log.d("OnPostExecute", "Success");
               this.service.callback.onSuccess(object);
            }
        }
        /**
         * The function is called immediately when
         * the back service is started
         */
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            if(this.service.callback != null)
            {
               this.service.callback.onInitialize();
            }
        }
    }
    public  void execute(@NotNull String url, @NotNull final Callback callback)
    {
        Log.d("Execute", url);
        if( (callback == null) || (url.isEmpty()== true))
        {
            throw new NullPointerException();
        }
        this.callback = callback;

        if(this.onExecute(url, callback) == true)
        {
           Log.d("Sending request", url);
          this.asyncRequest.execute(url);
        }
    }
    /*
    @brief
        The method is used to run the services
    */
    protected  abstract boolean onExecute(final String  url, final Callback callback);
}
