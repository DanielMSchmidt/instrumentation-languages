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

package kieker.common.record.flow.trace.operation;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import kieker.common.record.IMonitoringRecord;
import kieker.common.util.registry.IRegistry;

import kieker.common.record.flow.trace.operation.AbstractOperationEvent;
import kieker.common.record.flow.ICallRecord;

/**
 * @author Generic Kieker
 * 
 * @since 1.9
 */
public class CallOperationEvent extends AbstractOperationEvent implements IMonitoringRecord.Factory, IMonitoringRecord.BinaryFactory, ICallRecord {
	public static final int SIZE = 36; // serialization size (without variable part of strings)
	private static final long serialVersionUID = 1395940967805L;
	
	private static final Class<?>[] TYPES = {
		long.class, // AbstractEvent.timestamp
		long.class, // ITraceRecord.traceId
		int.class, // ITraceRecord.orderIndex
		String.class, // IOperationRecord.classSignature
		String.class, // IOperationRecord.operationSignature
		String.class, // ICallRecord.calleeClassSignature
		String.class, // ICallRecord.calleeOperationSignature
	};
	
	
	private final String calleeClassSignature;
	private final String calleeOperationSignature;

	/**
	 * Creates a new instance of this class using the given parameters.
	 * 
	 * @param timestamp
	 *            timestamp
	 * @param traceId
	 *            traceId
	 * @param orderIndex
	 *            orderIndex
	 * @param classSignature
	 *            classSignature
	 * @param operationSignature
	 *            operationSignature
	 * @param calleeClassSignature
	 *            calleeClassSignature
	 * @param calleeOperationSignature
	 *            calleeOperationSignature
	 */
	public CallOperationEvent(final long timestamp, final long traceId, final int orderIndex, final String classSignature, final String operationSignature, final String calleeClassSignature, final String calleeOperationSignature) {
		super(timestamp, traceId, orderIndex, classSignature, operationSignature);
		this.calleeClassSignature = calleeClassSignature;
		this.calleeOperationSignature = calleeOperationSignature;
	}

	/**
	 * This constructor converts the given array into a record. It is recommended to use the array which is the result of a call to {@link #toArray()}.
	 * 
	 * @param values
	 *            The values for the record.
	 */
	public CallOperationEvent(final Object[] values) { // NOPMD (direct store of values)
		super(values, TYPES);
		this.calleeClassSignature = (String) values[5];
		this.calleeOperationSignature = (String) values[6];
	}
	
	/**
	 * This constructor uses the given array to initialize the fields of this record.
	 * 
	 * @param values
	 *            The values for the record.
	 * @param valueTypes
	 *            The types of the elements in the first array.
	 */
	protected CallOperationEvent(final Object[] values, final Class<?>[] valueTypes) { // NOPMD (values stored directly)
		super(values, valueTypes);
		this.calleeClassSignature = (String) values[5];
		this.calleeOperationSignature = (String) values[6];
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
	public CallOperationEvent(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferUnderflowException {
		super(buffer, stringRegistry);
		this.calleeClassSignature = stringRegistry.get(buffer.getInt());
		this.calleeOperationSignature = stringRegistry.get(buffer.getInt());
	}

	/**
	 * {@inheritDoc}
	 */
	public Object[] toArray() {
		return new Object[] {
			this.getTimestamp(),
			this.getTraceId(),
			this.getOrderIndex(),
			this.getClassSignature(),
			this.getOperationSignature(),
			this.getCalleeClassSignature(),
			this.getCalleeOperationSignature()
		};
	}

	/**
	 * {@inheritDoc}
	 */
	public void writeBytes(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferOverflowException {
		buffer.putLong(this.getTimestamp());
		buffer.putLong(this.getTraceId());
		buffer.putInt(this.getOrderIndex());
		buffer.putInt(stringRegistry.get(this.getClassSignature()));
		buffer.putInt(stringRegistry.get(this.getOperationSignature()));
		buffer.putInt(stringRegistry.get(this.getCalleeClassSignature()));
		buffer.putInt(stringRegistry.get(this.getCalleeOperationSignature()));
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

	public final String getCallerClassSignature() {
		return this.getClassSignature();
	}
	
	public final String getCallerOperationSignature() {
		return this.getOperationSignature();
	}
	
	public final String getCalleeClassSignature() {
		return this.calleeClassSignature;
	}
	
	public final String getCalleeOperationSignature() {
		return this.calleeOperationSignature;
	}
	
}
