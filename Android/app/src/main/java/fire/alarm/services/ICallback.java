package fire.alarm.services;

import org.json.JSONObject;

/**
 * Created by Isreal on 25/09/2017.
 */

public interface ICallback
{
    void onSuccess(final JSONObject object);
    void onError   (final JSONObject error);
    void onProgress(final String error);
    void onInit();
}