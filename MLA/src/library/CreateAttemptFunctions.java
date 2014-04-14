package library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class CreateAttemptFunctions {

	private JSONParser jsonParser;
	private static String url_all_lists = "http://gza.bellwethersystems.com/vie_api/include/create_attempt.php";

	public ListFunctions() {
		jsonParser = new JSONParser();
	}

	public JSONObject createList(String uid_creator, String aid_belongsto, String pos_votes, String neg_votes, String picture_link, String video_link) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("uid_creator", uid_creator));
		params.add(new BasicNameValuePair("aid_belongsto", aid_belongsto));
		params.add(new BasicNameValuePair("pos_votes", pos_votes));
		params.add(new BasicNameValuePair("neg_votes", neg_votes));
		params.add(new BasicNameValuePair("picture_link", picture_link));
		params.add(new BasicNameValuePair("video_link", video_link));
		JSONObject json = jsonParser.getJSONFromUrl(url_all_lists,params);

		return json;
	}

}