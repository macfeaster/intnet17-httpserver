package se.mauritzz.kth.inet.application;

import java.util.Random;

/**
 * Extremely simple session storage, tailored to the number guessing application.
 */
class Session implements Comparable<Integer> {
	private int number;
	private int max = 100;
	private int min;
	private int count;

	Session() { number = new Random().nextInt(100); }

	int getCount() { return count; }
	int getMax() { return max; }
	int getMin() { return min; }

	boolean makeGuess(int guess) {
		if (guess > number)
			max = guess;
		if (guess < number)
			min = guess;

		count++;
		return guess == number;
	}

	@Override
	public int compareTo(Integer o) {
		return number - o;
	}
}
