package fi.muni.redhat.hiperex.util.old;

import java.util.HashMap;

import fi.muni.redhat.hiperex.exception.EmptyIdPoolException;



public interface Repeatable {

	/**
	 * Query to repeat
	 * @return elapsed time
	 * @throws EmptyIdPoolException 
	 */
	public long execute();
}
