package com.github.ompc.robot.hexapod.dragoon.device.impl;

import com.pi4j.io.serial.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;

public class MockPiSerial implements Serial {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void open(String device, int baud, int dataBits, int parity, int stopBits, int flowControl) throws IOException {

    }

    @Override
    public void open(String device, int baud) throws IOException {

    }

    @Override
    public void open(String device, Baud baud, DataBits dataBits, Parity parity, StopBits stopBits, FlowControl flowControl) throws IOException {

    }

    @Override
    public void open(SerialConfig serialConfig) throws IOException {

    }

    @Override
    public void close() throws IllegalStateException, IOException {

    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void flush() throws IllegalStateException, IOException {

    }

    @Override
    public void discardInput() throws IllegalStateException, IOException {

    }

    @Override
    public void discardOutput() throws IllegalStateException, IOException {

    }

    @Override
    public void discardAll() throws IllegalStateException, IOException {

    }

    @Override
    public void sendBreak(int duration) throws IllegalStateException, IOException {

    }

    @Override
    public void sendBreak() throws IllegalStateException, IOException {

    }

    @Override
    public void setBreak(boolean enabled) throws IllegalStateException, IOException {

    }

    @Override
    public void setRTS(boolean enabled) throws IllegalStateException, IOException {

    }

    @Override
    public void setDTR(boolean enabled) throws IllegalStateException, IOException {

    }

    @Override
    public boolean getRTS() throws IllegalStateException, IOException {
        return false;
    }

    @Override
    public boolean getDTR() throws IllegalStateException, IOException {
        return false;
    }

    @Override
    public boolean getCTS() throws IllegalStateException, IOException {
        return false;
    }

    @Override
    public boolean getDSR() throws IllegalStateException, IOException {
        return false;
    }

    @Override
    public boolean getRI() throws IllegalStateException, IOException {
        return false;
    }

    @Override
    public boolean getCD() throws IllegalStateException, IOException {
        return false;
    }

    @Override
    public void addListener(SerialDataEventListener... listener) {

    }

    @Override
    public void removeListener(SerialDataEventListener... listener) {

    }

    @Override
    public int getFileDescriptor() {
        return 0;
    }

    @Override
    public InputStream getInputStream() {
        return null;
    }

    @Override
    public OutputStream getOutputStream() {
        return null;
    }

    @Override
    public boolean isBufferingDataReceived() {
        return false;
    }

    @Override
    public void setBufferingDataReceived(boolean enabled) {

    }

    @Override
    public int available() throws IllegalStateException, IOException {
        return 0;
    }

    @Override
    public void discardData() throws IllegalStateException, IOException {

    }

    @Override
    public byte[] read() throws IllegalStateException, IOException {
        return new byte[0];
    }

    @Override
    public byte[] read(int length) throws IllegalStateException, IOException {
        return new byte[0];
    }

    @Override
    public void read(ByteBuffer buffer) throws IllegalStateException, IOException {

    }

    @Override
    public void read(int length, ByteBuffer buffer) throws IllegalStateException, IOException {

    }

    @Override
    public void read(OutputStream stream) throws IllegalStateException, IOException {

    }

    @Override
    public void read(int length, OutputStream stream) throws IllegalStateException, IOException {

    }

    @Override
    public void read(Collection<ByteBuffer> collection) throws IllegalStateException, IOException {

    }

    @Override
    public void read(int length, Collection<ByteBuffer> collection) throws IllegalStateException, IOException {

    }

    @Override
    public CharBuffer read(Charset charset) throws IllegalStateException, IOException {
        return null;
    }

    @Override
    public CharBuffer read(int length, Charset charset) throws IllegalStateException, IOException {
        return null;
    }

    @Override
    public void read(Charset charset, Writer writer) throws IllegalStateException, IOException {

    }

    @Override
    public void read(int length, Charset charset, Writer writer) throws IllegalStateException, IOException {

    }

    @Override
    public void write(byte[] data, int offset, int length) throws IllegalStateException, IOException {

    }

    @Override
    public void write(byte... data) throws IllegalStateException, IOException {

    }

    @Override
    public void write(byte[]... data) throws IllegalStateException, IOException {

    }

    @Override
    public void write(ByteBuffer... datas) throws IllegalStateException {
        Arrays.stream(datas).forEach(data -> {
            final byte[] dataArray = new byte[data.remaining()];
            data.get(dataArray);
            logForWrite(dataArray);
        });
    }

    private void logForWrite(byte[] dataArray) {
        final StringBuilder dataSB = new StringBuilder("|");
        for (byte data : dataArray) {
            dataSB.append(String.format("0x%02x", data)).append("|");
        }
        logger.info("piSerial-write:{}", dataSB);
    }

    @Override
    public void write(InputStream input) throws IllegalStateException, IOException {

    }

    @Override
    public void write(Charset charset, char[] data, int offset, int length) throws IllegalStateException, IOException {

    }

    @Override
    public void write(Charset charset, char... data) throws IllegalStateException, IOException {

    }

    @Override
    public void write(char... data) throws IllegalStateException, IOException {

    }

    @Override
    public void write(Charset charset, CharBuffer... data) throws IllegalStateException, IOException {

    }

    @Override
    public void write(CharBuffer... data) throws IllegalStateException, IOException {

    }

    @Override
    public void write(Charset charset, CharSequence... data) throws IllegalStateException, IOException {

    }

    @Override
    public void write(CharSequence... data) throws IllegalStateException, IOException {

    }

    @Override
    public void write(Charset charset, Collection<? extends CharSequence> data) throws IllegalStateException, IOException {

    }

    @Override
    public void write(Collection<? extends CharSequence> data) throws IllegalStateException, IOException {

    }

    @Override
    public void writeln(Charset charset, CharSequence... data) throws IllegalStateException, IOException {

    }

    @Override
    public void writeln(CharSequence... data) throws IllegalStateException, IOException {

    }

    @Override
    public void writeln(Charset charset, Collection<? extends CharSequence> data) throws IllegalStateException, IOException {

    }

    @Override
    public void writeln(Collection<? extends CharSequence> data) throws IllegalStateException, IOException {

    }
}
