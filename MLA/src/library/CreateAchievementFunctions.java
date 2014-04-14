package library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class CreateAchievementFunctions {

	private JSONParser jsonParser;
	private static String url_all_lists = "http://gza.bellwethersystems.com/vie_api/include/create_achievement.php";

	public CreateAchievementFunctions() {
		jsonParser = new JSONParser();
	}

	public JSONObject createList(String lid_belongsto, String uid_creator, String ach_name, String startTime, String endTime, String points, String threshold, String description, String icon, String single_completion) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("lid_belongsto", lid_belongsto));
		params.add(new BasicNameValuePair("uid_creator", uid_creator));
		params.add(new BasicNameValuePair("ach_name", ach_name));
		params.add(new BasicNameValuePair("startTime", startTime));
		params.add(new BasicNameValuePair("endTime", endTime));
		params.add(new BasicNameValuePair("points", points));
		params.add(new BasicNameValuePair("threshold", threshold));
		params.add(new BasicNameValuePair("description", description));
		params.add(new BasicNameValuePair("icon", icon));
		params.add(new BasicNameValuePair("single_completion", single_completion));
		JSONObject json = jsonParser.getJSONFromUrl(url_all_lists,params);

		return json;
	}

}