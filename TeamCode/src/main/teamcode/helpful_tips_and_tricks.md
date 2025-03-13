# Helpful Tips and Tricks

<pre>
This file is here to express 
information for the programmers of 
Tobor on how to program for the
FTC season Into The Deep.
</pre>

## Built in Code to Remember

<p>Create motor variables as follows:</p>
<pre>
DcMotor fl;
DcMotor fr;
DcMotor bl;
DcMotor br;
</pre>


## Inner Code Methods
<h3>I made helpful methods/functions for all programmers here, this makes it easier and quicker to code!</h3>

### Use the following commands to make the robot move in different directions
<pre>
turn(90); // This command that I made makes the robot turn 90 degrees.
// You can replace 90 with any number and it will turn that amount.

// use this command like this:
Turn angle = new Turn();
angle.turn(90);
// don't forget to import this:
import org.firstinspires.ftc.teamcode.innercode.operations.input.Turn;



setPower(1,1,1,1); // This command sets power to all four motors.
// Again, you can change any of the numbers to your desired value.

// Here is how to use it:
EasierMovement movement = new EasierMovement();
movement.setPower(1,1,1,1);
// don't forget to import this:
import org.firstinspires.ftc.teamcode.innercode.operations.output.EasierMovement;
</pre>