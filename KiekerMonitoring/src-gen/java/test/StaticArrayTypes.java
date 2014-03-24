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

package test;

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
public class StaticArrayTypes extends AbstractMonitoringRecord implements IMonitoringRecord.Factory, IMonitoringRecord.BinaryFactory {
	public static final int SIZE = 34; // serialization size (without variable part of strings)
	private static final long serialVersionUID = 1395682704859L;
	
	private static final Class<?>[] TYPES = {
		boolean[].class, // StaticArrayTypes.a
		byte[].class, // StaticArrayTypes.b
		char[].class, // StaticArrayTypes.c
		double[].class, // StaticArrayTypes.d
		float[].class, // StaticArrayTypes.f
		int[].class, // StaticArrayTypes.g
		long[].class, // StaticArrayTypes.h
		short[].class, // StaticArrayTypes.i
		String[].class, // StaticArrayTypes.s
	};
	
	public static final boolean[] A = { true, false };
	public static final byte[] B = { 1, 2 };
	public static final char[] C = { '1', '2' };
	public static final double[] D = { 1.1, 2.2 };
	public static final float[] F = { 1.0f, 2.0f };
	public static final int[] G = { 10, 20 };
	public static final long[] H = { 100, 200 };
	public static final short[] I = { 5, 6 };
	public static final String[] S = { "first", "second" };
	
	private final boolean[] a;
	private final byte[] b;
	private final char[] c;
	private final double[] d;
	private final float[] f;
	private final int[] g;
	private final long[] h;
	private final short[] i;
	private final String[] s;

	/**
	 * Creates a new instance of this class using the given parameters.
	 * 
	 * @param a
	 *            a
	 * @param b
	 *            b
	 * @param c
	 *            c
	 * @param d
	 *            d
	 * @param f
	 *            f
	 * @param g
	 *            g
	 * @param h
	 *            h
	 * @param i
	 *            i
	 * @param s
	 *            s
	 */
	public StaticArrayTypes(final boolean[] a, final byte[] b, final char[] c, final double[] d, final float[] f, final int[] g, final long[] h, final short[] i, final String[] s) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.f = f;
		this.g = g;
		this.h = h;
		this.i = i;
		this.s = s;
	}

	/**
	 * This constructor converts the given array into a record. It is recommended to use the array which is the result of a call to {@link #toArray()}.
	 * 
	 * @param values
	 *            The values for the record.
	 */
	public StaticArrayTypes(final Object[] values) { // NOPMD (direct store of values)
		AbstractMonitoringRecord.checkArray(values, TYPES);
		this.a = (boolean[]) values[0];
		this.b = (byte[]) values[1];
		this.c = (char[]) values[2];
		this.d = (double[]) values[3];
		this.f = (float[]) values[4];
		this.g = (int[]) values[5];
		this.h = (long[]) values[6];
		this.i = (short[]) values[7];
		this.s = (String[]) values[8];
	}
	
	/**
	 * This constructor uses the given array to initialize the fields of this record.
	 * 
	 * @param values
	 *            The values for the record.
	 * @param valueTypes
	 *            The types of the elements in the first array.
	 */
	protected StaticArrayTypes(final Object[] values, final Class<?>[] valueTypes) { // NOPMD (values stored directly)
		AbstractMonitoringRecord.checkArray(values, valueTypes);
		this.a = (boolean[]) values[0];
		this.b = (byte[]) values[1];
		this.c = (char[]) values[2];
		this.d = (double[]) values[3];
		this.f = (float[]) values[4];
		this.g = (int[]) values[5];
		this.h = (long[]) values[6];
		this.i = (short[]) values[7];
		this.s = (String[]) values[8];
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
	public StaticArrayTypes(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferUnderflowException {
		// load array sizes
		a = new boolean[2];
		for (int i0=0;i0<2;i0++)
			this.a[i0] = buffer.get()==1?true:false;
		
		// load array sizes
		b = new byte[2];
		for (int i0=0;i0<2;i0++)
			this.b[i0] = buffer.get();
		
		// load array sizes
		c = new char[2];
		for (int i0=0;i0<2;i0++)
			this.c[i0] = buffer.getChar();
		
		// load array sizes
		d = new double[2];
		for (int i0=0;i0<2;i0++)
			this.d[i0] = buffer.getDouble();
		
		// load array sizes
		f = new float[2];
		for (int i0=0;i0<2;i0++)
			this.f[i0] = buffer.getFloat();
		
		// load array sizes
		g = new int[2];
		for (int i0=0;i0<2;i0++)
			this.g[i0] = buffer.getInt();
		
		// load array sizes
		h = new long[2];
		for (int i0=0;i0<2;i0++)
			this.h[i0] = buffer.getLong();
		
		// load array sizes
		i = new short[2];
		for (int i0=0;i0<2;i0++)
			this.i[i0] = buffer.getShort();
		
		// load array sizes
		s = new String[2];
		for (int i0=0;i0<2;i0++)
			this.s[i0] = stringRegistry.get(buffer.getInt());
	}

	/**
	 * {@inheritDoc}
	 */
	public Object[] toArray() {
		return new Object[] {
			this.isA(),
			this.getB(),
			this.getC(),
			this.getD(),
			this.getF(),
			this.getG(),
			this.getH(),
			this.getI(),
			this.getS()
		};
	}

	/**
	 * {@inheritDoc}
	 */
	public void writeBytes(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferOverflowException {
		// store array sizes
		for (int i0=0;i0<2;i0++)
			buffer.put((byte)(this.isA()[i0]?1:0));
		
		// store array sizes
		for (int i0=0;i0<2;i0++)
			buffer.put((byte)this.getB()[i0]);
		
		// store array sizes
		for (int i0=0;i0<2;i0++)
			buffer.putChar(this.getC()[i0]);
		
		// store array sizes
		for (int i0=0;i0<2;i0++)
			buffer.putDouble(this.getD()[i0]);
		
		// store array sizes
		for (int i0=0;i0<2;i0++)
			buffer.putFloat(this.getF()[i0]);
		
		// store array sizes
		for (int i0=0;i0<2;i0++)
			buffer.putInt(this.getG()[i0]);
		
		// store array sizes
		for (int i0=0;i0<2;i0++)
			buffer.putLong(this.getH()[i0]);
		
		// store array sizes
		for (int i0=0;i0<2;i0++)
			buffer.putShort(this.getI()[i0]);
		
		// store array sizes
		for (int i0=0;i0<2;i0++)
			buffer.putInt(stringRegistry.get(this.getS()[i0]));
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

	public final boolean[] isA() {
		return this.a;
	}
	
	public final byte[] getB() {
		return this.b;
	}
	
	public final char[] getC() {
		return this.c;
	}
	
	public final double[] getD() {
		return this.d;
	}
	
	public final float[] getF() {
		return this.f;
	}
	
	public final int[] getG() {
		return this.g;
	}
	
	public final long[] getH() {
		return this.h;
	}
	
	public final short[] getI() {
		return this.i;
	}
	
	public final String[] getS() {
		return this.s;
	}
	
}
