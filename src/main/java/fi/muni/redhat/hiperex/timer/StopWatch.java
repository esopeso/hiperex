package fi.muni.redhat.hiperex.timer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StopWatch {

	// running states
	private static final int STATE_UNSTARTED = 0;
	private static final int STATE_RUNNING = 1;
	private static final int STATE_STOPED = 2;

	/**
	 * The current running state of the StopWatch.
	 */
	private int runningState = STATE_UNSTARTED;

	/**
	 * The start time.
	 */
	private long startTime = -1;

	/**
	 * The stop time.
	 */
	private long stopTime = -1;
	
	/**
	 * Represents last measured lap time
	 */
	private long lastLapTime = -1;
	
	/**
	 * Holds separate lap times
	 */
	private HashMap<String, Long> lapTimeList = new HashMap<String, Long>();
	/**
	 * <p>
	 * Start the stopwatch.
	 * </p>
	 * 
	 * <p>
	 * This method starts a new timing session, clearing any previous values.
	 * </p>
	 * 
	 * @throws IllegalStateException
	 *             if the StopWatch is already running.
	 */
	public void start() {
		if (this.runningState == STATE_STOPED) {
			throw new IllegalStateException("Stopwatch must be reset before being started. ");
		}
		if (this.runningState != STATE_UNSTARTED) {
			throw new IllegalStateException("Stopwatch already started. ");
		}
		stopTime = -1;
		this.startTime = System.nanoTime();
		this.runningState = STATE_RUNNING;
	}

	/**
	 * <p>
	 * Stop the stopwatch.
	 * </p>
	 * 
	 * <p>
	 * This method ends a new timing session, allowing the time to be retrieved.
	 * </p>
	 * 
	 * @throws IllegalStateException
	 *             if the StopWatch is not running.
	 */
	public void stop() {
		if (this.runningState != STATE_RUNNING) {
			throw new IllegalStateException("Stopwatch is not running");
		}
		if (this.runningState == STATE_RUNNING) {
			this.stopTime = System.nanoTime();
			this.lapTimeList.put("overall", this.stopTime - this.startTime);
		}
		this.runningState = STATE_STOPED;
	}

	 /**
     * <p>
     * Get the time on the stopwatch.
     * </p>
     * 
     * <p>
     * This is either the time between the start and the moment this method is called, or the amount of time between
     * start and stop.
     * </p>
     * 
     * @return the time in milliseconds
     */
	public long getTime() {
		if (this.runningState == STATE_STOPED) {
			return stopTime - startTime;
		} else if (this.runningState == STATE_UNSTARTED) {
			return 0;
		} else if (this.runningState == STATE_RUNNING) {
			return System.nanoTime() - this.startTime;
		}
		throw new RuntimeException("Illegal running state has occured. ");
	}

	/**
	 * <p>
	 * Resets the stopwatch. Stops it if need be.
	 * </p>
	 * 
	 * <p>
	 * This method clears the internal values to allow the object to be reused.
	 * </p>
	 */
	public void reset() {
		this.runningState = STATE_UNSTARTED;
		this.startTime = -1;
		this.stopTime = -1;
		this.lastLapTime = -1;
		this.lapTimeList = new HashMap<String, Long>();
	}
	
	/**
	 * <p>
	 * Stores lap time with specified key into map
	 * <p>
	 * @param key
	 */
	public void lap(String key) {
		if (this.runningState != STATE_RUNNING) {
			throw new IllegalStateException("Stopwatch must be running to measure lap time. ");
		}
		if (this.lapTimeList.size() == 0) {
			this.lastLapTime = this.startTime;
		}
		long lapTime = System.nanoTime();
		this.lapTimeList.put(key, lapTime - this.lastLapTime);
		this.lastLapTime = lapTime;
	}
	
	/**
	 * @return lapTimeList
	 */
	public HashMap<String, Long> getLapTimeList() {
		return this.lapTimeList;
	}

}
