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

public  class BaseService
{

    private final UrlRequest       asyncRequest;
    private       ParameterBag     parameters;
    private      RequestType       method;
    private       ICallback        callback;

    public enum  RequestType
    {
        POST,
        GET,
        PUT,
        DELETE
    }


     public BaseService(@NotNull final String name)
     {
         parameters    = new ParameterBag("service", name);
         asyncRequest  = new UrlRequest(this);
         callback      = null;
     }


     public String getRequestMethod()
     {
         String requestStr ="";
         switch(this.method)
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

    public final ICallback getCallback()
    {
        return callback;
    }

    public  final ParameterBag getParameters()
     {
        return parameters;
     }


    public  void execute(@NotNull String url, @NotNull final ICallback callback) throws  Exception
    {
        try {
            execute(url, RequestType.POST, callback);
        }catch( Exception err){
            throw err;
        }
    }

    public void execute(@NotNull String url ,RequestType method , final ICallback callback ) throws Exception
    {
        if(this.callback == null) {
            throw new Exception(this.getParameters().toUrlString() + " already sent\n");
        }
        this.callback = callback;
        this.method    = method;
       this.asyncRequest.execute(url);
    }

}
