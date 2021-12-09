package com.adventofcode.input;

public class BinaryInput {
    final byte[] data;

    public BinaryInput(String input) {
        data = new byte[input.length()];
        byte[] bytes = input.getBytes();
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (bytes[i] == '0' ? 0 : 1);
        }
    }

    public BinaryInput(byte[] data) {
        this.data = data;
    }

    public int length() {
        return data.length;
    }

    public boolean isZero(int index) {
        return data[index] == (byte) 0;
    }

    public boolean isOne(int index) {
        return !isZero(index);
    }

    public int value() {
        int result = 0;
        for (int i = 0; i < length(); i++) {
            if (isOne(i)) {
                result += (int) Math.pow(2, length() - i - 1);
            }
        }
        return result;
    }

    public BinaryInput inverse() {
        byte[] newData = new byte[this.data.length];
        for (int i = 0; i < this.data.length; i++) {
            byte datum = this.data[i];
            byte b = (byte) 1;
            newData[i] = ((byte) (b - datum));
        }
        return new BinaryInput(newData);
    }
}
