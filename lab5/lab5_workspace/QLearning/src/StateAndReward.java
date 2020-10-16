public class StateAndReward {

	public static final double maxangle = 2;
	public static final double minangle = -2;

	public static final double minvx = -1;
	public static final double maxvx = 1;
	public static final double minvy = -3;
	public static final double maxvy = 3;

	public static final int nrstates = 12;
	public static final int ystate =8;
	public static final int xstate =4;



	/* State discretization function for the angle controller */
	public static String getStateAngle(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */

		// discretize(double value, int nrValues, double min,double max)
		int tmp;
		tmp = discretize(angle,nrstates,minangle,maxangle);
		String state = String.valueOf(tmp);
		return state;
}
	/* Reward function for the angle controller */
	public static double getRewardAngle(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */

		double reward = 0;
		if (angle == 0)
		{
			reward = 10002;
		}
		else
		{
			reward = Math.abs(1/angle);
		}

	//	reward^2 =  Math.PI - math.abs(angle);
		return reward;
	}

	/* State discretization function for the full hover controller */
	public static String getStateHover(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		//states are combination of velocity x,y
		//and angle
		double vstatey;
		double vstatex;
		double anglestate;
		//discretisize based on velocity in x
		 vstatex =  discretize(vx,xstate,minvx,maxvx);
		 vstatey =  discretize(vy,ystate,minvy,maxvy);
		 anglestate = discretize(angle,nrstates,minangle,maxangle);

		String state = String.valueOf(vstatex) + String.valueOf(vstatey) + String.valueOf(anglestate);

		return state;
	}

	/* Reward function for the full hover controller */
	public static double getRewardHover(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		//higher rewards for low velocities in both x and y
		// also high rewards for low angles (rocket is vertical)
		double rewarda = 0;
		double rewardx = 0;
		double rewardy = 0;
		double reward = 0;
		if(Math.abs(angle) <= maxangle)
		{
			rewarda = Math.pow(1-Math.abs(angle/maxangle),2);
		}
		if(Math.abs(vx) <= maxvx)
		{
			rewardx = Math.pow(1 - Math.abs(vx/maxvx),2);
		}
		if(Math.abs(vy) <= maxvy)
		{
			rewardy = Math.pow(1 - Math.abs(vy/maxvy),2);
		}



/*
		reward = Math.pow(1/(Math.PI-angle),2);
		reward = Math.abs(reward)+Math.pow(1/Math.abs(vy),2);
		reward = reward + Math.pow(1/Math.abs(vx),2);
*/

		 //reward = 1 / Math.abs(vy+vx+angle);
		// Math.pow(reward,4);
		reward = rewardy + rewardx + rewarda;
		return reward;
	}

	// ///////////////////////////////////////////////////////////
	// discretize() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 1 and nrValues-2 is returned.
	//
	// Use discretize2() if you want a discretization method that does
	// not handle values lower than min and higher than max.
	// ///////////////////////////////////////////////////////////
	public static int discretize(double value, int nrValues, double min,
			double max) {
		if (nrValues < 2) {
			return 0;
		}

		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * (nrValues - 2)) + 1;
	}

	// ///////////////////////////////////////////////////////////
	// discretize2() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 0 and nrValues-1 is returned.
	// ///////////////////////////////////////////////////////////
	public static int discretize2(double value, int nrValues, double min,
			double max) {
		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * nrValues);
	}

}
