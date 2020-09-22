package tddc17;


import aima.core.environment.liuvacuum.*;
import aima.core.agent.Action;
import aima.core.agent.AgentProgram;
import aima.core.agent.Percept;
import aima.core.agent.impl.*;

import java.util.Random;
import java.io.*;
import java.util.*;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Iterator;

class Node
{
  int x;
  int y;
  int parent_x;
  int parent_y;
  Boolean visited;
  Boolean hasparent;
  int startdir;
  public Node(int x, int y, Boolean hasparent)
  {
    this.x = x;
    this.y = y;
    this.parent_x = 0;
    this.parent_y = 0;
    this.visited = false;
	  this.hasparent = hasparent;
    this.startdir = 999;
  }
  public int getparentx(){
    return this.parent_x;
  }
  public int getparenty(){
    return this.parent_y;
  }
  public int getx(){
    return this.x;
  }
  public int gety(){
    return this.y;
  }

  public void set_parent(int direction)
	{
		if (!this.hasparent){
			//startnode
			this.parent_x = -10;
			this.parent_y = -10;
      this.startdir = direction;
		}
		else{
		switch(direction)
		{
			case MyAgentState.NORTH:
			this.parent_x = this.x;
			this.parent_y = this.y + 1;
			break;
			case MyAgentState.WEST:
			this.parent_x = this.x + 1;
			this.parent_y = this.y;
			break;
			case MyAgentState.EAST:
			this.parent_x = this.x - 1;
			this.parent_y = this.y;
			break;
			case MyAgentState.SOUTH:
			this.parent_x = this.x;
			this.parent_y = this.y - 1;
			break;
		}
	}
	}
}

class MyAgentState
{
	public int[][] world = new int[30][30];

  public Node Graph[][] = new Node[30][30];

	public Queue<Integer> Q = new LinkedList<Integer>(); //action que


	public int initialized = 0;
	final int UNKNOWN 	= 0;
	final int WALL 		= 1;
	final int CLEAR 	= 2;
	final int DIRT		= 3;
	final int HOME		= 4;
	final int ACTION_NONE 			= 0;
	final int ACTION_MOVE_FORWARD 	= 1;
	final int ACTION_TURN_RIGHT 	= 2;
	final int ACTION_TURN_LEFT 		= 3;
	final int ACTION_SUCK	 		= 4;

	public int agent_x_position = 1;
	public int agent_y_position = 1;
	public int agent_last_action = ACTION_NONE;

	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	public int agent_direction = EAST;







	MyAgentState()
	{
		for (int i=0; i < world.length; i++)
			for (int j=0; j < world[i].length ; j++)
				world[i][j] = UNKNOWN;
		world[1][1] = HOME;
		agent_last_action = ACTION_NONE;
	}
	// Based on the last action and the received percept updates the x & y agent position
	public void updatePosition(DynamicPercept p)
	{
		Boolean bump = (Boolean)p.getAttribute("bump");

		if (agent_last_action==ACTION_MOVE_FORWARD && !bump)
	    {
			switch (agent_direction) {
			case MyAgentState.NORTH:
				agent_y_position--;
				break;
			case MyAgentState.EAST:
				agent_x_position++;
				break;
			case MyAgentState.SOUTH:
				agent_y_position++;
				break;
			case MyAgentState.WEST:
				agent_x_position--;
				break;
			}
	    }

	}

	public void updateWorld(int x_position, int y_position, int info)
	{
		world[x_position][y_position] = info;
	}

	public void printWorldDebug()
	{
		for (int i=0; i < world.length; i++)
		{
			for (int j=0; j < world[i].length ; j++)
			{
				if (world[j][i]==UNKNOWN)
					System.out.print(" ? ");
				if (world[j][i]==WALL)
					System.out.print(" # ");
				if (world[j][i]==CLEAR)
					System.out.print(" _ ");
				if (world[j][i]==DIRT)
					System.out.print(" D ");
				if (world[j][i]==HOME)
					System.out.print(" H ");
			}
			System.out.println("");

		}

	}
  void addnode(int x, int y, int direction,Boolean hasparent)
  {
  	Node n = new Node(x,y,hasparent);
  	Graph[x][y] = n;
  	n.set_parent(direction);
  }
  public void gotoparent(Node n, int direction){
    //move vacuumcleaner to parent for n
   //node - parent
   if (!n.hasparent){
      gohome(n,direction);
      Q.add(ACTION_SUCK);
      Q.add(ACTION_NONE);
    }


   if (n.getx() != n.getparentx() ){
     //move west or east
     if ( n.getparentx() - n.getx() < 0){
       //west
       switch(direction){
        case NORTH:
          Q.add(ACTION_TURN_LEFT);
          Q.add(ACTION_MOVE_FORWARD);
        break;
        case EAST:
          Q.add(ACTION_TURN_RIGHT);
          Q.add(ACTION_TURN_RIGHT);
          Q.add(ACTION_MOVE_FORWARD);
        break;
        case SOUTH:
          Q.add(ACTION_TURN_RIGHT);
          Q.add(ACTION_MOVE_FORWARD);
        break;
        case WEST:
          Q.add(ACTION_MOVE_FORWARD);
        break;
      }
     } else{
       //east
       switch(direction){
        case NORTH:
          Q.add(ACTION_TURN_RIGHT);
          Q.add(ACTION_MOVE_FORWARD);
        break;
        case EAST:
          Q.add(ACTION_MOVE_FORWARD);
        break;
        case SOUTH:
          Q.add(ACTION_TURN_LEFT);
          Q.add(ACTION_MOVE_FORWARD);
        break;
        case WEST:
        Q.add(ACTION_TURN_RIGHT);
        Q.add(ACTION_TURN_RIGHT);
        Q.add(ACTION_MOVE_FORWARD);
        break;
      }
     }
   }
   else{
     //move south or north.
     if ( n.getparenty() - n.gety() < 0){
       switch(direction){
       //north
       case NORTH:
         Q.add(ACTION_MOVE_FORWARD);
       break;
       case EAST:
         Q.add(ACTION_TURN_LEFT);
         Q.add(ACTION_MOVE_FORWARD);
       break;
       case SOUTH:
         Q.add(ACTION_TURN_RIGHT);
         Q.add(ACTION_TURN_RIGHT);
         Q.add(ACTION_MOVE_FORWARD);
       break;
       case WEST:
         Q.add(ACTION_TURN_RIGHT);
         Q.add(ACTION_MOVE_FORWARD);
         break;
       }
     } else{
       switch(direction){
       //south
       case NORTH:
       Q.add(ACTION_TURN_RIGHT);
       Q.add(ACTION_TURN_RIGHT);
       Q.add(ACTION_MOVE_FORWARD);
       break;
       case EAST:
         Q.add(ACTION_TURN_RIGHT);
         Q.add(ACTION_MOVE_FORWARD);
       break;
       case SOUTH:
         Q.add(ACTION_MOVE_FORWARD);
       break;
       case WEST:
         Q.add(ACTION_TURN_LEFT);
         Q.add(ACTION_MOVE_FORWARD);
         break;
       }
     }
   }

  }

public void turn(int agent_direction, Boolean bump)
{
    //check for unexplored tiles around agent
    switch (agent_direction) {
    case MyAgentState.NORTH:
      if(!bump && world[agent_x_position][agent_y_position-1] == UNKNOWN && world[agent_x_position][agent_y_position-1] != WALL || world[agent_x_position][agent_y_position-1] == HOME)
      {
        Q.add(ACTION_MOVE_FORWARD);
      }
      else if(world[agent_x_position+1][agent_y_position] != WALL && world[agent_x_position+1][agent_y_position] == UNKNOWN || world[agent_x_position+1][agent_y_position] == HOME )
      {
        Q.add(ACTION_TURN_RIGHT);
        Q.add(ACTION_MOVE_FORWARD);
      }
      else if(world[agent_x_position-1][agent_y_position] != WALL && world[agent_x_position-1][agent_y_position] == UNKNOWN || world[agent_x_position-1][agent_y_position] == HOME)
      {
        Q.add(ACTION_TURN_LEFT);
        Q.add(ACTION_MOVE_FORWARD);
      }
      else if(world[agent_x_position][agent_y_position+1] != WALL && world[agent_x_position][agent_y_position+1] == UNKNOWN || world[agent_x_position][agent_y_position+1] == HOME)
      {
        //turn 180 degrees
        Q.add(ACTION_TURN_RIGHT);
        Q.add(ACTION_TURN_RIGHT);
        Q.add(ACTION_MOVE_FORWARD);
      }
      else
      {
        //goto parent
        gotoparent(Graph[agent_x_position][agent_y_position],agent_direction);
      }
      break;
    case MyAgentState.EAST:
      if(!bump && world[agent_x_position+1][agent_y_position] == UNKNOWN && world[agent_x_position+1][agent_y_position] != WALL || world[agent_x_position+1][agent_y_position] == HOME)
      {
          Q.add(ACTION_MOVE_FORWARD);
      }
      else if(world[agent_x_position][agent_y_position+1] != WALL && world[agent_x_position][agent_y_position+1] == UNKNOWN || world[agent_x_position][agent_y_position+1] == HOME)
      {
        Q.add(ACTION_TURN_RIGHT);
        Q.add(ACTION_MOVE_FORWARD);
      }
      else if(world[agent_x_position][agent_y_position-1] != WALL && world[agent_x_position][agent_y_position-1] == UNKNOWN || world[agent_x_position][agent_y_position-1] == HOME)
      {
        Q.add(ACTION_TURN_LEFT);
        Q.add(ACTION_MOVE_FORWARD);
      }
      else if(world[agent_x_position-1][agent_y_position] != WALL && world[agent_x_position-1][agent_y_position] == UNKNOWN || world[agent_x_position-1][agent_y_position] == HOME)
      {
        //turn 180 degreess
        Q.add(ACTION_TURN_RIGHT);
        Q.add(ACTION_TURN_RIGHT);
        Q.add(ACTION_MOVE_FORWARD);
      }
      else
      {
        //go to parent
        gotoparent(Graph[agent_x_position][agent_y_position],agent_direction);
      }
    break;
    case MyAgentState.SOUTH:
      if(!bump && world[agent_x_position][agent_y_position+1] == UNKNOWN && world[agent_x_position][agent_y_position+1] != WALL || world[agent_x_position][agent_y_position+1] == HOME)
      {
          Q.add(ACTION_MOVE_FORWARD);
      }
      else if(world[agent_x_position-1][agent_y_position] != WALL && world[agent_x_position-1][agent_y_position] == UNKNOWN || world[agent_x_position-1][agent_y_position] == HOME )
      {
        Q.add(ACTION_TURN_RIGHT);
         Q.add(ACTION_MOVE_FORWARD);
      }
      else if(world[agent_x_position+1][agent_y_position] != WALL && world[agent_x_position+1][agent_y_position] == UNKNOWN || world[agent_x_position+1][agent_y_position] == HOME)
      {
         Q.add(ACTION_TURN_LEFT);
         Q.add(ACTION_MOVE_FORWARD);
      }
      else if(world[agent_x_position][agent_y_position-1] != WALL && world[agent_x_position][agent_y_position-1] == UNKNOWN || world[agent_x_position][agent_y_position-1] == HOME)
      {
        //turn 180 degrees
        Q.add(ACTION_TURN_RIGHT);
        Q.add(ACTION_TURN_RIGHT);
        Q.add(ACTION_MOVE_FORWARD);
      }
      else
      {
        //goto parent
        gotoparent(Graph[agent_x_position][agent_y_position],agent_direction);
      }
    break;
    case MyAgentState.WEST:
      if(!bump && world[agent_x_position-1][agent_y_position] == UNKNOWN && world[agent_x_position-1][agent_y_position] != WALL || world[agent_x_position-1][agent_y_position] == HOME)
      {
        Q.add(ACTION_MOVE_FORWARD);
      }
      else if(world[agent_x_position][agent_y_position-1] != WALL && world[agent_x_position][agent_y_position-1] == UNKNOWN || world[agent_x_position][agent_y_position-1] == HOME)
      {
         Q.add(ACTION_TURN_RIGHT);
         Q.add(ACTION_MOVE_FORWARD);
      }
      else if(world[agent_x_position][agent_y_position+1] != WALL && world[agent_x_position][agent_y_position+1] == UNKNOWN || world[agent_x_position][agent_y_position+1] == HOME)
      {
         Q.add(ACTION_TURN_LEFT);
         Q.add(ACTION_MOVE_FORWARD);
      }
      else if(world[agent_x_position+1][agent_y_position] != WALL && world[agent_x_position+1][agent_y_position] == UNKNOWN || world[agent_x_position+1][agent_y_position] == HOME)
      {
        //turn 180 degrees
        Q.add(ACTION_TURN_RIGHT);
        Q.add(ACTION_TURN_RIGHT);
        Q.add(ACTION_MOVE_FORWARD);
      }
      else
      {
        //goto parent
        gotoparent(Graph[agent_x_position][agent_y_position],agent_direction);
      }
    break;
    }

}
public void gohome(Node n, int agent_direction)
  {

    int goal_x = 1;
    int goal_y = 1;
    int cur_x = n.getx();
    int cur_y = n.gety();
    int cur_dir = agent_direction;
    //turn west
    while(cur_dir != WEST)
      {
        cur_dir++;
        if(cur_dir > 3 )
          {cur_dir=0;}

        if(cur_dir < 0)
          {cur_dir=3;}

        Q.add(ACTION_TURN_RIGHT);
      }


    while(cur_x != goal_x)
    {
      Q.add(ACTION_MOVE_FORWARD);
      cur_x--;
    }
    //turn NORTH
    Q.add(ACTION_TURN_RIGHT);
    while(cur_y != goal_y)
    {
      Q.add(ACTION_MOVE_FORWARD);
      cur_y--;
    }


  }


}

class MyAgentProgram implements AgentProgram {

	private int initnialRandomActions = 10;
	private Random random_generator = new Random();

	// Here you can define your variables!
	public int iterationCounter = 1000;
	public MyAgentState state = new MyAgentState();
  public int turn=0;



	// moves the Agent to a random start position
	// uses percepts to update the Agent position - only the position, other percepts are ignored
	// returns a random action
	private Action moveToRandomStartPosition(DynamicPercept percept) {
		int action = random_generator.nextInt(6);
		initnialRandomActions--;
		state.updatePosition(percept);
		if(action==0) {
		    state.agent_direction = ((state.agent_direction-1) % 4);
		    if (state.agent_direction<0)
		    	state.agent_direction +=4;
		    state.agent_last_action = state.ACTION_TURN_LEFT;
			return LIUVacuumEnvironment.ACTION_TURN_LEFT;
		} else if (action==1) {
			state.agent_direction = ((state.agent_direction+1) % 4);
		    state.agent_last_action = state.ACTION_TURN_RIGHT;
		    return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
		}
		state.agent_last_action=state.ACTION_MOVE_FORWARD;
		return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
	}


	@Override
	public Action execute(Percept percept) {

		// DO NOT REMOVE this if condition!!!
    	if (initnialRandomActions>0) {
    		return moveToRandomStartPosition((DynamicPercept) percept);
    	} else if (initnialRandomActions==0) {
    		// process percept for the last step of the initial random actions
    		initnialRandomActions--;
    		state.updatePosition((DynamicPercept) percept);
			System.out.println("Processing percepts after the last execution of moveToRandomStartPosition()");
			state.agent_last_action=state.ACTION_SUCK;
      state.addnode(state.agent_x_position,state.agent_y_position,state.agent_direction,false);
	    	return LIUVacuumEnvironment.ACTION_SUCK;
    	}

    	// This example agent program will update the internal agent state while only moving forward.
    	// START HERE - code below should be modified!

    	System.out.println("x=" + state.agent_x_position);
    	System.out.println("y=" + state.agent_y_position);
    	System.out.println("dir=" + state.agent_direction);




			iterationCounter--;

	    if (iterationCounter==0)
			{

	    	return NoOpAction.NO_OP;
			}

	    DynamicPercept p = (DynamicPercept) percept;
	    Boolean bump = (Boolean)p.getAttribute("bump");
	    Boolean dirt = (Boolean)p.getAttribute("dirt");
	    Boolean home = (Boolean)p.getAttribute("home");
	    System.out.println("percept: " + p);


	    // State update based on the percept value and the last action
	    state.updatePosition((DynamicPercept)percept);
      if(state.Graph[state.agent_x_position][state.agent_y_position] == null)
			{
				state.addnode(state.agent_x_position,state.agent_y_position,state.agent_direction,true);
			}

	    if (bump) {
			switch (state.agent_direction) {
			case MyAgentState.NORTH:
				state.updateWorld(state.agent_x_position,state.agent_y_position-1,state.WALL);
				break;
			case MyAgentState.EAST:
				state.updateWorld(state.agent_x_position+1,state.agent_y_position,state.WALL);
				break;
			case MyAgentState.SOUTH:
				state.updateWorld(state.agent_x_position,state.agent_y_position+1,state.WALL);
				break;
			case MyAgentState.WEST:
				state.updateWorld(state.agent_x_position-1,state.agent_y_position,state.WALL);
				break;
			}

	    }
	    if (dirt)
	    	state.updateWorld(state.agent_x_position,state.agent_y_position,state.DIRT);
	    else
	    	state.updateWorld(state.agent_x_position,state.agent_y_position,state.CLEAR);

	    state.printWorldDebug();


	    // Next action selection based on the percept value
			if (dirt)
		    {
		    	System.out.println("DIRT -> choosing SUCK action!");
		    	state.agent_last_action=state.ACTION_SUCK;
		    	return LIUVacuumEnvironment.ACTION_SUCK;
		    }
		    else
		    {
            while(state.Q.isEmpty())
            {
              //put actions in que
              state.turn(state.agent_direction,bump);
            }
            switch(state.Q.poll())
            {

              case 1:
              	 state.agent_last_action=state.ACTION_MOVE_FORWARD;
                 state.agent_direction = state.agent_direction;
        			   return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;

        			case 2:
                  state.agent_last_action = state.ACTION_TURN_RIGHT;
                  state.agent_direction++;
                  if(state.agent_direction >3)
                  {
                    state.agent_direction = 0;
                  }
        			   return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
        			case 3:
                  state.agent_last_action = state.ACTION_TURN_LEFT;
                  state.agent_direction--;
                  if(state.agent_direction < 0)
                  {
                    state.agent_direction = 3;
                  }
        				  return LIUVacuumEnvironment.ACTION_TURN_LEFT;
        			case 0:
        				state.agent_last_action = state.ACTION_NONE;
                state.agent_direction = state.agent_direction;
                return NoOpAction.NO_OP;

            }
        return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
      }
}

}

public class MyVacuumAgent extends AbstractAgent {
    public MyVacuumAgent() {
    	super(new MyAgentProgram());
	}
}
