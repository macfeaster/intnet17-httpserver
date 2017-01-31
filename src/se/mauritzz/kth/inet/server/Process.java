package se.mauritzz.kth.inet.server;

import se.mauritzz.kth.inet.http.request.HttpRequest;
import se.mauritzz.kth.inet.http.response.HttpResponse;

public interface Process {

	HttpResponse doGet(HttpRequest request);

	HttpResponse doPost(HttpRequest request);
}
