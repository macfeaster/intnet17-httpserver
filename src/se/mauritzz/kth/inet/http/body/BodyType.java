package se.mauritzz.kth.inet.http.body;

public enum BodyType {
	FORM_URLENCODED {
		@Override
		public String toString() {
			return "application/x-www-form-urlencoded";
		}
	},

	TEXT_HTML {
		@Override
		public String toString() {
			return "text/html";
		}
	};

	public static BodyType fromString(String raw) {
		raw = raw.toLowerCase().trim();

		switch (raw) {
			case "application/x-www-form-urlencoded":
				return FORM_URLENCODED;
			case "text/html":
				return TEXT_HTML;
			default:
				return TEXT_HTML;
		}
	}
}
