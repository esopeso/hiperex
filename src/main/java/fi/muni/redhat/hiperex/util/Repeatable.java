package fi.muni.redhat.hiperex.util;

import java.util.HashMap;

import fi.muni.redhat.hiperex.exception.EmptyIdPoolException;



public interface Repeatable {

	/**
	 * Query to repeat
	 * @return elapsed time
	 * @throws EmptyIdPoolException 
	 */
	public HashMap<String,Long> execute();
}
