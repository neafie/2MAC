package library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class GetAttemptsFunction {

	private JSONParser jsonParser;
	private static String url_all_lists = "http://gza.bellwethersystems.com/vie_api/include/get_all_attempts.php";

	public GetAttemptsFunction() {
		jsonParser = new JSONParser();
	}

	public JSONObject createList(String aid) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("aid", aid));
		JSONObject json = jsonParser.getJSONFromUrl(url_all_lists,params);

		return json;
	}

}