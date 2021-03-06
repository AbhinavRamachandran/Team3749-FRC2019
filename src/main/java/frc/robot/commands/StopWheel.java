package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class StopWheel extends Command
{
	public StopWheel()
	{
		requires(Robot.getFlywheel());
	}	
	
	protected void initialize(){}

	protected void execute()
	{
		Robot.getFlywheel().setSpeed(0);
	}

	protected boolean isFinished()
	{
		return Robot.getFlywheel().getSpeed() == 0;
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
