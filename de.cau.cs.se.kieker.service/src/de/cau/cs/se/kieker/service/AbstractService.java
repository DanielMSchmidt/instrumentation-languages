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

package de.cau.cs.se.kieker.service;

import java.util.ArrayList;
import java.util.Collection;

import kieker.common.configuration.Configuration;
import kieker.common.record.IMonitoringRecord;
import kieker.monitoring.core.controller.IMonitoringController;
/**
 * 
 */
import kieker.monitoring.core.controller.MonitoringController;

/**
 * @author rju
 * 
 */
public abstract class AbstractService {

	protected boolean active;    // is true when the service is running
	protected long recordCount;  // counter for received records
	
	private Configuration configuration;
	private Collection<IServiceListener> listeners;
	private long listenerUpdateInterval;
	private IMonitoringController kieker;
	private boolean respawn;
	
	/**
	 * @param configuration A configuration object for Kieker monitoring
	 */
	public AbstractService(final Configuration configuration) {
		this.setRespawn(false);
		this.configuration = configuration;
		this.listeners = new ArrayList<IServiceListener>();
		this.listenerUpdateInterval = 100;
	}

	/**
	 * Main loop of the Kieker bridge.
	 * 
	 * @throws Exception is may throw a wide range of exceptions, depending on the implementation of deserialize()
	 */
	public void run() throws Exception {
		this.kieker = MonitoringController.createInstance(this.configuration);
		do {
			sourceSetup();
			this.active = true;
			this.recordCount = 0;
			while (this.active) {
				final IMonitoringRecord record = deserialize();
				if (record != null) {
					this.kieker.newMonitoringRecord(record);
					this.recordCount++;
					if (this.recordCount % this.listenerUpdateInterval == 0) {
						updateState(null);
					}
				} else {
					this.active = false;
				}
			}
			updateState(null);
			sourceClose();
		} while (this.respawn);
		this.kieker.terminateMonitoring();
	}

	/**
	 * Safely end bridge loop. This routine should only be called from a signal handler or similar
	 * construct, as the run method waits for IO
	 * 
	 * @throws Exception transport exception from inner source
	 */
	public void shutdown() throws Exception {
		this.respawn = false;
		this.active = false;
		sourceClose();
		this.kieker.terminateMonitoring();
	}

	/**
	 * Informs all listeners about record count and an option message. The message may be null.
	 * 
	 * @param message the message passed to all listeners. May be null.
	 */
	private void updateState(final String message) {
		for (IServiceListener listener : this.listeners) {
			listener.handleEvent(this.recordCount, message);
		}
	}

	/**
	 * 
	 * @return returns the record count
	 */
	public long getRecordCount() {
		return this.recordCount;
	}

	/**
	 * The deserialize method must be implemented by subclasses. They read source data and return an IMonitoringRecord.
	 * 
	 * @return A IMonitoringRecord or null on read error or end of line.
	 * @throws Exception may be caused by a read error or an unknown record id.
	 */
	protected abstract IMonitoringRecord deserialize() throws Exception;

	/**
	 * Called to setup the channel to read record information. 
	 *  
	 * @throws Exception when an error occured setting up the record source.
	 */
	protected abstract void sourceSetup() throws Exception;

	/**
	 * Called to close the previously setup record source. 
	 *  
	 * @throws Exception when an error occured during connection close.
	 */
	protected abstract void sourceClose() throws Exception;

	/**
	 * @return the respawn
	 */
	public boolean isRespawn() {
		return this.respawn;
	}

	/**
	 * @param respawn
	 *            the respawn to set
	 */
	public void setRespawn(final boolean respawn) {
		this.respawn = respawn;
	}

	/**
	 * Add an update state listener.
	 * 
	 * @param listener an object implementing the IServiceListener interface
	 */
	public void addListener(final IServiceListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Set the update interval for the listener information. The default is 100 records. 
	 * @param listenerUpdateInterval the new update interval in number of records
	 */
	public void setListenerUpdateInterval(final long listenerUpdateInterval) {
		this.listenerUpdateInterval = listenerUpdateInterval;
	}

}
