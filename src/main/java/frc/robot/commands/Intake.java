package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Intake extends Command

{
	public Intake()
	{
		requires(Robot.getFlywheel());
	}	
	
	protected void initialize(){}

	protected void execute()
	{
		/*
		if(!Robot.getFlywheel().hasCargo())
		{
			Robot.getFlywheel().intake();
			Robot.getFlywheel().printTest();
		}
		else
			Robot.getFlywheel().stop();
		*/
		Robot.getFlywheel().intake();
			
	}

	protected boolean isFinished()
	{
		return Robot.getFlywheel().hasCargo();
	}

	protected void end() 
	{
		Robot.getFlywheel().setSpeed(0);
	}	

	protected void interrupted() 
	{
		end();
	}
}
