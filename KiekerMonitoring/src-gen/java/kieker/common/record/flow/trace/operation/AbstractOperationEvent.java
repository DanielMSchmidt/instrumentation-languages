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

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import kieker.common.record.IMonitoringRecord;
import kieker.common.util.registry.IRegistry;

import kieker.common.record.flow.trace.AbstractTraceEvent;
import kieker.common.record.flow.IOperationRecord;

/**
 * @author Generic Kieker
 * 
 * @since 1.9
 */
public abstract class AbstractOperationEvent extends AbstractTraceEvent implements IMonitoringRecord.Factory, IMonitoringRecord.BinaryFactory, IOperationRecord {
		private static final long serialVersionUID = 1395951954630L;
	
	
	
	private final String classSignature;
	private final String operationSignature;

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
	 */
	public AbstractOperationEvent(final long timestamp, final long traceId, final int orderIndex, final String classSignature, final String operationSignature) {
		super(timestamp, traceId, orderIndex);
		this.classSignature = classSignature;
		this.operationSignature = operationSignature;
	}

	
	/**
	 * This constructor uses the given array to initialize the fields of this record.
	 * 
	 * @param values
	 *            The values for the record.
	 * @param valueTypes
	 *            The types of the elements in the first array.
	 */
	protected AbstractOperationEvent(final Object[] values, final Class<?>[] valueTypes) { // NOPMD (values stored directly)
		super(values, valueTypes);
		this.classSignature = (String) values[3];
		this.operationSignature = (String) values[4];
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
	public AbstractOperationEvent(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferUnderflowException {
		super(buffer, stringRegistry);
		this.classSignature = stringRegistry.get(buffer.getInt());
		this.operationSignature = stringRegistry.get(buffer.getInt());
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

	public final String getClassSignature() {
		return this.classSignature;
	}
	
	public final String getOperationSignature() {
		return this.operationSignature;
	}
	
}
