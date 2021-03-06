package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.commands.TiltStick;

/**
 * class Tilt
 */
public class Elevator extends Subsystem
{
  // leading motor controllers, have built-in closed loop control
  private TalonSRX motor;

  // setpoint for motor in encoder units
  private double position;

  // range of encoder raw values -> 0 to ENCODER_IN are the limits
  private final double ENCODER_IN = 135000;
  // preferred range of encoder values (for degrees, percent, etc) -> 0 to ENCODER_OUT
  private final double ENCODER_OUT = 100;

  // limit switch at top of mvmt
  private DigitalInput switchie;
  
  public Elevator ()
  {
    motor = new TalonSRX(Robot.getMap().getCAN("elevator"));

    // PID constants (from/to encoder is reversed since it's multiplied by encoder error)
    motor.config_kP(0, 0.01 * ENCODER_OUT / ENCODER_IN);
    motor.config_kI(0, 0);
    motor.config_kD(0, 0);

    reset();

    // switchie = new DigitalInput(Robot.getMap().getDIO("elevator_switch"));
  }
  @Override
  public void initDefaultCommand()
  {
    setDefaultCommand(new TiltStick());
  }
  public void setVelocity(double pos)
  {
    position += toEncoder(pos);
    update();
  }
  
  public void setPosition(double pos)
  {
    position = toEncoder(pos);
    update();
  }
  public double getPosition()
  {
    return fromEncoder(motor.getSelectedSensorPosition());
  }
  public double getSetpoint ()
  {
    return fromEncoder(position);
  }
  public void reset()
  {
    motor.setSelectedSensorPosition(0);
    setPosition(0);
  }
  private void update()
  {
    if (position > ENCODER_IN)
      position = ENCODER_IN;
    if (position < 0)
      position = 0;
    motor.set(ControlMode.Position, position);
  }
  
  public boolean atTop() {
    return switchie == null ? false : switchie.get(); 
  }
  
  public void rawMove(double speed) {
    motor.set(ControlMode.PercentOutput, speed);
  }

  private double fromEncoder (double in)
  {
    // become bigger
    return in * ENCODER_OUT / ENCODER_IN;
  }
  private double toEncoder (double in)
  {
    // become smaller
    return in * ENCODER_IN / ENCODER_OUT;
  }
}
