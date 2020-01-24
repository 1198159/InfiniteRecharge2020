package frc.team2412.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team2412.robot.Commands.ExampleCommand;
import frc.team2412.robot.Commands.ControlPanelCommands.RotateControlPanelCommand;
import frc.team2412.robot.Commands.ControlPanelCommands.SetoToTargetColorCommand;
import frc.team2412.robot.Commands.IntakeCommands.IntakeDownCommand;
import frc.team2412.robot.Commands.IntakeCommands.IntakeOffCommand;
import frc.team2412.robot.Commands.IntakeCommands.IntakeOnCommand;
import frc.team2412.robot.Commands.IntakeCommands.IntakeUpCommand;
import frc.team2412.robot.Commands.LiftCommands.LiftDownCommand;
import frc.team2412.robot.Commands.LiftCommands.LiftUpCommand;

//This is the class in charge of all the buttons and joysticks that the drivers will use to control the robot
public class OI {

	// Joystick ports
	public static final int DRIVER_STICK_PORT = 0;
	public static final int CODRIVER_STICK_PORT = 1;

	// Button ports
	public static final int LIFT_UP_BUTTON_PORT = 1;
	public static final int LIFT_DOWN_BUTTON_PORT = 1;

	// Front on off
	public static final int INTAKE_FRONT_ON_BUTTON_PORT = 1;
	public static final int INTAKE_FRONT_OFF_BUTTON_PORT = 1;

	// back on off
	public static final int INTAKE_BACK_ON_BUTTON_PORT = 1;
	public static final int INTAKE_BACK_OFF_BUTTON_PORT = 1;

	// bring it up and down
	public static final int INTAKE_UP_BUTTON_PORT = 1;
	public static final int INTAKE_DOWN_BUTTON_PORT = 1;

	public static final int CONTROL_PANEL_SPIN_3_TIMES_BUTTON_PORT = 1;
	public static final int CONTROL_PANEL_SET_TO_TARGET_COLOR_BUTTON_PORT = 1;

	// Joysticks
	public Joystick driverStick = new Joystick(DRIVER_STICK_PORT);
	public Joystick codriverStick = new Joystick(CODRIVER_STICK_PORT);

	// Buttons
	public Button exampleSubsystemMethod = new JoystickButton(driverStick, 1);

	public Button liftUpButton = new JoystickButton(codriverStick, LIFT_UP_BUTTON_PORT);
	public Button liftDownButton = new JoystickButton(codriverStick, LIFT_DOWN_BUTTON_PORT);

	public Button intakeFrontOnButton = new JoystickButton(driverStick, INTAKE_FRONT_ON_BUTTON_PORT);
	public Button intakeFrontOffButton = new JoystickButton(driverStick, INTAKE_FRONT_OFF_BUTTON_PORT);
	public Button intakeBackOnButton = new JoystickButton(driverStick, INTAKE_BACK_ON_BUTTON_PORT);
	public Button intakeBackOffButton = new JoystickButton(driverStick, INTAKE_BACK_OFF_BUTTON_PORT);
	public Button intakeUpButton = new JoystickButton(driverStick, INTAKE_UP_BUTTON_PORT);
	public Button intakeDownButton = new JoystickButton(driverStick, INTAKE_DOWN_BUTTON_PORT);

	public Button controlPanelSpinThreeTimesButton = new JoystickButton(driverStick,
			CONTROL_PANEL_SPIN_3_TIMES_BUTTON_PORT);
	public Button controlPanelSetToTargetButton = new JoystickButton(driverStick,
			CONTROL_PANEL_SET_TO_TARGET_COLOR_BUTTON_PORT);

	// Constructor to set all of the commands and buttons
	public OI() {
		// telling the button that when its pressed to execute example command with the
		// robot container's instance of example subsystem

		liftUpButton.whenPressed(new LiftUpCommand(RobotMap.robotContainer.liftSubsystem));
		liftDownButton.whenPressed(new LiftDownCommand(RobotMap.robotContainer.liftSubsystem));

		intakeUpButton.whenPressed(new IntakeUpCommand(RobotMap.robotContainer.intakeUpDownSubsystem));
		intakeDownButton.whenPressed(new IntakeDownCommand(RobotMap.robotContainer.intakeUpDownSubsystem));
		intakeFrontOnButton.whenPressed(new IntakeOnCommand(RobotMap.robotContainer.frontIntakeMotorOnOffSubsystem));
		intakeFrontOffButton.whenPressed(new IntakeOffCommand(RobotMap.robotContainer.frontIntakeMotorOnOffSubsystem));
		intakeBackOnButton.whenPressed(new IntakeOnCommand(RobotMap.robotContainer.backIntakeMotorOnOffSubsystem));
		intakeBackOffButton.whenPressed(new IntakeOffCommand(RobotMap.robotContainer.backIntakeMotorOnOffSubsystem));

		controlPanelSpinThreeTimesButton
				.whenPressed(new RotateControlPanelCommand(RobotMap.robotContainer.controlPanelColorSubsystem));
		controlPanelSetToTargetButton
				.whenPressed(new SetoToTargetColorCommand(RobotMap.robotContainer.controlPanelColorSubsystem));

		exampleSubsystemMethod.whenPressed(new ExampleCommand(RobotMap.robotContainer.m_ExampleSubsystem));

	}
}
