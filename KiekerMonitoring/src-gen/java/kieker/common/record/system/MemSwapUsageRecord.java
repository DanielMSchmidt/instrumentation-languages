/***************************************************************************
 * Copyright 2013 Kieker Project (http://kieker-monitoring.net)
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

package kieker.common.record.system;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import kieker.common.record.AbstractMonitoringRecord;
import kieker.common.record.IMonitoringRecord;
import kieker.common.util.registry.IRegistry;


/**
 * @author Generic Kieker
 * 
 * @since 1.9
 */
public class MemSwapUsageRecord extends AbstractMonitoringRecord implements IMonitoringRecord.Factory, IMonitoringRecord.BinaryFactory {
	public static final int SIZE = 52; // serialization size (without variable part of strings)
	private static final long serialVersionUID = 1395682704883L;
	
	private static final Class<?>[] TYPES = {
		long.class, // MemSwapUsageRecord.timestamp
		String.class, // MemSwapUsageRecord.hostname
		long.class, // MemSwapUsageRecord.memUsed
		long.class, // MemSwapUsageRecord.memFree
		long.class, // MemSwapUsageRecord.swapTotal
		long.class, // MemSwapUsageRecord.swapUsed
		long.class, // MemSwapUsageRecord.swapFree
	};
	
	public static final long TIMESTAMP = 0;
	public static final String HOSTNAME = "";
	public static final long MEM_USED = 0;
	public static final long MEM_FREE = 0;
	public static final long SWAP_TOTAL = 0;
	public static final long SWAP_USED = 0;
	public static final long SWAP_FREE = 0;
	
	private final long timestamp;
	private final String hostname;
	private final long memUsed;
	private final long memFree;
	private final long swapTotal;
	private final long swapUsed;
	private final long swapFree;

	/**
	 * Creates a new instance of this class using the given parameters.
	 * 
	 * @param timestamp
	 *            timestamp
	 * @param hostname
	 *            hostname
	 * @param memUsed
	 *            memUsed
	 * @param memFree
	 *            memFree
	 * @param swapTotal
	 *            swapTotal
	 * @param swapUsed
	 *            swapUsed
	 * @param swapFree
	 *            swapFree
	 */
	public MemSwapUsageRecord(final long timestamp, final String hostname, final long memUsed, final long memFree, final long swapTotal, final long swapUsed, final long swapFree) {
		this.timestamp = timestamp;
		this.hostname = hostname;
		this.memUsed = memUsed;
		this.memFree = memFree;
		this.swapTotal = swapTotal;
		this.swapUsed = swapUsed;
		this.swapFree = swapFree;
	}

	/**
	 * This constructor converts the given array into a record. It is recommended to use the array which is the result of a call to {@link #toArray()}.
	 * 
	 * @param values
	 *            The values for the record.
	 */
	public MemSwapUsageRecord(final Object[] values) { // NOPMD (direct store of values)
		AbstractMonitoringRecord.checkArray(values, TYPES);
		this.timestamp = (long) values[0];
		this.hostname = (String) values[1];
		this.memUsed = (long) values[2];
		this.memFree = (long) values[3];
		this.swapTotal = (long) values[4];
		this.swapUsed = (long) values[5];
		this.swapFree = (long) values[6];
	}
	
	/**
	 * This constructor uses the given array to initialize the fields of this record.
	 * 
	 * @param values
	 *            The values for the record.
	 * @param valueTypes
	 *            The types of the elements in the first array.
	 */
	protected MemSwapUsageRecord(final Object[] values, final Class<?>[] valueTypes) { // NOPMD (values stored directly)
		AbstractMonitoringRecord.checkArray(values, valueTypes);
		this.timestamp = (long) values[0];
		this.hostname = (String) values[1];
		this.memUsed = (long) values[2];
		this.memFree = (long) values[3];
		this.swapTotal = (long) values[4];
		this.swapUsed = (long) values[5];
		this.swapFree = (long) values[6];
	}

	/**
	 * This constructor converts the given array into a record.
	 * 
	 * @param buffer
	 *            The bytes for the record.
	 * 
	 * @throws BufferUnderflowException
	 *             if buffer not sufficient
	 */
	public MemSwapUsageRecord(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferUnderflowException {
		this.timestamp = buffer.getLong();
		this.hostname = stringRegistry.get(buffer.getInt());
		this.memUsed = buffer.getLong();
		this.memFree = buffer.getLong();
		this.swapTotal = buffer.getLong();
		this.swapUsed = buffer.getLong();
		this.swapFree = buffer.getLong();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object[] toArray() {
		return new Object[] {
			this.getTimestamp(),
			this.getHostname(),
			this.getMemUsed(),
			this.getMemFree(),
			this.getSwapTotal(),
			this.getSwapUsed(),
			this.getSwapFree()
		};
	}

	/**
	 * {@inheritDoc}
	 */
	public void writeBytes(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferOverflowException {
		buffer.putLong(this.getTimestamp());
		buffer.putInt(stringRegistry.get(this.getHostname()));
		buffer.putLong(this.getMemUsed());
		buffer.putLong(this.getMemFree());
		buffer.putLong(this.getSwapTotal());
		buffer.putLong(this.getSwapUsed());
		buffer.putLong(this.getSwapFree());
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<?>[] getValueTypes() {
		return TYPES; // NOPMD
	}

	/**
	 * {@inheritDoc}
	 */
	public int getSize() {
		return SIZE;
	}
	/**
	 * {@inheritDoc}
	 * 
	 * @deprecated This record uses the {@link kieker.common.record.IMonitoringRecord.Factory} mechanism. Hence, this method is not implemented.
	 */
	@Deprecated
	public void initFromArray(final Object[] values) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @deprecated This record uses the {@link kieker.common.record.IMonitoringRecord.BinaryFactory} mechanism. Hence, this method is not implemented.
	 */
	@Deprecated
	public void initFromBytes(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferUnderflowException {
		throw new UnsupportedOperationException();
	}

	public final long getTimestamp() {
		return this.timestamp;
	}
	
	public final String getHostname() {
		return this.hostname;
	}
	
	public final long getMemUsed() {
		return this.memUsed;
	}
	
	public final long getMemFree() {
		return this.memFree;
	}
	
	public final long getSwapTotal() {
		return this.swapTotal;
	}
	
	public final long getSwapUsed() {
		return this.swapUsed;
	}
	
	public final long getSwapFree() {
		return this.swapFree;
	}
	
}
