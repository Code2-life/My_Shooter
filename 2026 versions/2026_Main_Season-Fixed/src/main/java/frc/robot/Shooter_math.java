package frc.robot;

import org.opencv.core.Mat;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Shooter_math {
    double velocity;
    double distance;
    double angle;
    double gravity;
    double height_of_target;

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry ty = table.getEntry("ty");
    double targetOffsetAngle_Vertical = ty.getDouble(0.0);

    // how many degrees back is your limelight rotated from perfectly vertical?
    double limelightMountAngleDegrees = 25.0; 

    // distance from the center of the Limelight lens to the floor
    double limelightLensHeightInches = 20.0; 

    // distance from the target to the floor
    double goalHeightInches = 60.0; 

    double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
    double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

    //calculate distance
    double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);

    public Shooter_math(){
        velocity = 0; // the velocity of the shooter in m/s

        distance = distanceFromLimelightToGoalInches*3.28084; // convert inches to meters
        
        angle = 0; // the angle of the shooter in degrees
        
        gravity = 9.81; // gravity in m/s^2
        
        height_of_target = 1.6002; // the distance from the top of the robot to the target in the y direction in meters
    }

    /** @return the minimum velocity needed to shoot the ball into the target
     */
    public double min_velocity(){
        velocity = Math.sqrt(gravity*(height_of_target+Math.sqrt(Math.pow(height_of_target, 2)+Math.pow(distance, 2))));
        // The above formula is used to calculate the minimum velocity need to shoot the projectile. It is from a physics textbook.
        return velocity;
    }

    public double min_angle(){
        angle = Math.atan((height_of_target+Math.sqrt(Math.pow(height_of_target, 2)+Math.pow(distance, 2)))/distance); // min angle required to get to the distance
        return angle*(180/Math.PI); // convert radians to degrees
    }
    
}
