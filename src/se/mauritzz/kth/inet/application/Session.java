package se.mauritzz.kth.inet.application;

import java.util.Random;

class Session implements Comparable<Integer> {
	private int number;
	private int guess;
	private int count;

	public Session() {
		number = new Random().nextInt(100);
	}

	public int getNumber() {
		return number;
	}

	public int getCount() {
		return count;
	}

	boolean makeGuess(int guess) {
		this.guess = guess;
		count++;

		return guess == number;
	}

	@Override
	public String toString() {
		return "Session{" +
				"number=" + number +
				", guess=" + guess +
				", count=" + count +
				'}';
	}

	@Override
	public int compareTo(Integer o) {
		return number - o;
	}
}
