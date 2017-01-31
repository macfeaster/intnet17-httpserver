package se.mauritzz.kth.inet.http.response;

public enum ResponseType {
	OK {
		@Override
		public int getCode() {
			return 200;
		}

		@Override
		public String toString() {
			return "OK";
		}
	},
	BAD_REQUEST {
		@Override
		public int getCode() {
			return 400;
		}

		@Override
		public String toString() {
			return "Bad Request";
		}
	},
	SERVER_ERROR {
		@Override
		public int getCode() {
			return 500;
		}

		@Override
		public String toString() {
			return "Internal Server Error";
		}
	};

	public int getCode() {
		return 0;
	}
}