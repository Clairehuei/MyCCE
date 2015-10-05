package com.codekitchen.allen.mycce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Activity_Async extends AppCompatActivity {

    @InjectView(R.id.dwlButton)
    Button button;

    @InjectView(R.id.dwlImageView)
    ImageView imageView;

    private ProgressDialog progressDialog;

    private final String IMAGE_PATH = "http://www.spwallpapers.com/var/albums/1080x1920/Samsung%20Galaxy%20S4%20cityscape%20wallpapers%201080x1920/Samsung%20Galaxy%20S4%20cityscape%20wallpapers%201080x1920%20(17).jpg?m=1385447723";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__async);
        ButterKnife.inject(this);

        //    彈出要給ProgressDialog
        progressDialog = new ProgressDialog(Activity_Async.this);
        progressDialog.setTitle("提示訊息");
        progressDialog.setMessage("正在下載中，請稍後......");

        //    設置setCancelable(false); 表示我們不能取消這個彈出框，等下載完成之後再讓彈出框消失
        progressDialog.setCancelable(false);

        //    設置ProgressDialog樣式為圓圈的形式
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        //    設置ProgressDialog樣式為水平的樣式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }


    @OnClick(R.id.dwlButton)
    void dwlButtonClick() {
        // 在UI Thread當中實例化AsyncTask對象，並調用execute方法
        new MyAsyncTask().execute(IMAGE_PATH);
    }


    /**
     * 定義一個類，讓其繼承AsyncTask這個類
     * Params: String類型，表示傳遞給異步任務的參數類型是String，通常指定的是URL路徑
     * Progress: Integer類型，進度條的單位通常都是Integer類型
     * Result：byte[]類型，表示我們下載好的圖片以字節數組返回
     * @author xiaoluo
     *
     */
    public class MyAsyncTask extends AsyncTask<String, Integer, byte[]>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //    在onPreExecute()中我們讓ProgressDialog顯示出來
            progressDialog.show();
        }
        @Override
        protected byte[] doInBackground(String... params)
        {
            //    通過Apache的HttpClient來訪問請求網絡中的一張圖片
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(params[0]);
            byte[] image = new byte[]{};
            try
            {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = null;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                if(httpEntity != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                {
                    //    得到文件的總長度
                    long file_length = httpEntity.getContentLength();
                    //    每次讀取後累加的長度
                    long total_length = 0;
                    int length = 0;
                    //    每次讀取1024個字節
                    byte[] data = new byte[1024];
                    inputStream = httpEntity.getContent();
                    while(-1 != (length = inputStream.read(data)))
                    {
                        //    每讀一次，就將total_length累加起來
                        total_length += length;
                        //    邊讀邊寫到ByteArrayOutputStream當中
                        byteArrayOutputStream.write(data, 0, length);
                        //    得到當前圖片下載的進度
                        int progress = ((int)(total_length/(float)file_length) * 100);
                        //    時刻將當前進度更新給onProgressUpdate方法
                        publishProgress(progress);
                    }
                }
                image = byteArrayOutputStream.toByteArray();
                if(inputStream!=null){
                    inputStream.close();
                }

                byteArrayOutputStream.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                httpClient.getConnectionManager().shutdown();
            }
            return image;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //    更新ProgressDialog的進度條
            progressDialog.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(byte[] result)
        {
            super.onPostExecute(result);
            //    將doInBackground方法返回的byte[]解碼成要給Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
            //    更新我們的ImageView控件
            imageView.setImageBitmap(bitmap);
            //    使ProgressDialog框消失
            progressDialog.dismiss();
        }
    }


}
