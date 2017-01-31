package se.mauritzz.kth.inet.server;

import se.mauritzz.kth.inet.http.request.HttpRequest;
import se.mauritzz.kth.inet.http.response.HttpResponse;

import java.io.IOException;

public interface Process {

	HttpResponse doGet(HttpRequest request) throws IOException;

	HttpResponse doPost(HttpRequest request) throws IOException;
}
