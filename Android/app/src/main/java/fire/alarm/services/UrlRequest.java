/**
 * Created by Isreal on 25/09/2017.
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



public class UrlRequest extends AsyncTask<String, String, JSONObject>
{
    private BaseService service;

    public UrlRequest(@NotNull BaseService baseClass) {
        service = baseClass;
    }

    @Override
    protected void onProgressUpdate(String... values)
    {
        super.onProgressUpdate(values);
        if(this.service.getCallback() != null)
        {
            String updateStr = (values.length >= 1)?values[0]:"";
            this.service.getCallback().onProgress(updateStr);
        }
    }

    @Override
    protected void onPostExecute(JSONObject object)
    {
        if(this.service.getCallback() != null)
        {
            Log.d("OnPostExecute", "Success");
            this.service.getCallback().onSuccess(object);
        }
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        if(this.service.getCallback() != null)
        {
            this.service.getCallback().onInit();
        }
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        final int count       = params.length;
        JSONObject jsonObject = null;

        if (count == 1)
        {
            HttpURLConnection connection = null;
            URL url = null;
            StringBuilder response = new StringBuilder();

            try {
                final String targetURL = params[0].toString() + this.service.getParameters().toUrlString();
                Log.e("Inbackground", targetURL);

                url = new URL(targetURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(this.service.getRequestMethod());
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                //create a data outstream object
                DataOutputStream outStream = new DataOutputStream(connection.getOutputStream());
                outStream.flush();
                outStream.close();

                //now get the reponse from the service
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                //read the response text line after lines
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);

                }
                bufferedReader.close();
                Log.e("Response dump ", response.toString());
                final String responseData = response.toString();
                jsonObject = this.parseJsonContext(responseData);

            } catch (Exception err) {
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

}