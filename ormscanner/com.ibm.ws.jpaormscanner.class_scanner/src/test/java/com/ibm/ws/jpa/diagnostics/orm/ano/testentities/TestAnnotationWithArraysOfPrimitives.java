/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package com.ibm.ws.jpa.diagnostics.orm.ano.testentities;

public @interface TestAnnotationWithArraysOfPrimitives {
    public boolean[] booleanArray() default { };
    public byte[] byteArray() default { };
    public char[] charArray() default { };
    public int[] intArray() default { };
    public long[] longArray() default { };
    public short[] shortArray() default { };
    public float[] floatArray() default { };
    public double[] doubleArray() default { };
}
