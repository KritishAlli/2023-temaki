// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.spline.PoseWithCurvature;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

public class AutoBalance extends CommandBase {
  /** Creates a new autoBalance. */

  Swerve swerve;
  Translation2d tilt;
  Translation2d initialTilt;
  

  public AutoBalance() {
    swerve = Swerve.getInstance();
    addRequirements(swerve);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      initialTilt = new Translation2d(getRoll(), getPitch());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    SmartDashboard.putNumber("roll", getRoll());
    SmartDashboard.putNumber("pitch", getPitch());

    tilt = new Translation2d(getRoll(), getPitch()); // TODO: switch them?? 
    SmartDashboard.putNumber("autobalance norm", tilt.getNorm());
    swerve.drive(tilt.times(Constants.kAutoBalance.MAX_SPEED), 0, false, false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    swerve.drive(new Translation2d(0, 0), 0.01, false, false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return initialTilt.getNorm() - tilt.getNorm() > 4 || tilt.getNorm() < 1;
  }

  private double getRoll() {
    return swerve.getGyro().getRoll();
  }

  private double getPitch() {
    return swerve.getGyro().getPitch();
  }
}
