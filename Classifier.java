import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import java.lang.Math;

public class Classifier {
    private String sendRequest(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public String classifyDisaster(String toClassify) throws Exception {
        String url = "https://api.uclassify.com/v1/guyguy40/disasterType/classify/?readKey=SG38lU7E90jF&text=";
        url += toClassify.replaceAll("\\s+","+");
        String response = sendRequest(url);
        JSONObject JSONResponse = new JSONObject(response.toString());
        double building = JSONResponse.getDouble("building");
        double road = JSONResponse.getDouble("road");
        double electric = JSONResponse.getDouble("electric");
        double stranded = JSONResponse.getDouble("stranded");
        double max = Math.max(Math.max(building, road), Math.max(electric, stranded));
        if(max == building) return "building collapsed";
        if(max == road) return "road blocked";
        if(max == electric) return "electric pole";
        return "stranded person";
    }

    public boolean classifyInjured(String toClassify) throws Exception {
        String url = "https://api.uclassify.com/v1/guyguy40/anyInjured/classify/?readKey=SG38lU7E90jF&text=";
        url += toClassify.replaceAll("\\s+","+");
        String response = sendRequest(url);
        JSONObject JSONResponse = new JSONObject(response.toString());
        double yes = JSONResponse.getDouble("yes");
        double no = JSONResponse.getDouble("no");
        if(yes > no) return true;
        return false;
    }
}
