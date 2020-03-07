if (cmd[0].equalsIgnoreCase("MOVE") && cmd.length == 6)
                        c = drive(cmd);
                    else if (cmd[0].equalsIgnoreCase("LIFT") && cmd.length == 2)
                        c = lift(cmd);
                    else if (cmd[0].equalsIgnoreCase("TILT") && cmd.length == 2)
                        c = tilt(cmd);
                    else if (cmd[0].equalsIgnoreCase("TURN") && cmd.length == 3)
                        c = turn(cmd);
                    else if (cmd[0].equalsIgnoreCase("SHOOT") && cmd.length == 4)
                        c = shoot(cmd);
                    else if (cmd[0].equalsIgnoreCase("INTAKE") && cmd.length == 2)
                        c = intake(cmd);
                    else if (cmd[0].equalsIgnoreCase("CLIMBER") && cmd.length == 3)
                        c = climber(cmd);
                    else if (cmd[0].equalsIgnoreCase("AUTOALIGN") && cmd.length == 1)
                        c = new AutoAlign(subsystems);
                        
- MOVE
    - Drive the robot normally, for an amount of time.
    - Parameters:
        - Time: Milliseconds to run for.
        - X-Speed: A value between -1.0 and 1.0. Using DifferentialDrive (Tank drive), this is always 0.
        - Y-Speed: A value between -1.0 and 1.0. Handles driving forwards and backwards.
        - R-Speed: A value between -1.0 and 1.0. Handles rotation factor.
        - QuickTurn: A value, either 0 and 1.
    - Examples:
        - ???
- LIFT
    - Handle the tilt for the shooter, up and down.
    - Parameters:
        - ``TOP``/``BOTTOM``/[TIME]: The position for the shooter to be, or the time to take.
        - Speed: A value between -1.0 and 1.0. A time must be provided in the previous one to count.
    - Examples:
        - ``LIFT TOP``
        - ``LIFT 1500``
- TILT
    -
    - Parameters:
        - ???
    - Examples:
        - ???
- TURN
    -
    - Parameters:
        - ???
    - Examples:
        - ???
- SHOOT
    -
    - Parameters:
        - ???
    - Examples:
        - ````
- NEXT
    - Move to the next empty slot.
    - Parameters:
        - ???
    - Examples:
        - ````
- INTAKE
    -
    - Parameters:
        - ???
    - Examples:
        - ````
- CLIMBER
    - Handle the climber's motors.
    - Parameters:
        - Time: Milliseconds to run for.
        - Speed: A value between -1.0 and 1.0.
    - Examples:
        - ``CLIMBER``
- AUTOALIGN
    - Summon the default AutoAlign. Requires a working vision controller, like the LimeLightLite.
    - Parameters: None
    - Examples:
        - ``AUTOALIGN``
- PARALLEL
    - Allows for multiple commands to run at the same time. Commands must not interfere with each other (aka require different subsystems.)
    - Parameters:
        - ``START``/``END``: Start or end a parallel sequence.
    - Examples:
- DELAY
    - Delays between two actions. Often used in parallel structures.
    - Parameters:
        - Time: Milliseconds to wait.
    - Examples:
        - ``DELAY 5000``
- PRINT
    - Prints out a message to the standard output.
    - Parameters:
        - Message. Can include spaces.
    - Examples:
        - ``PRINT THIS IS false``
- END
    - Ends the autonomous script.
    - Parameters: None
    - Examples:
        - ``END``