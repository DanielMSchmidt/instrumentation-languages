/***************************************************************************
 * Copyright 2013 by
 *  + Christian-Albrechts-University of Kiel
 *    + Department of Computer Science
 *      + Software Engineering Group 
 *  and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package de.cau.cs.se.kieker.service.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import kieker.common.configuration.Configuration;
import kieker.common.record.IMonitoringRecord;
import kieker.monitoring.core.configuration.ConfigurationFactory;

import de.cau.cs.se.kieker.service.IServiceListener;
import de.cau.cs.se.kieker.service.ServiceContainer;
import de.cau.cs.se.kieker.service.connector.IServiceConnector;
import de.cau.cs.se.kieker.service.connector.ServiceConnectorFactory;

/**
 * @author rju
 * 
 */
public final class CLIServerMain {

	private static boolean verbose;
	private static boolean stats;
	private static long startTime;
	private static long deltaTime;

	private static Options options;
	private static CommandLine commandLine;

	/**
	 * 
	 */
	private CLIServerMain() {

	}

	/**
	 * CLS server main.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(final String[] args) {
		declareOptions();
		try {
			// parse the command line arguments
			commandLine = new GnuParser().parse(options, args);

			Map<Integer, Class<IMonitoringRecord>> recordMap = null;

			// verbosity setup
			verbose = commandLine.hasOption("v");

			// statistics
			stats = commandLine.hasOption("s");

			// Find libraries and setup mapping
			recordMap = createRecordMap();

			// kieker setup
			Configuration configuration = null;
			if (commandLine.hasOption("k")) {
				configuration = ConfigurationFactory.createConfigurationFromFile(commandLine
						.getOptionValue("k"));
			} else {
				configuration = ConfigurationFactory.createDefaultConfiguration();
			}

			// start service depending on type
			if (commandLine.hasOption("type")) {
				runService(new ServiceContainer (configuration, createService(recordMap)));
			}
		} catch (ParseException exp) {
			// oops, something went wrong
			usage("Parsing failed.  Reason: " + exp.getMessage(), 20);
		}
	}

	/**
	 * Execute the bridge service.
	 * 
	 * @param service The service to be executed
	 */
	private static void runService(final ServiceContainer service) {
		if (verbose) {
			final String updateInterval = commandLine.getOptionValue("v");
			service.setListenerUpdateInterval((updateInterval != null) ? Integer.parseInt(updateInterval) : 100);
			service.addListener(new IServiceListener() {

				@Override
				public void handleEvent(final long count, final String message) {
					System.out.print("Received " + count + " records\r");
					if (message != null) {
						System.out.println("\n" + message);
					}
				}

			});
		}

		try {
			if (stats) {
				startTime = System.nanoTime();
			}
			// run the service
			service.run();
			if (stats) {
				deltaTime = System.nanoTime() - startTime;
			}
			if (verbose) {
				System.out.println("TCP server stopped.");
			}
			if (stats) {
				System.out.println("Execution time: " + deltaTime + " ns  " + deltaTime
						/ 1000000000 + " s");
				System.out.println("Time per records: " + deltaTime
						/ service.getRecordCount() + " ns/r");
				System.out.println("Records per second: "
						+ ((double) service.getRecordCount())
						/ ((double) deltaTime / 1000000000));
			}
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			System.err.println("CLIServerMain cannot start. Cause: " + e.getMessage());
		}

	}

	/**
	 * Create a record map of classes implementing IMonitoringRecord interface out of libraries
	 * with such classes and a textual mapping file.
	 * 
	 * @return A record map. null is never returned as a call of usage terminates the program.
	 */
	private static Map<Integer, Class<IMonitoringRecord>> createRecordMap() {
		Map<Integer, Class<IMonitoringRecord>> recordMap = null;
		if (commandLine.hasOption("L")) {
			final String[] libraries = commandLine.getOptionValues("L");

			if (commandLine.hasOption("m")) {
				recordMap = readMapping(libraries, commandLine.getOptionValue("m"));
				if (recordMap.size() == 0) {
					usage("At least one mapping must be specified in the mapping file.", 5);
				}
			} else {
				usage("Mapping file is required.", 5);
			}
		} else {
			usage("At least one library reference is required.", 5);
		}

		return recordMap;
	}

	/**
	 * Interpret command line type option.
	 * 
	 * @param recordList
	 *            the map for ids to Kieker records
	 * 
	 * @return a reference to an ServiceContainer
	 */
	private static IServiceConnector createService(final Map<Integer, Class<IMonitoringRecord>> recordList) {
		if ("tcp-client".equals(commandLine.getOptionValue("type"))) {
			return createTCPClientService(recordList);
		} else if ("tcp-single-server".equals(commandLine.getOptionValue("type"))) {
			return createTCPSingleServerService(recordList);
		} else if ("tcp-server".equals(commandLine.getOptionValue("type"))) {
			return createTCPMultiServerService(recordList);
		} else if ("jms-client".equals(commandLine.getOptionValue("type"))) {
			return createJMSService(recordList);
		} else if ("jms-embedded".equals(commandLine.getOptionValue("type"))) {
			return createJMSEmbeddedService(recordList);
		} else {
			usage("Unknown service type: '" + commandLine.getOptionValue("type") + "'", 10);
			return null;
		}
	}

	/**
	 * Create a JMSEmbeddedService.
	 * 
	 * @param recordList
	 *            the map for ids to Kieker records
	 * 
	 * @return a reference to an ServiceContainer
	 */
	private static IServiceConnector createJMSEmbeddedService(final Map<Integer, Class<IMonitoringRecord>> recordList) {
		if (commandLine.hasOption("port")) {
			final int port = Integer.parseInt(commandLine.getOptionValue("port"));
			try {
				return ServiceConnectorFactory.createJMSEmbeddedServiceConnector(recordList, port);
			} catch (URISyntaxException e) {
				usage("JMS service cannot be started. URI problem.", 10);
				return null;
			}
		} else {
			usage("Missing port for embedded server.", 10);
			return null;
		}
	}

	/**
	 * Create a JMSService.
	 * 
	 * @param recordList
	 *            the map for ids to Kieker records
	 * 
	 * @return a reference to an ServiceContainer
	 */
	private static IServiceConnector createJMSService(final Map<Integer, Class<IMonitoringRecord>> recordList) {
		final String username = commandLine.hasOption("u") ? commandLine.getOptionValue("u") : null;
		final String password = commandLine.hasOption("w") ? commandLine.getOptionValue("w") : null;

		if (commandLine.hasOption("url")) {
			try {
				return ServiceConnectorFactory.createJMSServiceConnector(recordList, username, password, new URI(commandLine.getOptionValue("url")));
			} catch (URISyntaxException e) {
				usage(commandLine.getOptionValue("url") + " is not a valid URI. JMS service cannot be started.", 10);
				return null;
			}
		} else {
			usage("Missing URL for JMS service", 10);
			return null;
		}
	}

	/**
	 * Create a TCPSingleServerService.
	 * 
	 * @param recordList
	 *            the map for ids to Kieker records
	 * 
	 * @return a reference to an ServiceContainer
	 */
	private static IServiceConnector createTCPSingleServerService(final Map<Integer, Class<IMonitoringRecord>> recordList) {
		if (commandLine.hasOption("port")) {
			final int port = Integer.parseInt(commandLine.getOptionValue("port"));
			final IServiceConnector service = ServiceConnectorFactory.createTCPSingleServerServiceConnector(recordList, port);
			if (verbose) {
				System.out.println("TCP server listening at " + port);
			}
			return service;
		} else {
			usage("Missing port for tcp server", 10);
			return null;
		}
	}

	/**
	 * Create a TCPMultiServerService.
	 * 
	 * @param recordList
	 *            the map for ids to Kieker records
	 * 
	 * @return a reference to an ServiceContainer
	 */
	private static IServiceConnector createTCPMultiServerService(final Map<Integer, Class<IMonitoringRecord>> recordList) {
		if (commandLine.hasOption("port")) {
			final int port = Integer.parseInt(commandLine.getOptionValue("port"));
			final IServiceConnector service = ServiceConnectorFactory.createTCPMultiServerServiceConnector(recordList, port);
			if (verbose) {
				System.out.println("TCP server listening at " + port);
			}
			return service;
		} else {
			usage("Missing port for tcp server", 10);
			return null;
		}
	}

	/**
	 * Create a TCPCLientService.
	 * 
	 * @param recordList
	 *            the map for ids to Kieker records
	 * 
	 * @return a reference to an ServiceContainer
	 */
	private static IServiceConnector createTCPClientService(final Map<Integer, Class<IMonitoringRecord>> recordList) {
		if (commandLine.hasOption("port")) {
			if (commandLine.hasOption("host")) {
				final int port = Integer.parseInt(commandLine.getOptionValue("port"));
				final String hostname = commandLine.getOptionValue("host");
				final IServiceConnector service = ServiceConnectorFactory.createTCPClientServiceConnector(recordList, hostname, port);
				if (verbose) {
					System.out.println("TCP client connected to " + hostname + ":"
							+ port);
				}
				return service;
			} else {
				usage("Missing hostname for tcp client", 10);
			}
		} else {
			usage("Missing port for tcp client", 10);
		}
		return null;
	}

	/**
	 * Print out the server usage and an additional message describing the cause of the failure. Finally terminate the server.
	 * 
	 * @param message
	 *            the message to be printed
	 * @param code
	 *            the exit code
	 */
	private static void usage(final String message, final int code) {
		System.err.println(message);
		final HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("cli-kieker-service", options, true);
		System.exit(code);
	}

	/**
	 * Read the CLI server Kieker classes to id mapping file.
	 * 
	 * @param libraries
	 *            array representing a list of library files (*.jar)
	 * @param filename
	 *            the path of the mapping file.
	 * 
	 * @return a complete IMonitoringRecord to id mapping
	 */
	@SuppressWarnings("unchecked")
	private static Map<Integer, Class<IMonitoringRecord>> readMapping(final String[] libraries,
			final String filename) {
		final Map<Integer, Class<IMonitoringRecord>> map = new HashMap<Integer, Class<IMonitoringRecord>>();
		final URL[] urls = new URL[libraries.length];
		for (int i = 0; i < libraries.length; i++) {
			try {
				urls[i] = new File(libraries[i]).toURI().toURL();
			} catch (MalformedURLException e) {
				System.err.println(libraries[i] + " is not a valid URL");
				System.exit(3);
			}
		}

		final URLClassLoader classLoader = new URLClassLoader(urls, CLIServerMain.class.getClassLoader());

		try {
			final BufferedReader in = new BufferedReader(new FileReader(filename));
			String line = null;
			do {
				try {
					line = in.readLine();
					if (line != null) {
						final String[] pair = line.split("=");
						if (pair.length == 2) {
							final Class<?> clazz = classLoader.loadClass(pair[1]);
							map.put(Integer.parseInt(pair[0]), (Class<IMonitoringRecord>) clazz);
						}
					}
				} catch (IOException e) {
					System.err.println("Mapping file \"" + filename + "\" read error. "
							+ e.getMessage());
					System.exit(2);
				} catch (ClassNotFoundException e) {
					System.err.println(e.getMessage());
				}
			} while (line != null);
			in.close();
		} catch (FileNotFoundException e) {
			System.err.println("Mapping file \"" + filename + "\" not found.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Mapping file \"" + filename + "\" read error. " + e.getMessage());
			System.exit(2);
		}

		return map;
	}

	/**
	 * Compile the options for the CLI server.
	 * 
	 * @return The composed options for the CLI server
	 */
	private static Options declareOptions() {
		options = new Options();
		Option option;

		// Type selection
		option = new Option("t", "type", true,
				"select the service type: tcp-client, tcp-server, tcp-single-server, jms-client, jms-embedded");
		option.setArgName("type");
		option.setRequired(true);
		options.addOption(option);

		// TCP client
		option = new Option("h", "host", true, "connect to server named <hostname>");
		option.setArgName("hostname");
		options.addOption(option);

		// TCP server
		option = new Option("p", "port", true,
				"listen at port (tcp-server or jms-embedded) or connect to port (tcp-client)");
		option.setArgName("number");
		option.setType(Number.class);
		options.addOption(option);

		// JMS client
		option = new Option("u", "user", true, "user name for a JMS service");
		option.setArgName("username");
		options.addOption(option);
		option = new Option("w", "password", true, "password for a JMS service");
		option.setArgName("password");
		options.addOption(option);
		option = new Option("l", "url", true, "URL for JMS server");
		option.setArgName("jms-url");
		option.setType(URL.class);
		options.addOption(option);

		// kieker configuration file
		option = new Option("k", "kieker", true, "kieker configuration file");
		option.setArgName("configuration");
		options.addOption(option);

		// mapping file for TCP and JMS
		option = new Option("m", "map", true, "Class name to id (integer or string) mapping");
		option.setArgName("map-file");
		option.setType(File.class);
		option.setRequired(true);
		options.addOption(option);

		// libraries
		option = new Option("L", "libraries", true, "List of library paths separated by " + File.pathSeparatorChar);
		option.setArgName("paths");
		option.setType(File.class);
		option.setRequired(true);
		option.setValueSeparator(File.pathSeparatorChar);
		options.addOption(option);

		// verbose
		option = new Option("v", "verbose", true, "output processing information");
		option.setRequired(false);
		option.setOptionalArg(true);
		options.addOption(option);

		// statistics
		option = new Option("s", "stats", false, "output performance statistics");
		option.setRequired(false);
		options.addOption(option);

		// daemon mode
		option = new Option("d", "daemon", false, "detach from console; TCP server allows multiple connections");
		option.setRequired(false);
		options.addOption(option);

		return options;
	}
}
