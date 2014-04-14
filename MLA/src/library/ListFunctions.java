package library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class ListFunctions {

	private JSONParser jsonParser;
	private static String url_all_lists = "http://gza.bellwethersystems.com/vie_api/include/get_all_lists.php";
	private static String url_all_achs = "http://gza.bellwethersystems.com/vie_api/include/get_all_achievements.php";
	
	public ListFunctions() {
		jsonParser = new JSONParser();
	}
	
	public JSONObject getLists(int uid) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("uid",Integer.toString(uid)));
		JSONObject json = jsonParser.getJSONFromUrl(url_all_lists, params);
	
		return json;
	}

	public JSONObject getAchievements(int lid) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("lid",Integer.toString(lid)));
		JSONObject json = jsonParser.getJSONFromUrl(url_all_achs, params);
	
		return json;
	}
}
