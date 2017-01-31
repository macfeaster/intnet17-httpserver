package se.mauritzz.kth.inet.http.body;

import se.mauritzz.kth.inet.http.common.UrlEncodedData;

public class FormRequestBody extends HttpBody {

	private UrlEncodedData data;

	public FormRequestBody(String raw) {
		data = UrlEncodedData.deserialize(raw);
	}

	@Override
	public String getBody() {
		return data.serialize();
	}
}
