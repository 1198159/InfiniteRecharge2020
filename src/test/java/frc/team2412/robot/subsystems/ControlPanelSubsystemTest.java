package frc.team2412.robot.subsystems;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revrobotics.ColorSensorV3;
import com.robototes.helpers.MockButton;
import com.robototes.helpers.MockHardwareExtension;
import com.robototes.helpers.TestWithScheduler;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.team2412.robot.Commands.ControlPanelCommands.RotateControlPanelCommand;
import frc.team2412.robot.Commands.ControlPanelCommands.SetToTargetColorCommand;
import frc.team2412.robot.Subsystems.ControlPanelColorSubsystem;

// This is an example test of the robot. This is to make sure that everything is working as intended before code goes on a robot.
public class ControlPanelSubsystemTest {

	// Mock instance of Example Subsystem
	ControlPanelColorSubsystem realControlPanelColorSubsystem;
	ColorSensorV3 mockedColorSensor;
	Talon mockedColorMotor;

	// This method is run before the tests begin. initialize all mocks you wish to
	// use in multiple functions here. Copy and paste this function in your own test
	@Before
	public void before() {
		TestWithScheduler.schedulerStart();
		TestWithScheduler.schedulerClear();
		MockHardwareExtension.beforeAll();

		mockedColorSensor = mock(ColorSensorV3.class);
		mockedColorMotor = mock(Talon.class);

		realControlPanelColorSubsystem = new ControlPanelColorSubsystem(mockedColorSensor, mockedColorMotor);
	}

	// This test makes sure that the example command calls the .subsystemMethod of
	// example subsystem
	@Test
	public void RotateControlPanelCommandOnControlPanelColorSubsystemCallsMotorandSensor() {
		// Reset the subsystem to make sure all mock values are reset
		reset(mockedColorMotor);
		reset(mockedColorSensor);

		// Create command
		RotateControlPanelCommand rotateControlPanelCommand = new RotateControlPanelCommand(
				realControlPanelColorSubsystem);

		// Create a fake button that will be "pressed"
		MockButton fakeButton = new MockButton();

		// Tell the button to run example command when pressed
		fakeButton.whenPressed(rotateControlPanelCommand);

		// Push the button and run the scheduler once
		fakeButton.push();
		CommandScheduler.getInstance().run();
		fakeButton.release();

		// Verify that subsystemMethod was called once
		verify(realControlPanelColorSubsystem, times(1)).rotateControlPanel();

		// Clear the scheduler
		TestWithScheduler.schedulerClear();
	}

	public void SetToTargetColorCommandOnControlPanelColorSubsystemCallsMotorandSensor() {
		// Reset the subsystem to make sure all mock values are reset
		reset(realControlPanelColorSubsystem);

		// Create command
		SetToTargetColorCommand setToTargetColorCommand = new SetToTargetColorCommand(realControlPanelColorSubsystem);

		// Create a fake button that will be "pressed"
		MockButton fakeButton = new MockButton();

		// Tell the button to run example command when pressed
		fakeButton.whenPressed(setToTargetColorCommand);

		// Push the button and run the scheduler once
		fakeButton.push();
		CommandScheduler.getInstance().run();
		fakeButton.release();

		// Verify that subsystemMethod was called once
		verify(realControlPanelColorSubsystem, times(1)).setToTargetColor();

		// Clear the scheduler
		TestWithScheduler.schedulerClear();
	}

	// This is called after tests, and makes sure that nothing is left open and
	// everything is ready for the next test class
	@After
	public void after() {
		TestWithScheduler.schedulerDestroy();
		MockHardwareExtension.afterAll();
	}

}
