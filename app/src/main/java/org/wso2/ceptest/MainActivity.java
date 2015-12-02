package org.wso2.ceptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClickSimpleFilter(View view){
        try {
            SimpleFilterSample.execute(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void buttonClickExtension(View view){
        try {
            ExtensionSample.execute(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void buttonClickFunction(View view){
        try {
            FunctionSample.execute(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void buttonClickPartition(View view){
        try {
            PartitionSample.execute(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
