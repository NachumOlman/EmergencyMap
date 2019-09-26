import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResults;
import org.json.JSONArray;
//import org.json.JSONException;
import org.json.JSONObject;
/*
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
*/

import java.io.FileInputStream;

public class STT {
    //name = "audio-file2.flac"
    public String VoiceToText(String name){
        IamOptions options = new IamOptions.Builder()
                .apiKey("t2MK7y5_Nif1YM1EL1db6UGrkf-f48wwrMTukHggUvrH")
                .build();

        SpeechToText speechToText = new SpeechToText(options);
        speechToText.setEndPoint("https://gateway-lon.watsonplatform.net/speech-to-text/api");

        try {
            RecognizeOptions recognizeOptions = new RecognizeOptions.Builder()
                    .audio(new FileInputStream(name))
                    .contentType("audio/flac")
                    .wordAlternativesThreshold((float) 0.9)
                    .build();

            SpeechRecognitionResults speechRecognitionResults =
                    speechToText.recognize(recognizeOptions).execute().getResult();
            //System.out.println(speechRecognitionResults);

            JSONObject obj = new JSONObject(speechRecognitionResults);
            JSONArray arr = obj.getJSONArray("results");
            JSONObject obj2 = (JSONObject) arr.get(0);
            JSONArray arr2 = obj2.getJSONArray("alternatives");
            JSONObject obj3 = (JSONObject) arr2.get(0);
            String str = (String) obj3.get("transcript");
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
