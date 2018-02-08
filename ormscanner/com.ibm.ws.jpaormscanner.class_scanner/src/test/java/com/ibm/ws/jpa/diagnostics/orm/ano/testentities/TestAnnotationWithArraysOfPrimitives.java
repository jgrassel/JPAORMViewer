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
