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

package kieker.common.record.controlflow;

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
public class OperationExecutionRecord extends AbstractMonitoringRecord implements IMonitoringRecord.Factory, IMonitoringRecord.BinaryFactory {
	public static final int SIZE = 44; // serialization size (without variable part of strings)
	private static final long serialVersionUID = 1395942620448L;
	
	private static final Class<?>[] TYPES = {
		String.class, // OperationExecutionRecord.operationSignature
		String.class, // OperationExecutionRecord.sessionId
		long.class, // OperationExecutionRecord.traceId
		long.class, // OperationExecutionRecord.tin
		long.class, // OperationExecutionRecord.tout
		String.class, // OperationExecutionRecord.hostname
		int.class, // OperationExecutionRecord.eoi
		int.class, // OperationExecutionRecord.ess
	};
	
	public static final String NO_HOSTNAME = "<default-host>";
	public static final String NO_SESSION_ID = "<no-session-id>";
	public static final String NO_OPERATION_SIGNATURE = "noOperation";
	public static final long NO_TRACE_ID = -1;
	public static final long NO_TIMESTAMP = -1;
	public static final int NO_EOI_ESS = -1;
	public static final String OPERATION_SIGNATURE = "noOperation";
	public static final String SESSION_ID = "<no-session-id>";
	public static final long TRACE_ID = -1;
	public static final long TIN = -1;
	public static final long TOUT = -1;
	public static final String HOSTNAME = "<default-host>";
	public static final int EOI = -1;
	public static final int ESS = -1;
	
	private final String operationSignature;
	private final String sessionId;
	private final long traceId;
	private final long tin;
	private final long tout;
	private final String hostname;
	private final int eoi;
	private final int ess;

	/**
	 * Creates a new instance of this class using the given parameters.
	 * 
	 * @param operationSignature
	 *            operationSignature
	 * @param sessionId
	 *            sessionId
	 * @param traceId
	 *            traceId
	 * @param tin
	 *            tin
	 * @param tout
	 *            tout
	 * @param hostname
	 *            hostname
	 * @param eoi
	 *            eoi
	 * @param ess
	 *            ess
	 */
	public OperationExecutionRecord(final String operationSignature, final String sessionId, final long traceId, final long tin, final long tout, final String hostname, final int eoi, final int ess) {
		this.operationSignature = operationSignature;
		this.sessionId = sessionId;
		this.traceId = traceId;
		this.tin = tin;
		this.tout = tout;
		this.hostname = hostname;
		this.eoi = eoi;
		this.ess = ess;
	}

	/**
	 * This constructor converts the given array into a record. It is recommended to use the array which is the result of a call to {@link #toArray()}.
	 * 
	 * @param values
	 *            The values for the record.
	 */
	public OperationExecutionRecord(final Object[] values) { // NOPMD (direct store of values)
		AbstractMonitoringRecord.checkArray(values, TYPES);
		this.operationSignature = (String) values[0];
		this.sessionId = (String) values[1];
		this.traceId = (long) values[2];
		this.tin = (long) values[3];
		this.tout = (long) values[4];
		this.hostname = (String) values[5];
		this.eoi = (int) values[6];
		this.ess = (int) values[7];
	}
	
	/**
	 * This constructor uses the given array to initialize the fields of this record.
	 * 
	 * @param values
	 *            The values for the record.
	 * @param valueTypes
	 *            The types of the elements in the first array.
	 */
	protected OperationExecutionRecord(final Object[] values, final Class<?>[] valueTypes) { // NOPMD (values stored directly)
		AbstractMonitoringRecord.checkArray(values, valueTypes);
		this.operationSignature = (String) values[0];
		this.sessionId = (String) values[1];
		this.traceId = (long) values[2];
		this.tin = (long) values[3];
		this.tout = (long) values[4];
		this.hostname = (String) values[5];
		this.eoi = (int) values[6];
		this.ess = (int) values[7];
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
	public OperationExecutionRecord(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferUnderflowException {
		this.operationSignature = stringRegistry.get(buffer.getInt());
		this.sessionId = stringRegistry.get(buffer.getInt());
		this.traceId = buffer.getLong();
		this.tin = buffer.getLong();
		this.tout = buffer.getLong();
		this.hostname = stringRegistry.get(buffer.getInt());
		this.eoi = buffer.getInt();
		this.ess = buffer.getInt();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object[] toArray() {
		return new Object[] {
			this.getOperationSignature(),
			this.getSessionId(),
			this.getTraceId(),
			this.getTin(),
			this.getTout(),
			this.getHostname(),
			this.getEoi(),
			this.getEss()
		};
	}

	/**
	 * {@inheritDoc}
	 */
	public void writeBytes(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferOverflowException {
		buffer.putInt(stringRegistry.get(this.getOperationSignature()));
		buffer.putInt(stringRegistry.get(this.getSessionId()));
		buffer.putLong(this.getTraceId());
		buffer.putLong(this.getTin());
		buffer.putLong(this.getTout());
		buffer.putInt(stringRegistry.get(this.getHostname()));
		buffer.putInt(this.getEoi());
		buffer.putInt(this.getEss());
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

	public final String getOperationSignature() {
		return this.operationSignature;
	}
	
	public final String getSessionId() {
		return this.sessionId;
	}
	
	public final long getTraceId() {
		return this.traceId;
	}
	
	public final long getTin() {
		return this.tin;
	}
	
	public final long getTout() {
		return this.tout;
	}
	
	public final String getHostname() {
		return this.hostname;
	}
	
	public final int getEoi() {
		return this.eoi;
	}
	
	public final int getEss() {
		return this.ess;
	}
	
}
