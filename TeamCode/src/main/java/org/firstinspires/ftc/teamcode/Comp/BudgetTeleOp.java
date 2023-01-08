/*
 * MECANUM/HOLOMETRIC MODE TELE OP MODE
 */

package org.firstinspires.ftc.teamcode.Comp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware.HardwareBudgetRobot;

@Config
@TeleOp(name="BudgetTeleOp", group="Comp")
public class BudgetTeleOp extends LinearOpMode {

    HardwareBudgetRobot robot = new HardwareBudgetRobot(this);
    static final int lowHeight = -1376;
    static final int mediumHeight = -2150;
    static final int groundHeight = -200;
    static final int floorHeight = 0;

    @Override
    public void runOpMode() {
        robot.init();


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            double x = -gamepad1.left_stick_y;
            double y = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;


            //int currentPosition = robot.arm.getCurrentPosition();
            //Servo to open/close hand

            

            //Used to ensure same ratio and contain values between [-1,1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;



            double throtte_control = 0.5;
            double slowDown = 1;
            if(gamepad1.right_trigger > 0)
                slowDown -= 0.5;

            robot.motor1.setPower(frontLeftPower*throtte_control*slowDown);
            robot.motor2.setPower(backLeftPower*throtte_control*slowDown);
            robot.motor3.setPower(frontRightPower*throtte_control*slowDown);
            robot.motor4.setPower(backRightPower*throtte_control*slowDown);
            //robot.hand.setPosition(gripPower);
/*
            telemetry.addData("frontLeft:", frontLeftPower);
            telemetry.addData("backLeft:", backLeftPower);
            telemetry.addData("frontRight:", frontRightPower);
            telemetry.addData("backRight:", backRightPower);
            telemetry.update();
*/


            /* INTAKE */
            if (gamepad2.y) {
                robot.arm.setTargetPosition(lowHeight);
                robot.arm.setPower(.5);
                robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            } else if (gamepad2.a) {
                robot.arm.setTargetPosition(floorHeight);
                robot.arm.setPower(.5);
                robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            }
            else if (gamepad2.x) {
                robot.arm.setTargetPosition(groundHeight);
                robot.arm.setPower(.5);
                robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            else if (gamepad2.b){
                robot.arm.setTargetPosition(lowHeight);
                robot.arm.setPower(.5);
                robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            else if (gamepad2.y) {
                robot.arm.setTargetPosition(mediumHeight);
                robot.arm.setPower(.5);
                robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            else if (gamepad2.left_stick_y * -1 > 0) { //up
                robot.arm.setTargetPosition(robot.arm.getCurrentPosition() - 50);
                robot.arm.setPower(.5);
                robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            else if (gamepad2.left_stick_y * -1 < 0) { //down
                robot.arm.setTargetPosition(robot.arm.getCurrentPosition() + 40);
                robot.arm.setPower(.5);
                robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            if(gamepad2.left_trigger > 0) { //close
                robot.hand.setPower(1);
            }
            else if(gamepad2.right_trigger > 0) {  //open
                robot.hand.setPower(-1);
            }
            else {
                robot.hand.setPower(0);
            }
            motorTelemetry();
        }
    }

    void motorTelemetry() {
        telemetry.addData("Arm", robot.arm.getCurrentPosition());
        telemetry.update();
    }
}
