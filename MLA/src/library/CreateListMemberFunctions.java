package library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class CreateListMemberFunctions {

	private JSONParser jsonParser;
	private static String url_all_lists = "http://gza.bellwethersystems.com/vie_api/include/create_list_members.php";

	public CreateListMemberFunctions() {
		jsonParser = new JSONParser();
	}

	public JSONObject createList(String lid, String uid) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("lid", lid));
		params.add(new BasicNameValuePair("uid", uid));
		JSONObject json = jsonParser.getJSONFromUrl(url_all_lists,params);

		return json;
	}

}