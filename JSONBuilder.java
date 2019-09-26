import org.json.JSONObject;
import java.util.Date;
import java.text.SimpleDateFormat;
public class JSONBuilder {
    private JSONObject buildFrom(String phoneNum, String location,
                                   String type, boolean injured,
                                   String fullDesc) {
        JSONObject ret = new JSONObject();
        ret.put("Time",
                new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date()));
        ret.put("Phone Number", phoneNum);
        ret.put("Location", location);
        ret.put("Type", type);
        ret.put("Injured", injured);
        ret.put("Full Description", fullDesc);
        return ret;
    }
    public JSONObject buildObject(String fileName, String phoneNum) {
        STT SpeechToText = new STT();
        Classifier clf = new Classifier();
        String fullFile = SpeechToText.VoiceToText(fileName);
        System.out.println(fullFile);
        System.out.println("---");
        String toReplace = "hello this is an automated receptionist said because due to a back log on the emergency line I will ask you a few questions what is the disaster";
        String startCut = fullFile.replace(toReplace,"");
        System.out.println(startCut);
        System.out.println("---");
        String[] tokens = startCut.split("what is your current location");
        System.out.println(tokens[0]);
        System.out.println("---");
        String fullDesc = tokens[0];
        String type = null;
        //try {type = clf.classifyDisaster(tokens[0]);}
        //catch(Exception e) {e.printStackTrace();}
        tokens = tokens[1].split("");
        String location = tokens[0];
        tokens = tokens[1].split("");
        boolean injured = false;
        //try {injured = clf.classifyInjured(tokens[0]);}
        //catch(Exception e) {e.printStackTrace();}
        return buildFrom(phoneNum, location, type, injured, fullDesc);
    }
}
