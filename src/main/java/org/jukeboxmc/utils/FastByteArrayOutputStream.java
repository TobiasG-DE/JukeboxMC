package org.jukeboxmc.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class FastByteArrayOutputStream extends OutputStream {

    public static final long ONEOVERPHI = 106039;

    /** The array backing the output stream. */
    public final static int DEFAULT_INITIAL_CAPACITY = 16;

    /** The array backing the output stream. */
    public byte[] array;

    /** The number of valid bytes in {@link #array}. */
    public int length;

    /** The current writing position. */
    private int position;

    /** Creates a new array output stream with an initial capacity of {@link #DEFAULT_INITIAL_CAPACITY} bytes. */
    public FastByteArrayOutputStream() {
        this( DEFAULT_INITIAL_CAPACITY );
    }

    /** Creates a new array output stream with a given initial capacity.
     *
     * @param initialCapacity the initial length of the backing array.
     */
    public FastByteArrayOutputStream( final int initialCapacity ) {
        array = new byte[ initialCapacity ];
    }

    /** Creates a new array output stream wrapping a given byte array.
     *
     * @param a the byte array to wrap.
     */
    public FastByteArrayOutputStream( final byte[] a ) {
        array = a;
    }

    /** Marks this array output stream as empty. */
    public FastByteArrayOutputStream reset() {
        length = 0;
        position = 0;
        return this;
    }

    public void write( final int b ) {
        if ( position == length ) {
            length++;
            if ( position == array.length ) array = grow( array, length );
        }
        array[ position++ ] = (byte)b;
    }

    public static void ensureOffsetLength( final int arrayLength, final int offset, final int length ) {
        if ( offset < 0 ) throw new ArrayIndexOutOfBoundsException( "Offset (" + offset + ") is negative" );
        if ( length < 0 ) throw new IllegalArgumentException( "Length (" + length + ") is negative" );
        if ( offset + length > arrayLength ) throw new ArrayIndexOutOfBoundsException( "Last index (" + ( offset + length ) + ") is greater than array length (" + arrayLength + ")" );
    }

    public static byte[] grow( final byte[] array, final int length ) {
        if ( length > array.length ) {
            final int newLength = (int)Math.min( Math.max( ( ONEOVERPHI * array.length ) >>> 16, length ), Integer.MAX_VALUE );
            final byte[] t =
                    new byte[ newLength ];
            System.arraycopy( array, 0, t, 0, array.length );
            return t;
        }
        return array;
    }

    public static byte[] grow( final byte[] array, final int length, final int preserve ) {
        if ( length > array.length ) {
            final int newLength = (int)Math.min( Math.max( ( ONEOVERPHI * array.length ) >>> 16, length ), Integer.MAX_VALUE );
            final byte[] t =
                    new byte[ newLength ];
            System.arraycopy( array, 0, t, 0, preserve );
            return t;
        }
        return array;
    }

    public void write( final byte[] b, final int off, final int len ) throws IOException {
        if ( position + len > array.length ) array = grow( array, position + len, position );
        System.arraycopy( b, off, array, position, len );
        if ( position + len > length ) length = position += len;
    }

    public void position( long newPosition ) {
        if ( position > Integer.MAX_VALUE ) throw new IllegalArgumentException( "Position too large: " + newPosition );
        position = (int)newPosition;
    }

    public long position() {
        return position;
    }

    public long length() throws IOException {
        return length;
    }

    public byte[] toByteArray() {
        if (position == array.length) return array;
        return Arrays.copyOfRange(array, 0, position);
    }
}