package com.leeburch;

import java.awt.event.KeyEvent;
import java.awt.AWTException;
import java.awt.Robot;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

/**
 * Toggle scroll lock to prevent idle
 */
public class StopIdleApp {
	final static int SPIN_DELAY = 250;
	final static String SPIN_CHARACTERS = "|/-|/-\\";
	final static String BANNER = "  ______     __                          ______       __  __           \n"
			+ " /      \\   |  \\                        |      \\     |  \\|  \\          \n"
			+ "|  $$$$$$\\ _| $$_     ______    ______   \\$$$$$$ ____| $$| $$  ______  \n"
			+ "| $$___\\$$|   $$ \\   /      \\  /      \\   | $$  /      $$| $$ /      \\ \n"
			+ " \\$$    \\  \\$$$$$$  |  $$$$$$\\|  $$$$$$\\  | $$ |  $$$$$$$| $$|  $$$$$$\\\n"
			+ " _\\$$$$$$\\  | $$ __ | $$  | $$| $$  | $$  | $$ | $$  | $$| $$| $$    $$\n"
			+ "|  \\__| $$  | $$|  \\| $$__/ $$| $$__/ $$ _| $$_| $$__| $$| $$| $$$$$$$$\n"
			+ " \\$$    $$   \\$$  $$ \\$$    $$| $$    $$|   $$ \\\\$$    $$| $$ \\$$     \\\n"
			+ "  \\$$$$$$     \\$$$$   \\$$$$$$ | $$$$$$$  \\$$$$$$ \\$$$$$$$ \\$$  \\$$$$$$$\n"
			+ "                              | $$                                     \n"
			+ "                              | $$                                     \n"
			+ "                               \\$$                                     \n";

	public static void main(String[] args) {
		ArgumentParser parser = ArgumentParsers.newFor("StopIdle").build().defaultHelp(true)
				.description("Stop computer from going \"idle\" to prevent things such as screen lock.");
		parser.addArgument("-q", "--quiet").action(Arguments.storeTrue()).setDefault(false)
				.help("Do not print startup banner");
		parser.addArgument("-v", "--verbose").action(Arguments.storeTrue()).setDefault(false)
				.help("Print messages when toggling keyboard toggle");
		parser.addArgument("-t", "--type").choices("scroll", "caps", "number").setDefault("scroll")
				.help("Specify which keyboard toggle to toggle");
		parser.addArgument("-p", "--period").setDefault(60).help("time in seconds between toggle");
		Namespace ns = null;
		try {
			ns = parser.parseArgs(args);
		} catch (ArgumentParserException e) {
			parser.handleError(e);
			System.exit(1);
		}

		int lockToToggle = KeyEvent.VK_SCROLL_LOCK;

		switch (ns.getString("type")) {
		case "scroll":
			lockToToggle = KeyEvent.VK_SCROLL_LOCK;
			break;
		case "caps":
			lockToToggle = KeyEvent.VK_CAPS_LOCK;
			break;
		case "number":
			lockToToggle = KeyEvent.VK_NUM_LOCK;
			break;
		}
		if (!ns.getBoolean("quiet")) {
			System.out.println(BANNER);
		}

		int period = Integer.parseInt(ns.getString("period"));
		boolean state = true;
		int spinPosition = 0;

		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
			System.exit(1);
		}

		while (true) {
			if (robot != null) {
				if (ns.getBoolean("verbose") == true) {
					System.out.println("\b" + ns.getString("type") + " lock to: " + state);
				}
				robot.keyPress(lockToToggle);
				robot.keyPress(lockToToggle);				
			} else {
				// should never be here
				System.exit(1);
			}
			

			state = !state;
			long toggleTime = System.currentTimeMillis() + period * 1000;
			while (System.currentTimeMillis() < toggleTime) {
				try {
					if (!ns.getBoolean("quiet")) {
						System.out.print("\b" + SPIN_CHARACTERS.charAt(spinPosition));
						spinPosition = (spinPosition + 1) % SPIN_CHARACTERS.length();
					}
					Thread.sleep(SPIN_DELAY);
				} catch (InterruptedException e) {
					System.out.println("Sleep interrupted");
				}
			}
		}
	}
}
