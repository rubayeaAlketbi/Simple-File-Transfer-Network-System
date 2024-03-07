//
// Specimen answer to the worksheet question. (i), (ii) etc. refer to subquestions.
//
// Compile with: javac ParseCmdLineArgs.java
// Execute with: java ParseCmdLineArgs arg1 arg2 arg3 ...
//

public class ParseCmdLineArgs {

	private String[] cmdLineArgs;

	// The sole constructor, which expects the command line arguments to be provided as a String array.
	public ParseCmdLineArgs( String[] args )
	{
		// (i) Output an error message and quit if there are no command line arguments.
		if( args.length==0 )
		{
			System.out.println( "No command line arguments; quitting." );
			return;
		}

		// (ii) Store the command line arguments in a persistent, private array.
		cmdLineArgs = args;

		// If still here, must have at least 1 command line argument. Print each to stdout, one per line, possibly with additional messages
		// as specified in the instructions.
		for( int i=0; i<cmdLineArgs.length; i++ )
		{
			System.out.print( "Argument " + i + " : " + cmdLineArgs[i] );		// (iii)

			// (iv) If there are any dots, append the message that it may be a hostname.
			if( cmdLineArgs[i].contains(".") ) System.out.print( " (may be a hostname)" );

			// (v) If it has exactly 3 dots, also append a message that it may be an IPv4 address. Various ways of doing this, this just 
			// seemed the nost obvious way to me (note the '[' and ']' are needed as split() expects a regular expression).
			if( cmdLineArgs[i].split("[.]").length == 4 ) System.out.print( " (may be an IPv4 address)" );

			// End the line.
			System.out.println();
		}
	}

    // main(): This is the function that is called after executing with 'java ParseCmdLineArgs'.
	// Any command line arguments are passed as the string array 'String[] args', i.e. if you execute the code with
	//   java ParseCmdLineArgs arg1 arg2 arg3
	// then String[] args will be an array of length 3, containing the strings 'arg1', arg2', and arg3.'
	public static void main( String[] args )
	{
		ParseCmdLineArgs parser = new ParseCmdLineArgs(args);
	}
}