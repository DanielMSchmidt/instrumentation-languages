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

package kieker.common.record.flow.trace.operation.object;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import kieker.common.record.IMonitoringRecord;
import kieker.common.util.registry.IRegistry;

import kieker.common.record.flow.trace.operation.CallOperationEvent;
import kieker.common.record.flow.ICallObjectRecord;

/**
 * @author Generic Kieker
 * 
 * @since 1.10
 */
public class CallOperationObjectEvent extends CallOperationEvent implements IMonitoringRecord.Factory, IMonitoringRecord.BinaryFactory, ICallObjectRecord {
	public static final int SIZE = 48; // serialization size (without variable part of strings)
	private static final long serialVersionUID = 1395333732180L;
	
	private static final Class<?>[] TYPES = {
		long.class, // AbstractEvent.timestamp
		long.class, // ITraceRecord.traceId
		int.class, // ITraceRecord.orderIndex
		String.class, // IOperationRecord.classSignature
		String.class, // IOperationRecord.operationSignature
		String.class, // ICallRecord.calleeClassSignature
		String.class, // ICallRecord.calleeOperationSignature
		int.class, // IObjectRecord.objectId
		int.class, // ICallObjectRecord.callerObjectId
		int.class, // ICallObjectRecord.calleeObjectId
	};
	
	
	private final int objectId;
	private final int callerObjectId;
	private final int calleeObjectId;

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
	 * @param objectId
	 *            objectId
	 * @param callerObjectId
	 *            callerObjectId
	 * @param calleeObjectId
	 *            calleeObjectId
	 */
	public CallOperationObjectEvent(final long timestamp, final long traceId, final int orderIndex, final String classSignature, final String operationSignature, final String calleeClassSignature, final String calleeOperationSignature, final int objectId, final int callerObjectId, final int calleeObjectId) {
		super(timestamp, traceId, orderIndex, classSignature, operationSignature, calleeClassSignature, calleeOperationSignature);
		this.objectId = objectId;
		this.callerObjectId = callerObjectId;
		this.calleeObjectId = calleeObjectId;
	}

	/**
	 * This constructor converts the given array into a record. It is recommended to use the array which is the result of a call to {@link #toArray()}.
	 * 
	 * @param values
	 *            The values for the record.
	 */
	public CallOperationObjectEvent(final Object[] values) { // NOPMD (direct store of values)
		super(values, TYPES);
		this.objectId = (int) values[7];
		this.callerObjectId = (int) values[8];
		this.calleeObjectId = (int) values[9];
	}
	
	/**
	 * This constructor uses the given array to initialize the fields of this record.
	 * 
	 * @param values
	 *            The values for the record.
	 * @param valueTypes
	 *            The types of the elements in the first array.
	 */
	protected CallOperationObjectEvent(final Object[] values, final Class<?>[] valueTypes) { // NOPMD (values stored directly)
		super(values, valueTypes);
		this.objectId = (int) values[7];
		this.callerObjectId = (int) values[8];
		this.calleeObjectId = (int) values[9];
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
	public CallOperationObjectEvent(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferUnderflowException {
		super(buffer, stringRegistry);
		this.objectId = buffer.getInt();
		this.callerObjectId = buffer.getInt();
		this.calleeObjectId = buffer.getInt();
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
			this.getCalleeOperationSignature(),
			this.getObjectId(),
			this.getCallerObjectId(),
			this.getCalleeObjectId()
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
		buffer.putInt(this.getObjectId());
		buffer.putInt(this.getCallerObjectId());
		buffer.putInt(this.getCalleeObjectId());
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

	public final int getObjectId() {
		return this.objectId;
	}
	
	public final int getCallerObjectId() {
		return this.callerObjectId;
	}
	
	public final int getCalleeObjectId() {
		return this.calleeObjectId;
	}
	
}
