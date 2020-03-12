package frc.team2412.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team2412.robot.commands.climb.ClimbDeployRailsCommand;
import frc.team2412.robot.commands.climb.ClimbJoystickCommand;
import frc.team2412.robot.commands.climb.ClimbRetractRailsCommand;
import frc.team2412.robot.commands.drive.DriveCommand;
import frc.team2412.robot.commands.drive.DriveShiftToHighGearCommand;
import frc.team2412.robot.commands.drive.DriveShiftToLowGearCommand;
import frc.team2412.robot.commands.drive.OneJoystickDriveCommand;
import frc.team2412.robot.commands.indexer.IndexShootCommand;
import frc.team2412.robot.commands.indexer.IndexSpitCommand;
import frc.team2412.robot.commands.intake.back.IntakeBackDownCommand;
import frc.team2412.robot.commands.intake.back.IntakeBackInCommand;
import frc.team2412.robot.commands.intake.back.IntakeBackOffCommand;
import frc.team2412.robot.commands.intake.back.IntakeBackOutCommand;
import frc.team2412.robot.commands.intake.back.IntakeBackUpCommand;
import frc.team2412.robot.commands.intake.front.IntakeFrontDownCommand;
import frc.team2412.robot.commands.intake.front.IntakeFrontInCommand;
import frc.team2412.robot.commands.intake.front.IntakeFrontOffCommand;
import frc.team2412.robot.commands.intake.front.IntakeFrontOutCommand;
import frc.team2412.robot.commands.intake.front.IntakeFrontUpCommand;
import frc.team2412.robot.commands.lift.LiftDownCommand;
import frc.team2412.robot.commands.lift.LiftUpCommand;

//This is the class in charge of all the buttons and joysticks that the drivers will use to control the robot
public class OI {

	public static interface ButtonEnumInterface {

		public int getButtonID();

		public int getJoystickID();

		public default Button createFrom(Joystick stick) {
			if (stick.getPort() != getJoystickID()) {
				System.err.println("Warning! Binding button to the wrong stick!");
			}
			return new JoystickButton(stick, getButtonID());
		}

		public default Button createFrom(XboxController controller) {
			if (controller.getPort() != getJoystickID()) {
				System.err.println("Warning! Binding button to the wrong stick!");
			}
			return new Button(() -> controller.getRawButton(getButtonID()));
		}

		public default Button createFromPOV(XboxController controller) {
			if (controller.getPort() != getJoystickID()) {
				System.err.println("Warning! Binding button to the wrong stick!");
			}
			return new Button(() -> controller.getPOV() == getButtonID());
		}
	}

	public enum Joysticks {
		DRIVER(0), CODRIVER(2), CODRIVER_MANUAL(3);

		public int id;

		private Joysticks(int id) {
			this.id = id;
		}
	}

	public enum DriverControls implements ButtonEnumInterface {
		SHOOT(Joysticks.DRIVER, 1), SPIT(Joysticks.DRIVER, 2);

		public Joysticks stick;
		public int buttonID;

		private DriverControls(Joysticks stick, int buttonID) {
			this.stick = stick;
			this.buttonID = buttonID;
		}

		@Override
		public int getButtonID() {
			return this.buttonID;
		}

		@Override
		public int getJoystickID() {
			return stick.id;
		}

	}

	public enum CodriverControls implements ButtonEnumInterface {
		LIFT(7), FRONT_INTAKE_DOWN(2), BACK_INTAKE_DOWN(1), INTAKE_BACK_IN(4), INTAKE_BACK_OUT(3), INTAKE_FRONT_IN(6),INTAKE_FRONT_OUT(5);

		public int buttonID;

		private CodriverControls(int buttonID) {
			this.buttonID = buttonID;
		}

		@Override
		public int getButtonID() {
			return this.buttonID;
		}

		@Override
		public int getJoystickID() {
			return Joysticks.CODRIVER.id;
		}

	}

	public enum CodriverManualControls implements ButtonEnumInterface {
		CLIMB_MODE(5);

		public int buttonID;

		private CodriverManualControls(int buttonID) {
			this.buttonID = buttonID;
		}

		@Override
		public int getButtonID() {
			return this.buttonID;
		}

		@Override
		public int getJoystickID() {
			return Joysticks.CODRIVER_MANUAL.id;
		}

	}

	// Joysticks
	public final Joystick driverStick = new Joystick(Joysticks.DRIVER.id);
	//public final Joystick driverLeftStick = new Joystick(Joysticks.DRIVER_LEFT.id);
	public final Joystick codriverStick = new Joystick(Joysticks.CODRIVER.id);
	public final Joystick codriverManualStick = new Joystick(Joysticks.CODRIVER_MANUAL.id);

	// Driver Controls
	//public final Button shifter = DriverControls.SHIFT.createFrom(driverRightStick);
	public final Button indexerShootButton = DriverControls.SHOOT.createFrom(driverStick);
	public final Button indexerSpitButton = DriverControls.SPIT.createFrom(driverStick);
	
	//DISABLE BABY MODE
	public final Button disableBabyModeButton = new JoystickButton(driverStick, 4);
	// Lift Controls
	public final Button liftButton = CodriverControls.LIFT.createFrom(codriverStick);

	// Intake Controls
	public final Button frontIntakeUpDown = CodriverControls.FRONT_INTAKE_DOWN.createFrom(codriverStick);
	public final Button backIntakeUpDown = CodriverControls.BACK_INTAKE_DOWN.createFrom(codriverStick);
	public final Button intakeFrontIn = CodriverControls.INTAKE_FRONT_IN.createFrom(codriverStick);
	public final Button intakeFrontOut = CodriverControls.INTAKE_FRONT_OUT.createFrom(codriverStick);
	public final Button intakeBackIn = CodriverControls.INTAKE_BACK_IN.createFrom(codriverStick);
	public final Button intakeBackOut = CodriverControls.INTAKE_BACK_OUT.createFrom(codriverStick);

	// Climb Controls
	public final Button climbModeButton = CodriverManualControls.CLIMB_MODE.createFrom(codriverManualStick);

	// Constructor to set all of the commands and buttons
	public OI(RobotContainer robotContainer) {
		bindClimbControls(robotContainer);
		bindDriverControls(robotContainer);
		bindIntakeControls(robotContainer);
		bindLiftControls(robotContainer);
		bindIndexControls(robotContainer);
		disableBabyModeButton.whenPressed(new InstantCommand(() -> RobotState.babyMode = false));
		disableBabyModeButton.whenReleased(new InstantCommand(() -> RobotState.babyMode = true));
	}

	public void bindIndexControls(RobotContainer robotContainer) {
		if (!RobotMap.INDEX_CONNECTED) {
			return;
		}

		indexerSpitButton.whileHeld(new IndexSpitCommand(robotContainer.m_indexerMotorSubsystem,
				robotContainer.m_intakeFrontMotorSubsystem, robotContainer.m_intakeBackMotorSubsystem));

		// Crashes due to intakeBothUpCommand requiring the same subsystem twice
		Command indexShootCommand = new IndexShootCommand(robotContainer.m_indexerMotorSubsystem);

		indexerShootButton.whenPressed(indexShootCommand);

		indexerShootButton
				.whenReleased(new InstantCommand(() -> CommandScheduler.getInstance().cancel(indexShootCommand)));
	}

	public void bindIntakeControls(RobotContainer robotContainer) {
		if (!RobotMap.INTAKE_CONNECTED) {
			return;
		}

		frontIntakeUpDown.whenReleased(new IntakeFrontDownCommand(robotContainer.m_intakeFrontPneumaticSubsystem)
				.alongWith(new IntakeFrontOffCommand(robotContainer.m_intakeFrontMotorSubsystem)));
		frontIntakeUpDown.whenPressed(new IntakeFrontUpCommand(robotContainer.m_intakeFrontPneumaticSubsystem)
				.alongWith(new IntakeFrontInCommand(robotContainer.m_intakeFrontMotorSubsystem)));

		backIntakeUpDown.whenReleased(new IntakeBackDownCommand(robotContainer.m_intakeBackPneumaticSubsystem)
				.alongWith(new IntakeBackOffCommand(robotContainer.m_intakeBackMotorSubsystem)));
		backIntakeUpDown.whenPressed(new IntakeBackUpCommand(robotContainer.m_intakeBackPneumaticSubsystem)
				.alongWith(new IntakeBackInCommand(robotContainer.m_intakeBackMotorSubsystem)));

		
		intakeFrontIn.whenReleased(new IntakeFrontOffCommand(robotContainer.m_intakeFrontMotorSubsystem));

		intakeFrontOut.whenPressed(new IntakeFrontOutCommand(robotContainer.m_intakeFrontMotorSubsystem));
		intakeFrontOut.whenReleased(new IntakeFrontOffCommand(robotContainer.m_intakeFrontMotorSubsystem));

		intakeBackIn.whenReleased(new IntakeBackOffCommand(robotContainer.m_intakeBackMotorSubsystem));

		intakeBackOut.whenPressed(new IntakeBackOutCommand(robotContainer.m_intakeBackMotorSubsystem));
		intakeBackOut.whenReleased(new IntakeBackOffCommand(robotContainer.m_intakeBackMotorSubsystem));

		intakeFrontIn.whileHeld(new IntakeFrontInCommand(robotContainer.m_intakeFrontMotorSubsystem));

		intakeBackIn.whileHeld(new IntakeBackInCommand(robotContainer.m_intakeBackMotorSubsystem));
		

	}

	public void bindClimbControls(RobotContainer robotContainer) {
		if (!RobotMap.CLIMB_CONNECTED) {
			return;
		}

		climbModeButton.whileHeld(new ClimbJoystickCommand(codriverManualStick, robotContainer.m_climbMotorSubsystem));
		climbModeButton.whenPressed(new ClimbDeployRailsCommand(robotContainer.m_climbLiftSubsystem));
		climbModeButton.whenReleased(new ClimbRetractRailsCommand(robotContainer.m_climbLiftSubsystem));
	}

	public void bindLiftControls(RobotContainer robotContainer) {
		if (!RobotMap.LIFT_CONNECTED) {
			return;
		}

		liftButton
				.whenPressed(new LiftUpCommand(robotContainer.m_liftSubsystem, robotContainer.m_indexerMotorSubsystem));
		liftButton.whenReleased(
				new LiftDownCommand(robotContainer.m_liftSubsystem, robotContainer.m_indexerMotorSubsystem));
	}

	public void bindDriverControls(RobotContainer robotContainer) {
		if (!RobotMap.DRIVE_BASE_CONNECTED) {
			return;
		}

		robotContainer.m_driveBaseSubsystem.setDefaultCommand(
				new OneJoystickDriveCommand(robotContainer.m_driveBaseSubsystem, driverStick));

		//shifter.whenPressed(new DriveShiftToHighGearCommand(robotContainer.m_driveBaseSubsystem));
		///shifter.whenReleased(new DriveShiftToLowGearCommand(robotContainer.m_driveBaseSubsystem));
	}

}
