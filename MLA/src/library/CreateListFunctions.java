package library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class CreateListFunctions {

	private JSONParser jsonParser;
	private static String url_all_lists = "http://gza.bellwethersystems.com/vie_api/include/create_list.php";

	public ListFunctions() {
		jsonParser = new JSONParser();
	}

	public JSONObject createList(String uid_creator, String list_name, String list_description) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("uid_creator", uid_creator));
		params.add(new BasicNameValuePair("list_name", list_name));
		params.add(new BasicNameValuePair("list_description", list_description));
		JSONObject json = jsonParser.getJSONFromUrl(url_all_lists,params);

		return json;
	}

}