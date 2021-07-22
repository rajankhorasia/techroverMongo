package techrovers.util;

import java.util.Random;

public class Utility {
	
	public static Long generateRandomLong(){
		long range = 1234567L;
		Random r = new Random();
		return (long)(r.nextDouble()*range);
	}

}
