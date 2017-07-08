package system.alarm.fire.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import fire.alarm.services.BaseService;


public class MainActivity extends AppCompatActivity {

   private BaseService service ;

    private Button btnSend;
    private TextView lblFeed;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        btnSend  = (Button) findViewById(R.id.btnSendRequest);
        lblFeed  = (TextView)findViewById(R.id.lblFeed);

        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                  service  = new BaseService("getFireStatus")
                  {
                      @Override
                      protected boolean onExecute(String url, Callback callback)
                      {
                          lblFeed.setText("About to execute");
                          return true;
                      }
                  };
                  service.addParameter("code", "8983489139");
                  service.execute("http://192.168.0.15/firealarm/", new BaseService.Callback() {
                      @Override
                      public void onSuccess(JSONObject object)
                      {
                          lblFeed.setText("Successful");
                      }

                      @Override
                      public void onError(JSONObject error)
                      {
                          lblFeed.setText("Json Error");
                      }

                      @Override
                      public void onProgress(String error)
                      {
                          lblFeed.setText("progress error : "+error);
                      }

                      @Override
                      public void onInitialize()
                      {
                          lblFeed.setText("Initialised");
                      }
                  });

            }
        });
    }
}
