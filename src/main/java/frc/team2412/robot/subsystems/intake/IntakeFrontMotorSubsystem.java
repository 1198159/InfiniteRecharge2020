package frc.team2412.robot.subsystems.intake;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.robototes.math.MathUtils;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;

public class IntakeFrontMotorSubsystem extends SubsystemBase implements IIntakeMotorSubsystem {
    private CANSparkMax m_intakeMotor;
	public IntakeFrontMotorSubsystem(CANSparkMax motor) {
        m_intakeMotor = motor;
	}
	@Override
	@Log.NumberBar(min = -1, max = 1, methodName = "get", name = "Current Intake Front Motor Speed", tabName = "Intake")
	public CANSparkMax getMainMotor() {
		return m_intakeMotor;
    }
    
    @Override
	@Config.NumberSlider(min = -1, max = 1, name = "Set Intake Front Motor Speed")
	public void set(double speed){
        IIntakeMotorSubsystem.super.set(speed);
    }

    @Override
    public IntakeMotorSubsystemType getType() {
        return IntakeMotorSubsystemType.FRONT;
    }
}
