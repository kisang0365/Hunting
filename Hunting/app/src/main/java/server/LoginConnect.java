package server;

import android.os.AsyncTask;
import android.util.Log;

import com.example.administrator.hunting.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017-03-02.
 */

public class LoginConnect extends AsyncTask<String, Void, Boolean> {
    @Override
    public Boolean doInBackground(String... params) {
        try {
            //요청할 주소의 파라미터의 정보를 입력.
            UrlAddress u = new UrlAddress();
            String url =  u.getUrl();

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("phoneNumber", params[0]);

            String json = jsonObject.toString();
            //URL클래스의 생성자로 주소 넘겨줌
            URL obj = new URL(url);
            //해당 주소의 페이지로 접속을 하고, 단일 HTTP 접속을 하기 위해 캐스트한다.
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //POST방식으로 데이터를 넘겨주겠다는 옵션임
            conn.setRequestMethod("POST");
            //InputStream으로 응답 헤더와 메시지를 읽어들이겠다는 옵션을 정의
            conn.setDoInput(true);
            //OutputStream으로 POST 데이터를 넘겨주겠다는 옵션을 정의.
            conn.setDoOutput(true);
            //요청 헤더를 정의한다. (원래 Content-Length값을 넘겨주어야하는데 넘겨주 않아도 되는것이 이상).
            conn.setRequestProperty("Content-Type","application/json");

           // byte[] outputInBytes = params[0].getBytes("UTF-8");
            //새로운 outputStream에 요청할 outputStream 넣음.
            OutputStream os = conn.getOutputStream();
            Log.d("kisang", "json : " + json);
            //os.write(body.getBytes("euc-kr")) UTF-8로 해도 되는데 한글일경우 EUC-KR로 인코딩해야만 한글 전달.
            os.write( json.getBytes("UTF-8") );
            // 스트림 버퍼 지워줌.
            os.flush();
            //당아줌
            os.close();

            int retCode = conn.getResponseCode();

            InputStream is = conn.getInputStream();
            //응답받은 메시지의 길이만큼 버퍼를 생성하여 읽어들이고, "EUC-KR"로 디코딩해서 읽어드림.
            //BufferedReader br = new BufferedReader( new OutputStreamReader( huc.getInputStream(), "EUC-KR" ), huc.getContentLength() );
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            //표준출력으로 한 라인씩 출력.
            while((line = br.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            br.close();

            String res = response.toString();
            Log.d("kisang", res);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}