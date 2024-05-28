package net.onelitefeather.microtus.nbt.serialization;

import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FastDataOutputStream extends DataOutputStream {
    /**
     * Creates a new data output stream to write data to the specified
     * underlying output stream. The counter {@code written} is
     * set to zero.
     *
     * @param out the underlying output stream, to be saved for later
     *            use.
     * @see FilterOutputStream#out
     */
    public FastDataOutputStream(OutputStream out) {
        super(out);
    }
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
        incCount(len);
    }

    @Override
    public void write(byte[] b) throws IOException {
        out.write(b);
        incCount(1);
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
        incCount(1);
    }

    private void incCount(int value) {
        int temp = written + value;
        if (temp < 0) {
            temp = Integer.MAX_VALUE;
        }
        written = temp;
    }
}
