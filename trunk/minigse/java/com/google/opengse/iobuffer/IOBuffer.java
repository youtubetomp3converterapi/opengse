// Copyright 2007 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.opengse.iobuffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The IOBuffer class stores an arbitrary amount of data in a
 * {@link java.util.LinkedList} of {@link java.nio.ByteBuffer} objects.
 * Buffers are added to the list during calls to the IOBuffer write
 * methods (i.e. write(char[]), writeBytes(byte[]), etc.). Buffers are
 * consumed from the start of the list (FIFO) during calls to the
 * IOBuffer read methods (i.e. read(char[]), readBytes(byte[]), etc.).
 *
 * There are three stages in the life of data in an IOBuffer. There's
 * the current write buffer, the buffer list, and the current read
 * buffer. All data contained in an IOBuffer arrives through calls to
 * the write methods (there are also transfer, prepend & append methods).
 * The write methods store data to the current write buffer, which
 * is available through a call to {@link #getWriteBuffer()}. Obviously,
 * the current write buffer has a limited capacity. Once full, however,
 * the {@link #getWriteBuffer()} method will automatically append
 * it to the internal buffer list and allocate a new buffer for writing.
 *
 * The internal buffer list can grow arbitrarily large and cannot be
 * accessed externally. Instead, the current read buffer is used to
 * retrieve data from the IOBuffer. Initially null, the current read
 * buffer is set to the first buffer in the internal buffer list on
 * calls to the read methods. When all of the data in the current read
 * buffer is consumed, it is discarded and the next buffer is popped
 * from the internal list. Any current write buffer will not be
 * automatically available for reading until is is manually flushed
 * with a call to <code>flush()</code>. This is necessary to support
 * concurrent reads and writes.
 *
 * There are also current read and write buffers for character-based
 * read/write operations. Note that you cannot mix character reads
 * with byte reads and character writes with byte writes. For example,
 * it is illegal to call <code>write(' ')</code> and then
 * <code>writeByte(32)</code> without an explicit {@code flush()}
 * call between the invocations.
 *
 * This class is safe for a concurrent reader and writer. That is,
 * one thread can read while another writes. Multiple readers and
 * multiple writers are not supported.
 *
 * <b>IMPORTANT:</b> It is necessary to call <code>flush()</code> to
 * ensure that data written to the IOBuffer is available for reading.
 *
 * A maximum IOBuffer size limit can be set by invoking the {@link
 * #setSizeLimit} method. When the limit is
 * exceeded, the contents of the IOBuffer are consumed via a call to
 * the {@link ConsumeCallback#handleConsume(IOBuffer, boolean)}
 * callback.  This provides a mechanism to dispense with the contents
 * of the IOBuffer in an application-dependent way.  By default, the
 * size limit is set to -1, which allows the IOBuffer to grow without
 * bound.
 *
 * @see com.google.opengse.iobuffer.IOBufferInputStream
 * @see com.google.opengse.iobuffer.IOBufferOutputStream
 * @see com.google.opengse.iobuffer.IOBufferReader
 * @see com.google.opengse.iobuffer.IOBufferWriter
 *
 * @author Spencer Kimball
 * @author Peter Mattis
 */
public class IOBuffer {
  private static final Logger logger_ =
    Logger.getLogger(IOBuffer.class.getName());

  private static final int UNBOUNDED = -1;

  private static final int BUFFER_SIZE = 4096;
  private static final int MIN_BUFFER_SIZE = 1024;
  private static final int MAX_BUFFER_SIZE = 4096 * 1024;

  // each buffer will actually be allocated ALLOC_EXTRA over the
  // current buffer_size_ bytes, so we can efficiently handle users
  // that write exactly 2^k bytes between flushes.
  //
  // ALLOC_EXTRA is 2 instead of 1 because some users of IOBuffer
  // expect the buffer size to always be even.
  public static final int ALLOC_EXTRA = 2;

  private final LinkedList<ByteBuffer> bufs_ = new LinkedList<ByteBuffer>();
  private ByteBuffer read_buf_ = null;
  private CharBuffer read_char_buf_ = null;
  private ByteBuffer write_buf_ = null;
  private CharBuffer write_char_buf_ = null;
  private String char_encoding_ = null;
  private CharsetEncoder char_encoder_ = null;
  private CharsetDecoder char_decoder_ = null;
  private int buffer_size_ = BUFFER_SIZE;
  private boolean recent_grow_ = false;
  private int num_bytes_written_to_last_buffer_ = 0;
  private int size_limit_ = UNBOUNDED;
  private int min_buffer_size_ = 0;
  private ConsumeCallback consume_cb_ = null;
  private ByteBuffer underflow_buf_ = null;
  private CharBuffer underflow_char_buf_ = null;
  private int min_reader_size_ = 0;


  /**
   * Default constructor.
   */
  public IOBuffer() {
  }

  /**
   * Copy constructor. The new IOBuffer will share the memory for
   * the byte buffer contents with the source, though their states
   * (position, limit, etc.) will be independent.
   * The size limit and consume callback are not copied.
   *
   * @param src is the source i/o buffer
   */
  public IOBuffer(IOBuffer src) {
    // the NIO buffers
    synchronized (src.bufs_) {
      for (ByteBuffer byte_buf : src.bufs_) {
        // don't need to synchronize on bufs as this is the constructor
        this.bufs_.addLast(byte_buf.duplicate());
      }
    }
    if (src.read_buf_ != null) {
      this.read_buf_ = src.read_buf_.duplicate();
    }
    if (src.read_char_buf_ != null) {
      this.read_char_buf_ = src.read_char_buf_.duplicate();
    }
    if (src.write_buf_ != null) {
      this.write_buf_ = src.write_buf_.duplicate();
    }
    if (src.write_char_buf_ != null) {
      this.write_char_buf_ = src.write_char_buf_.duplicate();
    }

    // the character encoding
    setCharacterEncoding(src.char_encoding_);

    // the current and min buffer size
    this.buffer_size_ = src.buffer_size_;
    this.min_buffer_size_ = src.min_buffer_size_;
  }

  /**
   * String constructor. The provided string is encoded based on
   * the specified character encoding to an initial byte buffer.
   *
   * @param str is the input string
   * @param char_encoding is the character encoding
   */
  public IOBuffer(String str, String char_encoding)
    throws CharacterCodingException {
    CharsetEncoder encoder = Charset.forName(char_encoding).newEncoder();
    read_buf_ = encoder.encode(CharBuffer.wrap(str));
  }

  // Replaces unmappable characters when encoding with non-UTF-8 charsets.
  private static final String BAD_INPUT_REPLACEMENT = "?";

  /**
   * Sets the current character encoding. Characters are encoded
   * to bytes when written using this encoding and are decoded
   * to characters when read using this encoding.
   *
   * @param char_encoding is the character encoding
   */
  public void setCharacterEncoding(String char_encoding) {
    this.char_encoding_ = char_encoding;
    if (this.char_encoding_ != null) {
      Charset charset = Charset.forName(char_encoding_);
      this.char_decoder_ = charset.newDecoder();
      this.char_encoder_ = charset.newEncoder();
      char_decoder_.onMalformedInput(CodingErrorAction.IGNORE);
      char_decoder_.onUnmappableCharacter(CodingErrorAction.IGNORE);

      ByteBuffer replacement = charset.encode(BAD_INPUT_REPLACEMENT);
      byte[] replace_bytes = new byte[replacement.remaining()];
      replacement.get(replace_bytes);
      char_encoder_.onMalformedInput(CodingErrorAction.REPLACE);
      char_encoder_.onUnmappableCharacter(CodingErrorAction.REPLACE);
      char_encoder_.replaceWith(replace_bytes);
    }
  }

  /**
   * Sets the IOBuffer size limit. By default, there is no limit.
   * Once set, write operations check the buffer size and if the
   * limit is exceeded, the <code>consume</code> method is called.
   *
   * @param size_limit is the size limit in bytes. -1 indicates there
   *        should be no size limit.  The actual max buffer size may
   *        exceed this limit by a very small amount (currently 2
   *        bytes).
   */
  public void setSizeLimit(int size_limit) {
    // TODO: This message is bogus...it says 10MB, but 10 << 20 is 1 MB.
    if (size_limit > (10 << 20)) {
      logger_.warning("setSizeLimit to greater than 10Mb");
    }
    this.size_limit_ = size_limit;
  }

  /**
   * Returns the size limit for this IOBuffer.
   * @return the size limit, or -1 if none
   */
  public int getSizeLimit() {
    return size_limit_;
  }

  /**
   * Sets the IOBuffer min buffer size from the writer thread.
   * Guarantees that the next getWriteBuffer that provides an
   * empty buffer will have at least the given capacity.
   * The minimum buffer size may be set on the reader thread
   * or the writer thread, but should NOT be set from both,
   * because there is no mechanism to avoid race conditions.
   * If set from the writer thread, call setMinSizeWriter.
   * If set from the reader thread, call setMinSizeReader.
   * The min_buffer_size parameter must be smaller than
   * the maximum buffer size constant.
   */
  public void setMinSizeWriter(int min_buffer_size) {
    min_buffer_size = Math.max(min_buffer_size, MIN_BUFFER_SIZE);
    min_buffer_size_ = min_buffer_size;
    if (MAX_BUFFER_SIZE < min_buffer_size) {
      throw new IllegalArgumentException("Bad buffer size: " +
                                         min_buffer_size + " not less than " +
                                         MAX_BUFFER_SIZE);
    }

    // drop pending write buffer if it is empty and too small
    if (write_buf_ != null && write_buf_.position() == 0
        && write_buf_.capacity() < min_buffer_size_) {
      write_buf_ = null;
    }
  }

  /**
   * Sets the IOBuffer min buffer size from the reader thread.
   * Guarantees that the next getReadBuffer will return a
   * buffer that has at least the given capacity; copies any
   * pending data into larger buffers if necessary.
   * The minimum buffer size may be set on the reader thread
   * or the writer thread, but should NOT be set from both,
   * because there is no mechanism to avoid race conditions.
   * If set from the writer thread, call setMinSizeWriter.
   * If set from the reader thread, call setMinSizeReader.
   * The min_reader_size parameter must be smaller than
   * the maximum buffer size constant.
   */
  public void setMinSizeReader(int min_reader_size) {
    min_reader_size = Math.max(MIN_BUFFER_SIZE, min_reader_size);
    if (MAX_BUFFER_SIZE < min_reader_size) {
      throw new IllegalArgumentException("Bad buffer size: " +
                                         MAX_BUFFER_SIZE + " not less than " +
                                         min_reader_size);
    }

    // this will _eventually_ make buffers the right size, but
    // it doesn't fix buffers that are in the pipeline
    min_buffer_size_ = min_reader_size;
    min_reader_size_ = min_reader_size;
  }

  /**
   * Returns the current min size, a number no less than was set by
   * setMinSizeWriter or setMinSizeReader.
   */
  public int getMinSize() {
    return min_buffer_size_;
  }

  /**
   * Sets the IOBuffer consume callback. The callback is invoked
   * to allow the application to consume the contents of the IOBuffer
   * as appropriate.
   *
   * @param consume_cb is the callback to invoke to handle the contents
   *        of the buffer. This can be <code>null</code> to disable
   *        the callback mechansim.
   */
  public void setConsumeCallback(ConsumeCallback consume_cb) {
    this.consume_cb_ = consume_cb;
  }

  /**
   * Forces an invocation of the consume callback, if set.
   * @exception IOException is thrown on consume callback failure
   */
  public void consume() throws IOException {
    consume(false);
  }

  /**
   * Forces an invocation of the consume callback, if set, with
   * the option to specify this is the last bit of data that will
   * need to be consumed.
   *
   * @param done <code>true</code> if this is the last data to consume
   * @exception IOException is thrown on consume callback failure
   */
  public void consume(boolean done) throws IOException {
    if (consume_cb_ != null) {
      consume_cb_.handleConsume(this, done);
    }
  }

  /**
   * Returns the character encoding.
   *
   * @return the character encoding
   */
  public String getCharacterEncoding() {
    return char_encoding_;
  }

  /**
   * Appends the provided byte buffer to the IOBuffer. The buffer
   * should not already be 'flipped' ({@link java.nio.Buffer#flip()}).
   * If using separate read/write threads, call by writer.
   *
   * @param buf is the new ByteBuffer.
   * @exception IOException
   */
  public void appendBuffer(ByteBuffer buf) throws IOException {
    flush();
    write_buf_ = buf;
  }

  /**
   * Clears the contents of the IOBuffer.
   * Safe for reader and writer.
   */
  public void clear() {
    bufs_.clear();
    read_buf_ = null;
    read_char_buf_ = null;
    write_buf_ = null;
    write_char_buf_ = null;
    if (char_encoding_ != null) {
      char_decoder_.reset();
      char_encoder_.reset();
    }
    buffer_size_ = BUFFER_SIZE;
  }

  /**
   * Flushes the IOBuffer so that any previously written data is
   * available for reading.
   * If using separate read/write threads, call by writer.
   * @exception IOException
   */
  public void flush() throws IOException {
    // clear the write character buffer (if applicable)
    if (write_char_buf_ != null) {
      flushWriteCharBuffer(true);
    }
    // now, if there is a non-empty write buffer, add it to the list
    if (write_buf_ != null && write_buf_.position() > 0) {
      flushWriteBuffer();
    }
  }

  /**
   * Discard "length" available bytes.
   * If using separate read/write threads, call by reader.
   *
   * @param length the number of bytes to discard. Length must be less than
   *        or equal to the number of available bytes.
   */
  public void discard(int length) {
    if (availableBytes() < length) {
      throw new IllegalArgumentException("Not enough bytes to discard: " +
                                         "requested " + length + ", only " +
                                         availableBytes() + " available.");
    }
    while (length > 0) {
      ByteBuffer bb = getReadBuffer();
      int count = Math.min(bb.remaining(), length);
      length -= count;
      bb.position(bb.position() + count);
      releaseReadBuffer();
    }
  }

  /**
   * Transfers the contents of the provided IOBuffer up to
   * the specified length.
   * If using separate read/write threads, call by writer.
   *
   * Note that this method does not check the buffer size
   * and will not call the consume callback, if any is set.
   *
   * @param src is the source IOBuffer
   * @param length is the maximum number of bytes to transfer
   *        Length must be less than or equal to the number of
   *        available bytes in the source buffer.
   * @exception IOException
   */
  public int transfer(IOBuffer src, int length) throws IOException {
    if (src.availableBytes() < length) {
      throw new IllegalArgumentException("Not enough bytes left: requested " +
                                         length + ", only " +
                                         src.availableBytes() + " available.");
    }

    int todo = length;
    while (todo > 0) {
      todo -= transfer(src.getReadBuffer(), todo);
      src.releaseReadBuffer();
    }
    return length - todo;
  }

  /**
   * Transfers the contents of the provided ByteBuffer up to
   * the specified length. The return value is the number
   * of bytes actually copied, which has a maximum value
   * of length.
   * If using separate read/write threads, call by writer.
   *
   * Note that this method does not check the buffer size
   * and will not call the consume callback, if any is set.
   *
   * @param src is the source ByteBuffer
   * @param length is the maximum number of bytes to transfer
   * @return the number of bytes actually copied
   * @exception IOException
   */
  public int transfer(ByteBuffer src, int length) throws IOException {
    int todo = length;
    while (src.hasRemaining() && (todo > 0)) {
      ByteBuffer dest = src.slice();
      int count = Math.min(dest.remaining(), todo);
      src.position(src.position() + count);
      dest.limit(dest.position() + count);
      dest.position(dest.limit());
      appendBuffer(dest);
      todo -= count;
    }
    return length - todo;
  }

  /**
   * Appends the contents of the provided IOBuffer.
   * Note that this method will fail if character data
   * has been read from the src buffer (i.e. any call to
   * the read(char[]) methods).
   * If using separate read/write threads, call by writer.
   *
   * Note that this method does not check the buffer size
   * and will not call the consume callback, if any is set.
   *
   * @param src is the source IOBuffer
   * @exception IOException
   */
  public void append(IOBuffer src) throws IOException {
    if (src.read_char_buf_ != null) {
      throw new IllegalArgumentException("Cannot append to the byte buffer: "
          + "character data has been read from source buffer.");
    }

    // flush src and dest
    src.flush();
    flush();

    // do NOT change the following line to Lists.newLinkedList()
    LinkedList<ByteBuffer> tmp_bufs = new LinkedList<ByteBuffer>();

    // add any current src read buffer to the list
    if (src.read_buf_ != null) {
      // slice the unread content and set position to limit
      ByteBuffer sliceOfReadBuffer = src.read_buf_.slice();
      sliceOfReadBuffer.position(sliceOfReadBuffer.limit());
      tmp_bufs.addLast(sliceOfReadBuffer);
    }
    // append all write bufs from source to dest
    synchronized (src.bufs_) {
      for (ByteBuffer writeBuffer : src.bufs_) {
        tmp_bufs.addLast(writeBuffer.duplicate());
      }
    }
    synchronized (bufs_) {
      bufs_.addAll(tmp_bufs);
    }
  }

  /**
   * Prepends the contents of the provided IOBuffer.
   * Note that this method will fail if character data
   * has been read from the src buffer (i.e. any call to
   * the read(char[]) methods).
   * If using separate read/write threads, call by writer.
   *
   * Note that this method does not check the buffer size
   * and will not call the consume callback, if any is set.
   *
   * @param src is the source IOBuffer
   * @exception IOException
   */
  public void prepend(IOBuffer src) throws IOException {
    if (src.read_char_buf_ != null) {
      throw new IllegalArgumentException("Cannot prepend: character data has" +
                                         " already been read from the " +
                                         "source buffer.");
    }

    // flush src only
    src.flush();

    // push current read buffer to write buffer list
    if (read_buf_ != null) {
      ByteBuffer sliceOfReadBuffer = read_buf_.slice();
      sliceOfReadBuffer.position(sliceOfReadBuffer.limit());
      synchronized (bufs_) {
        bufs_.addFirst(sliceOfReadBuffer);
      }
      read_buf_ = null;
    }
    // replace the read buffer
    if (src.read_buf_ != null) {
      read_buf_ = src.read_buf_.duplicate();
    }
    // prepend all write bufs to a temp list
    // do NOT change the following line to Lists.newLinkedList()
    LinkedList<ByteBuffer> tmp_bufs = new LinkedList<ByteBuffer>();
    synchronized (src.bufs_) {
      for (ByteBuffer byte_buf : src.bufs_) {
        tmp_bufs.addLast(byte_buf.duplicate());
      }
    }
    // prepend the temp list to the internal buffer list
    synchronized (bufs_) {
      bufs_.addAll(0, tmp_bufs);
    }
  }

  /**
   * Reads the contents of the IOBuffer into the provided
   * ByteArrayOutputStream until either a newline character is
   * encountered or the end of the input is reached.
   * If using separate read/write threads, call by reader.
   *
   * @param baos is ByteArrayOutputStream to store the result
   * @return true if a newline was encountered.
   */
  public boolean readLine(ByteArrayOutputStream baos)
    throws IOException {
    return readLine(baos, -1);
  }

  /**
   * Reads the contents of the IOBuffer into the provided
   * ByteArrayOutputStream until either a newline character is
   * encountered or the end of the input is reached.
   * An additional parameter limits the size of any line.
   * If the line limit is exceeded an exception is thrown
   * If using separate read/write threads, call by reader.
   *
   * @param baos is a ByteArrayOutputStream to store the result
   * @param limit is the maximum line length, -1 for no limit
   * @return true if a newline was encountered.
   * @exception IOException is thrown if the maximum line
   *            length is exceeded.
   */
  public boolean readLine(ByteArrayOutputStream baos,
                          int                   limit)
    throws IOException {
    for (;;) {
      ByteBuffer byte_buf = getReadBuffer();
      if (byte_buf == null) {
        return false;
      }

      byte[] buf = byte_buf.array();
      int o = byte_buf.arrayOffset();
      int s = byte_buf.position();
      int e = byte_buf.limit();

      while (s < e) {
        byte b = buf[s + o];
        s += 1;
        baos.write(b);

        if (b == '\n') {
          byte_buf.position(s);
          return true;
        } else if (limit != -1 && baos.size() >= limit) {
          throw new IOException ("readLine limit of " + limit +
                                 " bytes exceeded");
        }
      }

      byte_buf.position(s);

      releaseReadBuffer();
    }
  }

  /**
   * Reads the contents of the IOBuffer into the provided
   * byte array starting at the specified offset and limited
   * to the specified length.
   * If using separate read/write threads, call by reader.
   *
   * @param inbuf is the byte array for storing the line
   * @param off is the starting offset for storing bytes
   * @param len is the maximum number of bytes to read
   * @return the number of bytes read.
   */
  public int readLine(byte[] inbuf, int off, int len) {
    int nread = 0;

    for (;;) {
      ByteBuffer byte_buf = getReadBuffer();
      if (byte_buf == null) {
        return nread;
      }

      byte[] buf = byte_buf.array();
      int o = byte_buf.arrayOffset();
      int s = byte_buf.position();
      int e = byte_buf.limit();

      while (s < e) {
        if (nread >= len) {
          byte_buf.position(s);
          return nread;
        }

        byte b = buf[s + o];
        inbuf[off++] = b;
        s += 1;
        nread += 1;

        if (b == '\n') {
          byte_buf.position(s);
          return nread;
        }
      }

      byte_buf.position(s);

      releaseReadBuffer();
    }
  }

  /**
   * Reads a single character from the i/o buffer. This method
   * requires that the character encoding has been set.
   * If using separate read/write threads, call by reader.
   *
   * @return the next character in the i/o buffer
   */
  public int read() {
    CharBuffer char_buf = getReadCharBuffer();
    if (char_buf == null) {
      return -1;
    }
    return char_buf.get();
  }

  /**
   * Reads up to buf.length characters from the i/o buffer and
   * stores the results in the character array: buf. This method
   * requires that the character encoding has been set.
   * If using separate read/write threads, call by reader.
   *
   * @param buf is the character array to store the result.
   * @return the number of characters read.
   */
  public int read(char[] buf) {
    return read(buf, 0, buf.length);
  }

  /**
   * Reads up to count characters from the i/o buffer and
   * stores the results in the character array: buf starting
   * at array offset: offset. This method requires that the
   * character encoding has been set.
   * If using separate read/write threads, call by reader.
   *
   * @param buf is the character array to store the result.
   * @param offset is the starting offset in the character array
   *        to store the result
   * @param count is the maximum number of characters to read.
   * @return the number of characters read.
   */
  public int read(char[] buf, int offset, int count) {
    int n = count;
    while (n > 0) {
      CharBuffer char_buf = getReadCharBuffer();
      if (char_buf == null) {
        if (count == n) {
          return -1;
        }
        break;
      }

      int remain = char_buf.remaining();
      if (remain > n) {
        remain = n;
      }
      char_buf.get(buf, offset, remain);
      n -= remain;
      offset += remain;
    }
    return count - n;
  }

  /**
   * Skips the specified number of characters from the i/o buffer.
   * If there are fewer than n characters in the buffer, the
   * remaining characters will be skipped and the return value
   * will be the number skipped. Otherwise, the return value
   * will be equal to n.
   * If using separate read/write threads, call by reader.
   *
   * @param n the number of characters to skip.
   * @return the number of characters actually skipped.
   */
  public long skip(long n) {
    long c = n;
    while (c > 0) {
      CharBuffer char_buf = getReadCharBuffer();
      if (char_buf == null) {
        break;
      }

      int remain = char_buf.remaining();
      if (remain > c) {
        remain = (int) c;
      }
      char_buf.position(char_buf.position() + remain);
      c -= remain;
    }

    return n - c;
  }

  /**
   * Reads a single byte from the i/o buffer.
   * If using separate read/write threads, call by reader.
   *
   * @return the next byte value in the i/o buffer. The byte value is returned
   *         as an int in the range 0 to 255. If no byte is available, then -1
   *         is returned.
   */
  public int readByte() {
    if (read_char_buf_ != null) {
      throw new IllegalStateException("Cannot read from byte buffer: " +
                                      "character data has already been read.");
    }


    ByteBuffer byte_buf = getReadBuffer();
    if (byte_buf == null || !byte_buf.hasRemaining()) {
      return -1;
    }
    // note: remove sign bits
    int res = 0xff & byte_buf.get();
    releaseReadBuffer();
    return res;
  }

  /**
   * Reads up to buf.length bytes from the i/o buffer and stores
   * the result in the byte array, buf.
   * If using separate read/write threads, call by reader.
   *
   * @param buf is the byte array to store the result
   * @return the number of bytes read
   */
  public int readBytes(byte[] buf) {
    return readBytes(buf, 0, buf.length);
  }

  /**
   * Reads up to count bytes from the i/o buffer and stores
   * the result in the byte array, buf, starting at the array
   * index specified by offset.
   * If using separate read/write threads, call by reader.
   *
   * @param buf is the byte array to store the result
   * @param offset is the starting array index for the result
   * @param count is the maximum number of bytes to read
   * @return the number of bytes read
   */
  public int readBytes(byte[] buf, int offset, int count) {
    if (read_char_buf_ != null) {
      throw new IllegalStateException("Cannot read from the byte buffer: " +
                                      "character data has already been read.");
    }

    int n = count;
    while (n > 0) {
      ByteBuffer byte_buf = getReadBuffer();
      if (byte_buf == null) {
        if (count == n) {
          return -1;
        }
        break;
      }
      int remain = byte_buf.remaining();
      if (remain > n) {
        remain = n;
      }
      byte_buf.get(buf, offset, remain);
      n -= remain;
      offset += remain;

      releaseReadBuffer();
    }
    return count - n;
  }

  /**
   * Reads bytes from the i/o buffer and stores
   * the result in the ByteBuffer
   * If using separate read/write threads, call by reader.
   *
   * @param buf is the ByteBuffer
   * @return the number of bytes read
   */
  public int readByteBuffer(ByteBuffer buf) {
    if (read_char_buf_ != null) {
      throw new IllegalStateException("Cannot read from the byte buffer: " +
                                      "character data has already been read.");
    }

    int bytesRead = 0;
    int targetRemain = buf.remaining();
    while (targetRemain > 0) {
      ByteBuffer source = getReadBuffer();
      if (source == null) {
        break;
      }
      int sourceRemain = source.remaining();
      if (sourceRemain <= targetRemain) {
        buf.put(source);
        bytesRead += sourceRemain;
      } else {
        int oldLimit = source.limit();
        source.limit(source.position() + targetRemain);
        buf.put(source);
        bytesRead += targetRemain;
        source.limit(oldLimit);
      }
      targetRemain = buf.remaining();
      releaseReadBuffer();
    }
    return bytesRead;
  }

  /**
   * If using separate read/write threads, call by reader.
   * Skips up to n bytes from the i/o buffer. The actual
   * number of bytes skipped is returned. This will be the minimum
   * of the number of bytes remaining in the i/o buffer and n.
   * If using separate read/write threads, call by reader.
   *
   * @param n the number of bytes to skip
   * @return the number of bytes actually skipped
   */
  public long skipBytes(long n) {
    if (read_char_buf_ != null) {
      throw new IllegalStateException("Cannot skip bytes: character data " +
                                      "has already been read.");
    }

    long c = n;
    while (c > 0) {
      ByteBuffer byte_buf = getReadBuffer();
      if (byte_buf == null) {
        break;
      }
      int remain = byte_buf.remaining();
      if (remain > c) {
        remain = (int) c;
      }
      byte_buf.position(byte_buf.position() + remain);
      c -= remain;

      releaseReadBuffer();
    }
    return n - c;
  }

  /**
   * Returns the number of bytes remaining to be read from the
   * i/o buffer. Note that this does not return the number of
   * bytes in any current write buffers, if any of the
   * write methods were invoked. Call the <code>flush()</code>
   * method to make any buffered writes available for reading.
   * Safe for reader and writer.
   *
   * @return the number of available bytes for reading
   */
  public int availableBytes() {
    int avail = 0;

    // count the current read buf
    if (read_buf_ != null) {
      avail += read_buf_.remaining();
    }

    // all of the ByteBuffers on the bufs list are in write mode
    synchronized (bufs_) {
      for (ByteBuffer byte_buf : bufs_) {
        avail += byte_buf.position();
      }
    }
    return avail;
  }

  /**
   * Returns the total count of bytes, including any bytes in
   * the current byte write buffer. This method does not, however
   * count the number of bytes in any character write buffer.
   */
  public int totalBytes() {
    int avail = availableBytes();
    if (write_buf_ != null) {
      avail += write_buf_.position();
    }
    return avail;
  }

  /**
   * Returns <code>true</code> if the IOBuffer is empty.
   * That is, no data in any read or write buffers.
   *
   * @return whether there is any data in the IOBuffer
   */
  public boolean isEmpty() {
    if (read_buf_ != null && read_buf_.remaining() > 0) {
      return false;
    }
    if (read_char_buf_ != null && read_char_buf_.remaining() > 0) {
      return false;
    }
    if (write_buf_ != null && write_buf_.position() > 0) {
      return false;
    }
    if (write_char_buf_ != null && write_char_buf_.position() > 0) {
      return false;
    }
    synchronized (bufs_) {
      for (ByteBuffer byte_buf : bufs_) {
        if (byte_buf.position() > 0) {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * Writes the specified character to the i/o buffer. The
   * Character encoding must be set before invoking this
   * method.
   * If using separate read/write threads, call by writer.
   *
   * @param ch is the character to write
   * @exception IOException
   */
  public void write(int ch) throws IOException {
    CharBuffer char_buf = getWriteCharBuffer();
    char_buf.put((char) ch);
  }

  /**
   * Writes the supplied character buffer to the i/o buffer.
   * The character encoding must be set before invoking this
   * method.
   * If using separate read/write threads, call by writer.
   *
   * @param buf is the source character array
   * @exception IOException
   */
  public void write(char[] buf) throws IOException {
    write(buf, 0, buf.length);
  }

  /**
   * Writes the supplied character buffer to the i/o buffer
   * starting from the specified index (offset) up to a maximum
   * of count characters. The character encoding must be set
   * before invoking this method.
   * If using separate read/write threads, call by writer.
   *
   * @param buf is the source character array
   * @param offset is the starting index
   * @param count is the number of characters to write
   * @exception IOException
   */
  public void write(char[] buf, int offset, int count)
    throws IOException {
    CharBuffer char_buf = getWriteCharBuffer();

    while (count > 0) {
      if (!char_buf.hasRemaining()) {
        char_buf = getWriteCharBuffer();
      }
      int remain = char_buf.remaining();
      if (remain > count) {
        remain = count;
      }
      char_buf.put(buf, offset, remain);
      count -= remain;
      offset += remain;
    }
  }

  /**
   * Writes the specified byte to the i/o buffer.
   * This method should not be mixed with calls to any
   * of the IOBuffer character <code>write</code> methods.
   * If using separate read/write threads, call by writer.
   *
   * @param b is the byte to write to the buffer
   * @exception IOException
   */
  public void writeByte(int b) throws IOException {
    if (write_char_buf_ != null) {
      throw new IllegalStateException("Cannot write to the byte buffer: " +
                                      "character data has already been " +
                                      "written.");
    }
    ByteBuffer byte_buf = getWriteBuffer();
    byte_buf.put((byte) b);
  }

  /**
   * Writes the supplied byte array to the i/o buffer.
   * This method should not be mixed with calls to any
   * of the IOBuffer character <code>write</code> methods.
   * If using separate read/write threads, call by writer.
   *
   * @param buf is the source byte array
   * @exception IOException
   */
  public void writeBytes(byte[] buf) throws IOException {
    writeBytes(buf, 0, buf.length);
  }

  /**
   * Writes the supplied byte array to the i/o buffer starting
   * at the array index offset and continuing for count bytes.
   * This method should not be mixed with calls to any
   * of the IOBuffer character <code>write</code> methods.
   * If using separate read/write threads, call by writer.
   *
   * @param buf is the source byte array
   * @param offset is the starting index into the source
   * @param count is the number of bytes to write
   * @exception IOException
   */
  public void writeBytes(byte[] buf, int offset, int count)
    throws IOException {
    if (write_char_buf_ != null) {
      throw new IllegalStateException("Cannot write to the byte buffer: " +
                                      "character data has already been " +
                                      "written.");
    }

    while (count > 0) {
      ByteBuffer byte_buf = getWriteBuffer();
      int remain = byte_buf.remaining();
      if (remain > count) {
        remain = count;
      }
      byte_buf.put(buf, offset, remain);
      count -= remain;
      offset += remain;
    }
  }

  /**
   * Returns the current contents of the IOBuffer as a string.
   * Safe for reader and writer.
   *
   * @return the string representation of the IOBuffer
   */
  public String toString() {
    String result = "";
    String encoding = getCharacterEncoding();
    if (encoding == null) {
      encoding = "US-ASCII";
    }

    try {
      // append char buffer
      if (read_char_buf_ != null) {
        int offset = read_char_buf_.arrayOffset() + read_char_buf_.position();
        int length = read_char_buf_.length();
        result += new String(read_char_buf_.array(), offset, length);
      }
      // append read buffer
      if (read_buf_ != null) {
        int offset = read_buf_.arrayOffset() + read_buf_.position();
        int length = read_buf_.limit() - read_buf_.position();
        result += new String(read_buf_.array(), offset, length, encoding);
      }

      // append all write buffers
      synchronized (bufs_) {
        for (ByteBuffer byte_buf : bufs_) {
          int offset = byte_buf.arrayOffset();
          int length = byte_buf.position();
          result += new String(byte_buf.array(), offset, length, encoding);
        }
      }

      // append current char write buffer
      if (write_char_buf_ != null) {
        int offset = write_char_buf_.arrayOffset();
        int length = write_char_buf_.position();
        result += new String(write_char_buf_.array(), offset, length);
      }
      // append current write buffer
      if (write_buf_ != null) {
        int offset = write_buf_.arrayOffset();
        int length = write_buf_.position();
        result += new String(write_buf_.array(), offset, length, encoding);
      }
    } catch (UnsupportedEncodingException e) {
      return "unable to convert IOBuffer to string: " + e.getMessage();
    }
    return result;
  }

  /**
   * Returns the current write buffer. This buffer is used to store
   * bytes on invocations of the write methods.
   * If using separate read/write threads, call by writer.
   *
   * @return the current write buffer.
   * @exception IOException
   */
  public ByteBuffer getWriteBuffer() throws IOException {
    // return the current write buffer
    if ((write_buf_ != null) && write_buf_.hasRemaining()) {
      return write_buf_;
    } else if (write_buf_ != null) {
      flushWriteBuffer();
    }

    write_buf_ = allocateBuffer();
    write_buf_.clear();
    return write_buf_;
  }

  /**
   * Returns the current read buffer. This buffer is used to
   * read bytes on calls to readByte, readBytes(byte[]), and
   * readBytes(byte[],int,int). Note that this method will not
   * return the contents of any current write buffer. The
   * <code>flush()</code> method must be called manually to
   * ensure that all written data is available for reading.
   * If using separate read/write threads, call by reader.
   *
   * @return the current read buffer, or null if no more bytes are
   *         available for reading.
   */
  public ByteBuffer getReadBuffer() {
    if ((read_buf_ != null) && read_buf_.hasRemaining()) {
      ensureMinReaderSize();
      return read_buf_;
    } else if (read_buf_ != null) {
      read_buf_ = null;
    }

    synchronized (bufs_) {
      if (!bufs_.isEmpty()) {
        read_buf_ = bufs_.removeFirst();
        read_buf_.flip();
      }
    }

    ensureMinReaderSize();
    return read_buf_;
  }

  /**
   * Releases the read buffer if it has no data left to read. After
   * the buffer returned from getReadBuffer() has been modified, a
   * call should be made to releaseReadBuffer() to release the
   * reference to the read buffer (and free up memory) as soon as
   * possible.
   */
  public void releaseReadBuffer() {
    if ((read_buf_ != null) && !read_buf_.hasRemaining()) {
      read_buf_ = null;
    }
  }

  private void ensureMinReaderSize() {
    if (min_reader_size_ == 0 || read_buf_ == null ||
        read_buf_.capacity() >= min_reader_size_)
      return;
    ByteBuffer oldbuf = read_buf_;
    read_buf_ = ByteBuffer.allocate(min_reader_size_);
    read_buf_.put(oldbuf);
    read_buf_.flip();
  }

  /**
   * Returns a character buffer for writing character data.
   * The character encoding must be set before invoking this
   * method. The returned buffer is guaranteed to have at least
   * one character remaining.
   * If using separate read/write threads, call by writer.
   *
   * @return a character buffer with at least one character
   *         available.
   * @exception IOException
   */
  public CharBuffer getWriteCharBuffer() throws IOException {
    if (write_char_buf_ == null) {
      int alloc_size = (size_limit_ == UNBOUNDED) ?
                       1024 : Math.min(1024, size_limit_);
      write_char_buf_ = CharBuffer.allocate(alloc_size);
    } else if (write_char_buf_.hasRemaining()) {
      return write_char_buf_;
    } else {
      flushWriteCharBuffer(false);
    }

    return write_char_buf_;
  }

  /**
   * Returns a decoded character buffer. Before this method is
   * called, the character encoding must be set.
   * If using separate read/write threads, call by reader.
   *
   * @return a decoded character buffer for reading
   */
  public CharBuffer getReadCharBuffer() {
    if (char_decoder_ == null) {
      throw new IllegalStateException("No encoding has been set. Must call " +
                                      "setCharacterEncoding() " +
                                      "before reading");
    }

    if (read_char_buf_ == null) {
      read_char_buf_ = CharBuffer.allocate(1024);
    } else if (read_char_buf_.hasRemaining()) {
      return read_char_buf_;
    }

    // decode byte buffers into character buffer
    read_char_buf_.clear();
    decode(read_char_buf_);
    read_char_buf_.flip();

    if (!read_char_buf_.hasRemaining()) {
      return null;
    }
    return read_char_buf_;
  }

  /**
   * Allocates a new ByteBuffer. The size must be chosen carefully to handle the
   * cases of extremely large inputs and small, slowly streamed inputs.
   *
   * <p>This is accomplished based on the size of the previous write
   * buffer.  This size, which represents how much data was written to
   * the most recently used write buffer, is compared to the
   * capacity. If the last buffer was filled, then the next buffer's
   * size is a multiple of two. This will result in log2(X) allocs for
   * large inputs. If the last buffer was filled less than 1/2, and we
   * did not just increase the buffer size, the next buffer's size is
   * decreased by a factor of 2.  Anywhere from 1/2-full is left at
   * the same capacity. The allocation is bounded with reasonable min
   * and max values and by any size limit imposed on the IOBuffer via
   * a call to <code>setSizeLimit</code>.</p>
   */
  private ByteBuffer allocateBuffer() {
    ByteBuffer buf;
    int minSize = min_buffer_size_;

    if (num_bytes_written_to_last_buffer_ > 0) {
      if (!recent_grow_ &&
          num_bytes_written_to_last_buffer_ <= (buffer_size_ / 2)) {
        buffer_size_ >>= 1;
      } else if (num_bytes_written_to_last_buffer_ ==
                 buffer_size_ + ALLOC_EXTRA) {
        buffer_size_ <<= 1;
        recent_grow_ = true;
      } else {
        recent_grow_ = false;
      }
      buffer_size_ = Math.max(buffer_size_, minSize);
      buffer_size_ = Math.min(buffer_size_, MAX_BUFFER_SIZE);
    }

    if (size_limit_ != UNBOUNDED && size_limit_ < buffer_size_) {
      buffer_size_ = size_limit_;
    }

    if (min_buffer_size_ != 0 && minSize > buffer_size_) {
      buffer_size_ = minSize;
    }

    // We actually make the buffer slightly larger than 2^k, so we can
    // efficiently handle writers that repeatedly write 2^k bytes at a
    // time, flushing in between each write.

    buf = ByteBuffer.allocate(buffer_size_ + ALLOC_EXTRA);

    // log and update stat
    if (logger_.isLoggable(Level.FINEST)) {
      logger_.finest("allocating " + (buffer_size_ + ALLOC_EXTRA) +
                     " byte input buffer last byte buf size: " +
                     num_bytes_written_to_last_buffer_);
    }

    return buf;
  }

  /**
   * Flushes the contents of the current write buffer by adding it to
   * the bufs_ list.
   * @exception IOException is thrown on a consume callback failure
   *            (see comments at top of document)
   */
  private void flushWriteBuffer() throws IOException {
    num_bytes_written_to_last_buffer_ = write_buf_.position();
    if (write_buf_ == null) {
      throw new IllegalStateException("No write buffer is available to flush");
    }
    synchronized (bufs_) {
      bufs_.addLast(write_buf_);
    }
    write_buf_ = null;

    // check whether the recent flush pushed the IOBuffer over its limit
    checkSizeLimit();
  }

  /**
   * Flushes the contents of the current character write buffer.
   * This encodes the contents into byte buffers acquired through
   * calls to {@link #getWriteBuffer()}
   *
   * @param end_of_input <code>true</code> to specify that no
   *        further characters will be written to the buffer.
   * @exception IOException is thrown on a consume callback failure
   *            (see comments at top of document)
   */
  private void flushWriteCharBuffer(boolean end_of_input) throws IOException {
    if (char_encoder_ == null) {
      throw new IllegalStateException("No encoding has been set. Must call " +
                                      "setCharacterEncoding() " +
                                      "before flushing buffers.");
    }
    write_char_buf_.flip();

    // encode the characters
    encode(write_char_buf_, end_of_input);
    if (write_char_buf_.hasRemaining()) {
      throw new RuntimeException(
          "encode() should consume the entire write_char_buf_");
    }
    if (end_of_input) {
      char_encoder_.reset();
      write_char_buf_ = null;
    } else {
      write_char_buf_.clear();
    }

    // check whether the recent flush pushed the IOBuffer over its limit
    checkSizeLimit();
  }

  /**
   * Checks whether the current IOBuffer size exceeds its limit. If so,
   * any registered consume callback is called.
   *
   * @exception IOException is thrown on failure in the consume callback
   */
  private void checkSizeLimit() throws IOException {
    // if the consume callback is not null and the current IOBuffer
    // size exceeds the limit, invoke the callback
    if (size_limit_ != UNBOUNDED &&
        availableBytes() >= size_limit_) {
      consume();
    }
  }

  /**
   * Encodes bytes from the supplied character buffer into
   * the IOBuffer's list of byte buffers. This routine handles
   * encoding underflow and overflow correctly.
   *
   * @param write_char_buf the input buffer of characters to encode
   * @param end_of_input indicates no further characters will be encoded.
   * @exception IOException is thrown on a consume callback failure
   *            (see comments at top of document)
   */
  private void encode(CharBuffer write_char_buf, boolean end_of_input)
    throws IOException {
    CoderResult res;
    do {
      ByteBuffer byte_buf = getWriteBuffer();
      CharBuffer char_buf;

      // If there is leftover underflow use that instead.
      if (underflow_char_buf_ != null && underflow_char_buf_.hasRemaining()) {
        // Add the next char from the write char buf, if available.
        if (write_char_buf.hasRemaining()) {
          underflow_char_buf_.put(write_char_buf.get());
        }
        underflow_char_buf_.flip();
        char_buf = underflow_char_buf_;
      } else {
        char_buf = write_char_buf;
      }

      res = char_encoder_.encode(char_buf, byte_buf, end_of_input);
      if (res == CoderResult.OVERFLOW) {
        flushWriteBuffer();
      } else if (res == CoderResult.UNDERFLOW && char_buf.hasRemaining()) {
        // Create new underflow buf containing the remaining chars and room
        // for one more char. The longest sequence of characters that can
        // represent a single code point is 2 (a pair of surrogate chars).
        underflow_char_buf_ = CharBuffer.allocate(char_buf.remaining() + 1);
        // Copy the characters over to the underflow_char_buf_. Note that
        // calling underflow_char_buf_.append(char_buf) is not correct because
        // it does not properly advance the position of char_buf.
        while (char_buf.hasRemaining()) {
          underflow_char_buf_.put(char_buf.get());
        }
      }
    } while (write_char_buf.hasRemaining());
  }

  /**
   * Decodes bytes from the IOBuffer's list of byte buffers
   * and into the supplied character buffer. This routine
   * handles decoding underflow and overflow correctly.
   *
   * @param char_buf the output buffer for decoded characters
   */
  private void decode(CharBuffer char_buf) {
    // loop until the read char buffer is filled or the
    // byte buffer input is exhausted
    CoderResult res;
    do {
      ByteBuffer read_buf = getReadBuffer();
      ByteBuffer byte_buf;
      boolean    end_of_input;

      if (underflow_buf_ != null && underflow_buf_.hasRemaining()) {
        byte_buf = underflow_buf_;
        end_of_input = (read_buf == null);
      } else {
        if (read_buf == null) {
          char_decoder_.reset();
          break;
        }
        byte_buf = read_buf;
        end_of_input = (availableBytes() == read_buf.remaining());
      }

      res = char_decoder_.decode(byte_buf, char_buf, end_of_input);
      if (res == CoderResult.UNDERFLOW && byte_buf.hasRemaining()) {
        // create underflow buffer to hold remaining bytes + pulled byte
        underflow_buf_ = ByteBuffer.allocate(byte_buf.remaining() + 1);
        while (byte_buf.hasRemaining()) {
          underflow_buf_.put(byte_buf.get());
        }
        // pull a byte from the next read buf
        if ((read_buf = getReadBuffer()) != null) {
          underflow_buf_.put(read_buf.get());
        }
        underflow_buf_.flip();
      }
    } while (res != CoderResult.OVERFLOW);
  }
}

